package com.assignment.telus.todos.controller;


import static org.assertj.core.api.Assertions.assertThat;

import com.assignment.telus.todos.TodoApplication;
import com.assignment.telus.todos.model.Dummy;
import com.assignment.telus.todos.model.ToDoResponseList;
import com.assignment.telus.todos.model.ToDoServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql-test-scripts/insert-data.sql"})
@SpringBootTest(classes = TodoApplication.class, properties = {
    "grpc.server.inProcessName=test",
    "grpc.server.port=-1",
    "grpc.client.inProcess.address=in-process:test"
})
@ActiveProfiles("test")
@DirtiesContext
class ToDoGrpcControllerTest {

  @GrpcClient("inProcess")
  ToDoServiceGrpc.ToDoServiceBlockingStub service;


  @Test
  void givenNothing_whenReadRow_thenShouldGetAlreadyStoredRow() {
    ToDoResponseList toDoResponseList = service.findAll(Dummy.newBuilder().build());
    assertThat(toDoResponseList).isNotNull();

  }

}