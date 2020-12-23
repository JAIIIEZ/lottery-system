package com.spring.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.exception.LotteryAlreadyPassiveException;
import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSaveException;
import com.spring.model.Lottery;
import com.spring.model.LotteryStatus;
import com.spring.repository.LotteryRepository;
import com.spring.service.LotteryService;
import com.spring.service.LotteryTicketService;

@Service
public class LotteryServiceImpl implements LotteryService
{
    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private LotteryTicketService lotteryTicketService;

    private static final Logger logger = LoggerFactory.getLogger(LotteryServiceImpl.class);

    @Transactional
    @Override
    public Lottery startLotteryByName(String lotteryName) throws UnableToSaveException
    {
        checkActiveLotteryWithSameName(lotteryName);

        Lottery lottery = new Lottery();
        lottery.setName(lotteryName);
        lottery.setStatus(LotteryStatus.ACTIVE);
        lottery.setDate(new Date());
        return lotteryRepository.save(lottery);
    }

    public void checkActiveLotteryWithSameName(String lotteryName) throws UnableToSaveException
    {
        Long count = lotteryRepository.countByNameAndStatus(lotteryName, LotteryStatus.ACTIVE);

        if (count > 0)
        {
            throw new UnableToSaveException("There is already active lottery by this name :: " + lotteryName);
        }
    }


    @Override
    public Lottery findById(Long lotteryId)
    {
        Lottery lottery = lotteryRepository.findById(lotteryId);
        if (lottery == null)
        {
            throw new IllegalArgumentException("Lottery not found for this id :: " + lotteryId);
        }
        return lottery;
    }

    @Transactional
    @Override
    public void endActiveLotteriesAndSelectLotteryWinners()
    {
        List<Lottery> lotteries = getActiveLotteries();
        lotteries.stream().forEach(lottery -> {
            try
            {
                endLotteryAndSelectLotteryWinner(lottery.getId());
            }
            catch (ResourceNotFoundException | LotteryAlreadyPassiveException e)
            {
                logger.error("Lottery couldn't end for that id {} , error: {} ", lottery.getId(), e.getMessage());
            }
        });
    }

    @Transactional
    @Override
    public void endLotteryAndSelectLotteryWinner(Long lotteryId) throws ResourceNotFoundException, LotteryAlreadyPassiveException
    {
        endLotteryById(lotteryId);
        lotteryTicketService.selectRandomLotteryWinnerAndSaveResult(lotteryId);
    }

    private void endLotteryById(Long lotteryId) throws LotteryAlreadyPassiveException
    {
        Lottery lottery = findById(lotteryId);
        if (LotteryStatus.PASSIVE.equals(lottery.getStatus()))
        {
            throw new LotteryAlreadyPassiveException("Lottery id is already passive! :: " + lotteryId);
        }
        else
        {
            lottery.setStatus(LotteryStatus.PASSIVE);
            lotteryRepository.save(lottery);
        }
    }

    @Override
    public List<Lottery> getActiveLotteries()
    {
        return lotteryRepository.findLotteriesByStatus(LotteryStatus.ACTIVE);
    }

}
