package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.model.Lottery;
import com.spring.model.LotteryStatus;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, String>
{
    List<Lottery> findLotteriesByStatus(LotteryStatus status);
    Lottery findById(Long id);
}
