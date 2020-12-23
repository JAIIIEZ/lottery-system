package com.spring.service;

import com.spring.dto.LotteryResultDto;
import com.spring.exception.ResourceNotFoundException;
import com.spring.model.LotteryResult;

public interface LotteryResultService
{
    LotteryResult saveLotteryResult(Long lotteryId, Long winnerLotteryNum);

    LotteryResultDto getLotteryResultByLotteryId(Long lotteryId) throws ResourceNotFoundException;
}
