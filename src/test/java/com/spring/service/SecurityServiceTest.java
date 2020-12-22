package com.spring.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.dto.UserDto;
import com.spring.model.User;
import com.spring.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityServiceTest
{
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void initEach(){
        userRepository.deleteAll();
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowException_WhenProfileIsNotExist() {
        securityService.autoLogin("test", "password");
    }

    @Test(expected = BadCredentialsException.class)
    public void shouldThrowException_WhenPasswordIsInvalid() {
        User user = createUser("ezgi");
        securityService.autoLogin("ezgi", "password");
    }

    private User createUser(final String username) {
        final UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword("SecretPassword");
        userDto.setPasswordConfirm("SecretPassword");
        userDto.setFirstName("Merve");
        userDto.setLastName("Kaygisiz");
        return userService.createUser(userDto);
    }
}
