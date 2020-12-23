package com.spring.service;

import static org.junit.Assert.assertEquals;

import com.spring.exception.LotteryAlreadyPassiveException;
import com.spring.repository.LotteryRepository;
import com.spring.repository.LotteryResultRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.LotteryResultDto;
import com.spring.exception.LotteryIsNotFinishException;
import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSaveException;
import com.spring.model.Lottery;
import com.spring.model.LotteryResult;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryResultServiceTest {

    @Autowired
    private LotteryResultService lotteryResultService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private LotteryResultRepository lotteryResultRepository;

    @Before
    public void initEach() {
        lotteryResultRepository.deleteAll();
        lotteryRepository.deleteAll();
    }

    @Test
    public void shouldSaveLotteryResult() throws UnableToSaveException, ResourceNotFoundException, LotteryAlreadyPassiveException {
        lotteryResultRepository.deleteAll();
        lotteryRepository.deleteAll();
        LotteryResult result = createLotteryResult();

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldntSaveLotteryResultForSameLotteryId() throws UnableToSaveException, ResourceNotFoundException, LotteryAlreadyPassiveException {
        createLotteryResult();
        createLotteryResult();
    }

    @Test
    public void shouldGiveLotteryResult_WhenLotteryIdIsValid() throws ResourceNotFoundException, LotteryIsNotFinishException, UnableToSaveException, LotteryAlreadyPassiveException {
        LotteryResult result = createLotteryResult();
        LotteryResultDto result2 = lotteryResultService.getLotteryResultByLotteryId(result.getLotteryId());

        assertEquals(result.getWinnerLotteryNumber().toString(), result2.getWinnerLotteryNumber());
    }

    @Transactional
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_WhenLotteryIdIsInvalid() throws ResourceNotFoundException, LotteryIsNotFinishException {
        lotteryResultService.getLotteryResultByLotteryId(null);
    }

    @Transactional
    @Test(expected = LotteryIsNotFinishException.class)
    public void shouldThrowException_WhenLotteryIsStillActive() throws ResourceNotFoundException, LotteryIsNotFinishException, UnableToSaveException {
        Lottery lottery = lotteryService.startLotteryByName("Lotterytest");
        lotteryResultService.getLotteryResultByLotteryId(lottery.getId());
    }

    private LotteryResult createLotteryResult() throws UnableToSaveException, ResourceNotFoundException, LotteryAlreadyPassiveException {
        final String lotteryName = UUID.randomUUID().toString();
        Lottery lottery = lotteryService.startLotteryByName(lotteryName);
        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());
        return lotteryResultService.saveLotteryResult(lottery.getId(), 3L);
    }
}
