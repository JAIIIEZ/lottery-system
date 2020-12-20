package com.spring.LotterySystem.service;

import java.util.Date;

import com.spring.LotterySystem.dto.LotteryResultDto;


public interface LotteryResultService
{
    LotteryResultDto getLotteryResultByDate(Date lotteryDate);
}
