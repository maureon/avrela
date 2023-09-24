package es.ubu.lsi.avrela.css.model;

import es.ubu.lsi.avrela.scm.model.Commit;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class CommitComparison {

  private Commit caseStudy;

  private Commit simulation;

  private CommitSimilarityResult similarity;


}
