package com.github.maureon.avrela.scm.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class CommitFile {

  @EqualsAndHashCode.Include
  private String name;

  private Integer additions;

  private Integer deletions;

  private String status;

}
