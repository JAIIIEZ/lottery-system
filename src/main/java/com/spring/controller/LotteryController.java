package com.spring.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.exception.ResourceNotFoundException;
import com.spring.model.Lottery;
import com.spring.model.LotteryResult;
import com.spring.service.LotteryResultService;
import com.spring.service.LotteryService;
import com.spring.service.LotteryTicketService;
import com.spring.utils.DateUtils;

@RestController("/lottery")
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
    @PostMapping("/startNextLottery")
    public void endLotteryAndSelectRandomLotteryWinner(Long lotteryId) throws ResourceNotFoundException
    {
        Date yesterday = DateUtils.yesterday();
        lotteryService.endLotteryById(lotteryId);
        Long winnerLotteryNumber = lotteryTicketService.selectRandomLotteryWinner(lotteryId);
        lotteryResultService.saveLotteryResult(new LotteryResult(yesterday, lotteryId, winnerLotteryNumber));
        Lottery lottery = lotteryService.findById(lotteryId);
        lotteryService.startLotteryByName(lottery.getName());
    }

    @GetMapping("/activeLotteries")
    public List<Lottery> getActiveLotteries() {
        return lotteryService.getActiveLotteries();
    }
}
