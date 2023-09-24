package es.ubu.lsi.avrela.scm.adapter.github.model;

import lombok.Data;

/**
 * Models a GitHub commit.
 */
@Data
public class GitHubCommitFile {

  private String filename;

  private Integer additions;

  private Integer deletions;

  private String status;

}
