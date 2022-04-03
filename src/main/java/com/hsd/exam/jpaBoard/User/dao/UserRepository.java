package com.hsd.exam.jpaBoard.User.dao;

import com.hsd.exam.jpaBoard.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);//이렇게 만들어서 사용가능

    Optional<User> findByEmail(String email);
}