package com.github.maureon.avrela.css.model;

import com.github.maureon.avrela.apm.model.HistoricalApmData;
import com.github.maureon.avrela.apm.model.Issue;
import com.github.maureon.avrela.apm.model.IssueSimilarity;
import com.github.maureon.avrela.apm.model.IssueSimilarity.Feature;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/** Models a case study simulation. */
@Data
@Builder
@Slf4j
public class ApmCaseStudySimulation {

  private HistoricalApmData caseStudy;

  private HistoricalApmData simulation;

  public List<Issue> filterIssueMatchComparisons() {
    List<Issue> result = new ArrayList<>();
    List<Issue> caseStudyIssues = caseStudy.filterIssues( issue -> true);
    List<Issue> simulationIssues = simulation.filterIssues( issue -> true);
    Iterator<Issue> caseStudyIssuesIterator = caseStudyIssues.iterator();
    Iterator<Issue> simulationIssuesIterator = simulationIssues.iterator();
    Issue caseStudyIssue = null;
    Issue simulationIssue = null;
    while(caseStudyIssuesIterator.hasNext() && simulationIssuesIterator.hasNext()){
      caseStudyIssue = caseStudyIssuesIterator.next();
      simulationIssue = simulationIssuesIterator.next();
      //TODO compare based on similarity:
      // - Order
      // - Title (Jaro–Winkler distance)
      // - Type
      if (caseStudyIssue.issueDescriptionMatch(simulationIssue)){
        result.add(caseStudyIssue);
      }
    }
    return result;
  }

  public List<IssueComparison> compare(EnumMap<Feature, Double> featureWeights, Double similarityThreshold) {
    log.debug("Similarity threshold is [{}]", similarityThreshold);
    List<IssueComparison> result = new ArrayList<>();
    Iterator<Issue> caseStudyIssues = caseStudy.getIssues().iterator();
    Iterator<Issue> simulationIssues = simulation.getIssues().iterator();
    Issue caseStudyIssue = null;
    Issue simulationIssue = null;
    while (caseStudyIssues.hasNext() && simulationIssues.hasNext()) {
      caseStudyIssue = caseStudyIssues.next();
      simulationIssue = simulationIssues.next();
      result.add(
          IssueComparison.builder()
              .caseStudy(caseStudyIssue)
              .simulation(simulationIssue)
              .similarity(IssueSimilarity.similarityDetailOf(caseStudyIssue, simulationIssue,
                  featureWeights))
              .build()
      );
    }
    while (caseStudyIssues.hasNext()) {
      caseStudyIssue = caseStudyIssues.next();
      result.add(
          IssueComparison.builder()
              .caseStudy(caseStudyIssue)
              .simulation(Issue.emptyIssue())
              .similarity(IssueSimilarityResult.emptyResult())
              .build()
      );
    }
    while (simulationIssues.hasNext()) {
      simulationIssue = simulationIssues.next();
      result.add(
          IssueComparison.builder()
              .caseStudy(Issue.emptyIssue())
              .simulation(simulationIssue)
              .similarity(IssueSimilarityResult.emptyResult())
              .build()
      );
    }
    return result;
  }
}
