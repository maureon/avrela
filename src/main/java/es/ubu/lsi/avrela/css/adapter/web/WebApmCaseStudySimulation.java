package es.ubu.lsi.avrela.css.adapter.web;

import es.ubu.lsi.avrela.apm.adapter.web.WebHistoricalApmData;
import es.ubu.lsi.avrela.css.model.IssueComparison;
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
