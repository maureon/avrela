package com.github.maureon.avrela.scm.adapter.github;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import feign.Logger.Level;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * GitHub SCM client integration test.
 *
 * @see <a
 * href="https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.1/com/google/gson/TypeAdapter.html">Gson
 * type adapter example.</a>
 */
class GitHubScmClientTest {

  /**
   * GitHub Client.
   */
  private static GitHubScmClient gitHubScmClient;
  /**
   * Repository owner.
   */
  private final String owner = "davidmigloz";
  /**
   * Repository.
   */
  private final String repo = "go-bees";
  /**
   * Branch.
   */
  private final String branch = "master";
  /**
   * Commit sha with files.
   */
  private final String shaWithFiles = "434cd9935020fdcceb4c6fbb5b1f2c9fe4a10d87";



  @BeforeAll
  public static void setUp() {
    gitHubScmClient = GitHubScmClient.with(Level.FULL);
  }


  @Nested
  @DisplayName("Given a GitHub repository")
  public class GitHubCommitsTest {

    @Nested
    @DisplayName("When repository has commits")
    class GitHubRepositoryWithCommits {

      @Test
      @DisplayName("Then commits should be fetched")
      void commitsShouldBeRetrieved() {
        var commits = gitHubScmClient.findCommits(owner, repo, branch, null, ZonedDateTime.now(), 1,
            1);

        assertNotNull(commits, "Commit list must be none null.");
        assertTrue(commits.size() > 0, "Commit list length must be greater than zero");
      }

      @Test
      @DisplayName("Then commit relevant info should be fetched")
      void commitsInfoShouldBeComplete() {
        var commits = gitHubScmClient.findCommits(owner, repo, branch, null, ZonedDateTime.now(), 1,
            100);

        assertAll("Verify relevant info is present",
            () -> assertTrue(commits.stream().anyMatch(commit -> commit.getSha() != null),
                "SHA must be retrieved"),
            () -> assertTrue(
                commits.stream().anyMatch(commit -> commit.getData().getMessage() != null),
                "Message must be retrieved"),
            () -> assertTrue(
                commits.stream().anyMatch(commit -> commit.getData().getAuthor().getName() != null),
                "Author name must be retrieved"),
            () -> assertTrue(
                commits.stream().anyMatch(commit -> commit.getData().getAuthor().getDate() != null),
                "Date must be retrieved"));
      }
    }
  }

  @Nested
  @DisplayName("Given a GitHub repository")
  public class GitHubCommitTest {

    @Nested
    @DisplayName("When I fetch a commit with file changes")
    class GitHubRepositoryWithCommits {

      @Test
      @DisplayName("Then commit relevant info should be present")
      void commitInfoShouldBeComplete() {
        var commit = gitHubScmClient.findCommit(owner, repo, shaWithFiles);

        assertAll("Verify relevant info is present",
            () -> assertNotNull(commit.getSha(), "SHA must be retrieved"),
            () -> assertNotNull(commit.getData().getMessage(), "Message must be retrieved"),
            () -> assertNotNull(commit.getData().getAuthor().getName(),
                "Author name must be retrieved"),
            () -> assertNotNull(commit.getData().getAuthor().getDate(), "Date must be retrieved")
        );

        var files = commit.getFiles();
        assertAll( "Verify change set",
            () -> assertTrue(files.stream().anyMatch(file -> file.getFilename() != null), "Change must be associated to a file"),
            () -> assertTrue(files.stream().anyMatch(file -> file.getAdditions() != null), "Number of additions must be provided"),
            () -> assertTrue(files.stream().anyMatch(file -> file.getDeletions() != null), "Number of deletions must be provided"),
            () -> assertTrue(files.stream().anyMatch(file -> file.getStatus() != null), "Change status must be provided")
        );
      }
    }
  }

}