package com.te.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.te.flight.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
