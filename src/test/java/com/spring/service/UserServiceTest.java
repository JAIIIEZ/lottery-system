package com.spring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.UserDto;
import com.spring.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest
{
    @Autowired
    private UserService userService;

    @Test
    public void givenNewUser_whenRegistered_thenCorrect()
    {
        final String username = UUID.randomUUID().toString();
        final UserDto userDto = createUserDto(username);

        final User user = userService.createUser(userDto);

        assertNotNull(user);
        assertNotNull(user.getUsername());
        assertEquals(username, user.getUsername());
        assertNotNull(user.getId());
    }

    @Transactional
    @Test
    public void givenDetachedUser_whenServiceLoadById_thenCorrect()
    {
        final User user = registerUser();
        final User user2 = userService.findByUsername(user.getUsername());
        assertEquals(user, user2);
    }


    private UserDto createUserDto(final String username) {
        final UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword("SecretPassword");
        userDto.setPasswordConfirm("SecretPassword");
        userDto.setFirstName("Merve");
        userDto.setLastName("Kaygisiz");
        return userDto;
    }

    private User registerUser()
    {
        final String username = UUID.randomUUID().toString();
        final UserDto userDto = createUserDto(username);
        final User user = userService.createUser(userDto);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(username, user.getUsername());
        return user;
    }

}
