package com.hsd.exam.jpaBoard.User.dao;

import com.hsd.exam.jpaBoard.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}