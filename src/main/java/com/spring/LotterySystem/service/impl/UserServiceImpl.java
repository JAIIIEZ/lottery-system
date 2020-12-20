package com.spring.LotterySystem.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.LotterySystem.model.Role;
import com.spring.LotterySystem.model.User;
import com.spring.LotterySystem.repository.UserRepository;
import com.spring.LotterySystem.service.UserService;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user)
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(Arrays.asList(new Role("ADMIN")));
        return userRepository.save(user);
    }
}
