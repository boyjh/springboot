package com.xwbing.util;

import com.xwbing.exception.UtilException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author xiangwb
 * @date 20/2/17 20:34
 */
@Slf4j
public class IoUtil {
    /**
     * 文件转换byte字节数组
     *
     * @param file
     * @return
     */
    public static byte[] toByte(File file) {
        try (FileInputStream fis = new FileInputStream(file); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] data = new byte[fis.available()];
            int len;
            while ((len = fis.read(data)) != -1) {
                bos.write(data, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new UtilException("文件转化错误");
        }
    }

    /**
     * 获取网络图片信息
     *
     * @param pic
     * @return
     * @throws IOException
     */
    private byte[] readUrlPic(String pic) throws IOException {
        HttpURLConnection connection = null;
        InputStream inStream = null;
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
            URL url = new URL(pic);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            inStream = connection.getInputStream();
            byte[] buffer = new byte[inStream.available()];
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            return outStream.toByteArray();
        } finally {
            if (inStream != null) {
                inStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
