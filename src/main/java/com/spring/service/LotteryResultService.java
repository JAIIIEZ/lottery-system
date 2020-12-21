package com.spring.service;

import com.spring.exception.ResourceNotFoundException;
import com.spring.model.LotteryResult;

public interface LotteryResultService
{
    LotteryResult saveLotteryResult(LotteryResult result);
    LotteryResult getLotteryResultByLotteryId(Long lotteryId) throws ResourceNotFoundException;
}
