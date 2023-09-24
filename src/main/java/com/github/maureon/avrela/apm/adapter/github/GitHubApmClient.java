package com.github.maureon.avrela.apm.adapter.github;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.github.maureon.avrela.apm.adapter.github.model.GitHubComment;
import com.github.maureon.avrela.apm.adapter.github.model.GitHubIssue;
import com.github.maureon.avrela.apm.adapter.github.model.GitHubIssueEvent;
import com.github.maureon.avrela.apm.adapter.github.model.GitHubMilestone;
import feign.Feign;
import feign.Logger.Level;
import feign.Param;
import feign.RequestLine;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Feign based GitHub client.
 *
 * @see <a href="https://github.com/OpenFeign/feign">Official documentation</a>
 * @see <a href="https://www.baeldung.com/intro-to-feign">Intro to Feign</a>
 */
public interface GitHubApmClient {

  static GitHubApmClient with(Level loggerLevel) {
    final Gson gson =
        new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
            .serializeNulls()
            .create();
    final Decoder decoder = new GsonDecoder(gson);

    if (System.getenv("GITHUB_TOKEN") != null){
      final GitHubAuthenticationInterceptor authInterceptor = new GitHubAuthenticationInterceptor(System.getenv("GITHUB_TOKEN"));
      return Feign.builder()
          .requestInterceptor(authInterceptor)
          .logger(new Slf4jLogger(GitHubApmClient.class))
          .encoder(new GsonEncoder())
          .decoder(decoder)
          .logLevel(loggerLevel)
          .target(GitHubApmClient.class, "https://api.github.com");
    }

    return Feign.builder()
        .logger(new Slf4jLogger(GitHubApmClient.class))
        .encoder(new GsonEncoder())
        .decoder(decoder)
        .logLevel(loggerLevel)
        .target(GitHubApmClient.class, "https://api.github.com");
  }

  /**
   * Find all milestones/sprints. Results are sorted by due_on field in ascending order.
   * Notice that this API operation does not accept due_on filtering.
   * @param owner
   * @param repo
   * @param page
   * @param pageSize
   * @return
   * @see <a href="https://docs.github.com/en/rest/issues/milestones">GitHub Milestones REST API</a>
   */
  @RequestLine("GET /repos/{owner}/{repo}/milestones?state=all&page={page}&per_page={pageSize}")
  List<GitHubMilestone> findMilestones(@Param("owner") String owner, @Param("repo") String repo, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

  /**
   * Find issues by milestone. Results are sorted by creation time.
   * @param owner
   * @param repo
   * @param milestone
   * @param page
   * @param pageSize
   * @return
   * @see <a href="https://docs.github.com/en/rest/issues/issues#list-repository-issues">GitHub Issues REST API</a>
   */
  @RequestLine("GET /repos/{owner}/{repo}/issues?milestone={milestone}&state=all&page={page}&per_page={pageSize}")
  List<GitHubIssue> findIssuesByMilestone(@Param("owner") String owner, @Param("repo") String repo,@Param("milestone") Integer milestone, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

  /**
   * Find issue commments.
   * @param owner
   * @param repo
   * @param issue
   * @return
   */
  @RequestLine("GET /repos/{owner}/{repo}/issues/{issue}/comments")
  List<GitHubComment> findCommentsByIssue(@Param("owner") String owner, @Param("repo") String repo, @Param("issue") String issue);

  /**
   * Fid issue events.
   * @param owner
   * @param repo
   * @param issue
   * @return
   */
  @RequestLine("GET /repos/{owner}/{repo}/issues/{issue}/timeline")
  List<GitHubIssueEvent> findEventsByIssue(@Param("owner") String owner, @Param("repo") String repo, @Param("issue") String issue);

  /**
   *
   * @param repoOwner
   * @param repoName
   * @param issueId
   * @return
   */
  @RequestLine("GET /repos/{owner}/{repo}/issues/{issue}")
  GitHubIssue findIssueById(@Param("owner") String owner,@Param("repo") String repo, @Param("issue") String issue);
}
