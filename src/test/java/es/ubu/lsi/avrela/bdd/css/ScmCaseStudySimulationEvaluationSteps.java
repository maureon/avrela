package es.ubu.lsi.avrela.bdd.css;

import es.ubu.lsi.avrela.apm.adapter.github.GitHubApmClient;
import es.ubu.lsi.avrela.css.model.Rubric;
import es.ubu.lsi.avrela.css.model.ScmCaseStudySimulation;
import es.ubu.lsi.avrela.css.port.ScmCriteriaService;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubCommitRepository;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubHistoricalScmDataRepository;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubScmClient;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitFileMapper;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitMapper;
import es.ubu.lsi.avrela.scm.model.CommitSimilarity;
import es.ubu.lsi.avrela.scm.model.CommitSimilarity.Feature;
import es.ubu.lsi.avrela.scm.model.HistoricalScmData;
import feign.Logger.Level;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;


@Slf4j
public class ScmCaseStudySimulationEvaluationSteps {

  GitHubHistoricalScmDataRepository scmHistoricalDataRepository;

  HistoricalScmData caseStudy;

  HistoricalScmData simulation;

  Integer simulationParticipants;

  List<Double> teamWorkCriteriaScale;

  List<Double> commitSimilarityCriteriaScale;

  List<Double> apmIssueTraceabilityCriteriaScale;
  private int commitSimilarityThreshold;

  private ScmCaseStudySimulation scmCaseStudySimulation;

  private EnumMap<Feature, Double> featureWeights;

  private ScmCriteriaService scmCriteriaService = new ScmCriteriaService();

  @Given("a scm case study with repo owner {string}, name {string}, branch is {string} and time period {zoneddatetime} {zoneddatetime}")
  public void aScmCaseStudyWithRepoOwnerNameBranchIsAndTimePeriod(String repoOwner, String repoName, String branch, ZonedDateTime startAt, ZonedDateTime endAt) {
    // Init GitHubClient
    GitHubScmClient gitHubScmClient = GitHubScmClient.with(Level.FULL);

    GitHubApmClient gitHubApmClient = GitHubApmClient.with(Level.FULL);

    GitHubCommitRepository commitRepository = new GitHubCommitRepository(gitHubScmClient, gitHubApmClient, new GitHubCommitMapper(new GitHubCommitFileMapper()));

    scmHistoricalDataRepository = new GitHubHistoricalScmDataRepository(commitRepository);

    caseStudy = scmHistoricalDataRepository.findByRepoOwnerAndRepoNameAndBranchAndDatesBetween(repoOwner, repoName, branch, startAt, endAt);

    //TODO temporary use case study as validation
    simulation = caseStudy;

    this.scmCaseStudySimulation = ScmCaseStudySimulation.builder()
        .caseStudy(this.caseStudy)
        .simulation(this.simulation)
        .build();

  }

  @And("a scm simulation with repo owner {string}, name {string}, branch is {string} and time period {zoneddatetime} {zoneddatetime} and {int} participant\\(s)")
  public void aScmSimulationWithRepoOwnerNameBranchIsAndTimePeriod(String repoOwner, String repoName, String branch, ZonedDateTime startAt, ZonedDateTime endAt, Integer simulationParticipants) {
    this.simulationParticipants = simulationParticipants;
  }

  @And("commit similarity function weights are \\(commit files = {double} , commit message = {double})")
  public void commitSimilarityFunctionWeightsAreCommitFilesCommitMessage(double commitFilesWeight, double commitNameWeight) {
    this.featureWeights = new EnumMap<>(CommitSimilarity.Feature.class);
    this.featureWeights.put(CommitSimilarity.Feature.FILES, commitFilesWeight);
    this.featureWeights.put(CommitSimilarity.Feature.MESSAGE, commitNameWeight);
  }

  @And("commit similarity threshold is set at {int}")
  public void commitSimilarityThresholdIsSetAt(int commitSimilarityThreshold) {
    this.commitSimilarityThreshold = commitSimilarityThreshold;
  }

  @And("a SCM evaluation rubric")
  public void aRubric(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    //Obtain teamwork criteria  scale
    teamWorkCriteriaScale = Rubric.toCriteriaScale(rows.get(0));

    commitSimilarityCriteriaScale = Rubric.toCriteriaScale(rows.get(1));

    apmIssueTraceabilityCriteriaScale = Rubric.toCriteriaScale(rows.get(2));
  }

  @When("I apply the SCM evaluation rubric")
  public void iApplyTheRubric() {
    //Evaluate Teamwork criteria



  }

  @Then("SCM evaluation rubric score should be")
  public void rubricScoreShouldBe(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    //Evaluate Teamwork: criteria won't apply as it is defined.
    //TODO: redefine as a numeric measure in order to consider evaluation scale
    Integer teamworkGrade = scmCriteriaService.teamWorkEvaluation(simulation, simulationParticipants);
    Assertions.assertEquals(2, teamworkGrade, "Teamwork grade");

    //Evaluate commit similarity
    Double commitSimilarity = scmCriteriaService.getCommitSimilarity(scmCaseStudySimulation, featureWeights, commitSimilarityThreshold);
    log.debug( "Commit similarity value is [{}]", commitSimilarity);

    Integer commitSimilarityActualRubricValue = Rubric.evaluateCriteria(commitSimilarityCriteriaScale, commitSimilarity);
    Integer commitSimilarityExpectedRubricValue = Rubric.getExpectedRubricValue(rows.get(1));

    Assertions.assertEquals(commitSimilarityExpectedRubricValue, commitSimilarityActualRubricValue, "Similarity evaluation should match");

    Double commitsWithIssueTraceability = scmCriteriaService.getCommitsWithIssueTraceability(scmCaseStudySimulation);


    Integer commitsWithIssueTraceabilityActualRubricValue = Rubric.evaluateCriteria(apmIssueTraceabilityCriteriaScale, commitsWithIssueTraceability);
    Integer commitsWithIssueTraceabilityExpectedRubricValue = Rubric.getExpectedRubricValue(rows.get(2));

    Assertions.assertEquals(commitsWithIssueTraceabilityExpectedRubricValue, commitsWithIssueTraceabilityActualRubricValue, "APM Issue Traceability evaluation should match");
  }


}
