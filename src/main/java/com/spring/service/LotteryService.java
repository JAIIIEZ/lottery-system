package com.spring.service;

import java.util.List;

import com.spring.exception.ResourceNotFoundException;
import com.spring.model.Lottery;

public interface LotteryService
{
    Lottery startLotteryByName(String lotteryName);
    void endLotteryById(Long lotteryId) throws ResourceNotFoundException;
    Lottery findById(Long lotteryId) throws ResourceNotFoundException;
    List<Lottery> getActiveLotteries();
}
