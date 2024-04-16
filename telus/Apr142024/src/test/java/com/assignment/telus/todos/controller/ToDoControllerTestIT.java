package com.assignment.telus.todos.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.assignment.telus.todos.TodoApplication;
import com.assignment.telus.todos.dto.Completion;
import com.assignment.telus.todos.dto.ToDoDtoResponse;
import com.assignment.telus.todos.dto.TodoDtoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql-test-scripts/insert-data.sql"})
@SpringBootTest(classes = TodoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ToDoControllerTestIT {

  @LocalServerPort
  private int localServerPort;

  @Autowired
  private TestRestTemplate client;

  @Autowired
  private ObjectMapper mapper;


  @Test
  @SuppressWarnings("unchecked")
  public void givenNothing_whenReadAllRows_thenReturnNothingVanilla()
      throws URISyntaxException {
    // given

    // when
    String baseUrl = "http://localhost:" + localServerPort + "/todos";
    URI uri = new URI(baseUrl);
    ResponseEntity<ToDoDtoResponse[]> response = client.getForEntity(uri, ToDoDtoResponse[].class);
    ToDoDtoResponse[] returnedData = response.getBody();

    assertThat(Stream.of(returnedData).map(ToDoDtoResponse::getDescription).toList())
        .containsExactlyInAnyOrder("description1", "description2",
            "description3", "description5");

  }

  @Test
  public void givenInsertingAndUpdatingRow_whenReadNewUpdatedRow_thenShouldReadUpdatedRow()
      throws URISyntaxException, JsonProcessingException {
    // given
    String baseUrl = "http://localhost:" + localServerPort + "/todos";
    URI uri = new URI(baseUrl);

    ToDoDtoResponse responseCreate = client.postForObject(uri,
        new TodoDtoRequest("new_description2", Completion.NOT_COMPLETED),
        ToDoDtoResponse.class);
    assertThat(responseCreate.getDescription()).isEqualTo("new_description2");


    ToDoDtoResponse responseUpdate = client.patchForObject(uri + "/" + responseCreate.getId(),
        new TodoDtoRequest("new_description233", Completion.COMPLETED), ToDoDtoResponse.class);
    assertThat(responseUpdate.getDescription()).isEqualTo("new_description233");

    //when
    ToDoDtoResponse readResponse = client.getForObject(uri + "/" + responseUpdate.getId(),
        ToDoDtoResponse.class);

    // then
    assertThat(readResponse.getDescription()).isEqualTo("new_description233");
    assertThat(readResponse.getCompletion()).isEqualTo(Completion.COMPLETED);
    assertThat(readResponse.getId()).isEqualTo(responseCreate.getId());

  }

  @Test
  public void givenInsertingAndDeletingRow_whenReadThisRow_thenShouldGetInvalidResponse()
      throws URISyntaxException, JsonProcessingException {
    // given
    String baseUrl = "http://localhost:" + localServerPort + "/todos";
    URI uri = new URI(baseUrl);

    ToDoDtoResponse responseCreate = client.postForObject(uri,
        new TodoDtoRequest("new_description23", Completion.NOT_COMPLETED),
        ToDoDtoResponse.class);
    assertThat(responseCreate.getDescription()).isEqualTo("new_description23");


    client.delete(uri + "/" + responseCreate.getId());

    //when
    ResponseEntity<ToDoDtoResponse> readResponse = client.getForEntity(uri + "/" + responseCreate.getId(),
        ToDoDtoResponse.class);

    HttpStatusCode code = readResponse.getStatusCode();
    assertThat(code).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  public void givenInserting2Rows_whenReadingAll_thenShouldGetRows() throws URISyntaxException {
    // given
    String baseUrl = "http://localhost:" + localServerPort + "/todos";
    URI uri = new URI(baseUrl);

    ToDoDtoResponse responseCreate1 = client.postForObject(uri,
        new TodoDtoRequest("new_description2445", Completion.NOT_COMPLETED),
        ToDoDtoResponse.class);
    assertThat(responseCreate1.getDescription()).isEqualTo("new_description2445");

    ToDoDtoResponse responseCreate2 = client.postForObject(uri,
        new TodoDtoRequest("new_description2333", Completion.NOT_COMPLETED),
        ToDoDtoResponse.class);
    assertThat(responseCreate2.getDescription()).isEqualTo("new_description2333");


    // when
    ResponseEntity<ToDoDtoResponse[]> response = client.getForEntity(uri, ToDoDtoResponse[].class);
    assertThat(response).isNotNull();
    ToDoDtoResponse[] returnedData = response.getBody();

    //then
    assertThat(Stream.of(returnedData).map(ToDoDtoResponse::getDescription).toList())
        .contains("new_description2445", "new_description2333");

  }

}
