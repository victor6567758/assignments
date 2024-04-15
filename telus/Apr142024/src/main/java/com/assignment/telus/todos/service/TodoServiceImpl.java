package com.assignment.telus.todos.service;

import com.assignment.telus.todos.dto.ToDoDtoResponse;
import com.assignment.telus.todos.dto.TodoDtoRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

  private final DAOToDoService daoToDoService;

  @Transactional(readOnly = true)
  @Override
  public ToDoDtoResponse findById(long id) {
    return daoToDoService.getById(id)
        .orElseThrow(() -> new IllegalArgumentException("Cannot find the row by id: " + id));
  }

  @Transactional(readOnly = true)
  @Override
  public List<ToDoDtoResponse> findAll() {
    return daoToDoService.getAll();
  }

  @Override
  public ToDoDtoResponse create(TodoDtoRequest todoDtoRequest) {
    return daoToDoService.create(todoDtoRequest)
        .orElseThrow(() -> new IllegalArgumentException("Cannot create the new row"));
  }

  @Override
  public ToDoDtoResponse update(long id, TodoDtoRequest todoDtoRequest) {
    return daoToDoService.update(id, todoDtoRequest)
        .orElseThrow(() -> new IllegalArgumentException("Cannot update the row by id: " + id));
  }

  @Override
  public void delete(long id) {
    if (!daoToDoService.deleteById(id)) {
      throw new IllegalArgumentException("Cannot delete the row by id: " + id);
    }
  }

  @Override
  public void clear() {
    daoToDoService.clear();
  }
}
