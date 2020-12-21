package com.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lottery_ticket")
public class LotteryTicket implements Serializable
{
    private static final long serialVersionUID = -1505299657602326790L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lottery_id")
    private Long lotteryId;

    @Column(name = "username")
    private String username;

    @Column(name = "lottery_num")
    private Long lotteryNumber;
}
