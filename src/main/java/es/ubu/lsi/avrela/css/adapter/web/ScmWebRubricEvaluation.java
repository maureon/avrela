package es.ubu.lsi.avrela.css.adapter.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScmWebRubricEvaluation {

  private WebRubricCriteriaEvaluation teamWork;

  /**
   * Commit similarity
   */
  private WebRubricCriteriaEvaluation similarity;

  /**
   * Issue traceability
   */
  private WebRubricCriteriaEvaluation issueTraceability;

  public Integer getMark(){
    return teamWork.getMark() + similarity.getMark() + issueTraceability.getMark();
  }

}
