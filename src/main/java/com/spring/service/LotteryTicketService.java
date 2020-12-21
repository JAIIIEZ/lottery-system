package com.spring.service;

import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSubmitLotteryTicket;
import com.spring.model.LotteryTicket;

public interface LotteryTicketService
{
    LotteryTicket submitLotteryTicketByUsername(String username, Long lotteryId) throws ResourceNotFoundException, UnableToSubmitLotteryTicket;
    LotteryTicket submitLotteryTicket(Long lotteryId) throws ResourceNotFoundException, UnableToSubmitLotteryTicket;
    Long selectRandomLotteryWinner(Long lotteryId) throws ResourceNotFoundException;
}
