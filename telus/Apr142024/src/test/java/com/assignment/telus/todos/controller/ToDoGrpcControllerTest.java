package com.assignment.telus.todos.controller;


import static org.assertj.core.api.Assertions.assertThat;


import com.assignment.telus.todos.TodoApplication;
import com.assignment.telus.todos.model.Dummy;
import com.assignment.telus.todos.model.ToDoResponse;
import com.assignment.telus.todos.model.ToDoResponseList;
import com.assignment.telus.todos.model.ToDoServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql-test-scripts/insert-data.sql"})
@SpringBootTest(properties = {
    "grpc.server.inProcessName=test",
    "grpc.server.port=-1",
    "grpc.client.inProcess.address=in-process:test"
})
@ActiveProfiles("test")
@DirtiesContext
class ToDoGrpcControllerTest {

  @GrpcClient("test")
  private ToDoServiceGrpc.ToDoServiceBlockingStub service;


  @Test
  void givenDefaultSetup_whenReadAllRows_thenShouldGetCorrectRows() {
    // given

    // when
    ToDoResponseList toDoResponseList = service.findAll(Dummy.newBuilder().build());

    // then
    assertThat(toDoResponseList.getListList()).hasSize(4);
    assertThat(toDoResponseList.getListList().stream().map(ToDoResponse::getDescription).toList())
        .containsExactlyInAnyOrder("description1", "description2", "description3", "description5");

    assertThat(toDoResponseList.getListList().stream().map(ToDoResponse::getId).toList())
        .containsExactlyInAnyOrder(1L, 2L, 3L, 5L);
  }

}