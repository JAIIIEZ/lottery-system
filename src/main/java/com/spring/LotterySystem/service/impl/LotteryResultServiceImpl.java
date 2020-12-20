package com.spring.LotterySystem.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.LotterySystem.dto.LotteryResultDto;
import com.spring.LotterySystem.model.LotteryResult;
import com.spring.LotterySystem.model.User;
import com.spring.LotterySystem.repository.LotteryResultDAO;
import com.spring.LotterySystem.service.LotteryResultService;


@Service
public class LotteryResultServiceImpl implements LotteryResultService
{
    @Autowired
    private LotteryResultDAO lotteryResultDAO;


    @Override
    public LotteryResultDto getLotteryResultByDate(Date lotteryDate)
    {   /*
        LotteryResult result = lotteryResultDAO.getLotteryResultByDate(lotteryDate);
        User user = result.getUser();
        LotteryResultDto resultDto = new LotteryResultDto(user.getName(), user.getSurname(), user.getUsername());
        return resultDto;
        */
        return null;
    }
}
