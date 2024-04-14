package com.assignment.telus.todos.controller;

import com.assignment.telus.todos.dto.ToDoDtoResponse;
import com.assignment.telus.todos.dto.TodoDtoRequest;
import com.assignment.telus.todos.service.TodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class ToDoController {

  private final TodoService todoService;

  @GetMapping("/{id}")
  public ToDoDtoResponse findById(@PathVariable long id) {
    return todoService.findById(id);
  }

  @GetMapping()
  public List<ToDoDtoResponse> findAll() {
    return todoService.findAll();
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public ToDoDtoResponse create(@RequestBody TodoDtoRequest todoDtoRequest) {
    return todoService.create(todoDtoRequest);
  }

  @PatchMapping("/{id}")
  public ToDoDtoResponse update(@PathVariable long id, @RequestBody TodoDtoRequest todoDtoRequest) {
    return todoService.update(id, todoDtoRequest);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable long id) {
    todoService.delete(id);
  }
}
