package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.model.Lottery;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, String> {

    List<Lottery> findLotteriesByEndDateIsNull();

    Lottery findById(Long id);

    Long countByNameAndEndDateIsNull(String name);
}
