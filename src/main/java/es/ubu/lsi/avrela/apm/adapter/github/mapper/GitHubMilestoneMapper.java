package es.ubu.lsi.avrela.apm.adapter.github.mapper;

import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubMilestone;
import es.ubu.lsi.avrela.apm.model.Sprint;
import es.ubu.lsi.avrela.apm.model.SprintState;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubMilestoneMapper {

  private final GitHubIssueMapper issueMapper;

  public static GitHubMilestoneMapper build() {
    return new GitHubMilestoneMapper(new GitHubIssueMapper(
        new GitHubCommentMapper(),
        new GitHubLabelMapper(),
        new GitHubIssueEventMapper()));
  }

  public Sprint toDomain(GitHubMilestone milestone) {
    if (milestone == null){
      return null;
    }
    return Sprint.builder()
        .id(milestone.getNumber().toString())
        .title(milestone.getTitle())
        .description(milestone.getDescription())
        .state(SprintState.valueOf(milestone.getState().name()))
        .dueOn(milestone.getDueOn())
        .issues(issueMapper.toDomain(milestone.getIssues()))
        .build();
  }

  public List<Sprint> toDomain(List<GitHubMilestone> milestones) {
    if(milestones == null){
      return new ArrayList<>();
    }
    List<Sprint> result = new ArrayList<>(milestones.size());
    for (GitHubMilestone milestone : milestones){
      result.add(toDomain(milestone));
    }
    return result;
  }
}
