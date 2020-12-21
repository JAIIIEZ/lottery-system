package com.spring.service.impl;

import java.security.SecureRandom;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSubmitLotteryTicket;
import com.spring.model.Lottery;
import com.spring.model.LotteryResult;
import com.spring.model.LotteryStatus;
import com.spring.model.LotteryTicket;
import com.spring.repository.LotteryTicketRepository;
import com.spring.service.LotteryResultService;
import com.spring.service.LotteryService;
import com.spring.service.LotteryTicketService;
import com.spring.service.SecurityService;
import com.spring.utils.DateUtils;

@Service
public class LotteryTicketServiceImpl implements LotteryTicketService
{
    @Autowired
    private LotteryTicketRepository lotteryTicketRepository;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private LotteryResultService lotteryResultService;

    @Autowired
    private SecurityService securityService;

    private static final Logger logger = LoggerFactory.getLogger(LotteryTicketServiceImpl.class);

    @Override
    public LotteryTicket submitLotteryTicketSync(Long lotteryId) throws ResourceNotFoundException, UnableToSubmitLotteryTicket
    {
        synchronized (this) {
            return submitLotteryTicketByLotteryId(lotteryId);
        }
    }

    @Override
    public LotteryTicket submitLotteryTicketByLotteryId(Long lotteryId) throws ResourceNotFoundException, UnableToSubmitLotteryTicket {
        String username = securityService.findLoggedInUsername();
        Lottery lottery = lotteryService.findById(lotteryId);

        lotteryStatusCheck(lottery);
        validateUsername(username);

        LotteryTicket ticket = new LotteryTicket();
        ticket.setLotteryId(lotteryId);
        ticket.setLotteryNumber(generateLotteryNumber(lotteryId));
        ticket.setUsername(username);
        lotteryTicketRepository.save(ticket);

        logger.info("Lottery ticket {} bought successfully for lottery id {} !", ticket.getLotteryNumber(), lotteryId);
        return ticket;
    }

    private void lotteryStatusCheck(Lottery lottery) throws UnableToSubmitLotteryTicket
    {
        if (LotteryStatus.PASSIVE.equals(lottery.getStatus())) {
            throw new UnableToSubmitLotteryTicket("Lottery is passive for this id :: " + lottery.getId());
        }
    }

    private void validateUsername(String username) {
        if (username == null || username.equals("")) {
            throw new UsernameNotFoundException("username couldn't find :: " + username);
        }
    }

    private Long generateLotteryNumber(Long lotteryId) {
        LotteryTicket ticket = lotteryTicketRepository.findFirstByLotteryIdOrderByLotteryNumberDesc(lotteryId);
        return ticket == null ? 1L : ticket.getLotteryNumber() + 1;
    }

    private Long selectRandomLotteryWinner(Long lotteryId) throws ResourceNotFoundException
    {
        checkLotteryWinnerAlreadyExist(lotteryId);

        Long count = lotteryTicketRepository.countLotteryTicketByLotteryId(lotteryId);

        long random = getRandom(count);
        LotteryTicket ticket = lotteryTicketRepository.findByLotteryNumberAndLotteryId(random, lotteryId);
        if (ticket == null) {
            throw new ResourceNotFoundException("Lottery ticket couldn't find for this lottery number :: " + random);
        }
        return ticket.getLotteryNumber();
    }

    @Override
    public void endLotteryAndSelectLotteryWinner(Long lotteryId) throws ResourceNotFoundException
    {
        lotteryService.endLotteryById(lotteryId);
        Long winnerLotteryNum = selectRandomLotteryWinner(lotteryId);
        saveLotteryResultByLotteryId(lotteryId, winnerLotteryNum);
    }

    private void saveLotteryResultByLotteryId(Long lotteryId, Long winnerLotteryNum)
    {
        lotteryResultService.saveLotteryResult(lotteryId, winnerLotteryNum);
    }


    private long getRandom(Long count)
    {
        SecureRandom rand = new SecureRandom();
        return rand.nextInt(count.intValue()) + 1L;
    }

    private void checkLotteryWinnerAlreadyExist(Long lotteryId) throws ResourceNotFoundException
    {
        LotteryResult result = lotteryResultService.getLotteryResultByLotteryId(lotteryId);
        if (result != null) {
            throw new RuntimeException("This lottery id alread has winner :: " + lotteryId);
        }
    }
}
