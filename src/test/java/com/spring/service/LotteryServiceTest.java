package com.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSaveException;
import com.spring.model.Lottery;
import com.spring.model.LotteryStatus;
import com.spring.repository.LotteryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryServiceTest
{
    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private LotteryRepository lotteryRepository;

    final static String LOTTERY_A = "Lottery A";
    final static String LOTTERY_B = "Lottery B";

    @Test
    public void shouldStartLottery() throws UnableToSaveException
    {
        lotteryRepository.deleteAll();
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);

        Assert.assertNotNull(lottery);
        Assert.assertNotNull(lottery.getId());
        Assert.assertEquals(LotteryStatus.ACTIVE, lottery.getStatus());
        Assert.assertEquals(LOTTERY_A, lottery.getName());
    }

    @Transactional
    @Test
    public void shouldFindLotteryById() throws ResourceNotFoundException, UnableToSaveException
    {
        lotteryRepository.deleteAll();
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);

        Lottery lottery2 = lotteryService.findById(lottery.getId());

        Assert.assertEquals(lottery, lottery2);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowException_WhenLotteryCouldntFind() throws ResourceNotFoundException {
        lotteryService.findById(null);
    }

    @Transactional
    @Test
    public void shouldEndLotteryById() throws ResourceNotFoundException, UnableToSaveException
    {
        lotteryRepository.deleteAll();
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);

        lotteryService.endLotteryById(lottery.getId());

        Assert.assertEquals(LotteryStatus.PASSIVE, lottery.getStatus());
    }

    @Test
    public void whenDeleteAllFromRepository_thenRepositoryShouldBeEmpty() {
        lotteryRepository.deleteAll();
        Assert.assertEquals(0, lotteryRepository.count());
    }

    @Transactional
    @Test
    public void shouldGetAllActiveLotteries() throws UnableToSaveException {
        lotteryRepository.deleteAll();
        List<Lottery> expectedLotteries = new ArrayList<>();
        expectedLotteries.add(lotteryService.startLotteryByName(LOTTERY_A));
        expectedLotteries.add(lotteryService.startLotteryByName(LOTTERY_B));

        List<Lottery> givenLotteries = lotteryService.getActiveLotteries();

        Assert.assertEquals(expectedLotteries, givenLotteries);
    }

    @Transactional
    @Test
    public void shouldGetActiveLotteries_AfterEndLottery() throws ResourceNotFoundException, UnableToSaveException
    {
        lotteryRepository.deleteAll();

        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);
        Lottery lottery2 = lotteryService.startLotteryByName(LOTTERY_B);

        lotteryService.endLotteryById(lottery.getId());

        Assert.assertEquals(1,lotteryService.getActiveLotteries().size());
    }

    @Transactional
    @Test
    public void shouldThrowException_WhenLotteryIsAlreadyPassive() throws ResourceNotFoundException, UnableToSaveException {
        lotteryRepository.deleteAll();
        Lottery lottery = lotteryService.startLotteryByName(LOTTERY_A);
        lottery.setStatus(LotteryStatus.PASSIVE);

        lotteryService.endLotteryById(lottery.getId());

        Assert.assertEquals(LotteryStatus.PASSIVE, lottery.getStatus());
    }

}
