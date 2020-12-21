package com.spring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "lottery_result")
public class LotteryResult implements Serializable
{
    private static final long serialVersionUID = 3963803585404654754L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Column(unique=true, nullable = false)
    private Long lotteryId;

    @NotEmpty
    private Long winnerLotteryNumber;

    public LotteryResult(Date date, Long lotteryId, Long winnerLotteryNumber)
    {
        this.date = date;
        this.lotteryId = lotteryId;
        this.winnerLotteryNumber = winnerLotteryNumber;
    }
}
