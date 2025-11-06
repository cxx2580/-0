package com.card.po;

import lombok.Data;

@Data
public class UserPO {
    private Integer id;
    private String uname;
    private String upwd;
    private String email;
}