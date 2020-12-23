package com.spring.repository;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.spring.model.LotteryTicket;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryTicketRepositoryTest {

    @Autowired
    private LotteryTicketRepository lotteryTicketRepository;

    @Before
    public void initEach() {
        lotteryTicketRepository.deleteAll();
    }

    @Test
    public void whenSaved_thenFindsByLotteryNumberAndLotteryId() {
        LotteryTicket ticket = new LotteryTicket();
        ticket.setUsername("merve");
        ticket.setLotteryId(1L);
        ticket.setLotteryNumber(5L);
        ticket.setDate(new Date());

        lotteryTicketRepository.save(ticket);

        LotteryTicket actual = lotteryTicketRepository.findByLotteryNumberAndLotteryId(ticket.getLotteryNumber(), ticket.getLotteryId());
        Assert.assertEquals(ticket, actual);
    }

    @Test
    public void whenSaved_thenCountsByLotteryId() {
        LotteryTicket ticket = new LotteryTicket();
        ticket.setUsername("merve");
        ticket.setLotteryId(1L);
        ticket.setLotteryNumber(5L);
        ticket.setDate(new Date());

        LotteryTicket ticket2 = new LotteryTicket();
        ticket2.setUsername("merve");
        ticket2.setLotteryId(2L);
        ticket2.setLotteryNumber(4L);
        ticket2.setDate(new Date());

        lotteryTicketRepository.save(ticket);
        lotteryTicketRepository.save(ticket2);

        Long expected = lotteryTicketRepository.countLotteryTicketByLotteryId(ticket.getLotteryId());
        Long actual = 1L;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void whenSaved_thenFindsFirstByLotteryIdOrderByLotteryNumberDesc() {
        LotteryTicket ticket = new LotteryTicket();
        ticket.setUsername("merve");
        ticket.setLotteryId(1L);
        ticket.setLotteryNumber(4L);
        ticket.setDate(new Date());

        LotteryTicket ticket2 = new LotteryTicket();
        ticket2.setUsername("ezgi");
        ticket2.setLotteryId(1L);
        ticket2.setLotteryNumber(5L);
        ticket2.setDate(new Date());

        LotteryTicket ticket3 = new LotteryTicket();
        ticket3.setUsername("ezgi");
        ticket3.setLotteryId(1L);
        ticket3.setLotteryNumber(6L);
        ticket3.setDate(new Date());

        lotteryTicketRepository.save(ticket);
        lotteryTicketRepository.save(ticket2);
        lotteryTicketRepository.save(ticket3);

        LotteryTicket actual = lotteryTicketRepository.findFirstByLotteryIdOrderByLotteryNumberDesc(1L);

        Assert.assertEquals(ticket3, actual);
    }
}
