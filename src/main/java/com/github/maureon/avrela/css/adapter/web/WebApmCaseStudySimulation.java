package com.github.maureon.avrela.css.adapter.web;

import com.github.maureon.avrela.apm.adapter.web.WebHistoricalApmData;
import com.github.maureon.avrela.css.model.IssueComparison;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WebApmCaseStudySimulation {

  private WebHistoricalApmData caseStudy;

  private WebHistoricalApmData simulation;

  private Integer participants;

  private WebIssueSimilarityFunctionConfig issueSimilarityFunctionConfig;

  private List<IssueComparison> issueComparisons;

  private Double similarityThreshold;

  private WebRubricEvaluation  rubricEvaluation;

}
