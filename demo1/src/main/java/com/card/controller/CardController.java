package com.card.controller;

import com.card.model.Card;
import com.card.po.CardPO;
import com.card.po.UserPO;
import com.card.service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/card")
public class CardController {
    @Resource
    private CardService cardService;

    // 名片列表（系统主页）
    @GetMapping("/list")
    public String listCard(HttpSession session, Model model) {
        UserPO loginUser = (UserPO) session.getAttribute("loginUser");
        List<CardPO> cardList = cardService.listCard(loginUser.getId());
        model.addAttribute("cardList", cardList);
        return "main";
    }

    // 跳转到添加名片页
    @GetMapping("/add")
    public String toAddCard(Model model) {
        model.addAttribute("card", new Card());
        return "addCard";
    }

    // 添加名片提交
    @PostMapping("/add")
    public String addCard(@Valid Card card, BindingResult result, HttpSession session, Model model) {
        if (result.hasErrors()) {
            return "addCard";
        }
        UserPO loginUser = (UserPO) session.getAttribute("loginUser");
        card.setUserId(loginUser.getId());
        boolean success = cardService.addCard(card);
        if (success) {
            return "redirect:/card/list";
        } else {
            model.addAttribute("addError", "添加失败，请重试");
            return "addCard";
        }
    }

    // 名片详情/修改跳转
    @GetMapping("/detail")
    public String detailCard(@RequestParam Integer id, @RequestParam String act, HttpSession session, Model model) {
        UserPO loginUser = (UserPO) session.getAttribute("loginUser");
        // 1. 查询数据库获取CardPO
        CardPO cardPO = cardService.getCardById(id, loginUser.getId());

        // 2. 将CardPO转换为Card对象（因为Card类有logoFile属性）
        Card card = new Card();
        card.setId(cardPO.getId());
        card.setName(cardPO.getName());
        card.setTelephone(cardPO.getTelephone());
        card.setEmail(cardPO.getEmail());
        card.setCompany(cardPO.getCompany());
        card.setPost(cardPO.getPost());
        card.setAddress(cardPO.getAddress());
        card.setLogoName(cardPO.getLogoName()); // 保留原logo路径
        card.setUserId(cardPO.getUserId());

        // 3. 传递Card对象给模板
        model.addAttribute("card", card);

        // 根据操作类型跳转页面
        if ("detail".equals(act)) {
            return "detailCard";
        } else if ("update".equals(act)) {
            return "updateCard";
        }
        return "redirect:/card/list";
    }

    // 修改名片提交
    @PostMapping("/update")
    public String updateCard(@Valid Card card, BindingResult result, HttpSession session, Model model) {
        if (result.hasErrors()) {
            return "updateCard";
        }
        UserPO loginUser = (UserPO) session.getAttribute("loginUser");
        card.setUserId(loginUser.getId());
        boolean success = cardService.updateCard(card);
        if (success) {
            return "redirect:/card/list";
        } else {
            model.addAttribute("updateError", "修改失败，请重试");
            return "updateCard";
        }
    }



    // 修改请求方式为POST
    @PostMapping("/delete") // 从@GetMapping改为@PostMapping
    @ResponseBody

    public boolean deleteCard(@RequestParam Integer id, HttpSession session) {
        UserPO loginUser = (UserPO) session.getAttribute("loginUser");
        // 验证用户权限（确保只能删除自己的名片）
        if (loginUser == null) {
            return false;
        }
        // 先查询名片是否存在且属于当前用户
        CardPO card = cardService.getCardById(id, loginUser.getId());
        if (card == null) {
            return false;
        }
        // 执行删除（包含可能的文件删除逻辑）
        return cardService.deleteCard(id, loginUser.getId());
    }
}