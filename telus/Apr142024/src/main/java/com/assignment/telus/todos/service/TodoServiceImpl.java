package com.assignment.telus.todos.service;

import com.assignment.telus.todos.dto.ToDoDtoResponse;
import com.assignment.telus.todos.dto.TodoDtoRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

  private final DAOToDoService daoToDoService;

  @Override
  public ToDoDtoResponse findById(long id) {
    return null;
  }

  @Override
  public List<ToDoDtoResponse> findAll() {
    return List.of();
  }

  @Override
  public ToDoDtoResponse create(TodoDtoRequest todoDtoRequest) {
    return null;
  }

  @Override
  public ToDoDtoResponse update(long id, TodoDtoRequest todoDtoRequest) {
    return null;
  }

  @Override
  public void delete(long id) {

  }
}
