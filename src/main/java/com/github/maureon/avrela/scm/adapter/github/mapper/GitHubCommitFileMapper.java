package com.github.maureon.avrela.scm.adapter.github.mapper;

import com.github.maureon.avrela.scm.adapter.github.model.GitHubCommitFile;
import com.github.maureon.avrela.scm.model.CommitFile;
import java.util.ArrayList;
import java.util.List;

public class GitHubCommitFileMapper {

  public CommitFile toDomain(GitHubCommitFile commitFile){
    return CommitFile.builder()
        .name(commitFile.getFilename())
        .additions(commitFile.getAdditions())
        .deletions(commitFile.getDeletions())
        .build();
  }

  public List<CommitFile> toDomain(List<GitHubCommitFile> commitFiles){
    if (commitFiles == null){
      return new ArrayList<>();
    }
    List<CommitFile> result = new ArrayList<>(commitFiles.size());
    for(GitHubCommitFile commitFile : commitFiles){
      result.add(toDomain(commitFile));
    }
    return result;
  }
}


