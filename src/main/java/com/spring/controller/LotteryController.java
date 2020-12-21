package com.spring.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.exception.ResourceNotFoundException;
import com.spring.model.Lottery;
import com.spring.service.LotteryService;
import com.spring.service.LotteryTicketService;

@RestController
@RequestMapping("/lottery")
public class LotteryController
{
    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private LotteryTicketService lotteryTicketService;


    // at 12:00 AM every day
    @Scheduled(cron = "0 0 0 * * ?")
    @PostMapping("/endLotteryAndSelectRandomLotteryWinner/{lotteryId}")
    public void endLotteryAndSelectRandomLotteryWinner(@PathVariable("lotteryId") Long lotteryId) throws ResourceNotFoundException
    {
        if (!isEligibleLotteryId(lotteryId)) return;
        lotteryTicketService.endLotteryAndSelectLotteryWinner(lotteryId);
    }

    private boolean isEligibleLotteryId(Long lotteryId)
    {
        return !(lotteryId == null || lotteryId < 0);
    }

    @GetMapping("/activeLotteries")
    public List<Lottery> getActiveLotteries()
    {
        return lotteryService.getActiveLotteries();
    }
}
