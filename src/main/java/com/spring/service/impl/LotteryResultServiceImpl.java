package com.spring.service.impl;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.exception.ResourceNotFoundException;
import com.spring.model.LotteryResult;
import com.spring.repository.LotteryResultRepository;
import com.spring.service.LotteryResultService;
import com.spring.utils.DateUtils;

@Service
public class LotteryResultServiceImpl implements LotteryResultService
{
    @Autowired
    private LotteryResultRepository lotteryResultRepository;

    @Override
    public LotteryResult getLotteryResultByLotteryId(Long lotteryId) throws ResourceNotFoundException {
        LotteryResult result = lotteryResultRepository.findByLotteryId(lotteryId);
        if (result == null) {
            throw new ResourceNotFoundException("Lottery result couldn't find for this lottery id :: " + lotteryId);
        }
        return result;
    }

    @Override
    public LotteryResult saveLotteryResult(Long lotteryId, Long winnerNum) {
        Date yesterday = DateUtils.yesterday();
        LotteryResult result = new LotteryResult(yesterday, lotteryId, winnerNum);
        return lotteryResultRepository.save(result);
    }
}
