package com.spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.exception.ResourceNotFoundException;
import com.spring.model.LotteryResult;
import com.spring.service.LotteryResultService;

@RestController
@RequestMapping("/lotteryResult")
public class LotteryResultController
{
    @Autowired
    private LotteryResultService lotteryResultService;

    @GetMapping("/{lotteryId}")
    public LotteryResult getLotteryResultsByDateAndLotteryId(@PathVariable("lotteryId") Long lotteryId) throws ResourceNotFoundException
    {
        return lotteryResultService.getLotteryResultByLotteryId(lotteryId);
    }
}
