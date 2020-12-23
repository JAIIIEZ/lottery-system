package com.spring.repository;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.spring.model.Role;
import com.spring.model.User;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void initEach() {
        userRepository.deleteAll();
    }

    @Test
    public void whenSaved_thenFindsByUsername() {
        User user = new User();
        user.setUsername("merve");
        user.setPassword("password");
        user.setRole(Arrays.asList(new Role("ADMIN")));
        userRepository.save(user);

        User expected = userRepository.findByUsername(user.getUsername());

        Assert.assertEquals(user, expected);
    }

}

