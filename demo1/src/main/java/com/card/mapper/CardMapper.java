package com.card.mapper;

import com.card.po.CardPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CardMapper {
    // 根据用户ID查询所有名片
    List<CardPO> selectByUserId(Integer userId);

    // 根据ID查询名片
    CardPO selectById(@Param("id") Integer id, @Param("userId") Integer userId);

    // 添加名片
    int insert(CardPO cardPO);

    // 修改名片
    int update(CardPO cardPO);

    // 删除名片
    int delete(@Param("id") Integer id, @Param("userId") Integer userId);
}