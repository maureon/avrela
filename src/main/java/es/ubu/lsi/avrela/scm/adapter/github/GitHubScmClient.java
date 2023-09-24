package es.ubu.lsi.avrela.scm.adapter.github;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.ubu.lsi.avrela.apm.adapter.github.GitHubAuthenticationInterceptor;
import es.ubu.lsi.avrela.apm.adapter.github.ZonedDateTimeTypeAdapter;
import es.ubu.lsi.avrela.scm.adapter.github.model.GitHubCommit;
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
 * Feign based GitHub SCM client.
 *
 * @see <a href="https://github.com/OpenFeign/feign">Official documentation</a>
 * @see <a href="https://www.baeldung.com/intro-to-feign">Intro to Feign</a>
 */
public interface GitHubScmClient {

  static GitHubScmClient with(Level loggerLevel) {
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
          .logger(new Slf4jLogger(GitHubScmClient.class))
          .encoder(new GsonEncoder())
          .decoder(decoder)
          .logLevel(loggerLevel)
          .target(GitHubScmClient.class, "https://api.github.com");
    }

    return Feign.builder()
        .logger(new Slf4jLogger(GitHubScmClient.class))
        .encoder(new GsonEncoder())
        .decoder(decoder)
        .logLevel(loggerLevel)
        .target(GitHubScmClient.class, "https://api.github.com");
  }

  /**
   * Find commits.
   *
   * @param owner
   * @param repo
   * @param branch
   * @param since
   * @param until
   * @param page
   * @param pageSize
   * @return
   * @see <a href="https://docs.github.com/en/rest/commits/commits#list-commits">List commits API</a>
   * @see <a href="https://docs.github.com/en/enterprise-cloud@latest/rest/guides/traversing-with-pagination">Pagination guidelines</a>
   */
  @RequestLine("GET /repos/{owner}/{repo}/commits?sha={branch}&since={since}&until={until}&page={page}&per_page={pageSize}")
  List<GitHubCommit> findCommits(@Param("owner") String owner, @Param("repo") String repo, @Param("branch") String branch,
      @Param("since") ZonedDateTime since, @Param("until") ZonedDateTime until,
      @Param("page") Integer page, @Param("pageSize") Integer pageSize);

  /**
   * Get a commit
   * @param owner
   * @param repo
   * @param sha
   * @return the commit
   */
  @RequestLine("GET /repos/{owner}/{repo}/commits/{sha}")
  GitHubCommit findCommit(@Param("owner") String owner, @Param("repo") String repo,@Param("sha") String sha);

}
