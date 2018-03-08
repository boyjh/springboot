package com.xwbing.demo;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.exception.UtilException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 作者: xiangwb
 * 说明: FileUploadDemo
 */
@Service
public class FileUploadDemo {
    public List<JSONObject> fileUpload(CommonsMultipartFile[] multipartFiles) {
        List<JSONObject> list = new ArrayList<>();
        JSONObject materialInfo;
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                materialInfo=new JSONObject();
                String originName = multipartFile.getOriginalFilename();//原始名字
                materialInfo.put("name",originName);
                String fileType = originName.substring(originName.lastIndexOf(".") + 1);//获取文件后缀
                materialInfo.put("type",fileType);
                byte[] data;
                try {
                    InputStream is = multipartFile.getInputStream();//文件输入流
                    data = new byte[1024 * 10];
                    is.read(data);
                    is.close();
                } catch (IOException e) {
                    throw new UtilException("读取数据错误");
                }
                String base64 = Base64.getEncoder().encodeToString(data);//对数据字节进行base64编码
                materialInfo.put("data",base64);
                list.add(materialInfo);
            }
        }
        return list;
    }
}
