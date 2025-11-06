package com.card.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 验证码宽度
        properties.setProperty("kaptcha.image.width", "120");
        // 验证码高度
        properties.setProperty("kaptcha.image.height", "40");
        // 验证码字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // 验证码字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");
        // 验证码字符集
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 干扰线样式
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}