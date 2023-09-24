package com.github.maureon.avrela.apm.adapter.github.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GitHubIssueTest {

  @Test
  void hasTaskListShouldReturnTrueWhenBodyContainsUndoneTask() {
    var issue = new GitHubIssue();
    issue.setBody("Loren ipsun \n -[ ]");

    assertTrue(issue.hasTaskList());
  }

  @Test
  void hasTaskListShouldReturnTrueWhenBodyContainsDoneTask() {
    var issue = new GitHubIssue();
    issue.setBody("Loren ipsun \n -[x]");

    assertTrue(issue.hasTaskList());
  }

  @Test
  void hasTaskListShouldReturnFalseWhenBodyIsEmpty() {
    var issue = new GitHubIssue();

    assertFalse(issue.hasTaskList());
  }

}