package com.spring.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.exception.ResourceNotFoundException;
import com.spring.model.Lottery;
import com.spring.model.LotteryStatus;
import com.spring.repository.LotteryRepository;
import com.spring.service.LotteryService;
import com.spring.utils.DateUtils;

@Service
public class LotteryServiceImpl implements LotteryService
{
    @Autowired
    private LotteryRepository lotteryRepository;

    @Override
    public Lottery startLottery(String lotteryName)
    {
        Lottery lottery = new Lottery();
        lottery.setName(lotteryName);
        lottery.setStatus(LotteryStatus.ACTIVE);
        lottery.setDate(DateUtils.atStartOfDay(new Date()));
        return lotteryRepository.save(lottery);
    }


    @Override
    public Lottery findById(Long lotteryId) throws ResourceNotFoundException
    {
        return lotteryRepository.findById(lotteryId.toString())
                .orElseThrow(() -> new ResourceNotFoundException("Lottery not found for this id :: " + lotteryId));
    }

    @Override
    public void endLotteryByDateAndId(Date date, Long id) throws ResourceNotFoundException
    {
        Lottery lottery = lotteryRepository.findLotteryByDateAndId(date, id)
        .orElseThrow(() -> new ResourceNotFoundException("Lottery not found for this id  :: " + id + " and date :: " + date));

        lottery.setStatus(LotteryStatus.PASSIVE);
        lotteryRepository.save(lottery);
    }

}
