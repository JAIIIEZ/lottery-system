package com.spring.service.impl;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSubmitLotteryTicket;
import com.spring.model.Lottery;
import com.spring.model.LotteryStatus;
import com.spring.model.LotteryTicket;
import com.spring.repository.LotteryTicketRepository;
import com.spring.service.LotteryResultService;
import com.spring.service.LotteryService;
import com.spring.service.LotteryTicketService;
import com.spring.service.UserService;

@Service
public class LotteryTicketServiceImpl implements LotteryTicketService {
    @Autowired
    private LotteryTicketRepository lotteryTicketRepository;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private LotteryResultService lotteryResultService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(LotteryTicketServiceImpl.class);

    @Override
    public synchronized LotteryTicket submitLotteryTicketSync(Long lotteryId, String username) throws ResourceNotFoundException,
            UnableToSubmitLotteryTicket {
        return submitLotteryTicketByLotteryId(lotteryId, username);
    }

    public LotteryTicket submitLotteryTicketByLotteryId(Long lotteryId, String username) throws ResourceNotFoundException, UnableToSubmitLotteryTicket {
        Lottery lottery = lotteryService.findById(lotteryId);

        lotteryStatusCheck(lottery);
        validateUsername(username);

        LotteryTicket ticket = new LotteryTicket();
        ticket.setLotteryId(lotteryId);
        ticket.setLotteryNumber(generateLotteryNumber(lotteryId));
        ticket.setUsername(username);
        ticket.setDate(new Date());
        lotteryTicketRepository.save(ticket);

        logger.info("Lottery ticket {} bought successfully for lottery id {} !", ticket.getLotteryNumber(), lotteryId);
        return ticket;
    }

    private void lotteryStatusCheck(Lottery lottery) throws UnableToSubmitLotteryTicket {
        if (LotteryStatus.PASSIVE.equals(lottery.getStatus())) {
            throw new UnableToSubmitLotteryTicket("Lottery is passive for this id :: " + lottery.getId());
        }
    }

    private void validateUsername(String username) {
        if (username == null || username.equals("") || userService.findByUsername(username) == null) {
            throw new UsernameNotFoundException("username couldn't find :: " + username);
        }
    }

    private Long generateLotteryNumber(Long lotteryId) {
        LotteryTicket ticket = lotteryTicketRepository.findFirstByLotteryIdOrderByLotteryNumberDesc(lotteryId);
        return ticket == null ? 1L : ticket.getLotteryNumber() + 1;
    }

    @Override
    public void selectRandomLotteryWinnerAndSaveResult(Long lotteryId) throws ResourceNotFoundException {
        Long winnerLotteryNum = selectRandomLotteryWinner(lotteryId);
        saveLotteryResultByLotteryId(lotteryId, winnerLotteryNum);
    }

    private Long selectRandomLotteryWinner(Long lotteryId) throws ResourceNotFoundException {
        Long count = lotteryTicketRepository.countLotteryTicketByLotteryId(lotteryId);

        if (count == 0) {
            logger.info("Couldn't find any participate related to lottery id :: " + lotteryId);
            return -1L;
        }
        long random = getRandom(count);
        LotteryTicket ticket = lotteryTicketRepository.findByLotteryNumberAndLotteryId(random, lotteryId);
        if (ticket == null) {
            throw new ResourceNotFoundException("Lottery ticket couldn't find for this lottery number :: " + random);
        }
        return ticket.getLotteryNumber();
    }

    private void saveLotteryResultByLotteryId(Long lotteryId, Long winnerLotteryNum) {
        lotteryResultService.saveLotteryResult(lotteryId, winnerLotteryNum);
    }

    private long getRandom(Long count) {
        Random rand = new Random();
        return rand.nextInt(count.intValue()) + 1L;
    }

}
