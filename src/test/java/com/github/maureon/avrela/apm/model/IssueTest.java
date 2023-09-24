package com.github.maureon.avrela.apm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IssueTest {

  @Test
  void shouldMatchDescription() {
    Issue expected = new Issue.IssueBuilder()
        .hasLink(Boolean.TRUE)
        .hasImages(Boolean.TRUE)
        .hasTaskList(Boolean.TRUE)
        .build();
    Issue actual = new Issue.IssueBuilder()
        .hasLink(Boolean.TRUE)
        .hasImages(Boolean.TRUE)
        .hasTaskList(Boolean.TRUE)
        .build();

    assertTrue(expected.issueDescriptionMatch(actual));
  }

  @Test
  void shouldNotMatchDescription() {
    Issue expected = new Issue.IssueBuilder()
        .hasLink(Boolean.TRUE)
        .hasImages(Boolean.TRUE)
        .hasTaskList(Boolean.TRUE)
        .build();
    Issue actual = new Issue.IssueBuilder()
        .hasLink(Boolean.TRUE)
        .hasImages(Boolean.TRUE)
        .hasTaskList(Boolean.FALSE)
        .build();

    assertFalse(expected.issueDescriptionMatch(actual));

  }
}