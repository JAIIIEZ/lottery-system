package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.exception.LotteryAlreadyPassiveException;
import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSaveException;
import com.spring.model.Lottery;
import com.spring.model.ResponseTransfer;
import com.spring.service.LotteryService;

@RestController
@RequestMapping("/lottery")
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    // at 12:00 AM every day
    @Scheduled(cron = "0 0 0 * * ?")
    @PostMapping("/endActiveLotteriesAndSelectWinnersMidnight")
    public void endLotteryAndSelectRandomLotteryWinner() throws ResourceNotFoundException {
        lotteryService.endActiveLotteriesAndSelectLotteryWinners();
    }

    @PostMapping("/endLotteryAndSelectRandomLotteryWinner/{lotteryId}")
    public ResponseTransfer endLotteryAndSelectRandomLotteryWinner(@PathVariable("lotteryId") Long lotteryId) throws ResourceNotFoundException, LotteryAlreadyPassiveException {
        if (!isEligibleLotteryId(lotteryId)) {
            throw new ResourceNotFoundException("Lottery id not found :: " + lotteryId);
        }
        lotteryService.endLotteryAndSelectLotteryWinner(lotteryId);
        return new ResponseTransfer("Lottery ended successfully!");
    }

    private boolean isEligibleLotteryId(Long lotteryId) {
        return !(lotteryId == null || lotteryId < 0);
    }

    @GetMapping(value = "/activeLotteries", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Lottery> getActiveLotteries() {
        return lotteryService.getActiveLotteries();
    }

    @PostMapping(value = "/startLottery")
    public Lottery startLottery(@RequestParam("lotteryName") String lotteryName) throws UnableToSaveException {
        return lotteryService.startLotteryByName(lotteryName);
    }
}
