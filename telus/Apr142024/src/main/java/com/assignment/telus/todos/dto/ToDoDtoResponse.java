package com.assignment.telus.todos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ToDoDtoResponse {
    private int id;
    private String description;
    private Completion completion;
}
