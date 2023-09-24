package com.github.maureon.avrela.scm.adapter.github.model;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class GitHubCommitAuthor {

  private String name;

  private String email;

  private ZonedDateTime date;

}
