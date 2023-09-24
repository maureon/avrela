package com.github.maureon.avrela.apm.port;

import com.github.maureon.avrela.apm.model.Sprint;
import java.time.ZonedDateTime;
import java.util.List;

public interface SprintRepository {

  List<Sprint> findByDueOnBetween(String repoOwner, String repoName, ZonedDateTime startAt, ZonedDateTime endAt);

}
