package com.github.maureon.avrela.scm.adapter.github.mapper;

import com.github.maureon.avrela.scm.adapter.github.model.GitHubCommit;
import com.github.maureon.avrela.scm.model.Commit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubCommitMapper {

  private final GitHubCommitFileMapper commitFileMapper;

  public Commit toDomain(GitHubCommit commit){
    Set<String> associatedIssues = Collections.emptySet();
    associatedIssues = toAssociatedIssues(commit.getData().getMessage());
    Commit result = Commit.builder()
        .sha(commit.getSha())
        .message(commit.getData().getMessage())
        .associatedIssues(associatedIssues)
        .author(commit.getData().getAuthor().getName())
        .date(commit.getData().getAuthor().getDate())
        .files(commitFileMapper.toDomain(commit.getFiles()))
        .build();
    return result;
  }

  public Set<String> toAssociatedIssues(String message) {
    Set<String> result = new HashSet<>();
    Pattern pattern = Pattern.compile("#\\d+");
    Matcher matcher = pattern.matcher(message);
    int matchCount = 0;
    while(matcher.find()){
      result.add(matcher.group().substring(1));
    }
    return result;
  }

  public List<Commit> toDomain(List<GitHubCommit> gitCommits) {
    if (gitCommits == null){
      return new ArrayList<>();
    }
    List<Commit> result = new ArrayList<>(gitCommits.size());
    for(GitHubCommit commit : gitCommits){
      result.add(toDomain(commit));
    }
    return result;
  }
}
