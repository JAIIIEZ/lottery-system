package com.spring;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.spring.repository.LotteryRepositoryTest;
import com.spring.repository.LotteryResultRepositoryTest;
import com.spring.repository.LotteryTicketRepositoryTest;
import com.spring.repository.UserRepositoryTest;
import com.spring.service.LotteryResultServiceTest;
import com.spring.service.LotteryServiceTest;
import com.spring.service.LotteryTicketServiceTest;
import com.spring.service.SecurityServiceTest;
import com.spring.service.UserServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LotteryResultServiceTest.class,
        LotteryServiceTest.class,
        LotteryTicketServiceTest.class,
        SecurityServiceTest.class,
        UserServiceTest.class,
        LotteryRepositoryTest.class,
        LotteryTicketRepositoryTest.class,
        LotteryResultRepositoryTest.class,
        UserRepositoryTest.class
})
public class JunitTestSuite
{
}
