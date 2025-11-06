package com.card.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Card {
    private Integer id;

    @NotBlank(message = "客户姓名不能为空")
    private String name;

    @NotBlank(message = "联系电话不能为空")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    private String telephone;

    private String email;
    private String company;
    private String post;
    private String address;
    private MultipartFile logoFile;  // 上传的Logo文件
    private String logoName;  // 存储的Logo文件名
    private Integer userId;  // 所属用户ID
}