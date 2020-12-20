package com.spring.service;

import java.util.Date;

import com.spring.dto.LotteryResultDto;
import com.spring.exception.ResourceNotFoundException;
import com.spring.model.LotteryResult;


public interface LotteryResultService
{
    LotteryResultDto getLotteryResultByDateAndLotteryId(Date lotteryDate, Long lotteryId) throws ResourceNotFoundException;
    void saveLotteryResult(LotteryResult result);
}
