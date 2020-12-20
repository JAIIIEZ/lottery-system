package com.spring.LotterySystem.service;

public interface SecurityService {

    String findLoggedInEmail();

    void autoLogin(String email, String password);
}
