package com.spring.service;

import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSubmitLotteryTicket;
import com.spring.model.LotteryTicket;

public interface LotteryTicketService
{
    LotteryTicket submitLotteryTicketByLotteryId(Long lotteryId) throws ResourceNotFoundException, UnableToSubmitLotteryTicket;

    LotteryTicket submitLotteryTicketSync(Long lotteryId) throws ResourceNotFoundException, UnableToSubmitLotteryTicket;

    void endLotteryAndSelectLotteryWinner(Long lotteryId) throws ResourceNotFoundException;
}
