package com.github.maureon.avrela.scm.adapter.github;

import com.github.maureon.avrela.scm.model.Commit;
import com.github.maureon.avrela.scm.model.HistoricalScmData;
import com.github.maureon.avrela.scm.port.HistoricalScmDataRepository;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubHistoricalScmDataRepository implements
    HistoricalScmDataRepository {

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
