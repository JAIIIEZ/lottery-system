package com.spring.service;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.spring.exception.ResourceNotFoundException;
import com.spring.model.LotteryResult;
import com.spring.utils.DateUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryResultServiceTest
{
    @Autowired
    private LotteryResultService lotteryResultService;

    @Test
    public void shouldSaveLotteryResult() {
        LotteryResult result = createLotteryResult(22L);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldntSaveLotteryResultForSameLotteryId() {
        createLotteryResult(36L);
        createLotteryResult(36L);
    }

    @Transactional
    @Test
    public void shouldGiveLotteryResult_WhenLotteryIdIsValid() throws ResourceNotFoundException
    {
        final LotteryResult result = createLotteryResult(67L);
        final LotteryResult result2 = lotteryResultService.getLotteryResultByLotteryId(67L);

        assertEquals(result, result2);
    }

    @Transactional
    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowException_WhenLotteryIdIsInvalid() throws ResourceNotFoundException {
        LotteryResult result = lotteryResultService.getLotteryResultByLotteryId(null);
    }

    private LotteryResult createLotteryResult(Long lotteryId) {
        LotteryResult result = generateLotteryResult(lotteryId);
        return lotteryResultService.saveLotteryResult(result);
    }

    private LotteryResult generateLotteryResult(Long lotteryId) {
        LotteryResult result = new LotteryResult();
        result.setLotteryId(lotteryId);
        result.setWinnerLotteryNumber(3L);
        result.setDate(DateUtils.yesterday());
        return result;
    }

}
