package com.github.maureon.avrela.scm.port;

import com.github.maureon.avrela.scm.model.Commit;
import java.time.ZonedDateTime;
import java.util.List;

public interface CommitRepository {

  /**
   *
   * @param owner
   * @param repo
   * @param sha
   * @return
   */
  Commit findCommit(String owner,String repo,String sha);

  /**
   * @param owner
   * @param repo
   * @param branch
   * @param beginAt
   * @param endAt
   * @return∫
   */
  List<Commit> findCommitsByBranchAndDateRange(String owner,String repo,String branch, ZonedDateTime beginAt, ZonedDateTime endAt);

}
