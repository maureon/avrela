package com.github.maureon.avrela.scm.adapter.github.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class GitHubCommit {

  private String sha;

  @SerializedName("commit")
  private GitHubCommitData data;

  private List<GitHubCommitFile> files;

}
