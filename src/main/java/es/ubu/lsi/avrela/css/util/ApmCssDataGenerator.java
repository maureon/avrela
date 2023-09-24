package es.ubu.lsi.avrela.css.util;

import es.ubu.lsi.avrela.apm.adapter.web.WebHistoricalApmData;
import es.ubu.lsi.avrela.apm.model.Issue;
import es.ubu.lsi.avrela.apm.model.IssueState;
import es.ubu.lsi.avrela.apm.model.Sprint;
import es.ubu.lsi.avrela.css.adapter.web.WebApmCaseStudySimulation;
import es.ubu.lsi.avrela.css.adapter.web.WebIssueSimilarityFunctionConfig;
import es.ubu.lsi.avrela.css.adapter.web.WebRubricCriteriaEvaluation;
import es.ubu.lsi.avrela.css.adapter.web.WebRubricEvaluation;
import java.util.List;

public class ApmCssDataGenerator {

  public static WebApmCaseStudySimulation getWebApmCaseStudySimulation() {
    return WebApmCaseStudySimulation.builder()
        .caseStudy(getWebHistoricalApmData())
        .simulation(getWebHistoricalApmData())
        .similarityThreshold(0.75)
        .participants(1)
        .issueSimilarityFunctionConfig(
            getSimilarityFunctionConfig()
        )
        .rubricEvaluation(
            getWebRubricEvaluation()
        )
        .build();
  }

  public static WebRubricEvaluation getWebRubricEvaluation() {
    return WebRubricEvaluation.builder()
        .teamWork(new WebRubricCriteriaEvaluation(100d, 2))
        .ttlDescription(new WebRubricCriteriaEvaluation(100d, 1))
        .ttlOrganization(new WebRubricCriteriaEvaluation(100d, 1))
        .build();
  }

  public static WebIssueSimilarityFunctionConfig getSimilarityFunctionConfig() {
    return WebIssueSimilarityFunctionConfig.builder()
        .labelWeight(1.0)
        .stateWeight(1.0)
        .issueNameWeight(1.0)
        .build();
  }

  private static WebHistoricalApmData getWebHistoricalApmData() {
    return WebHistoricalApmData.builder()
        .repoOwner("davidmigloz")
        .repoName("go-bees")
        .stringifyStartAt("2017-01-25")
        .stringifyEndAt("2017-01-26")
        .sprints(
            List.of(
                Sprint.builder()
                    .issues(
                        List.of(
                            Issue.builder()
                                .id("Id")
                                .name("Issue Name")
                                .state(IssueState.OPEN)
                                .body("Loren ipsum, loren ipsum, loren ipsum, loren ipsum ...")
                                .labels(
                                    List.of(
                                        "label1",
                                        "label2",
                                        "label3"
                                    )
                                )
                                .hasImages(Boolean.FALSE)
                                .hasLink(Boolean.TRUE)
                                .hasTaskList(Boolean.FALSE)
                                .build()
                        )
                    )
                    .build()
            )
        )
        .build();
  }

  public static WebApmCaseStudySimulation webApmCaseStudySimulationBeforeEvaluation(){
    WebApmCaseStudySimulation result = getWebApmCaseStudySimulation();
    result.setRubricEvaluation(null);
    return result;
  }
}
