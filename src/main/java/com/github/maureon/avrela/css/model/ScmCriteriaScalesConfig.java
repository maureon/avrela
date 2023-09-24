package com.github.maureon.avrela.css.model;

import java.util.List;
import lombok.Data;

@Data
public class ScmCriteriaScalesConfig {

  private List<Double> teamWorkCriteriaScale;

  private List<Double> commitSimilarityCriteriaScale;

  private List<Double> apmIssueTraceabilityCriteriaScale;

}
