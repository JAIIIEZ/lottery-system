package com.spring.service;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.dto.LotteryResultDto;
import com.spring.dto.UserDto;
import com.spring.exception.LotteryAlreadyPassiveException;
import com.spring.exception.LotteryIsNotFinishException;
import com.spring.exception.ResourceNotFoundException;
import com.spring.exception.UnableToSaveException;
import com.spring.exception.UnableToSubmitLotteryTicket;
import com.spring.model.Lottery;
import com.spring.model.LotteryTicket;
import com.spring.repository.LotteryRepository;
import com.spring.repository.LotteryTicketRepository;
import com.spring.repository.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryTicketServiceTest {

    @Autowired
    private LotteryTicketService lotteryTicketService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private UserService userService;

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LotteryTicketRepository lotteryTicketRepository;

    @Autowired
    private LotteryResultService lotteryResultService;

    private final static int NUM_THREADS = 1000;

    @Before
    public void initEach() {
        lotteryRepository.deleteAll();
        userRepository.deleteAll();
        lotteryTicketRepository.deleteAll();
    }

    @Test
    public void shouldSubmitLotteryTicket() throws UnableToSaveException, ResourceNotFoundException, UnableToSubmitLotteryTicket {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        createUser("mervekaygisiz");

        LotteryTicket ticket = lotteryTicketService.submitLotteryTicketSync(lottery.getId(), "mervekaygisiz");
        assertNotNull(ticket);
        assertNotNull(ticket.getId());
    }

    @Test
    public void shouldSubmitManyLotteryTicket() throws UnableToSaveException, ResourceNotFoundException, UnableToSubmitLotteryTicket {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        createUser("mervekaygisiz");

        LotteryTicket ticket = lotteryTicketService.submitLotteryTicketSync(lottery.getId(), "mervekaygisiz");
        LotteryTicket ticket2 = lotteryTicketService.submitLotteryTicketSync(lottery.getId(), "mervekaygisiz");

        assertNotNull(ticket);
        assertNotNull(ticket.getId());
        assertNotNull(ticket2);
        assertNotNull(ticket2.getId());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowException_WhenUsernameIsInvalid() throws UnableToSaveException, ResourceNotFoundException, UnableToSubmitLotteryTicket {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        lotteryTicketService.submitLotteryTicketSync(lottery.getId(), null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowException_WhenUsernameIsNotRegistered() throws UnableToSaveException, ResourceNotFoundException, UnableToSubmitLotteryTicket {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        lotteryTicketService.submitLotteryTicketSync(lottery.getId(), "test");
    }

    @Test(expected = UnableToSubmitLotteryTicket.class)
    public void shouldThrowException_WhenLotteryIsFinish() throws UnableToSaveException, ResourceNotFoundException, UnableToSubmitLotteryTicket, LotteryAlreadyPassiveException {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        lotteryService.endLotteryAndSelectLotteryWinner(lottery.getId());

        lotteryTicketService.submitLotteryTicketSync(lottery.getId(), null);
    }

    @Test
    public void givenMultiThread_whenSubmitLotteryTicketSync() throws UnableToSaveException, InterruptedException {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        createUser("mervekaygisiz");

        generateMultiThreadSubmitLottery(lottery.getId());

        Assert.assertEquals(lotteryTicketRepository.count(), NUM_THREADS);
    }

    private void generateMultiThreadSubmitLottery(Long lotteryId) throws UnableToSaveException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                try {
                    lotteryTicketService.submitLotteryTicketSync(lotteryId, "mervekaygisiz");
                } catch (ResourceNotFoundException e) {
                    e.printStackTrace();
                } catch (UnableToSubmitLotteryTicket unableToSubmitLotteryTicket) {
                    unableToSubmitLotteryTicket.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void shouldSaveLotteryResult_WhenSelectLotteryWinner() throws UnableToSaveException, ResourceNotFoundException, InterruptedException, LotteryIsNotFinishException {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        createUser("mervekaygisiz");

        generateMultiThreadSubmitLottery(lottery.getId());
        lotteryTicketService.selectRandomLotteryWinnerAndSaveResult(lottery.getId());

        LotteryResultDto result = lotteryResultService.getLotteryResultByLotteryId(lottery.getId());
        assertNotNull(result);
        Assert.assertTrue(isNumeric(result.getWinnerLotteryNumber()));
    }

    @Test
    public void shouldSaveLotteryResult_WhenLotteryWinnerNotExist() throws UnableToSaveException, ResourceNotFoundException, LotteryIsNotFinishException {
        Lottery lottery = lotteryService.startLotteryByName("lotteryA");
        createUser("mervekaygisiz");

        lotteryTicketService.selectRandomLotteryWinnerAndSaveResult(lottery.getId());

        LotteryResultDto result = lotteryResultService.getLotteryResultByLotteryId(lottery.getId());
        Assert.assertFalse(isNumeric(result.getWinnerLotteryNumber()));
    }

    private void createUser(final String username) {
        final UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword("SecretPassword");
        userDto.setPasswordConfirm("SecretPassword");
        userDto.setFirstName("Merve");
        userDto.setLastName("Kaygisiz");
        userService.createUser(userDto);
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
