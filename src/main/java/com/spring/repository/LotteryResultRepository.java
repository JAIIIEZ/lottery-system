package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.model.LotteryResult;

@Repository
public interface LotteryResultRepository extends JpaRepository<LotteryResult, Long> {

    LotteryResult findByLotteryId(Long lotteryId);
}
