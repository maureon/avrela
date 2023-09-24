package es.ubu.lsi.avrela.scm.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoricalScmData {

  private String repoOwner;

  private String repoName;

  private String branch;

  private ZonedDateTime startAt;

  private ZonedDateTime endAt;

  /*
   *  Commits ordered by date desc.
   */
  private List<Commit> commits;

  /**
   *
   * @return simulation unique participants (authors).
   */
  public Set<String> getParticipants() {
    Set<String> result = new HashSet<>();
    commits.forEach(commit -> result.add(commit.getAuthor()));
    return result;
  }

  /**
   *
   * @param simulationParticipants
   * @return
   */
  public Boolean alternativeCommits(Integer simulationParticipants) {
    Boolean result = true;
    Integer differentCommitAuthorStreak = 0;
    List<String> authors = new ArrayList<>();
    for(Commit commit : commits){
      if (authors.size() == simulationParticipants){
        //Cycle ends
        authors = new ArrayList<>();
      }
      if(authors.contains(commit.getAuthor())){
       return false;
      }
      authors.add(commit.getAuthor());
    }
    return result;
  }

  public List<Commit> getCommitsWithIssueTraceability() {
    List<Commit> result = new ArrayList<>();
    for(Commit commit: this.commits){
      if (commit.hasAssociatedIssues()){
        result.add(commit);
      }
    }
    return result;
  }
}
