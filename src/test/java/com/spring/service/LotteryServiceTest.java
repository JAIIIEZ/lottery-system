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

    @Test
    public void shouldStartLottery() {
        String lotteryName = "Lottery A";
        Lottery lottery = lotteryService.startLotteryByName(lotteryName);

        Assert.assertNotNull(lottery);
        Assert.assertNotNull(lottery.getId());
        Assert.assertEquals(LotteryStatus.ACTIVE, lottery.getStatus());
        Assert.assertEquals(lotteryName, lottery.getName());
    }

    @Transactional
    @Test
    public void shouldFindLotteryById() throws ResourceNotFoundException
    {
        String lotteryName = "Lottery A";
        Lottery lottery = lotteryService.startLotteryByName(lotteryName);

        Lottery lottery2 = lotteryService.findById(lottery.getId());

        Assert.assertEquals(lottery, lottery2);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowException_WhenLotteryCouldntFind() throws ResourceNotFoundException {
        lotteryService.findById(null);
    }

    @Transactional
    @Test
    public void shouldEndLotteryById() throws ResourceNotFoundException
    {
        String lotteryName = "Lottery A";
        Lottery lottery = lotteryService.startLotteryByName(lotteryName);

        lotteryService.endLotteryById(lottery.getId());

        Assert.assertEquals(LotteryStatus.PASSIVE, lottery.getStatus());
    }

    @Test
    public void whenDeleteAllFromRepository_thenRepositoryShouldBeEmpty() {
        lotteryRepository.deleteAll();
        Assert.assertEquals(lotteryRepository.count(), 0);
    }

    @Transactional
    @Test
    public void shouldGetAllActiveLotteries() {
        lotteryRepository.deleteAll();
        List<Lottery> expectedLotteries = new ArrayList<>();
        expectedLotteries.add(lotteryService.startLotteryByName("Lottery A"));
        expectedLotteries.add(lotteryService.startLotteryByName("Lottery B"));

        List<Lottery> givenLotteries = lotteryService.getActiveLotteries();

        Assert.assertEquals(expectedLotteries, givenLotteries);
    }

    @Transactional
    @Test
    public void shouldGetActiveLotteries_AfterEndLottery() throws ResourceNotFoundException
    {
        lotteryRepository.deleteAll();

        Lottery lottery = lotteryService.startLotteryByName("Lottery A");
        Lottery lottery2 = lotteryService.startLotteryByName("Lottery B");

        lotteryService.endLotteryById(lottery.getId());

        Assert.assertEquals(lotteryService.getActiveLotteries().size(), 1);
    }

    @Transactional
    @Test
    public void shouldThrowException_WhenLotteryIsAlreadyPassive() throws ResourceNotFoundException {
        Lottery lottery = lotteryService.startLotteryByName("Lottery A");
        lottery.setStatus(LotteryStatus.PASSIVE);

        lotteryService.endLotteryById(lottery.getId());

        Assert.assertEquals(LotteryStatus.PASSIVE, lottery.getStatus());
    }

}
