package es.ubu.lsi.avrela.css.adapter.web;

import es.ubu.lsi.avrela.css.model.CommitComparison;
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
