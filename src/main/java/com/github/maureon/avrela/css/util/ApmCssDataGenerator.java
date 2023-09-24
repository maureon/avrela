package com.github.maureon.avrela.css.util;

import com.github.maureon.avrela.apm.adapter.web.WebHistoricalApmData;
import com.github.maureon.avrela.apm.model.Issue;
import com.github.maureon.avrela.apm.model.IssueState;
import com.github.maureon.avrela.apm.model.Sprint;
import com.github.maureon.avrela.css.adapter.web.WebApmCaseStudySimulation;
import com.github.maureon.avrela.css.adapter.web.WebIssueSimilarityFunctionConfig;
import com.github.maureon.avrela.css.adapter.web.WebRubricCriteriaEvaluation;
import com.github.maureon.avrela.css.adapter.web.WebRubricEvaluation;
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
