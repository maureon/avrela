package com.github.maureon.avrela.scm.adapter.github.mapper;

import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GitHubCommitMapperTest {

  GitHubCommitMapper gitHubCommitMapper = new GitHubCommitMapper(new GitHubCommitFileMapper());

  @Test
  void whenPresentShouldExtractAssociatedIssues() {
    String commitMessageReferencing3Issues =
        "Refs to #26 and #31 and #1";

    Set<String> result = gitHubCommitMapper.toAssociatedIssues(commitMessageReferencing3Issues);

    Assertions.assertEquals(3, result.size());
    Assertions.assertTrue(result.containsAll(Set.of("26", "31", "1")));
  }
}