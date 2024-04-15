package com.assignment.telus.todos.dto;

import io.micrometer.common.util.StringUtils;
import liquibase.util.StringUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Completion {
  COMPLETED("C"),
  NOT_COMPLETED("N")
  ;

  private final String cShort;
  public static Completion fromCShort(String value) {
    if (StringUtils.isEmpty(value)) {
      throw new IllegalArgumentException("Invalid value");
    }
    return switch(value.charAt(0)) {
      case 'C' -> Completion.COMPLETED;
      case 'N' -> Completion.NOT_COMPLETED;
      default -> throw new IllegalStateException("Unexpected value: " + value);
    };
  }
}
