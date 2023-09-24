package com.github.maureon.avrela.scm.port;

import com.github.maureon.avrela.scm.model.HistoricalScmData;
import java.time.ZonedDateTime;

public interface HistoricalScmDataRepository {

  HistoricalScmData findByRepoOwnerAndRepoNameAndBranchAndDatesBetween(
      String repoOwner, String repoName, String branch, ZonedDateTime startAt,
      ZonedDateTime endAt);
}
