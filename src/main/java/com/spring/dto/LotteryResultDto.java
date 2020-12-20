package com.spring.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LotteryResultDto
{
    private Date date;
    private String lotteryName;
    private Long winnerLotteryNumber;
}
