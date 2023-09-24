package es.ubu.lsi.avrela.apm.model;


import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

/**
 * Historical Agile Project Management data.
 */
@Data
@Builder
public class HistoricalApmData {

  private String repoOwner;

  private String repoName;

  private ZonedDateTime startAt;

  private ZonedDateTime endAt;

  /*
   *  Sprints ordered by date desc.
   */
  private List<Sprint> sprints;

  /**
   *
   * @return participants in time box.
   */
  public Set<String> getParticipants(){
    Set<String> result = new HashSet<>();
    sprints.forEach(
        sprint -> {
          result.addAll(sprint.getParticipants());
        }
    );
    return result;
  }

  /**
   * Count participants in time box.
   * @return number of participants.
   */
  public Integer countParticipants(){
    return getParticipants().size();
  }

  public Long countIssues(){
    Long result = 0L;
    for(Sprint sprint: sprints){
      result += sprint.countIssues();
    }
    return result;
  }

  /**
   * Filter issues
   * @param filter
   * @return filtered issues
   */
  public List<Issue> filterIssues(Predicate<Issue> filter){
    return sprints.stream()
        .flatMap( sprint -> sprint.getIssues().stream()
            .filter(filter)
        )
        .collect(Collectors.toList());
  }

  public List<Issue> getIssues(){
    return this.filterIssues( issue -> true);
  }

}
