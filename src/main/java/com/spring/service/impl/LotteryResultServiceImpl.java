package com.spring.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dto.LotteryResultDto;
import com.spring.model.Lottery;
import com.spring.model.LotteryResult;
import com.spring.repository.LotteryResultRepository;
import com.spring.service.LotteryResultService;
import com.spring.service.LotteryService;
import com.spring.utils.DateUtils;


@Service
public class LotteryResultServiceImpl implements LotteryResultService
{
    @Autowired
    private LotteryResultRepository lotteryResultRepository;

    @Autowired
    private LotteryService lotteryService;

    //paginatig if returns list
    @Override
    public LotteryResultDto getLotteryResultByDateAndLotteryId(Date lotteryDate, Long lotteryId)
    {
        Date endDate = DateUtils.atEndOfDay(lotteryDate);
        Date startDate = DateUtils.atStartOfDay(lotteryDate);
        LotteryResult result = lotteryResultRepository.findByLotteryIdAndDateBetween(lotteryId, startDate, endDate);
        Lottery lottery = lotteryService.findById(lotteryId);
        return new LotteryResultDto(startDate, lottery.getName(), result.getWinnerLotteryNumber());
    }

    @Override
    public void saveLotteryResult(LotteryResult result) {
        lotteryResultRepository.save(result);
    }
}
