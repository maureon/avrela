package com.github.maureon.avrela.apm.adapter.github.mapper;

import com.github.maureon.avrela.apm.adapter.github.model.GitHubLabel;
import java.util.ArrayList;
import java.util.List;

public class GitHubLabelMapper {

  public String toDomain(GitHubLabel label){
    return label.getName();
  }

  public List<String> toDomain(List<GitHubLabel> gitHubLabels){
    if (gitHubLabels == null){
      return new ArrayList<>();
    }
    List<String> result = new ArrayList<>(gitHubLabels.size());
    for (GitHubLabel label : gitHubLabels) {
      result.add(toDomain(label));
    }
    return result;
  }

}
