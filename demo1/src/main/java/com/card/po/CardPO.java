package com.card.po;

import lombok.Data;

@Data
public class CardPO {
    private Integer id;
    private String name;
    private String telephone;
    private String email;
    private String company;
    private String post;
    private String address;
    private String logoName;
    private Integer userId;  // 所属用户ID
}