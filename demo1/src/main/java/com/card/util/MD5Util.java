package com.card.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    // 盐值（增强安全性）
    private static final String SALT = "card_system_2025";

    // MD5加密（密码+盐值）
    public static String md5(String password) {
        return DigestUtils.md5Hex(password + SALT);
    }
}