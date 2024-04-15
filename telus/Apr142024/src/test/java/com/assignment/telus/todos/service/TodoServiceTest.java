package com.assignment.telus.todos.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.assignment.telus.todos.dto.Completion;
import com.assignment.telus.todos.dto.ToDoDtoResponse;
import com.assignment.telus.todos.dto.TodoDtoRequest;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest

class TodoServiceTest {
  @Autowired
  private TodoService todoService;

  @BeforeEach
  void setUp() {
    todoService.clear();
  }

  @Test
  void givenInsertRow_whenQueryRow_thenShouldGetValidRow() {
    // given
    ToDoDtoResponse response =
        todoService.create(new TodoDtoRequest("description1", Completion.COMPLETED));

    // when

    // then
    assertThat(response.getCompletion()).isEqualTo(Completion.COMPLETED);
    assertThat(response.getDescription()).isEqualTo("description1");
    assertThat(response.getId()).isGreaterThan(0);

  }

  @Test
  void givenInsertRow_whenRemoveRow_thenShouldGetInvalidRowException() {
    // given
    ToDoDtoResponse response =
        todoService.create(new TodoDtoRequest("description2", Completion.NOT_COMPLETED));

    // when
    todoService.delete(response.getId());

    // then
    assertThrows(IllegalArgumentException.class, () -> {
      todoService.findById(response.getId());
    });

  }

  @Test
  @SuppressWarnings("unchecked")
  void givenInsertSeveralRows_whenQueryRows_thenShouldGetRows() {
    // given
    ToDoDtoResponse response1 =
        todoService.create(new TodoDtoRequest("description1", Completion.NOT_COMPLETED));
    ToDoDtoResponse response2 =
        todoService.create(new TodoDtoRequest("description2", Completion.COMPLETED));
    ToDoDtoResponse response3 =
        todoService.create(new TodoDtoRequest("description3", Completion.NOT_COMPLETED));

    // when
    List<ToDoDtoResponse> responses = todoService.findAll();

    List<Pair<String, Completion>> returnedPairs = responses.stream()
        .map(n -> new ImmutablePair<>(n.getDescription(), n.getCompletion()))
        .sorted().collect(Collectors.toList());


    List<Pair<String, Completion>> expectedPairs = Stream.of(response1, response2, response3)
        .map(n -> new ImmutablePair<>(n.getDescription(), n.getCompletion()))
        .sorted().collect(Collectors.toList());

    // then
    assertThat(returnedPairs).containsExactly(expectedPairs.toArray(Pair[]::new));

  }

  @Test
  void givenInsertSeveralRows_whenUpdateRow_thenShouldGetUpdatedRow() {
    // given
    ToDoDtoResponse response1 =
        todoService.create(new TodoDtoRequest("description1", Completion.NOT_COMPLETED));

    ToDoDtoResponse response2 =
        todoService.create(new TodoDtoRequest("description2", Completion.COMPLETED));

    ToDoDtoResponse response3 =
        todoService.create(new TodoDtoRequest("description3", Completion.NOT_COMPLETED));

    // when
    ToDoDtoResponse toUpdate = todoService.update(response2.getId(),
        new TodoDtoRequest("description2_updated", Completion.NOT_COMPLETED));

    // then
    assertThat(toUpdate.getDescription()).isEqualTo("description2_updated");
    assertThat(toUpdate.getCompletion()).isEqualTo(Completion.NOT_COMPLETED);

  }

  @Test
  void givenInsertSeveralRows_whenFindRowById_thenShouldGetvalidRowRow() {
    // given
    ToDoDtoResponse response1 =
        todoService.create(new TodoDtoRequest("description1", Completion.NOT_COMPLETED));

    ToDoDtoResponse response2 =
        todoService.create(new TodoDtoRequest("description2", Completion.COMPLETED));

    ToDoDtoResponse response3 =
        todoService.create(new TodoDtoRequest("description3", Completion.NOT_COMPLETED));

    // when
    ToDoDtoResponse toUpdate = todoService.findById(response2.getId());

    // then
    assertThat(toUpdate.getId()).isEqualTo(response2.getId());
    assertThat(toUpdate.getDescription()).isEqualTo(response2.getDescription());
    assertThat(toUpdate.getCompletion()).isEqualTo(response2.getCompletion());

  }
}