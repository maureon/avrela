package es.ubu.lsi.avrela.css.adapter.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebRubricEvaluation {

  private WebRubricCriteriaEvaluation teamWork;

  /**
   * TaskManagement tool learning - Description
   */
  private WebRubricCriteriaEvaluation ttlDescription;

  /**
   * TaskManagement tool learning - Organization
   */
  private WebRubricCriteriaEvaluation ttlOrganization;

  public Integer getMark(){
    return teamWork.getMark() + ttlDescription.getMark() + ttlOrganization.getMark();
  }

}
