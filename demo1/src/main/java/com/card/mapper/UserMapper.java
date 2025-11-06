package com.card.mapper;

import com.card.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 已有的方法...
    UserPO selectByUname(String uname);
    int insert(UserPO userPO);
    int updatePwdById(UserPO userPO);

    // 新增：根据ID查询用户
    UserPO selectById(Integer id);


}