package com.spring.service;

import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSubmitLotteryTicket;

public interface LotteryTicketService
{
    boolean submitLotteryTicket(Long userId, Long lotteryId) throws ResourceNotFoundException, UnableToSubmitLotteryTicket;
    Long selectRandomLotteryWinner(Long lotteryId) throws ResourceNotFoundException;
}
