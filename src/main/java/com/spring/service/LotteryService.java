package com.spring.service;

import java.util.Date;

import com.spring.exception.ResourceNotFoundException;
import com.spring.model.Lottery;

public interface LotteryService
{
    Lottery startLottery(String lotteryName);
    void endLotteryByDateAndId(Date date, Long id) throws ResourceNotFoundException;
    Lottery findById(Long lotteryId) throws ResourceNotFoundException;
}
