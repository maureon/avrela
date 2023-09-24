package com.github.maureon.avrela.apm.model;

import com.github.maureon.avrela.apm.model.IssueSimilarity.Feature;
import java.util.EnumMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssueSimilarityTest {

  @Test
  public void whenLabelsAndStateAreConsideredIssuesShouldBeTotallySimilar(){
    Issue a = getIssue();

    double result = IssueSimilarity.similarityOf(a,a, getFeatureWeightsConsideringLabelsAndState100());

    Assertions.assertEquals(1.0, result, "Should be totally similar");

  }
  @Test
  public void whenLabelsAndStateAndNameAreConsideredIssuesShouldBeTotallySimilar(){
    Issue a = getIssue();

    double result = IssueSimilarity.similarityOf(a,a, getFeatureWeightsConsideringLabelsAndStateAndIssueName100());

    Assertions.assertEquals(1.0, result, "Should be totally similar");

  }

  @Test
  public void whenLabelsAndStateAreConsideredIssuesShouldBeTotallyDifferent(){
    Issue a = getIssue();

    Issue b = new Issue.IssueBuilder()
        .id("xxx")
        .hasLink(true)
        .hasImages(true)
        .hasTaskList(true)
        .state(IssueState.CLOSED)
        .labels(List.of("label3", "label4"))
        .build();

    double result = IssueSimilarity.similarityOf(a,b, getFeatureWeightsConsideringLabelsAndState100());

    Assertions.assertEquals(0.0, result, "Should be totally different");

  }
  @Test
  public void whenLabelsAndStateAndNameAreConsideredIssuesShouldBeTotallyDifferent(){
    Issue a = getIssue();

    Issue b = new Issue.IssueBuilder()
        .id("xxx")
        .name("modify me")
        .hasLink(true)
        .hasImages(true)
        .hasTaskList(true)
        .state(IssueState.CLOSED)
        .labels(List.of("label3", "label4"))
        .build();

    double result = IssueSimilarity.similarityOf(a,b, getFeatureWeightsConsideringLabelsAndState100());

    Assertions.assertEquals(0.0, result, "Should be totally different");

  }



  private static Issue getIssue() {
    return new Issue.IssueBuilder()
        .id("xxx")
        .name("Hello issue")
        .hasLink(true)
        .hasImages(true)
        .hasTaskList(true)
        .labels(List.of("label1", "label2"))
        .build();
  }

  private static EnumMap<IssueSimilarity.Feature, Double> getFeatureWeightsConsideringLabelsAndState100() {
    EnumMap<IssueSimilarity.Feature, Double> result = new EnumMap<>(IssueSimilarity.Feature.class);
    result.put(Feature.LABELS, 1.0);
    result.put(Feature.STATE, 1.0);
    result.put(Feature.ISSUE_NAME, 0.0);
    return result;
  }

  private static EnumMap<IssueSimilarity.Feature, Double> getFeatureWeightsConsideringLabelsAndStateAndIssueName100() {
    EnumMap<IssueSimilarity.Feature, Double> result = new EnumMap<>(IssueSimilarity.Feature.class);
    result.put(Feature.LABELS, 1.0);
    result.put(Feature.STATE, 1.0);
    result.put(Feature.ISSUE_NAME, 1.0);
    return result;
  }


}
