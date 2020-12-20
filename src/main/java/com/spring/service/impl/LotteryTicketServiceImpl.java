package com.spring.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.model.Lottery;
import com.spring.model.LotteryStatus;
import com.spring.model.LotteryTicket;
import com.spring.repository.LotteryTicketRepository;
import com.spring.service.LotteryService;
import com.spring.service.LotteryTicketService;

@Service
public class LotteryTicketServiceImpl implements LotteryTicketService
{
    @Autowired
    private LotteryTicketRepository lotteryTicketRepository;

    @Autowired
    private LotteryService lotteryService;

    @Override
    public boolean submitLotteryTicket(Long userId, Long lotteryId)
    {
        Lottery lottery = lotteryService.findById(lotteryId);

        if (LotteryStatus.PASSIVE.equals(lottery.getStatus())) {
            //new throw .. this lottery had finished
            return false;
        } else {
            LotteryTicket ticket = new LotteryTicket();
            ticket.setLotteryId(lotteryId);
            ticket.setLotteryNumber(generateLotteryNumber(lotteryId));
            ticket.setUserId(userId);
            lotteryTicketRepository.save(ticket);
            return true;
        }
    }

    private Long generateLotteryNumber(Long lotteryId) {
        LotteryTicket ticket = lotteryTicketRepository.findFirstByLotteryIdOrderByLotteryNumberDesc(lotteryId);
        return ticket == null ? 1L : ticket.getLotteryNumber() + 1;
    }

    @Override
    public Long selectRandomLotteryWinner(Long lotteryId) {
        Long count = lotteryTicketRepository.countLotteryTicketByLotteryId(lotteryId);

        try
        {
            Random rand = SecureRandom.getInstanceStrong();
            int random = rand.nextInt(count.intValue() + 1);
            LotteryTicket ticket = lotteryTicketRepository.findByLotteryNumberAndLotteryId(Long.valueOf(random), lotteryId);
            return ticket.getLotteryNumber();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }

    }
}
