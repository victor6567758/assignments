package com.assignment.telus.todos.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class DAOToDoServiceTest {

  @Autowired
  private DAOToDoService daoToDoService;

  @BeforeEach
  void setUp() {
    daoToDoService.clear();
  }

  @Test
  @Rollback
  void givenNothing_whenReadRow_thenShouldGetInvalidRow() {
    // given

    // when

    // then
    assertThat(daoToDoService.getById(25L).orElse(null)).isNull();
  }
}