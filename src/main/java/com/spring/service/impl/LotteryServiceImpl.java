package com.spring.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.spring.dto.LotteryResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.exception.LotteryStatusException;
import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSaveException;
import com.spring.model.Lottery;
import com.spring.repository.LotteryRepository;
import com.spring.service.LotteryService;
import com.spring.service.LotteryTicketService;

@Service
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private LotteryTicketService lotteryTicketService;

    private static final Logger logger = LoggerFactory.getLogger(LotteryServiceImpl.class);

    @Transactional
    @Override
    public Lottery startLotteryByName(String lotteryName) throws UnableToSaveException {
        checkActiveLotteryWithSameName(lotteryName);

        Lottery lottery = new Lottery();
        lottery.setName(lotteryName);
        lottery.setStartDate(new Date());
        return lotteryRepository.save(lottery);
    }

    public void checkActiveLotteryWithSameName(String lotteryName) throws UnableToSaveException {
        Long count = lotteryRepository.countByNameAndEndDateIsNull(lotteryName);

        if (count > 0) {
            throw new UnableToSaveException("There is already active lottery by this name :: " + lotteryName);
        }
    }


    @Override
    public Lottery findByLotteryId(Long lotteryId) {
        Lottery lottery = lotteryRepository.findById(lotteryId);
        if (lottery == null) {
            throw new IllegalArgumentException("Lottery not found for this id :: " + lotteryId);
        }
        return lottery;
    }


    @Override
    public void endActiveLotteriesAndSelectLotteryWinners() {
        List<Lottery> lotteries = getActiveLotteries();
        lotteries.stream().forEach(lottery -> {
            try {
                endLotteryAndSelectLotteryWinner(lottery.getId());
            } catch (ResourceNotFoundException | LotteryStatusException e) {
                logger.error("Lottery couldn't end for that id {} , error: {} ", lottery.getId(), e.getMessage());
            }
        });
    }

    @Transactional
    @Override
    public void endLotteryAndSelectLotteryWinner(Long lotteryId) throws ResourceNotFoundException, LotteryStatusException {
        checkLotteryIsPassive(lotteryId);
        Long winnerNumber = lotteryTicketService.selectRandomLotteryWinner(lotteryId);
        endLotteryAndSaveLotteryResult(lotteryId, winnerNumber);
    }

    private void endLotteryAndSaveLotteryResult(Long lotteryId, Long winnerLotteryNum) {
        Lottery lottery = findByLotteryId(lotteryId);
        lottery.setWinnerLotteryNumber(winnerLotteryNum);
        lottery.setEndDate(new Date());
        lotteryRepository.save(lottery);
    }

    @Override
    public LotteryResultDto getLotteryResultByLotteryId(Long lotteryId) throws LotteryStatusException {
        checkLotteryIsActive(lotteryId);
        return getLotteryResultDto(lotteryId);
    }

    private void checkLotteryIsPassive(Long lotteryId) throws LotteryStatusException {
        Lottery lottery = findByLotteryId(lotteryId);
        if (lottery.getEndDate() != null) {
            throw new LotteryStatusException("Lottery id is already passive! :: " + lotteryId);
        }
    }

    private void checkLotteryIsActive(Long lotteryId) throws LotteryStatusException {
        Lottery lottery = findByLotteryId(lotteryId);
        if (lottery.getEndDate() == null) {
            throw new LotteryStatusException("Lottery is not finish yet :: " + lotteryId);
        }
    }

    private LotteryResultDto getLotteryResultDto(Long lotteryId) {
        Lottery lottery = findByLotteryId(lotteryId);
        String winner = (lottery.getWinnerLotteryNumber() == -1 ? "Nobody won!!" : lottery.getWinnerLotteryNumber().toString());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = dateFormat.format(lottery.getEndDate());

        return new LotteryResultDto(stringDate, winner);
    }

    @Override
    public List<Lottery> getActiveLotteries() {
        return lotteryRepository.findLotteriesByEndDateIsNull();
    }


}
