package com.spring.service;

import java.util.List;

import com.spring.dto.LotteryResultDto;
import com.spring.exception.LotteryStatusException;
import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSaveException;
import com.spring.model.Lottery;

public interface LotteryService {

    Lottery startLotteryByName(String lotteryName) throws UnableToSaveException;

    Lottery findByLotteryId(Long lotteryId) throws ResourceNotFoundException;

    List<Lottery> getActiveLotteries();

    void endLotteryAndSelectLotteryWinner(Long lotteryId) throws ResourceNotFoundException, LotteryStatusException;

    void endActiveLotteriesAndSelectLotteryWinners() throws ResourceNotFoundException;

    LotteryResultDto getLotteryResultByLotteryId(Long lotteryId) throws ResourceNotFoundException, LotteryStatusException;
}
