package com.spring.LotterySystem.service;


import com.spring.LotterySystem.model.User;

public interface UserService {

    User findByEmail(String email);

    User createUser(User user);

}
