package es.ubu.lsi.avrela.scm.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HistoricalScmDataTest {

  @Test
  void shouldGetCommitsWithIssueTraceability() {
    HistoricalScmData historicalScmData = HistoricalScmData.builder()
        .commits(get3outOf5CommitsWithIssueReference())
        .build();

    Integer result = historicalScmData.getCommitsWithIssueTraceability().size();

    Assertions.assertEquals(3, result);
  }

  private List<Commit> get3outOf5CommitsWithIssueReference() {
    List<Commit> result = new ArrayList<>();
    result.add(
        Commit.builder()
            .associatedIssues(Set.of("1"))
            .build()
    );
    result.add(
        Commit.builder()
            .associatedIssues(Set.of("2"))
            .build()
    );    result.add(
        Commit.builder()
            .associatedIssues(Set.of("3"))
            .build()
    );
    result.add(
        Commit.builder()
            .associatedIssues(Collections.emptySet())
            .build()
    );
    result.add(
        Commit.builder()
            .associatedIssues(null)
            .build()
    );
    return result;
  }
}