package es.ubu.lsi.avrela.apm.adapter.github;

import es.ubu.lsi.avrela.apm.model.HistoricalApmData;
import es.ubu.lsi.avrela.apm.port.HistoricalApmDataRepository;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubHistoricalApmDataRepository implements HistoricalApmDataRepository {

  private final GitHubSprintRepository gitHubSprintRepository;

  @Override
  public HistoricalApmData findByRepoOwnerAndRepoNameAndSprintDueBetween(String repoOwner,
      String repoName, ZonedDateTime startAt, ZonedDateTime endAt) {
    return HistoricalApmData.builder()
        .repoOwner(repoOwner)
        .repoName(repoName)
        .sprints(gitHubSprintRepository.findByDueOnBetween(repoOwner, repoName,startAt, endAt))
        .build();
  }
}
