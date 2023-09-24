package com.github.maureon.avrela.apm.adapter.github;

import com.github.maureon.avrela.apm.model.Issue;
import com.github.maureon.avrela.apm.port.IssueRepository;
import com.github.maureon.avrela.apm.adapter.github.mapper.GitHubIssueMapper;
import com.github.maureon.avrela.apm.adapter.github.model.GitHubComment;
import com.github.maureon.avrela.apm.adapter.github.model.GitHubIssue;
import com.github.maureon.avrela.apm.adapter.github.model.GitHubIssueEvent;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class GitHubIssueRepository implements IssueRepository {

  private final GitHubApmClient gitHubApmClient;

  private final GitHubIssueMapper gitHubIssueMapper;


  @Override
  public Issue findById(String repoOwner, String repoName, String issueId) {
    log.debug("Using repository identified by owner [{}] and name [{}]", repoOwner, repoName);
    log.debug("Fetching issue with id [{}]", issueId);
    GitHubIssue issue = gitHubApmClient.findIssueById(repoOwner, repoName, issueId);
    log.debug("Fetching issue [{}] events", issue.getTitle());
    List<GitHubIssueEvent> events = gitHubApmClient.findEventsByIssue(repoOwner, repoName, issue.getNumber().toString());
    log.debug("Fetched [{}] events", events.size());
    List<GitHubIssueEvent> filteredEvents = new ArrayList<>();
    events.stream().forEach( event -> {
          log.debug("Processing event [{}] of issue [{}]", event, issue.getTitle());
          if (event.getType() != null && GitHubIssueEvent.TYPES_SUPPORTED.contains(event.getType())){
            filteredEvents.add(event);
          }
        }
    );
    issue.setEvents(filteredEvents);

    if(issue.getTotalComments() > 0){
      //TODO: Paginate comments. Currently, only 100 comments per Issue are fetched.
      log.debug("Fetching issue [{}] comments", issue.getTitle());
      List<GitHubComment> comments = gitHubApmClient.findCommentsByIssue(repoOwner, repoName,
          issue.getNumber().toString());
      log.debug("[{}] comments fetched", comments.size());
      issue.setComments(comments);
    }

    return gitHubIssueMapper.toDomain(issue);
  }

}
