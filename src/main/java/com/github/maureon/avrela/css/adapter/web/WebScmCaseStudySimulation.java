package com.github.maureon.avrela.css.adapter.web;

import com.github.maureon.avrela.css.model.CommitComparison;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WebScmCaseStudySimulation {

  private WebHistoricalScmData caseStudy;

  private WebHistoricalScmData simulation;

  private Integer participants;

  private WebCommitSimilarityFunctionConfig commitSimilarityFunctionConfig;

  private List<CommitComparison> commitComparisons;

  private Double similarityThreshold;

  private ScmWebRubricEvaluation  rubricEvaluation;

}
