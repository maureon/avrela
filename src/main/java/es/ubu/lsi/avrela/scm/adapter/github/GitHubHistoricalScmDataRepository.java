package es.ubu.lsi.avrela.scm.adapter.github;

import es.ubu.lsi.avrela.scm.model.Commit;
import es.ubu.lsi.avrela.scm.model.HistoricalScmData;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubHistoricalScmDataRepository implements
    es.ubu.lsi.avrela.scm.port.HistoricalScmDataRepository {

  private final GitHubCommitRepository gitHubCommitRepository;

  @Override
  public HistoricalScmData findByRepoOwnerAndRepoNameAndBranchAndDatesBetween(
      String repoOwner, String repoName, String branch, ZonedDateTime startAt,
      ZonedDateTime endAt) {
    //Get commits
    List<Commit> commits = gitHubCommitRepository.findCommitsByBranchAndDateRange(repoOwner, repoName, branch, startAt, endAt);
    //For each commit, get commit full info
    List<Commit> fullInfoCommits = new ArrayList<>();
    for (Commit commit: commits){
      fullInfoCommits.add(gitHubCommitRepository.findCommit(repoOwner, repoName, commit.getSha()));
    }
    return HistoricalScmData.builder()
        .repoOwner(repoOwner)
        .repoName(repoName)
        .branch(branch)
        .startAt(startAt)
        .endAt(endAt)
        .commits(fullInfoCommits)
        .build();
  }

}
