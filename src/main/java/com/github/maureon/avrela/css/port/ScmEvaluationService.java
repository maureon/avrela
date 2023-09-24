package com.github.maureon.avrela.css.port;

import com.github.maureon.avrela.apm.adapter.github.GitHubApmClient;
import com.github.maureon.avrela.css.adapter.web.ScmWebRubricEvaluation;
import com.github.maureon.avrela.css.adapter.web.WebHistoricalScmData;
import com.github.maureon.avrela.css.adapter.web.WebRubricCriteriaEvaluation;
import com.github.maureon.avrela.css.adapter.web.WebScmCaseStudySimulation;
import com.github.maureon.avrela.css.model.CommitComparison;
import com.github.maureon.avrela.css.model.Rubric;
import com.github.maureon.avrela.css.model.ScmCaseStudySimulation;
import com.github.maureon.avrela.css.util.RubricDataGenerator;
import com.github.maureon.avrela.scm.adapter.github.GitHubCommitRepository;
import com.github.maureon.avrela.scm.adapter.github.GitHubHistoricalScmDataRepository;
import com.github.maureon.avrela.scm.adapter.github.GitHubScmClient;
import com.github.maureon.avrela.scm.adapter.github.mapper.GitHubCommitFileMapper;
import com.github.maureon.avrela.scm.adapter.github.mapper.GitHubCommitMapper;
import com.github.maureon.avrela.scm.model.CommitSimilarity;
import com.github.maureon.avrela.scm.model.CommitSimilarity.Feature;
import com.github.maureon.avrela.scm.model.HistoricalScmData;
import feign.Logger.Level;
import java.util.EnumMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScmEvaluationService {

  private ScmCriteriaService scmCriteriaService = new ScmCriteriaService();

  private WebHistoricalScmDataMapper webHistorialScmDataMapper = new WebHistoricalScmDataMapper();

  public WebScmCaseStudySimulation evaluate (WebScmCaseStudySimulation  scmCss){
    // Init GitHubClient
    GitHubScmClient gitHubScmClient = GitHubScmClient.with(Level.BASIC);
    GitHubApmClient gitHubApmClient = GitHubApmClient.with(Level.BASIC);

    GitHubCommitRepository commitRepository = new GitHubCommitRepository(gitHubScmClient, gitHubApmClient, new GitHubCommitMapper(new GitHubCommitFileMapper()));

    HistoricalScmData caseStudy, simulation;

    GitHubHistoricalScmDataRepository scmHistoricalDataRepository = new GitHubHistoricalScmDataRepository(commitRepository);

    WebHistoricalScmData  caseStudyRequest = scmCss.getCaseStudy();
    caseStudy = scmHistoricalDataRepository.findByRepoOwnerAndRepoNameAndBranchAndDatesBetween(
        caseStudyRequest.getRepoOwner(),
        caseStudyRequest.getRepoName(),
        caseStudyRequest.getBranch(),
        caseStudyRequest.getStartAt(),
        caseStudyRequest.getEndAt());
    caseStudy.setStartAt(caseStudyRequest.getStartAt());
    caseStudy.setEndAt(caseStudyRequest.getEndAt());

    WebHistoricalScmData simulationRequest = scmCss.getSimulation();
    simulation = scmHistoricalDataRepository.findByRepoOwnerAndRepoNameAndBranchAndDatesBetween(
        simulationRequest.getRepoOwner(),
        simulationRequest.getRepoName(),
        simulationRequest.getBranch(),
        simulationRequest.getStartAt(),
        simulationRequest.getEndAt());
    simulation.setStartAt(simulationRequest.getStartAt());
    simulation.setEndAt(simulationRequest.getEndAt());

    //Features-weight mapping
    EnumMap<Feature, Double> featureWeights = new EnumMap<>(CommitSimilarity.Feature.class);
    log.debug("files {}  messages {} ", scmCss.getCommitSimilarityFunctionConfig()
        .getFilesWeight(), scmCss.getCommitSimilarityFunctionConfig()
        .getMessageWeight());
    featureWeights.put(CommitSimilarity.Feature.FILES,  scmCss.getCommitSimilarityFunctionConfig()
        .getFilesWeight());
    featureWeights.put(CommitSimilarity.Feature.MESSAGE, scmCss.getCommitSimilarityFunctionConfig()
        .getMessageWeight());
    //calculations
    //teamwork
    Double teamWork = 100*Double.valueOf(
        scmCriteriaService.teamWorkEvaluationBasedOnAlternativeCommits(simulation, scmCss.getParticipants())/(simulation.getCommits().size()));
    Integer teamworkGrade = Rubric.evaluateCriteria(RubricDataGenerator.scmCriteria()
        .getTeamWorkCriteriaScale(), teamWork);
    //commitSimilarity
    Integer similarityThreshold = (int) Math.round(scmCss.getSimilarityThreshold()*100);
    Double commitSimilarity = scmCriteriaService.getCommitSimilarity(
            ScmCaseStudySimulation.builder().caseStudy(caseStudy).simulation(simulation).build(),
          featureWeights,
          similarityThreshold);
    Integer commitSimilarityMark = Rubric.evaluateCriteria(RubricDataGenerator.scmCriteria()
        .getCommitSimilarityCriteriaScale(), commitSimilarity);

    List<CommitComparison> commitComparisons = ScmCaseStudySimulation.builder().caseStudy(caseStudy).simulation(simulation).build().compare(featureWeights, similarityThreshold);
    //issueTraceability
    Double issueTraceability = scmCriteriaService.getCommitsWithIssueTraceability(ScmCaseStudySimulation.builder().caseStudy(caseStudy).simulation(simulation).build());
    Integer issueTraceabilityMark = Rubric.evaluateCriteria(RubricDataGenerator.scmCriteria()
        .getApmIssueTraceabilityCriteriaScale(), issueTraceability);



    ScmWebRubricEvaluation evaluation = ScmWebRubricEvaluation.builder()
        .teamWork(
            new WebRubricCriteriaEvaluation(teamWork, teamworkGrade)
        )
        .similarity(
            new WebRubricCriteriaEvaluation(commitSimilarity,commitSimilarityMark)
        )
        .issueTraceability(
            new WebRubricCriteriaEvaluation(issueTraceability, issueTraceabilityMark)
        )
        .build();

    WebScmCaseStudySimulation result = WebScmCaseStudySimulation.builder()
        .caseStudy(webHistorialScmDataMapper.toDto(caseStudy))
        .simulation(webHistorialScmDataMapper.toDto(simulation))
        .participants(scmCss.getParticipants())
        .commitSimilarityFunctionConfig(scmCss.getCommitSimilarityFunctionConfig())
        .commitComparisons(commitComparisons)
        .similarityThreshold(scmCss.getSimilarityThreshold())
        .rubricEvaluation(evaluation)
        .build();

    return result;
  }

}
