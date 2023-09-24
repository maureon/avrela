package com.github.maureon.avrela.apm.adapter.github.model;

import com.google.gson.annotations.SerializedName;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubIssue {

  private Integer number;

  private GitHubMilestone milestone;

  private String title;

  private String body;

  private List<GitHubLabel> labels;

  private GitHubItemState state;

  private GitHubUser assignee;

  @SerializedName("comments")
  private Integer totalComments;

  /** Comments - Artificial field */
  @SerializedName("commentsArtificial")
  private List<GitHubComment> comments;

  /** Events - Artificial field */
  private List<GitHubIssueEvent> events;

  @SerializedName("created_at")
  private ZonedDateTime createdAt;

  /**
   * Checks whether body contain task list.
   */
  public Boolean hasTaskList(){
    if (body == null ){
      return false;
    }
    return body.contains("[ ]") || body.contains("[x]");
  }

  /**
   * Checks whether body contain link.
   */
  public Boolean hasLink(){
    if (body == null ){
      return false;
    }
    return body.contains("http://") || body.contains("https://");
  }

  /**
   * Checks whether body contain link.
   *
   * @see <a href="https://regex101.com/r/1kqFpw/1">https://regex101.com/r/1kqFpw/1</a>
   */
  public Boolean hasImage(){
    if (this.body == null ){
      return false;
    }
    Pattern pattern = Pattern.compile("^.+(.jpg|.jpeg|.bmp|.svg|.gif)");
    Matcher matcher = pattern.matcher(this.body);
    return matcher.find();
  }

}
