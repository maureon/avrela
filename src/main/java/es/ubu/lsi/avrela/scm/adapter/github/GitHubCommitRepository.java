package es.ubu.lsi.avrela.scm.adapter.github;

import es.ubu.lsi.avrela.apm.adapter.github.GitHubApmClient;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitMapper;
import es.ubu.lsi.avrela.scm.adapter.github.model.GitHubCommit;
import es.ubu.lsi.avrela.scm.model.Commit;
import es.ubu.lsi.avrela.scm.port.CommitRepository;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class GitHubCommitRepository implements CommitRepository {

  private final GitHubScmClient gitHubScmClient;
  private final GitHubApmClient gitHubApmClient;
  private final GitHubCommitMapper commitMapper;

  @Override
  public Commit findCommit(String owner, String repo, String sha) {
    GitHubCommit gitHubCommit = gitHubScmClient.findCommit(owner, repo, sha);
    Commit result = commitMapper.toDomain(gitHubCommit);
    if (result.hasAssociatedIssues()) {
      Iterator<String> namesIterator = result.getAssociatedIssues().iterator();
      while (namesIterator.hasNext()){
        String issueId = namesIterator.next();
        try {
          gitHubApmClient.findIssueById(owner, repo, issueId);
        } catch (Exception ex){
          result.getAssociatedIssues().remove(issueId);
        }
      }

    }

    return result;
  }

  @Override
  public List<Commit> findCommitsByBranchAndDateRange(String owner, String repo, String branch,
      ZonedDateTime beginAt, ZonedDateTime endAt) {
    List<GitHubCommit> gitCommits = gitHubScmClient.findCommits(owner, repo, branch, beginAt, endAt, null, null);
    List<Commit> result = commitMapper.toDomain(gitCommits);
    return result;
  }

}
