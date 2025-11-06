package com.card.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

@Controller
public class ValidateCodeController {
    @Resource
    private DefaultKaptcha defaultKaptcha;

    @GetMapping("/validateCode")
    public void getValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 生成验证码文本
        String code = defaultKaptcha.createText();
        // 存入Session
        HttpSession session = request.getSession();
        session.setAttribute("validateCode", code);
        // 生成验证码图片
        BufferedImage image = defaultKaptcha.createImage(code);
        // 响应图片
        response.setContentType("image/jpeg");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "jpeg", os);
        os.flush();
        os.close();
    }
}