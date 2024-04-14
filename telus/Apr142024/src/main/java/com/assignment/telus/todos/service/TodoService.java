package com.assignment.telus.todos.service;

import com.assignment.telus.todos.dto.ToDoDtoResponse;
import com.assignment.telus.todos.dto.TodoDtoRequest;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

public interface TodoService {
  ToDoDtoResponse findById(long id);

  List<ToDoDtoResponse> findAll();

  ToDoDtoResponse create(TodoDtoRequest todoDtoRequest);

  ToDoDtoResponse update(long id, TodoDtoRequest todoDtoRequest);

  void delete(long id);

  void clear();

}
