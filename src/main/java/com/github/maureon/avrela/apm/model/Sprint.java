package com.github.maureon.avrela.apm.model;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;



/** Models a Sprint. */
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Data
@Builder
public class Sprint {

  /** UID */
  @EqualsAndHashCode.Include
  private String id;

  private String title;

  private String description;

  /** State. */
  private SprintState state;

  /** Issues */
  private List<Issue> issues;

  private ZonedDateTime dueOn;

  public Long countIssues() {
    return (long) issues.size();
  }

  public Long countIssuesByLabel(String label) {
    return issues.stream()
        .filter(issue -> issue.getLabels().contains(label))
        .count();
  }

  public Long countIssuesByHasComments(Boolean hasComments) {
    return issues.stream()
        .filter(issue -> hasComments.equals(issue.getComments().size() != 0))
        .count();
  }

  public Long countIssuesByState(IssueState state) {
    return issues.stream()
        .filter(issue -> state.equals(issue.getState()))
        .count();
  }

  public long countIssuesByHasTaskList(boolean hasTaskList) {
    return issues.stream()
        .filter(issue -> issue.getHasTaskList().equals(hasTaskList))
        .count();
  }

  /**
   *
   * @return sprint participants.
   */
  public Collection<String> getParticipants() {
    Set<String> result = new HashSet<>();
    issues.forEach(
        issue -> result.addAll(issue.getParticipants())
    );
    return result;
  }

  /**
   * Count sprint participants.
   * @return number of participants.
   */
  public Integer countParticipants(){
    return getParticipants().size();
  }
}
