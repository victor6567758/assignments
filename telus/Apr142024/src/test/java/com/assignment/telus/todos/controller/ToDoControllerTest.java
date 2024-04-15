package com.assignment.telus.todos.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.assignment.telus.todos.TodoApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Sql({"classpath:sql-test-scripts/insert-data.sql"})
@SpringBootTest(classes = TodoApplication.class)
@ActiveProfiles("test")
class ToDoControllerTest {

  protected MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
        .build();
  }

  @Test
  void givenNothing_whenReadRow_thenShouldGetAlreadyStoredRow() throws Exception {
    // given

   // when
    mockMvc.perform(get("/todos/1"))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.description", is("description1")))
        .andExpect(jsonPath("$.completion", is("COMPLETED")));
  }
}