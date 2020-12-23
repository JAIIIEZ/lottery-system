package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}