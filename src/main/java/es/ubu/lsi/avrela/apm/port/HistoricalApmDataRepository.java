package es.ubu.lsi.avrela.apm.port;

import es.ubu.lsi.avrela.apm.model.HistoricalApmData;
import java.time.ZonedDateTime;

public interface HistoricalApmDataRepository {

  HistoricalApmData findByRepoOwnerAndRepoNameAndSprintDueBetween(String repoOwner, String repoName, ZonedDateTime startAt, ZonedDateTime endAt);

}
