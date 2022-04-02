package com.hsd.exam.jpaBoard.User.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private String email;
    private String name;
    @JsonIgnore // 패스워드를 안보이게 하는 기능 json문서중 일부 무시(안보이게)
    private String password;
}