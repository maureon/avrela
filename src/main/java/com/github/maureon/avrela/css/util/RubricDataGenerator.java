package com.github.maureon.avrela.css.util;

import com.github.maureon.avrela.css.model.ApmCriteriaScalesConfig;
import com.github.maureon.avrela.css.model.Rubric;
import com.github.maureon.avrela.css.model.ScmCriteriaScalesConfig;
import java.util.HashMap;
import java.util.Map;

public class RubricDataGenerator {

  public static ApmCriteriaScalesConfig apmCriteria(){
    // teamwork criteria
    Map<String, String> teamWorkCriteriaDefinition = new HashMap<>();
    teamWorkCriteriaDefinition.put("0", "50");
    teamWorkCriteriaDefinition.put("1", "None");
    teamWorkCriteriaDefinition.put("2", "100");
    // Task management tool - description
    Map<String, String> ttlDescriptionCriteriaDefinition = new HashMap<>();
    ttlDescriptionCriteriaDefinition.put("0", "0");
    ttlDescriptionCriteriaDefinition.put("1", "100");
    ttlDescriptionCriteriaDefinition.put("2", "None");
    // Task management tool - organization
    Map<String, String> ttlOrganizationCriteriaDefinition = new HashMap<>();
    ttlOrganizationCriteriaDefinition.put("0", "0");
    ttlOrganizationCriteriaDefinition.put("1", "100");
    ttlOrganizationCriteriaDefinition.put("2", "None");

    ApmCriteriaScalesConfig result = new ApmCriteriaScalesConfig();
    result.setTeamWorkCriteriaScale(Rubric.toCriteriaScale(teamWorkCriteriaDefinition));
    result.setToolLearningDescriptionCriteriaScale(Rubric.toCriteriaScale(ttlDescriptionCriteriaDefinition));
    //TODO: task management tool - organization

    return result;
  }

  public static ScmCriteriaScalesConfig scmCriteria(){
    // teamwork criteria
    Map<String, String> scmTeamWorkCriteriaDefinition = new HashMap<>();
    scmTeamWorkCriteriaDefinition.put("0", "0");
    scmTeamWorkCriteriaDefinition.put("1", "50");
    scmTeamWorkCriteriaDefinition.put("2", "100");
    // Task management tool - description
    Map<String, String> commitSimilarityCriteriaDefinition = new HashMap<>();
    commitSimilarityCriteriaDefinition.put("0", "None");
    commitSimilarityCriteriaDefinition.put("1", "75");
    commitSimilarityCriteriaDefinition.put("2", "100");
    // Task management tool - organization
    Map<String, String> apmIssueTraceabilityCriteriaDefinition = new HashMap<>();
    apmIssueTraceabilityCriteriaDefinition.put("0", "None");
    apmIssueTraceabilityCriteriaDefinition.put("1", "None");
    apmIssueTraceabilityCriteriaDefinition.put("2", "50");

    ScmCriteriaScalesConfig result = new ScmCriteriaScalesConfig();
    result.setTeamWorkCriteriaScale(Rubric.toCriteriaScale(scmTeamWorkCriteriaDefinition));
    result.setCommitSimilarityCriteriaScale(Rubric.toCriteriaScale(commitSimilarityCriteriaDefinition));
    result.setApmIssueTraceabilityCriteriaScale(Rubric.toCriteriaScale(apmIssueTraceabilityCriteriaDefinition));

    return result;
  }


}
