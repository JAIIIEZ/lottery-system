package com.spring.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dto.LotteryResultDto;
import com.spring.exception.LotteryIsNotFinishException;
import com.spring.exception.ResourceNotFoundException;
import com.spring.model.Lottery;
import com.spring.model.LotteryResult;
import com.spring.model.LotteryStatus;
import com.spring.repository.LotteryResultRepository;
import com.spring.service.LotteryResultService;
import com.spring.service.LotteryService;

@Service
public class LotteryResultServiceImpl implements LotteryResultService {

    @Autowired
    private LotteryResultRepository lotteryResultRepository;

    @Autowired
    private LotteryService lotteryService;

    @Override
    public LotteryResultDto getLotteryResultByLotteryId(Long lotteryId) throws ResourceNotFoundException, LotteryIsNotFinishException {
        checkLotteryStatus(lotteryId);
        LotteryResult result = lotteryResultRepository.findByLotteryId(lotteryId);
        if (result == null) {
            throw new ResourceNotFoundException("Lottery result couldn't find for this lottery id :: " + lotteryId);
        }
        return getLotteryResultDto(result);
    }

    private void checkLotteryStatus(Long lotteryId) throws ResourceNotFoundException, LotteryIsNotFinishException {
        Lottery lottery = lotteryService.findById(lotteryId);
        if (LotteryStatus.ACTIVE.equals(lottery.getStatus())) {
            throw new LotteryIsNotFinishException("Lottery is not finish yet :: " + lotteryId);
        }
    }

    private LotteryResultDto getLotteryResultDto(LotteryResult result) {
        String winner = (result.getWinnerLotteryNumber() == -1 ? "Nobody won!!" : result.getWinnerLotteryNumber().toString());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = dateFormat.format(result.getDate());

        return new LotteryResultDto(stringDate, winner);
    }

    @Override
    public LotteryResult saveLotteryResult(Long lotteryId, Long winnerNum) {
        LotteryResult result = new LotteryResult(new Date(), lotteryId, winnerNum);
        return lotteryResultRepository.save(result);
    }
}
