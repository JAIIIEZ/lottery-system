package com.spring.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.exception.ResourceNotFoundException;
import com.spring.model.Lottery;
import com.spring.model.LotteryResult;
import com.spring.service.LotteryResultService;
import com.spring.service.LotteryService;
import com.spring.service.LotteryTicketService;
import com.spring.utils.DateUtils;

@Controller
public class LotteryController
{
    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private LotteryTicketService lotteryTicketService;

    @Autowired
    private LotteryResultService lotteryResultService;

    // at 12:00 AM every day
    @Scheduled(cron="0 0 0 * * ?")
    @PostMapping
    public void endLotteryAndSelectRandomLotteryWinner(Lottery lottery) throws ResourceNotFoundException
    {
        Date yesterday = DateUtils.yesterday();
        lotteryService.endLotteryByDateAndId(yesterday, lottery.getId());
        Long winnerLotteryNumber = lotteryTicketService.selectRandomLotteryWinner(lottery.getId());
        lotteryResultService.saveLotteryResult(new LotteryResult(yesterday, lottery.getId(), winnerLotteryNumber));
        lotteryService.startLottery(lottery.getName());
    }
}
