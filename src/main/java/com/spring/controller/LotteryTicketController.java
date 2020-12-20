package com.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.model.Lottery;
import com.spring.model.User;
import com.spring.service.LotteryTicketService;

@Controller
public class LotteryTicketController
{
    @Autowired
    LotteryTicketService lotteryTicketService;

    @PostMapping
    public boolean submitTicket(@ModelAttribute("userForm") User userForm, @ModelAttribute("lottery") Lottery lottery) {
        return lotteryTicketService.submitLotteryTicket(userForm.getId(), lottery.getId());
    }
}
