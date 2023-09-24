package com.github.maureon.avrela.apm.model;

import com.google.gson.annotations.SerializedName;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Models an issue event. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueEvent {

  private String id;

  private IssueEventType eventType;

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
  private String user;


}
