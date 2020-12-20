package com.spring.LotterySystem.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.LotterySystem.model.LotteryBallot;
import com.spring.LotterySystem.model.User;
import com.spring.LotterySystem.repository.LotteryBallotRepository;
import com.spring.LotterySystem.service.LotteryBallotService;
import com.spring.LotterySystem.service.UserService;

@Service
public class LotteryBallotServiceImpl implements LotteryBallotService
{
    @Autowired
    private LotteryBallotRepository lotteryBallotRepository;

    @Autowired
    private UserService userService;

    @Override
    public LotteryBallot submitLotteryBallot(String email)
    {
        User user = userService.findByEmail(email);
        LotteryBallot ballot = new LotteryBallot();
        ballot.setDate(new Date());
        ballot.setUserId(user.getId());
        return lotteryBallotRepository.save(ballot);
    }
}
