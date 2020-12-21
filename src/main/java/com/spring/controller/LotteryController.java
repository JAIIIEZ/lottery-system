package com.spring.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSaveException;
import com.spring.model.Lottery;
import com.spring.service.LotteryService;
import com.spring.service.LotteryTicketService;

@RestController("/lottery")
public class LotteryController
{
    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private LotteryTicketService lotteryTicketService;


    // at 12:00 AM every day
    @Scheduled(cron="0 0 0 * * ?")
    @PostMapping("/startNextLottery")
    public void endLotteryAndSelectRandomLotteryWinner(Long lotteryId) throws ResourceNotFoundException, UnableToSaveException
    {
        if (isEligibleLotteryId(lotteryId)) return;
        lotteryTicketService.endLotteryAndSelectLotteryWinner(lotteryId);
    }

    private boolean isEligibleLotteryId(Long lotteryId)
    {
        if (lotteryId == null || lotteryId < 0) {
            return true;
        }
        return false;
    }

    @GetMapping("/activeLotteries")
    public List<Lottery> getActiveLotteries() {
        return lotteryService.getActiveLotteries();
    }
}
