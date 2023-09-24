package com.github.maureon.avrela.apm.adapter.github;

import com.github.maureon.avrela.apm.adapter.github.model.GitHubComment;
import com.github.maureon.avrela.apm.model.Sprint;
import com.github.maureon.avrela.apm.port.SprintRepository;
import com.github.maureon.avrela.apm.adapter.github.mapper.GitHubMilestoneMapper;
import com.github.maureon.avrela.apm.adapter.github.model.GitHubIssue;
import com.github.maureon.avrela.apm.adapter.github.model.GitHubIssueEvent;
import com.github.maureon.avrela.apm.adapter.github.model.GitHubMilestone;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class GitHubSprintRepository implements SprintRepository {

  private final GitHubApmClient gitHubApmClient;

  private final GitHubMilestoneMapper gitHubMilestoneMapper;

  @Override
  public List<Sprint> findByDueOnBetween(String repoOwner, String repoName, ZonedDateTime startAt,
      ZonedDateTime endAt) {
    log.debug("Using repository identified by owner [{}] and name [{}]", repoOwner, repoName);
    //TODO: review MAX page size. GitHub Milestones REST API cannot filter by dates.
    log.debug("Fetching milestones between [{}] and [{}]", startAt, endAt);
    List<GitHubMilestone> milestones = gitHubApmClient.findMilestones(repoOwner, repoName, 1, 100);
    log.debug("[{}] sprints fetched", milestones.size());
    if (milestones.isEmpty()) {
      return new ArrayList<>();
    }
    List<GitHubMilestone> targetMilestones = milestones
        .stream()
        .filter(milestone -> milestone.getDueOn().isAfter(startAt) && milestone.getDueOn()
            .isBefore(endAt))
        .toList();
    log.debug("[{}] sprints filtered", targetMilestones.size());

    for (GitHubMilestone milestone : targetMilestones) {
      //TODO: Paginate issues. Currently, only 100 issues per Sprint are fetched.
      log.debug("Fetching milestone [{}] due on [{}] issues", milestone.getTitle(), milestone.getDueOn());
      List<GitHubIssue> issues = gitHubApmClient.findIssuesByMilestone(repoOwner, repoName,
          milestone.getNumber(), 1, 100);
      log.debug("[{}] issues fetched", issues.size());
      for (GitHubIssue issue : issues) {
        log.debug("Fetching issue [{}] events", issue.getTitle());
        List<GitHubIssueEvent> events = gitHubApmClient.findEventsByIssue(repoOwner, repoName, issue.getNumber().toString());
        log.debug("Fetched [{}] events", events.size());
        List<GitHubIssueEvent> filteredEvents = new ArrayList<>();
        events.forEach( event -> {
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
      }
      milestone.setIssues(issues);
    }

    return gitHubMilestoneMapper.toDomain(targetMilestones);
  }

}
