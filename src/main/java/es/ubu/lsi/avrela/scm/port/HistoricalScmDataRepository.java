package es.ubu.lsi.avrela.scm.port;

import es.ubu.lsi.avrela.scm.model.HistoricalScmData;
import java.time.ZonedDateTime;

public interface HistoricalScmDataRepository {

  HistoricalScmData findByRepoOwnerAndRepoNameAndBranchAndDatesBetween(
      String repoOwner, String repoName, String branch, ZonedDateTime startAt,
      ZonedDateTime endAt);
}
