package com.github.maureon.avrela.apm.adapter.github.model;

import com.google.gson.annotations.SerializedName;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GitHubMilestone {

  private Integer number;

  private String title;

  private String description;

  private GitHubItemState state;

  @SerializedName("created_at")
  private ZonedDateTime createdAt;

  /** Issues - artificial field */
  private List<GitHubIssue> issues;

  @SerializedName("updated_at")
  private ZonedDateTime updatedAt;

  @SerializedName("due_on")
  private ZonedDateTime dueOn;

  @SerializedName("closed_at")
  private ZonedDateTime closedAt;

}
