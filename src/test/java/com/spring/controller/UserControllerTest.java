package com.spring.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.spring.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void init() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldUserRegister() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/register")
                .accept(MediaType.APPLICATION_JSON)
                .content(
                        "{\"firstName\":\"Merve\"," +
                        "\"lastName\":\"Kaygısız\"," +
                        "\"username\":\"merve832\"," +
                        "\"password\":\"password\"," +
                        "\"passwordConfirm\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON);

        String expectedData = "Thanks For Registration!!!";
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(expectedData));

    }

    @Test
    public void givenUserRegistered_whenDuplicatedRegister_thenCorrect() throws Exception
    {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/register")
                .accept(MediaType.APPLICATION_JSON)
                .content(
                        "{\"firstName\":\"Merve\"," +
                                "\"lastName\":\"Kaygısız\"," +
                                "\"username\":\"merve87442\"," +
                                "\"password\":\"password\"," +
                                "\"passwordConfirm\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request);
        mockMvc.perform(request)
                .andExpect(status().isAlreadyReported());

    }

}
