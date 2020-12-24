package com.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.spring.dto.LotteryResultDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.spring.exception.LotteryStatusException;
import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSaveException;
import com.spring.model.Lottery;
import com.spring.repository.LotteryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryServiceTest {

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private LotteryRepository lotteryRepository;

    final static String LOTTERY_A = "Lottery A";
    final static String LOTTERY_B = "Lottery B";

    @Before
    public void initEach() {
        lotteryRepository.deleteAll();
    }

    @Test
    public void shouldStartLottery() throws UnableToSaveException {
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);

        Assert.assertNotNull(lottery);
        Assert.assertNotNull(lottery.getId());
        Assert.assertNotNull(lottery.getStartDate());
        Assert.assertNull(lottery.getEndDate());
        Assert.assertEquals(LOTTERY_A, lottery.getName());
    }

    @Test(expected = UnableToSaveException.class)
    public void shouldThrowException_WhenActiveLotteryWithSameNameExist() throws UnableToSaveException {
        lotteryService.startLotteryByName(LOTTERY_A);
        lotteryService.startLotteryByName(LOTTERY_A);
    }


    @Transactional
    @Test
    public void shouldFindLotteryById() throws ResourceNotFoundException, UnableToSaveException {
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);

        Lottery lottery2 = lotteryService.findByLotteryId(lottery.getId());

        Assert.assertEquals(lottery, lottery2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_WhenLotteryCouldntFind() throws ResourceNotFoundException {
        lotteryService.findByLotteryId(null);
    }

    @Test
    public void whenDeleteAllFromRepository_thenRepositoryShouldBeEmpty() {
        lotteryRepository.deleteAll();
        Assert.assertEquals(0, lotteryRepository.count());
    }

    @Transactional
    @Test
    public void shouldGetAllActiveLotteries() throws UnableToSaveException {
        List<Lottery> expectedLotteries = new ArrayList<>();
        expectedLotteries.add(lotteryService.startLotteryByName(LOTTERY_A));
        expectedLotteries.add(lotteryService.startLotteryByName(LOTTERY_B));

        List<Lottery> givenLotteries = lotteryService.getActiveLotteries();

        Assert.assertEquals(expectedLotteries, givenLotteries);
    }

    @Transactional
    @Test
    public void shouldGetActiveLotteries_AfterEndLottery() throws ResourceNotFoundException, UnableToSaveException, LotteryStatusException {
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);
        Lottery lottery2 = lotteryService.startLotteryByName(LOTTERY_B);

        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());

        Assert.assertEquals(1, lotteryService.getActiveLotteries().size());
    }

    @Transactional
    @Test
    public void shouldEndLotteryById() throws ResourceNotFoundException, UnableToSaveException, LotteryStatusException {
        final String lotteryName = UUID.randomUUID().toString();
        Lottery lottery = lotteryService.startLotteryByName(lotteryName);

        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());

        Assert.assertNotNull(lottery.getEndDate());
    }

    @Test(expected = LotteryStatusException.class)
    public void shouldThrowException_WhenLotteryIsAlreadyPassive() throws ResourceNotFoundException, UnableToSaveException, LotteryStatusException {
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);
        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());

        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());
    }

    @Transactional
    @Test
    public void shouldEndActiveLotteries() throws ResourceNotFoundException, UnableToSaveException {
        lotteryService.startLotteryByName(LOTTERY_A);
        lotteryService.startLotteryByName(LOTTERY_B);

        lotteryService.endActiveLotteriesAndSelectLotteryWinners();

        Assert.assertEquals(0, lotteryService.getActiveLotteries().size());
    }

    @Transactional
    @Test
    public void shouldEndOtherActiveLotteries_WhenOneOfThemFails() throws ResourceNotFoundException, UnableToSaveException, LotteryStatusException {
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);
        lotteryService.startLotteryByName(LOTTERY_B);
        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());

        lotteryService.endActiveLotteriesAndSelectLotteryWinners();

        Assert.assertEquals(0, lotteryService.getActiveLotteries().size());
    }

    @Transactional
    @Test
    public void shouldSaveLotteryResult() throws UnableToSaveException, ResourceNotFoundException, LotteryStatusException {
        Lottery lottery = createAndEndLottery();

        Assert.assertNotNull(lottery.getEndDate());
        Assert.assertNotNull(lottery.getWinnerLotteryNumber());
    }


    @Test
    public void shouldGiveLotteryResult_WhenLotteryIdIsValid() throws ResourceNotFoundException, UnableToSaveException, LotteryStatusException {
        Lottery lottery = createAndEndLottery();
        LotteryResultDto result = lotteryService.getLotteryResultByLotteryId(lottery.getId());

        Assert.assertNotNull(result);
    }

    @Transactional
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_WhenLotteryIdIsInvalid() throws ResourceNotFoundException, LotteryStatusException {
        lotteryService.getLotteryResultByLotteryId(null);
    }

    @Transactional
    @Test(expected = LotteryStatusException.class)
    public void shouldThrowException_WhenLotteryIsStillActive() throws ResourceNotFoundException, UnableToSaveException, LotteryStatusException {
        final String lotteryName = UUID.randomUUID().toString();
        Lottery lottery = lotteryService.startLotteryByName(lotteryName);
        lotteryService.getLotteryResultByLotteryId(lottery.getId());
    }

    private Lottery createAndEndLottery() throws UnableToSaveException, ResourceNotFoundException, LotteryStatusException {
        final String lotteryName = UUID.randomUUID().toString();
        Lottery lottery = lotteryService.startLotteryByName(lotteryName);
        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());
        return lottery;
    }



}
