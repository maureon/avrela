package com.github.maureon.avrela.css.model;

import com.github.maureon.avrela.scm.model.Commit;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class CommitComparison {

  private Commit caseStudy;

  private Commit simulation;

  private CommitSimilarityResult similarity;


}
