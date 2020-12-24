package com.spring;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.spring.controller.UserControllerTest;
import com.spring.repository.LotteryRepositoryTest;
import com.spring.repository.LotteryTicketRepositoryTest;
import com.spring.repository.UserRepositoryTest;
import com.spring.service.LotteryServiceTest;
import com.spring.service.LotteryTicketServiceTest;
import com.spring.service.SecurityServiceTest;
import com.spring.service.UserServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LotteryRepositoryTest.class,
        LotteryTicketRepositoryTest.class,
        UserRepositoryTest.class,
        LotteryServiceTest.class,
        LotteryTicketServiceTest.class,
        SecurityServiceTest.class,
        UserServiceTest.class,
        UserControllerTest.class
})
public class JunitTestSuite {
}
