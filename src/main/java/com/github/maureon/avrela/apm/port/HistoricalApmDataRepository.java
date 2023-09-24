package com.github.maureon.avrela.apm.port;

import com.github.maureon.avrela.apm.model.HistoricalApmData;
import java.time.ZonedDateTime;

public interface HistoricalApmDataRepository {

  HistoricalApmData findByRepoOwnerAndRepoNameAndSprintDueBetween(String repoOwner, String repoName, ZonedDateTime startAt, ZonedDateTime endAt);

}
