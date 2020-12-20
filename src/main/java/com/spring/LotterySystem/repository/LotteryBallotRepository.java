package com.spring.LotterySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.LotterySystem.model.LotteryBallot;

@Repository
public interface LotteryBallotRepository extends JpaRepository<LotteryBallot, Long>
{
}
