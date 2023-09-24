package es.ubu.lsi.avrela.css.port;

import es.ubu.lsi.avrela.apm.model.HistoricalApmData;
import es.ubu.lsi.avrela.apm.model.Issue;
import es.ubu.lsi.avrela.css.model.ApmCaseStudySimulation;

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
