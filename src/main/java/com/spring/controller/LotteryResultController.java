package com.spring.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.dto.LotteryResultDto;
import com.spring.service.LotteryResultService;

@Controller
public class LotteryResultController
{
    @Autowired
    private LotteryResultService lotteryResultService;

    @GetMapping
    public LotteryResultDto getLotteryResultsByDateAndLotteryId(Date lotteryDate, Long lotteryId) {
        return lotteryResultService.getLotteryResultByDateAndLotteryId(lotteryDate, lotteryId);
    }
}
