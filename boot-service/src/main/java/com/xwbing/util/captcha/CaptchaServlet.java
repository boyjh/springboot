package com.xwbing.util.captcha;

import com.xwbing.constant.CommonConstant;
import com.xwbing.util.CommonDataUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 说明: 创建验证码的servlet
 * 创建日期: 2016年8月29日 上午10:59:27
 * 作者: xiangwbd
 */
public class CaptchaServlet extends HttpServlet {
    private static final long serialVersionUID = -124247581620199710L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) {
        // 设置相应类型,告诉浏览器输出的内容为图片
        res.setContentType("image/jpeg");
        // 禁止图像缓存。
        res.setHeader("Pragma", "No-cache");
        res.setHeader("Cache-Control", "no-cache");
        res.setDateHeader("Expire", 0);
        try {
            CaptchaUtil tool = new CaptchaUtil();
            StringBuffer code = new StringBuffer();
            BufferedImage image = tool.genRandomCodeImage(code);
//            HttpSession session = req.getSession();
//            session.removeAttribute(CommonConstant.KEY_CAPTCHA);
//            session.setAttribute(CommonConstant.KEY_CAPTCHA, code.toString());
            CommonDataUtil.setToken(CommonConstant.KEY_CAPTCHA, code.toString());
            // 将内存中的图片通过流形式输出到客户端
            OutputStream out = res.getOutputStream();
            ImageIO.write(image, "JPEG", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
