package com.spring.LotterySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.LotterySystem.model.Role;

public interface RoleRepository extends JpaRepository<Role, String>
{

}