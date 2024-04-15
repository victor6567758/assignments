package com.assignment.telus.todos.service;

import com.assignment.telus.todos.dto.ToDoDtoResponse;
import com.assignment.telus.todos.dto.TodoDtoRequest;
import com.google.common.annotations.VisibleForTesting;
import java.util.List;

public interface TodoService {

  ToDoDtoResponse findById(long id);

  List<ToDoDtoResponse> findAll();

  ToDoDtoResponse create(TodoDtoRequest todoDtoRequest);

  ToDoDtoResponse update(long id, TodoDtoRequest todoDtoRequest);

  void delete(long id);

  @VisibleForTesting
  void clear();

}
