package com.assignment.telus.todos.controller;

import com.assignment.telus.todos.TodoApplication;
import com.assignment.telus.todos.service.TodoService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = TodoApplication.class, webEnvironment = WebEnvironment.MOCK)
@ActiveProfiles("test")
public class BaseContractTest {

  protected MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private TodoService todoService;

  @BeforeEach
  public void setup() {

    mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    RestAssuredMockMvc.mockMvc(mockMvc);

    todoService.clear();

  }
}
