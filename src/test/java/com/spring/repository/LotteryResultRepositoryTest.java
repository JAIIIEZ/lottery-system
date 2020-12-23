package com.spring.repository;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.spring.model.LotteryResult;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LotteryResultRepositoryTest {

    @Autowired
    private LotteryResultRepository lotteryResultRepository;

    @Test
    public void whenSaved_thenFindsByLotteryId() {
        LotteryResult lotteryResult = new LotteryResult(
                new Date(),
                1L,
                3L);
        lotteryResultRepository.save(lotteryResult);

        LotteryResult result = lotteryResultRepository.findByLotteryId(lotteryResult.getLotteryId());
        Assert.assertNotNull(result);
    }
}
