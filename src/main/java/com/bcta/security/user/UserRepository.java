package com.bcta.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // This method is to get the user details with respect to email
    Optional<User> findByEmail(String email);
}
