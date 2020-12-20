package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.model.LotteryTicket;

@Repository
public interface LotteryTicketRepository extends JpaRepository<LotteryTicket, Long>
{
    LotteryTicket findFirstByLotteryIdOrderByLotteryNumberDesc(Long lotteryId);
    Long countLotteryTicketByLotteryId(Long lotteryId);
    LotteryTicket findByLotteryNumberAndLotteryId(Long lotteryNumber, Long lotteryId);
}
