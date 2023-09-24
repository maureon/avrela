package es.ubu.lsi.avrela.scm.adapter.github.model;

import lombok.Data;

@Data
public class GitHubCommitData {

  private String message;

  private GitHubCommitAuthor author;

}
