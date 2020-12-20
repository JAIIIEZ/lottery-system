package com.spring.LotterySystem.service;

import com.spring.LotterySystem.model.LotteryBallot;

public interface LotteryBallotService
{
    LotteryBallot submitLotteryBallot(String email);
}
