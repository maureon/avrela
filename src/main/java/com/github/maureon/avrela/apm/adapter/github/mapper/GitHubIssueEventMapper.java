package com.github.maureon.avrela.apm.adapter.github.mapper;

import com.github.maureon.avrela.apm.adapter.github.model.GitHubIssueEvent;
import com.github.maureon.avrela.apm.model.IssueEvent;
import com.github.maureon.avrela.apm.model.IssueEventType;
import java.util.ArrayList;
import java.util.List;

public class GitHubIssueEventMapper {

  public IssueEvent toDomain(GitHubIssueEvent event){
    if (event == null) {return null;}
    return IssueEvent.builder()
        .id(event.getId() == null ? null : event.getId().toString())
        .createdAt(event.getCreatedAt())
        .commitId(event.getCommitId())
        .user(event.getActor().getLogin())
        .body(event.getBody())
        .eventType(IssueEventType.valueOf(event.getType().name()))
        .build();
  }

  public List<IssueEvent> toDomain(List<GitHubIssueEvent> events) {
    if(events == null){
      return new ArrayList<>();
    }
    List<IssueEvent> result = new ArrayList<>(events.size());
    for (GitHubIssueEvent event : events){
      result.add(toDomain(event));
    }
    return result;
  }
}
