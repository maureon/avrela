package com.github.maureon.avrela.css.model;

import com.github.maureon.avrela.scm.model.Commit;
import com.github.maureon.avrela.scm.model.CommitFile;
import com.github.maureon.avrela.scm.model.CommitSimilarity.Feature;
import com.github.maureon.avrela.scm.model.HistoricalScmData;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ScmCaseStudySimulationTest {

  @Test
  void filterCommitMatchComparisonShouldWorkWhenSelfTestIsMade() {
    List<Commit> commits = new ArrayList<>();
    commits.add(commitABCDEFG(commitFiles12345()));
    commits.add(commitABCDE(commitFiles1()));
    HistoricalScmData scmData = HistoricalScmData.builder()
        .commits(commits)
        .build();
    ScmCaseStudySimulation scmCaseStudySimulation = ScmCaseStudySimulation.builder()
        .caseStudy(scmData)
        .simulation(scmData)
        .build();

    List<Commit>  result = scmCaseStudySimulation.filterCommitMatchComparison(files1Message1(), 100);

    Assertions.assertEquals(2, result.size());

  }

  private EnumMap<Feature, Double> files1Message1() {
    EnumMap<Feature, Double> result = new EnumMap<>(Feature.class);
    result.put(Feature.FILES, 1.0);
    result.put(Feature.MESSAGE, 1.0);
    return result;
  }

  private  EnumMap<Feature, Double> files0dot5Message1() {
    EnumMap<Feature, Double> result = new EnumMap<>(Feature.class);
    result.put(Feature.FILES, 0.5);
    result.put(Feature.MESSAGE, 1.0);
    return result;
  }

  private Commit commitABCDE(List<CommitFile> commitFiles) {
    return Commit.builder()
        .message("ABCDE")
        .files(commitFiles)
        .build();
  }

  private Commit commitABCDEFG(List<CommitFile> commitFiles) {
    return  Commit.builder()
        .message("ABCDEFG")
        .files(commitFiles)
        .build();
  }

  private  List<CommitFile> commitFiles12345() {
    List<CommitFile> result = new ArrayList<>();
    result.add(CommitFile.builder().name("1").build());
    result.add(CommitFile.builder().name("2").build());
    result.add(CommitFile.builder().name("3").build());
    result.add(CommitFile.builder().name("4").build());
    result.add(CommitFile.builder().name("5").build());
    return result;
  }

  private  List<CommitFile> commitFiles1234() {
    List<CommitFile> result = new ArrayList<>();
    result.add(CommitFile.builder().name("1").build());
    result.add(CommitFile.builder().name("2").build());
    result.add(CommitFile.builder().name("3").build());
    result.add(CommitFile.builder().name("4").build());
    return result;
  }

  private  List<CommitFile> commitFiles1() {
    List<CommitFile> result = new ArrayList<>();
    result.add(CommitFile.builder().name("1").build());
    return result;
  }

  private  List<CommitFile> commitFiles9() {
    List<CommitFile> result = new ArrayList<>();
    result.add(CommitFile.builder().name("9").build());
    return result;
  }


}