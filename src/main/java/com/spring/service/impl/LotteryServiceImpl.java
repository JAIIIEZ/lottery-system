package com.spring.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.exception.ResourceNotFoundException;
import com.spring.model.Lottery;
import com.spring.model.LotteryStatus;
import com.spring.repository.LotteryRepository;
import com.spring.service.LotteryService;

@Service
public class LotteryServiceImpl implements LotteryService
{
    @Autowired
    private LotteryRepository lotteryRepository;

    private static final Logger logger = LoggerFactory.getLogger(LotteryServiceImpl.class);

    @Override
    public Lottery startLotteryByName(String lotteryName)
    {
        Lottery lottery = new Lottery();
        lottery.setName(lotteryName);
        lottery.setStatus(LotteryStatus.ACTIVE);
        lottery.setDate(new Date());
        return lotteryRepository.save(lottery);
    }


    @Override
    public Lottery findById(Long lotteryId) throws ResourceNotFoundException
    {
        Lottery lottery =  lotteryRepository.findById(lotteryId);
        if (lottery == null) {
            throw new ResourceNotFoundException("Lottery not found for this id :: " + lotteryId);
        }
        return lottery;
    }

    @Override
    public void endLotteryById(Long lotteryId) throws ResourceNotFoundException
    {
        Lottery lottery = findById(lotteryId);
        if (LotteryStatus.PASSIVE.equals(lottery.getStatus())) {
            logger.info("Lottery id {} is already passive!", lotteryId);
        } else {
            lottery.setStatus(LotteryStatus.PASSIVE);
            lotteryRepository.save(lottery);
        }
    }

    @Override
    public List<Lottery> getActiveLotteries() {
        return lotteryRepository.findLotteriesByStatus(LotteryStatus.ACTIVE);
    }


}
