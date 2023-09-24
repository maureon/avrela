package es.ubu.lsi.avrela.apm.port;

import es.ubu.lsi.avrela.apm.model.Sprint;
import java.time.ZonedDateTime;
import java.util.List;

public interface SprintRepository {

  List<Sprint> findByDueOnBetween(String repoOwner, String repoName, ZonedDateTime startAt, ZonedDateTime endAt);

}
