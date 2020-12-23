package com.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LotteryResultDto {

    private String date;
    private String winnerLotteryNumber;
}
