package com.card.service;

import com.card.model.User;
import com.card.po.UserPO;

public interface UserService {
    // 注册用户
    boolean register(User user);

    // 验证用户名是否已存在
    boolean checkUname(String uname);

    // 用户登录
    UserPO login(User user);

    boolean forceUpdatePwd(Integer userId, String newPwd);

    // 修改密码
    boolean updatePwd(Integer userId, String oldPwd, String newPwd);
}