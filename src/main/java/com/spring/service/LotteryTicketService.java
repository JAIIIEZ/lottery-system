package com.spring.service;

public interface LotteryTicketService
{
    boolean submitLotteryTicket(Long userId, Long lotteryId);
    Long selectRandomLotteryWinner(Long lotteryId);
}
