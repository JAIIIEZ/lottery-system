package com.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSubmitLotteryTicket;
import com.spring.model.LotteryTicket;
import com.spring.service.LotteryTicketService;

@RestController
@RequestMapping("/lotteryTicket")
public class LotteryTicketController
{
    @Autowired
    private LotteryTicketService lotteryTicketService;

    @PostMapping("submitLottery/{lotteryId}/{username}")
    public LotteryTicket submitLottery(@PathVariable("lotteryId") Long lotteryId, @PathVariable("username") String username) throws ResourceNotFoundException, UnableToSubmitLotteryTicket
    {
        return lotteryTicketService.submitLotteryTicketSync(lotteryId, username);
    }
}
