package com.spring.service;

import java.util.Date;

import com.spring.model.Lottery;

public interface LotteryService
{
    Lottery startLottery(String lotteryName); // her gün yeni bir kura başlatacak
    void endLotteryByDateAndId(Date date, Long id);
    Lottery findById(Long lotteryId);
}
