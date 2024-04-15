package com.assignment.telus.todos.error;

import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomRestExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException constraintViolationException) {

    HttpStatus status = HttpStatus.BAD_REQUEST;

    return new ResponseEntity<>(
        new ErrorResponse(
            status,
            ExceptionUtils.getMessage(constraintViolationException)), status);
  }


  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleExceptions(RuntimeException runtimeException)
      throws IOException {

    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    return new ResponseEntity<>(
        new ErrorResponse(
            status,
            ExceptionUtils.getMessage(runtimeException),
            writeStackTrace(runtimeException)
        ), status
    );
  }

  private String writeStackTrace(Throwable throwable) throws IOException {
    if (throwable == null) {
      return "N/A";
    }

    try (StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter)
    ) {

      throwable.printStackTrace(printWriter);
      return stringWriter.toString();
    }
  }
}
