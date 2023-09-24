package com.github.maureon.avrela.css.port;

import com.github.maureon.avrela.apm.model.HistoricalApmData;
import com.github.maureon.avrela.apm.model.Issue;
import com.github.maureon.avrela.css.model.ApmCaseStudySimulation;

public class ApmCriteriaService {

  public Double getTeamWorkValue(HistoricalApmData historicalApmData, Integer participants) {
    Double teamWorkDividend = 100d *  historicalApmData.filterIssues(
        Issue.participantsGreaterThanOrEqual(participants)).size();
    Double teamWordDivisor = historicalApmData.countIssues().doubleValue();
    Double teamWork = teamWorkDividend / teamWordDivisor;
    return teamWork;
  }

  public Double getTtlDescriptionValue(ApmCaseStudySimulation apmCaseStudySimulation){
    Double toolLearningDescriptionDividend = 100d * apmCaseStudySimulation.filterIssueMatchComparisons(
    ).size();
    Double toolLearningDescriptionDivisor = apmCaseStudySimulation.getSimulation().countIssues().doubleValue();
    return toolLearningDescriptionDividend / toolLearningDescriptionDivisor;
  }
}
