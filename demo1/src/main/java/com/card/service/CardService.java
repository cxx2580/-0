package com.card.service;

import com.card.model.Card;
import com.card.po.CardPO;

import java.util.List;

public interface CardService {
    // 查询当前用户的所有名片
    List<CardPO> listCard(Integer userId);

    // 根据ID查询名片
    CardPO getCardById(Integer id, Integer userId);

    // 添加名片
    boolean addCard(Card card);

    // 修改名片
    boolean updateCard(Card card);

    // 删除名片
    boolean deleteCard(Integer id, Integer userId);
}