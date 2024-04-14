package com.assignment.telus.todos.service;

import com.assignment.telus.todos.dto.ToDoDtoResponse;
import com.assignment.telus.todos.dto.TodoDtoRequest;
import java.util.List;
import java.util.Optional;

public interface DAOToDoService {
   Optional<ToDoDtoResponse> create(TodoDtoRequest toDoDtoRequest);

   Optional<ToDoDtoResponse> update(long id, TodoDtoRequest toDoDtoRequest);

   List<ToDoDtoResponse> getAll();

   Optional<ToDoDtoResponse> getById(Long id);

   boolean deleteById(Long id);

}
