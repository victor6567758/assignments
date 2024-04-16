package com.assignment.telus.todos.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.assignment.telus.todos.dto.Completion;
import com.assignment.telus.todos.dto.ToDoDtoResponse;
import com.assignment.telus.todos.dto.TodoDtoRequest;
import java.util.List;
import java.util.Optional;
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

  @Test
  @Rollback
  void givenInsertRow_whenReadInvalidRow_thenShouldGetInvalidRow() {
    // given
    Optional<ToDoDtoResponse> createResult =
        daoToDoService.create(new TodoDtoRequest("d1", Completion.COMPLETED));
    assertThat(createResult.isPresent()).isTrue();
    assertThat(createResult.get().getDescription()).isEqualTo("d1");


    // when
    Optional<ToDoDtoResponse>  getResponse = daoToDoService.getById(createResult.get().getId() + 1);

    // then
    assertThat(getResponse.isPresent()).isFalse();
  }

  @Test
  @Rollback
  void givenInsertRow_whenThisRow_thenShouldGetInsertedRow() {
    // given
    Optional<ToDoDtoResponse> createResult =
        daoToDoService.create(new TodoDtoRequest("d256", Completion.COMPLETED));
    assertThat(createResult.isPresent()).isTrue();
    assertThat(createResult.get().getDescription()).isEqualTo("d256");

    //when
    Optional<ToDoDtoResponse> getResponse = daoToDoService.getById(createResult.get().getId());
    assertThat(getResponse.isPresent()).isTrue();
    assertThat(getResponse.get().getDescription()).isEqualTo("d256");
  }

  @Test
  @Rollback
  void givenInsertRow_whenReadAllRows_thenShouldFindInsertedRow() {
    // given
    Optional<ToDoDtoResponse> createResult =
        daoToDoService.create(new TodoDtoRequest("d1", Completion.COMPLETED));
    assertThat(createResult.isPresent()).isTrue();
    assertThat(createResult.get().getDescription()).isEqualTo("d1");

    //when
    List<ToDoDtoResponse> allRows = daoToDoService.getAll();

    // then
    assertThat(allRows).isNotEmpty();
    assertThat(allRows.stream().filter(e -> "d1".equals(e.getDescription())).count()).isGreaterThan(0L);
  }
}