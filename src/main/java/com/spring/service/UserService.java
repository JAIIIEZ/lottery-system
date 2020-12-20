package com.spring.service;

import com.spring.model.User;

public interface UserService {

    User findByEmail(String email);

    User createUser(User user);

}
