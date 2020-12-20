package com.spring.LotterySystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.LotterySystem.model.LotteryResult;

@Repository
public interface LotteryResultDAO extends JpaRepository<LotteryResult, Long>
{
}
