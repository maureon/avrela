package es.ubu.lsi.avrela.css.model;

import es.ubu.lsi.avrela.apm.model.Issue;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IssueComparison {

  private Issue caseStudy;

  private Issue simulation;

  private IssueSimilarityResult similarity;
}
