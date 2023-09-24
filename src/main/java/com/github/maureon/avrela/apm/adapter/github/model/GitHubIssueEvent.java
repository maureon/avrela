package com.github.maureon.avrela.apm.adapter.github.model;

import com.google.gson.annotations.SerializedName;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Models a GitHub issue event
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubIssueEvent {

  public static final List<GitHubIssueEventType> TYPES_SUPPORTED = List.of(
      GitHubIssueEventType.CLOSED,
      GitHubIssueEventType.ASSIGNED,
      GitHubIssueEventType.LABELED,
      GitHubIssueEventType.COMMENTED,
      GitHubIssueEventType.REFERENCED);

  private Long id;

  @SerializedName("event")
  private GitHubIssueEventType type;

  @SerializedName("created_at")
  private ZonedDateTime createdAt;

  /**
   * Comment body for comment events.
   */
  private String body;

  /**
   * Only applies for reference events.
   */
  @SerializedName("commit_id")
  private String commitId;

  /**
   * User who triggered the event.
   */
  private GitHubUser actor;

}
