package com.assignment.telus.todos.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity implements Serializable {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name="id")
  private Long id;

  @Version
  @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
  private long version = 0L;
}
