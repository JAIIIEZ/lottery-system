package com.spring.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.model.Lottery;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, String>
{
    Optional<Lottery> findLotteryByDateAndId(Date date, Long id);

    List<Lottery> findAllByDate(Date date);
}
