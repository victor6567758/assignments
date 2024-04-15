package com.assignment.telus.todos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class TodoDtoRequest {

  @NotNull
  private String description;

  @NotNull
  private Completion completion;
}
