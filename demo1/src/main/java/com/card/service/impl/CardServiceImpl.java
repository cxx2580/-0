package com.card.service.impl;

import com.card.mapper.CardMapper;
import com.card.model.Card;
import com.card.po.CardPO;
import com.card.service.CardService;
import com.card.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class CardServiceImpl implements CardService {
    @Resource
    private CardMapper cardMapper;

    @Resource
    private FileUtil fileUtil;

    @Override
    public List<CardPO> listCard(Integer userId) {
        return cardMapper.selectByUserId(userId);
    }

    @Override
    public CardPO getCardById(Integer id, Integer userId) {
        return cardMapper.selectById(id, userId);
    }

    @Override
    public boolean addCard(Card card) {
        CardPO cardPO = new CardPO();
        cardPO.setName(card.getName());
        cardPO.setTelephone(card.getTelephone());
        cardPO.setEmail(card.getEmail());
        cardPO.setCompany(card.getCompany());
        cardPO.setPost(card.getPost());
        cardPO.setAddress(card.getAddress());
        cardPO.setUserId(card.getUserId());

        // 处理Logo上传
        if (card.getLogoFile() != null && !card.getLogoFile().isEmpty()) {
            try {
                String logoPath = fileUtil.uploadFile(card.getLogoFile());
                cardPO.setLogoName(logoPath);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return cardMapper.insert(cardPO) > 0;
    }

    @Override
    public boolean updateCard(Card card) {
        CardPO cardPO = new CardPO();
        cardPO.setId(card.getId());
        cardPO.setName(card.getName());
        cardPO.setTelephone(card.getTelephone());
        cardPO.setEmail(card.getEmail());
        cardPO.setCompany(card.getCompany());
        cardPO.setPost(card.getPost());
        cardPO.setAddress(card.getAddress());
        cardPO.setUserId(card.getUserId());

        // 处理新Logo上传（如果有）
        if (card.getLogoFile() != null && !card.getLogoFile().isEmpty()) {
            try {
                String logoPath = fileUtil.uploadFile(card.getLogoFile());
                cardPO.setLogoName(logoPath);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            // 未上传新Logo，保留原Logo
            CardPO oldCard = cardMapper.selectById(card.getId(), card.getUserId());
            cardPO.setLogoName(oldCard.getLogoName());
        }

        return cardMapper.update(cardPO) > 0;
    }

    @Override
    public boolean deleteCard(Integer id, Integer userId) {
        return cardMapper.delete(id, userId) > 0;
    }
}