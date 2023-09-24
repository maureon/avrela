package com.github.maureon.avrela.css.model;

import com.github.maureon.avrela.scm.model.Commit;
import com.github.maureon.avrela.scm.model.CommitSimilarity;
import com.github.maureon.avrela.scm.model.CommitSimilarity.Feature;
import com.github.maureon.avrela.scm.model.HistoricalScmData;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class ScmCaseStudySimulation {

  private HistoricalScmData caseStudy;

  private HistoricalScmData simulation;
  public List<Commit> filterCommitMatchComparison(EnumMap<Feature, Double> featureWeights, int commitSimilarityThreshold) {
    log.debug( "Similarity threshold is [{}]", commitSimilarityThreshold);
    List<Commit> result = new ArrayList<>();
    Iterator<Commit> caseStudyCommits = caseStudy.getCommits().iterator();
    Iterator<Commit> simulationCommits = simulation.getCommits().iterator();
    while (caseStudyCommits.hasNext() && simulationCommits.hasNext()){
      Commit commit = caseStudyCommits.next();
      if ( 100*CommitSimilarity.similarityOf(commit, simulationCommits.next(), featureWeights)
          >= commitSimilarityThreshold){
        result.add(commit);
      }
    }
    return result;
  }

  public List<CommitComparison> compare(EnumMap<Feature, Double> featureWeights, double commitSimilarityThreshold){
    //first take the commits
    log.debug( "Similarity threshold is [{}]", commitSimilarityThreshold);
    List<CommitComparison> result = new ArrayList<>();
    Iterator<Commit> caseStudyCommits = caseStudy.getCommits().iterator();
    Iterator<Commit> simulationCommits = simulation.getCommits().iterator();
    Commit caseStudyCommit = null;
    Commit simulationCommit = null;
    while (caseStudyCommits.hasNext() && simulationCommits.hasNext()){
      caseStudyCommit = caseStudyCommits.next();
      simulationCommit = simulationCommits.next();
        result.add(
            CommitComparison.builder()
                .caseStudy(caseStudyCommit)
                .simulation(simulationCommit)
                .similarity(CommitSimilarity.similarityDetailOf(caseStudyCommit, simulationCommit, featureWeights))
                .build()
        );
    }
    while (caseStudyCommits.hasNext()) {
      caseStudyCommit = caseStudyCommits.next();
      result.add(
          CommitComparison.builder()
              .caseStudy(caseStudyCommit)
              .simulation(Commit.emptyCommit())
              .similarity(CommitSimilarityResult.emptyResult())
              .build()
      );
    }
    while (simulationCommits.hasNext()) {
      simulationCommit = simulationCommits.next();
      result.add(
          CommitComparison.builder()
              .caseStudy(Commit.emptyCommit())
              .simulation(simulationCommit)
              .similarity(CommitSimilarityResult.emptyResult())
              .build()
      );
    }
    return result;
  }
}
