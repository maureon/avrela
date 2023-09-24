package com.github.maureon.avrela.css.adapter.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebRubricCriteriaEvaluation {

  /**
   * Criteria value
   */
  private Double value;

  /**
   * Rubric mark
   */
  private Integer mark;

}
