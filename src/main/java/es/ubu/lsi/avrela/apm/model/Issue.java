package es.ubu.lsi.avrela.apm.model;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/** Models a project issue. */
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Getter
@Builder
public class Issue {

  /** UUID. */
  @EqualsAndHashCode.Include
  @ToString.Include
  private String id;

  /** Name. */
  @ToString.Include
  private String name;

  private String body;

  /** Sprint. */
  private Sprint sprint;

  /** State. */
  @ToString.Include
  private IssueState state;

  /** Check whether description contains task list*/
  private Boolean hasTaskList;

  /** Story points. */
  private Integer storyPoints;

  /** Creation time. */
  private ZonedDateTime createdAt;

  /** Labels. */
  private List<String> labels;

  /** Comments. */
  private List<Comment> comments;

  /** Events. */
  private List<IssueEvent> events;

  /** Assignee. */
  private String assignee;

  /** Body has links. */
  private Boolean hasLink;

  /** Images. */
  private Boolean hasImages;

  public static Issue emptyIssue() {
    return Issue.builder()
        .name("No issue to compare")
        .labels(List.of("No labels to compare"))
        .state(null)
        .build();
  }

  public Long countByEventType(IssueEventType eventType) {
    if (this.events == null ) {return 0L;};
    return this.events.stream()
            .filter(event -> event.getEventType() != null && eventType.equals(event.getEventType()))
            .count();
  }

  public boolean isLabeled() {
    return labels !=null && labels.size() > 0;
  }

  public boolean isLabeledWithLabel(String arg) {
    return isLabeled() && labels.contains(arg);
  }

  /**
   *
   * @return issue participants.
   */
  public Collection<String> getParticipants() {
    Set<String> result = new HashSet<>();
    events.forEach(
        event -> {
          if (!result.contains(event.getUser())){
           result.add(event.getUser());
          }
        }
    );
    return result;
  }

  /**
   * Count issue participants.
   * @return number of participants.
   */
  public Integer countParticipants(){
    return getParticipants().size();
  }

  /**
   * Check whether issue participants is greater or equals to provided value.
   * @param participants
   * @return
   */
  public static Predicate<Issue> participantsGreaterThanOrEqual(Integer participants){
    return p -> (p.getParticipants() != null && p.getParticipants().size() != 0 && p.getParticipants().size() >= participants);
  }

  /**
   * Check whether issues description match.
   * @return comparison result.
   */
  public Boolean issueDescriptionMatch(Issue actualIssue){
  Boolean result = false;
  result = this.hasImages.equals(actualIssue.getHasImages());
  result = result && this.hasLink.equals(actualIssue.getHasLink());
  result = result && this.hasTaskList.equals(actualIssue.getHasTaskList());
  return result;
  }

}
