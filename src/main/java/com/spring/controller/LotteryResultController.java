package com.spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.LotteryResultDto;
import com.spring.exception.LotteryIsNotFinishException;
import com.spring.exception.ResourceNotFoundException;
import com.spring.service.LotteryResultService;

@RestController
@RequestMapping("/lotteryResult")
public class LotteryResultController {

    @Autowired
    private LotteryResultService lotteryResultService;

    @GetMapping(value = "/{lotteryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LotteryResultDto getLotteryResultsByDateAndLotteryId(@PathVariable("lotteryId") Long lotteryId) throws ResourceNotFoundException, LotteryIsNotFinishException {
        return lotteryResultService.getLotteryResultByLotteryId(lotteryId);
    }
}
