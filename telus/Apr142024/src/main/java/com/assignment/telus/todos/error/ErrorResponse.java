package com.assignment.telus.todos.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  private int code;

  private String status;

  private String message;

  private String stackTrace;

  private Object data;

  public ErrorResponse() {
    timestamp = LocalDateTime.now();
  }

  public ErrorResponse(
      HttpStatus httpStatus,
      String message) {
    this();

    this.code = httpStatus.value();
    this.status = httpStatus.name();
    this.message = message;
  }

  public ErrorResponse(
      HttpStatus httpStatus,
      String message,
      String stackTrace
  ) {
    this(httpStatus, message);
    this.stackTrace = stackTrace;
  }

  public ErrorResponse(
      HttpStatus httpStatus,
      String message,
      String stackTrace,
      Object data
  ) {
    this(httpStatus, message, stackTrace);
    this.data = data;
  }
}
