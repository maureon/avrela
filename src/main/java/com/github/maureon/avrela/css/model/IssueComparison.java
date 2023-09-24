package com.github.maureon.avrela.css.model;

import com.github.maureon.avrela.apm.model.Issue;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IssueComparison {

  private Issue caseStudy;

  private Issue simulation;

  private IssueSimilarityResult similarity;
}
