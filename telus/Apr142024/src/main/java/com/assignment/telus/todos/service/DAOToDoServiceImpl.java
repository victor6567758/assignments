package com.assignment.telus.todos.service;

import com.assignment.telus.todos.dto.ToDoDtoResponse;
import com.assignment.telus.todos.dto.TodoDtoRequest;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class DAOToDoServiceImpl implements DAOToDoService {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public Optional<ToDoDtoResponse> create(TodoDtoRequest toDoDtoRequest) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(
          "insert into todos(description, completion) values ( ?, ? )",
          Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, toDoDtoRequest.getDescription());
      ps.setString(2, toDoDtoRequest.getCompletion().name());

      return ps;
    }, keyHolder);

    long id = keyHolder.getKey().longValue();
    return getById(id);

  }

  @Override
  public Optional<ToDoDtoResponse> update(long id, TodoDtoRequest toDoDtoRequest) {
    jdbcTemplate.update("update todos set description = ?, completion = ? where id = ?",
        toDoDtoRequest.getDescription(), toDoDtoRequest.getCompletion().name(), id);

    return getById(id);
  }


  @Override
  public List<ToDoDtoResponse> getAll() {
    return jdbcTemplate.query("select id, description, completion from todos",
        new BeanPropertyRowMapper<>(ToDoDtoResponse.class));
  }

  @Override
  public Optional<ToDoDtoResponse> getById(Long id) {
    List<ToDoDtoResponse> response = jdbcTemplate.query(
        "select id, description, completion from todos where id = ?",
        new BeanPropertyRowMapper<>(ToDoDtoResponse.class), id);
    return response.isEmpty() ? Optional.empty() : Optional.of(response.get(0));
  }


  @Override
  public boolean deleteById(Long id) {
    return jdbcTemplate.update("delete from todos where id = ?", id) > 0;
  }

  @Override
  public void clear() {
    // truncate is not applicable
    jdbcTemplate.update("DELETE from todos");
  }
}
