package com.card.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class User {
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 20, message = "用户名长度2-20位")
    private String uname;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度6-16位")
    private String upwd;

    private String email;
    private String newPwd;  // 新密码（修改密码用）
    private String code;  // 验证码
}