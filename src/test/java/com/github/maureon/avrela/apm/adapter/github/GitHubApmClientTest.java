package com.github.maureon.avrela.apm.adapter.github;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import feign.Logger.Level;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * GitHub client integration test.
 *
 * @see <a
 * href="https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.1/com/google/gson/TypeAdapter.html">Gson
 * type adapter example.</a>
 */
class GitHubApmClientTest {

  /**
   * GitHub Client.
   */
  private static GitHubApmClient gitHubApmClient;
  /**
   * Repository owner.
   */
  private final String owner = "davidmigloz";
  /**
   * Repository.
   */
  private final String repo = "go-bees";

  /**
   * Milestone.
   */
  private final Integer milestone = 1;
  /**
   * Issue.
   */
  private final String issueWithComments = "202";

  @BeforeAll
  public static void setUp() {
    gitHubApmClient = GitHubApmClient.with(Level.BASIC);
  }




  @Nested
  @DisplayName("Given a GitHub repository")
  class GitHubSprintsTest {

    @Nested
    @DisplayName("When repository has milestones")
    class RepositoryWithMilestones {

      @Test
      @DisplayName("Then milestones should be fetched")
      void milestonesShouldBeRetrieved() {
        var milestones = gitHubApmClient.findMilestones(owner, repo, 1, 1);

        assertNotNull(milestones, "Milestones list must be none null.");
        assertTrue(milestones.size() > 0, "Milestones list is not empty");
      }

      @Test
      @DisplayName("Then milestone relevant info should be fetched")
      void milestonesInfoShouldBeComplete() {
        var milestones = gitHubApmClient.findMilestones(owner, repo, 1, 100);

        assertAll("Verify milestone  relevant info is present",
            () -> assertTrue(
                milestones.stream().anyMatch(milestone -> milestone.getNumber() != null)),
            () -> assertTrue(
                milestones.stream().anyMatch(milestone -> milestone.getNumber() != null)),
            () -> assertTrue(
                milestones.stream().anyMatch(milestone -> milestone.getNumber() != null)),
            () -> assertTrue(
                milestones.stream().anyMatch(milestone -> milestone.getTitle() != null)),
            () -> assertTrue(
                milestones.stream().anyMatch(milestone -> milestone.getState() != null)),
            () -> assertTrue(
                milestones.stream().anyMatch(milestone -> milestone.getClosedAt() != null))
        );
      }

    }

  }

  @Nested
  @DisplayName("Given a GitHub repository")
  class GitHubIssuesTest {

    @Nested
    @DisplayName("When repository has issues")
    class RepositoryWithIssues {

      @Test
      @DisplayName("Then issues should be fetched")
      void issuesShouldBeRetrieved() {
        var issues = gitHubApmClient.findIssuesByMilestone(owner, repo, milestone, 1, 1);

        assertNotNull(issues, "Issue list must be none null.");
        assertTrue(issues.size() > 0, "Issue list length must be greater than zero");
      }

      @Test
      @DisplayName("Then issue relevant info should be fetched")
      void issuesInfoShouldBeComplete() {
        var issues = gitHubApmClient.findIssuesByMilestone(owner, repo, milestone, 1, 1);

        assertAll("Verify all relevant issue info is present",
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getNumber() != null),
                "Number must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getMilestone() != null),
                "Milestone must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getTitle() != null),
                "Title must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getBody() != null),
                "Body must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getLabels() != null),
                "Labels must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getState() != null),
                "State must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getAssignee() != null),
                "Assignee must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getCreatedAt() != null),
                "Created_at must be retrieved")
        );
      }
    }
  }

  @Nested
  @DisplayName("Given an issue")
  public class GitHubCommentsTest {

    @Nested
    @DisplayName("When issue has comments")
    class GitHubIssueWithComments {

      @Test
      @DisplayName("Then issues should be fetched")
      void commentsShouldBeRetrieved() {
        var comments = gitHubApmClient.findCommentsByIssue(owner, repo, issueWithComments);

        assertNotNull(comments, "Comment list must be none null.");
        assertTrue(comments.size() > 0, "Comment list length must be greater than zero");
      }

      @Test
      @DisplayName("Then issue relevant info should be fetched")
      void commentsInfoShouldBeComplete() {
        var comments = gitHubApmClient.findCommentsByIssue(owner, repo, issueWithComments);

        assertAll("Verify comment data",
            () -> assertTrue(comments.stream().anyMatch(comment -> comment.getId() != null),
                "Id must be retrieved"),
            () -> assertTrue(comments.stream().anyMatch(comment -> comment.getBody() != null),
                "Body must be retrieved"),
            () -> assertTrue(comments.stream().anyMatch(comment -> comment.getUser() != null),
                "User must be retrieved"),
            () -> assertTrue(comments.stream().anyMatch(comment -> comment.getCreatedAt() != null),
                "Created at must be retrieved"),
            () -> assertTrue(comments.stream().anyMatch(comment -> comment.getUpdatedAt() != null),
                "Updated at must be retrieved")
        );
      }

    }

  }

  @Nested
  @DisplayName("Given a GitHub repository")
  public class GitHubEventTest {

    @Nested
    @DisplayName("When an issue has events")
    class IssueWithEvents {

      @Test
      @DisplayName("Then events should be fetched")
      void commitsShouldBeRetrieved() {
        var events = gitHubApmClient.findEventsByIssue(owner, repo, "181");

        assertNotNull(events, "Events list must be none null.");
        assertTrue(events.size() > 0, "Event list length must be greater than zero");
      }

      @Test
      @DisplayName("Then commit relevant info should be fetched")
      void commitsInfoShouldBeComplete() {
        var events = gitHubApmClient.findEventsByIssue(owner, repo, "181");

        assertAll("Verify relevant info is present",
            () -> assertTrue(events.stream().anyMatch(event -> event.getId() != null),
                "Id must be retrieved"),
            () -> assertTrue(
                events.stream().anyMatch(event -> event.getType() != null),
                "Message must be retrieved"),
            () -> assertTrue(
                events.stream().anyMatch(event -> event.getCreatedAt() != null),
                "Created at must be retrieved"),
            () -> assertTrue(
                events.stream().anyMatch(event -> event.getBody() != null),
                "Body must be retrieved"),
            () -> assertTrue(
                events.stream().anyMatch(event -> event.getCommitId() != null),
                "Referenced commit must be retrieved"));
      }
    }
  }
}