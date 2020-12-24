package com.spring.repository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.spring.utils.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.spring.model.Lottery;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryRepositoryTest {

    @Autowired
    private LotteryRepository lotteryRepository;

    @Before
    public void initEach() {
        lotteryRepository.deleteAll();
    }

    @Test
    public void whenSaved_thenFindsActiveLotteries() {
        Lottery lottery = new Lottery();
        lottery.setStartDate(new Date());
        lottery.setName("test");
        List<Lottery> expected = Arrays.asList(lottery);

        lotteryRepository.save(lottery);

        List<Lottery> actual = lotteryRepository.findLotteriesByEndDateIsNull();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void whenSaved_thenFindsByLotteryId() {
        Lottery lottery = new Lottery();
        lottery.setStartDate(new Date());
        lottery.setName("test");

        lotteryRepository.save(lottery);

        Lottery actual = lotteryRepository.findById(lottery.getId());
        Assert.assertEquals(lottery, actual);
    }

    @Test
    public void whenSaved_thenCountActiveLotteriesByName() {
        Lottery lottery = new Lottery();
        lottery.setStartDate(new Date());
        lottery.setName("test");

        Lottery lottery2 = new Lottery();
        lottery2.setStartDate(DateUtils.atBeginningOfDay(new Date()));
        lottery2.setEndDate(DateUtils.atEndOfDay(new Date()));
        lottery2.setName("test2");

        lotteryRepository.save(lottery);
        lotteryRepository.save(lottery2);

        Long actual = lotteryRepository.countByNameAndEndDateIsNull(lottery.getName());
        Long expected = 1L;
        Assert.assertEquals(expected, actual);
    }


}
