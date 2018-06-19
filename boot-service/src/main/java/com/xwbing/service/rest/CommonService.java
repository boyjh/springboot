package com.xwbing.service.rest;

import com.xwbing.domain.entity.rest.FilesUpload;
import com.xwbing.domain.repository.FilesUploadRepository;
import com.xwbing.exception.BusinessException;
import com.xwbing.util.DigestsUtil;
import com.xwbing.util.RestMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/5/7 8:59
 * 作者: xiangwb
 * 说明:
 */
@Service
public class CommonService {
    @Resource
    private FilesUploadRepository uploadRepository;

    /**
     * 保存信息表单提交时获取校验签名
     *
     * @param request
     * @return
     */
    public String getSign(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sign = DigestsUtil.getSign();
        session.setAttribute("sign", sign);
        return sign;
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public RestMessage upload(CommonsMultipartFile file) {
        RestMessage result = new RestMessage();
        if (file == null) {
            throw new BusinessException("请选择文件");
        }
        FilesUpload filesUpload = new FilesUpload();
        //原始名字
        String originName = file.getOriginalFilename();
        filesUpload.setName(originName);
        //获取文件后缀名
        String fileType = originName.substring(originName.lastIndexOf(".") + 1);
        filesUpload.setType(fileType);
        byte[] data;
        try {
            InputStream is = file.getInputStream();
            data = new byte[is.available()];
            is.read(data);
            is.close();
        } catch (IOException e) {
            throw new BusinessException("读取数据错误");
        }
        //对数据字节进行base64编码
        String base64 = Base64.getEncoder().encodeToString(data);
        filesUpload.setData(base64);
        filesUpload.setCreateTime(new Date());
        FilesUpload save = uploadRepository.save(filesUpload);
        if (save != null) {
            result.setSuccess(true);
            result.setId(save.getId());
            result.setMessage("保存文件成功");
        } else {
            result.setMessage("保存文件失败");
        }
        return result;
    }
}
