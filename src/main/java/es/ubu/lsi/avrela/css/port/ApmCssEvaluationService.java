package es.ubu.lsi.avrela.css.port;

import es.ubu.lsi.avrela.apm.adapter.github.GitHubApmClient;
import es.ubu.lsi.avrela.apm.adapter.github.GitHubHistoricalApmDataRepository;
import es.ubu.lsi.avrela.apm.adapter.github.GitHubSprintRepository;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubMilestoneMapper;
import es.ubu.lsi.avrela.apm.adapter.web.WebHistoricalApmData;
import es.ubu.lsi.avrela.apm.adapter.web.mapper.WebHistoricalApmDataMapper;
import es.ubu.lsi.avrela.apm.model.HistoricalApmData;
import es.ubu.lsi.avrela.apm.model.IssueSimilarity;
import es.ubu.lsi.avrela.apm.model.IssueSimilarity.Feature;
import es.ubu.lsi.avrela.css.adapter.web.WebApmCaseStudySimulation;
import es.ubu.lsi.avrela.css.adapter.web.WebRubricCriteriaEvaluation;
import es.ubu.lsi.avrela.css.adapter.web.WebRubricEvaluation;
import es.ubu.lsi.avrela.css.model.ApmCaseStudySimulation;
import es.ubu.lsi.avrela.css.model.ApmCriteriaScalesConfig;
import es.ubu.lsi.avrela.css.model.IssueComparison;
import es.ubu.lsi.avrela.css.model.Rubric;
import es.ubu.lsi.avrela.css.util.ApmCssDataGenerator;
import es.ubu.lsi.avrela.css.util.RubricDataGenerator;
import feign.Logger.Level;
import java.util.EnumMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApmCssEvaluationService {

  private ApmCriteriaService criteriaService = new ApmCriteriaService();

  private WebHistoricalApmDataMapper webHistoricalApmDataMapper = new WebHistoricalApmDataMapper();

  public WebApmCaseStudySimulation evaluate (WebApmCaseStudySimulation apmCss){
    //Init services
    GitHubApmClient gitHubApmClient = GitHubApmClient.with(Level.BASIC);
    GitHubMilestoneMapper milestoneMapper = GitHubMilestoneMapper.build();
    GitHubSprintRepository sprintFinder = new GitHubSprintRepository(gitHubApmClient, milestoneMapper);
    GitHubHistoricalApmDataRepository apmDataRepository = new GitHubHistoricalApmDataRepository(sprintFinder);
    //Get case study commits
    WebHistoricalApmData caseStudyRequest = apmCss.getCaseStudy();
    HistoricalApmData caseStudy =
        apmDataRepository.findByRepoOwnerAndRepoNameAndSprintDueBetween(
            caseStudyRequest.getRepoOwner(),
            caseStudyRequest.getRepoName(),
            caseStudyRequest.getStartAt(),
            caseStudyRequest.getEndAt());
    caseStudy.setStartAt(caseStudyRequest.getStartAt());
    caseStudy.setEndAt(caseStudyRequest.getEndAt());

    //Get simulation commits
    WebHistoricalApmData simulationRequest = apmCss.getSimulation();
    HistoricalApmData simulation =
        apmDataRepository.findByRepoOwnerAndRepoNameAndSprintDueBetween(
            simulationRequest.getRepoOwner(),
            simulationRequest.getRepoName(),
            simulationRequest.getStartAt(),
            simulationRequest.getEndAt());
    simulation.setStartAt(simulationRequest.getStartAt());
    simulation.setEndAt(simulationRequest.getEndAt());

    //Features-weight mapping
    EnumMap<IssueSimilarity.Feature, Double> featureWeights = new EnumMap<>(IssueSimilarity.Feature.class);
    log.debug("labels {}  states {} name {} ",
        apmCss.getIssueSimilarityFunctionConfig().getLabelWeight(),
        apmCss.getIssueSimilarityFunctionConfig().getStateWeight(),
        apmCss.getIssueSimilarityFunctionConfig().getIssueNameWeight());

    featureWeights.put(IssueSimilarity.Feature.LABELS,  apmCss.getIssueSimilarityFunctionConfig()
        .getLabelWeight());
    featureWeights.put(Feature.STATE, apmCss.getIssueSimilarityFunctionConfig()
        .getStateWeight());
    featureWeights.put(Feature.ISSUE_NAME, apmCss.getIssueSimilarityFunctionConfig()
        .getIssueNameWeight());

    //calculations
    ApmCriteriaScalesConfig apmScales = RubricDataGenerator.apmCriteria();
    //teamwork
    Double teamWorkValue = criteriaService.getTeamWorkValue(simulation, apmCss.getParticipants());
    Integer teamWorkMark = Rubric.evaluateCriteria(apmScales.getTeamWorkCriteriaScale() ,teamWorkValue);
    // ttl - description
    Double ttlDescriptionValue = criteriaService.getTtlDescriptionValue(
        ApmCaseStudySimulation.builder()
            .caseStudy(caseStudy)
            .simulation(simulation)
            .build()

    );
    Integer ttlDescriptionMark = Rubric.evaluateCriteria(apmScales.getToolLearningDescriptionCriteriaScale() ,ttlDescriptionValue);
    WebRubricEvaluation webRubricEvaluation = WebRubricEvaluation.builder()
        .teamWork(
            new WebRubricCriteriaEvaluation(teamWorkValue , teamWorkMark )
        )
        .ttlDescription(
            new WebRubricCriteriaEvaluation(ttlDescriptionValue , ttlDescriptionMark )
        )
        .ttlOrganization(ApmCssDataGenerator.getWebRubricEvaluation().getTtlOrganization()) // TODO: calculate & replace
        .build();

    List<IssueComparison> issueComparisons = ApmCaseStudySimulation.builder().caseStudy(caseStudy).simulation(simulation).build().compare(featureWeights, apmCss.getSimilarityThreshold());

    //Results
    WebApmCaseStudySimulation result = WebApmCaseStudySimulation.builder()
        .caseStudy(webHistoricalApmDataMapper.toDto(caseStudy))
        .simulation(webHistoricalApmDataMapper.toDto(simulation))
        .rubricEvaluation(webRubricEvaluation)
        .similarityThreshold(apmCss.getSimilarityThreshold())
        .issueSimilarityFunctionConfig(apmCss.getIssueSimilarityFunctionConfig())
        .participants(apmCss.getParticipants())
        .issueComparisons(issueComparisons)
        .build();

    return result;
  }

}
