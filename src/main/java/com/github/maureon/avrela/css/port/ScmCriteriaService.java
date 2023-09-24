package com.github.maureon.avrela.css.port;

import com.github.maureon.avrela.css.model.ScmCaseStudySimulation;
import com.github.maureon.avrela.scm.model.Commit;
import com.github.maureon.avrela.scm.model.CommitSimilarity.Feature;
import com.github.maureon.avrela.scm.model.HistoricalScmData;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ScmCriteriaService {

  public Integer teamWorkEvaluation(HistoricalScmData simulation, Integer simulationParticipants) {
    Set<String> participants = simulation.getParticipants();
    //Evaluate agile fidelity (authors alternate commits)
    if (simulationParticipants.equals(simulation.getParticipants().size())){
      if (simulation.alternativeCommits(simulationParticipants)){
        return 2;
      } else {
        return 1;
      }
    }else {
      return 0;
    }
  }

  public Integer teamWorkEvaluationBasedOnAlternativeCommits(HistoricalScmData simulation, Integer simulationParticipants) {
    List<String> currentStreakAuthors = new ArrayList<>();
    //Evaluate agile fidelity (authors alternate commits)
    Integer result = 0;
    if(simulationParticipants == 1) {
      return simulation.getCommits().size();
    }
    for (Commit commit : simulation.getCommits()){
        if (currentStreakAuthors.contains(commit.getAuthor())){
          // a new streak may start
          currentStreakAuthors = new ArrayList<>();
          currentStreakAuthors.add(commit.getAuthor());
        } else {
          currentStreakAuthors.add(commit.getAuthor());
          if (currentStreakAuthors.size() == simulationParticipants){
            //count the streak
            result =  result + simulationParticipants;
            //new streak starts
            currentStreakAuthors = new ArrayList<>();
          }
        }
    }
    return result;
  }



  public Double getCommitSimilarity(ScmCaseStudySimulation scmCaseStudySimulation, EnumMap<Feature, Double> featureWeights, int similarityThreshold){
    Double result;
    Integer commitSimilarityDividend = scmCaseStudySimulation.filterCommitMatchComparison(featureWeights, similarityThreshold).size();
    log.debug( "Found [{}] similar commits", commitSimilarityDividend);
    Integer commitSimilarityDivisor = scmCaseStudySimulation.getCaseStudy().getCommits().size();
    if (commitSimilarityDivisor == 0){
      return 0d;
    }
    result = 100*Double.valueOf(commitSimilarityDividend / (double)commitSimilarityDivisor);
    log.debug( "Commit similarity value is [{}]", result);
    return result;
  }

  public Double getCommitsWithIssueTraceability(ScmCaseStudySimulation scmCaseStudySimulation){
    Integer commitsWithIssueTraceabilityDividend = scmCaseStudySimulation
        .getSimulation()
        .getCommitsWithIssueTraceability()
        .size();

    Integer commitsWithIssueTraceabilityDivisor = scmCaseStudySimulation.getSimulation().getCommits().size();


    if( commitsWithIssueTraceabilityDividend > commitsWithIssueTraceabilityDivisor){
      return 100d;
    }

    if( commitsWithIssueTraceabilityDivisor == 0){
      return 0d;
    }

    Double result = 100*Double.valueOf((double)commitsWithIssueTraceabilityDividend / commitsWithIssueTraceabilityDivisor);
    log.debug( "Issue traceability [{}]/[{}] adjusted is [{}]", commitsWithIssueTraceabilityDividend, commitsWithIssueTraceabilityDivisor, result);
    return result;
  }
}
