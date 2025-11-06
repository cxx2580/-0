package com.card.service.impl;

import com.card.mapper.UserMapper;
import com.card.model.User;
import com.card.po.UserPO;
import com.card.service.UserService;
import com.card.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean register(User user) {
        // 密码MD5加密
        String md5Pwd = MD5Util.md5(user.getUpwd());
        UserPO userPO = new UserPO();
        userPO.setUname(user.getUname());
        userPO.setUpwd(md5Pwd);
        userPO.setEmail(user.getEmail());
        // 插入数据库
        return userMapper.insert(userPO) > 0;
    }

    @Override
    public boolean checkUname(String uname) {
        // 查询用户名是否存在
        UserPO userPO = userMapper.selectByUname(uname);
        return userPO != null;
    }

    @Override
    public UserPO login(User user) {
        // 密码加密后与数据库比对
        String md5Pwd = MD5Util.md5(user.getUpwd());
        UserPO userPO = userMapper.selectByUname(user.getUname());
        if (userPO != null && userPO.getUpwd().equals(md5Pwd)) {
            return userPO;
        }
        return null;
    }

    @Override
    public boolean updatePwd(Integer userId, String oldPwd, String newPwd) {
        // 1. 查询用户信息（通过ID获取用户）
        UserPO userPO = userMapper.selectById(userId);

        // 2. 验证旧密码（加密后比对）
/*
        String encryptedOldPwd = MD5Util.md5(oldPwd);
        if (!encryptedOldPwd.equals(userPO.getUpwd())) {
            return false; // 旧密码错误
        }
*/

        // 3. 加密新密码，并设置到 UserPO 中
        userPO.setUpwd(MD5Util.md5(newPwd));
        // 确保 UserPO 的 id 已设置（用于更新条件）
        userPO.setId(userId);

        // 4. 调用 Mapper 方法更新密码（传入 UserPO 对象）
        return userMapper.updatePwdById(userPO) > 0;
    }


    // UserServiceImpl.java 实现类
    @Override
    public boolean forceUpdatePwd(Integer userId, String newPwd) {
        // 1. 加密新密码（和注册时保持一致，比如MD5）
        String encryptedNewPwd = MD5Util.md5(newPwd); // 如果注册时没加密，直接用newPwd

        // 2. 创建UserPO对象，设置ID和新密码
        UserPO userPO = new UserPO();
        userPO.setId(userId);
        userPO.setUpwd(encryptedNewPwd);

        // 3. 直接调用Mapper更新
        return userMapper.updatePwdById(userPO) > 0;
    }
}