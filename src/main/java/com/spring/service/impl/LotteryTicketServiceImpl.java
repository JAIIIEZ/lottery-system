package com.spring.service.impl;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSubmitLotteryTicket;
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
    public boolean submitLotteryTicket(Long userId, Long lotteryId) throws ResourceNotFoundException, UnableToSubmitLotteryTicket
    {
        Lottery lottery = lotteryService.findById(lotteryId);

        if (LotteryStatus.PASSIVE.equals(lottery.getStatus())) {
            throw new UnableToSubmitLotteryTicket("Lottery is passive for this id :: " + lottery.getId());
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
    public Long selectRandomLotteryWinner(Long lotteryId) throws ResourceNotFoundException
    {
        Long count = lotteryTicketRepository.countLotteryTicketByLotteryId(lotteryId);

        SecureRandom rand = new SecureRandom();
        int random = rand.nextInt(count.intValue());
        LotteryTicket ticket = lotteryTicketRepository.findByLotteryNumberAndLotteryId(Long.valueOf(random + 1), lotteryId);
        if (ticket == null) {
            throw new ResourceNotFoundException("Lottery ticket couldn't find for this lottery number :: " + random + 1);
        }
        return ticket.getLotteryNumber();
    }
}
