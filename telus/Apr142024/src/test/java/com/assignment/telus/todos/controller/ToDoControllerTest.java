package com.assignment.telus.todos.controller;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.assignment.telus.todos.TodoApplication;
import com.assignment.telus.todos.dto.Completion;
import com.assignment.telus.todos.dto.ToDoDtoResponse;
import com.assignment.telus.todos.dto.TodoDtoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.NumberUtils;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

@Sql({"classpath:sql-test-scripts/insert-data.sql"})
@SpringBootTest(classes = TodoApplication.class)
@ActiveProfiles("test")
class ToDoControllerTest {

  protected MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

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

  @Test
  void givenNothing_whenReadRow_thenShouldGetSeveralAlreadyStoredRows() throws Exception {
    // given

    // when
    mockMvc.perform(get("/todos"))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[*].id", hasItems(1, 2, 3, 5)))
        .andExpect(jsonPath("$.[*].description", hasItems("description1", "description2",
            "description3", "description5")))
        .andExpect(jsonPath("$.[*].completion", hasItems("COMPLETED",
            "NOT_COMPLETED", "NOT_COMPLETED", "COMPLETED")));
  }

  @Test
  void givenInsertingRow_whenReadNewRow_thenShouldGetInsertedRow() throws Exception {
    // given
    String content = mapper.writeValueAsString(new TodoDtoRequest("new_description", Completion.COMPLETED));
    MvcResult result = mockMvc.perform(
        post("/todos")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andReturn();

    ToDoDtoResponse response =
        mapper.readValue(result.getResponse().getContentAsString(), ToDoDtoResponse.class);
    assertThat(response.getDescription()).isEqualTo("new_description");

    // when
    mockMvc.perform(get("/todos/" + response.getId()))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.description", is("new_description")))
        .andExpect(jsonPath("$.completion", is("COMPLETED")));

  }

  @Test
  void givenInsertingAndUpdatingRow_whenReadNewUpdatedRow_thenShouldRaise4xxNotSupported() throws Exception {
    // given
    String content = mapper.writeValueAsString(new TodoDtoRequest("new_description2", Completion.NOT_COMPLETED));
    MvcResult result = mockMvc.perform(
            post("/todos")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andReturn();

    ToDoDtoResponse response =
        mapper.readValue(result.getResponse().getContentAsString(), ToDoDtoResponse.class);
    assertThat(response.getDescription()).isEqualTo("new_description2");

    // when
    String contentUpdate = mapper.writeValueAsString(new TodoDtoRequest("new_description4", Completion.NOT_COMPLETED));
    MvcResult resultUpdate = mockMvc.perform(
            patch("/todos")
                .header("Allow", "POST, GET, PATCH")
                .content(contentUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andReturn();



  }

}