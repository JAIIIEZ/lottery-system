package com.spring.service.impl;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Lottery findById(Long lotteryId)
    {
        try
        {
            Optional<Lottery> lottery = lotteryRepository.findById(lotteryId.toString());
            return lottery.get();

        } catch(NoSuchElementException e) {
            // do something with the exception
        }

        return new Lottery();
    }

    @Override
    public void endLotteryByDateAndId(Date date, Long id)
    {
        Lottery lottery = lotteryRepository.findLotteryByDateAndId(date, id);
        lottery.setStatus(LotteryStatus.PASSIVE);
        try {
            lotteryRepository.save(lottery);
        } catch(Exception ex){
            //throw new UnableToSaveException("unknown error");
        }
    }

}
