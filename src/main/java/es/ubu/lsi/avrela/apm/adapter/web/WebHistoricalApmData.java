package es.ubu.lsi.avrela.apm.adapter.web;


import es.ubu.lsi.avrela.apm.model.Issue;
import es.ubu.lsi.avrela.apm.model.Sprint;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

/**
 * Historical Agile Project Management data.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebHistoricalApmData {

  private String repoOwner;

  private String repoName;

  private ZonedDateTime startAt;

  private String stringifyStartAt;
  private ZonedDateTime endAt;

  private String stringifyEndAt;

  public void setStringifyStartAt(String stringStartAt){
    this.stringifyStartAt = stringStartAt;
    this.startAt = this.toZonedDateTime(stringStartAt);
  }

  public void setStringifyEndAt(String stringEndAt){
    this.stringifyEndAt = stringEndAt;
    this.endAt = this.toZonedDateTime(stringEndAt);
  }

  public void setStartAt(ZonedDateTime pStartAt){
    this.startAt = pStartAt;
    this.stringifyStartAt = this.toStringifyDate(pStartAt);
  }

  public void setEndAt(ZonedDateTime pEndAt){
    this.endAt = pEndAt;
    this.stringifyEndAt = this.toStringifyDate(pEndAt);
  }

  @SneakyThrows
  private ZonedDateTime toZonedDateTime(String string)  {
    Objects.requireNonNull(string, "date string should not be null");
    SimpleDateFormat iso8601DateFormat = new SimpleDateFormat("yyyy-MM-dd");
    long dateEpoch = (iso8601DateFormat.parse(string)).getTime();

    return Instant
        .ofEpochMilli(dateEpoch)
        .atZone(ZoneId.systemDefault());
  }

  private String toStringifyDate(ZonedDateTime zonedDateTime){
    Objects.requireNonNull(zonedDateTime, "date should not be null");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return zonedDateTime.format(formatter);
  }


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
    if (this.sprints == null){
      return Collections.emptyList();
    }
    return sprints.stream()
        .flatMap( sprint -> sprint.getIssues().stream()
            .filter(filter)
        )
        .collect(Collectors.toList());
  }

  public List<Issue> issues(){
    return this.filterIssues(issue -> true);
  }

}
