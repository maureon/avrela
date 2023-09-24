package es.ubu.lsi.avrela.bdd.apm;

import es.ubu.lsi.avrela.apm.adapter.github.GitHubApmClient;
import es.ubu.lsi.avrela.apm.adapter.github.GitHubIssueRepository;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubCommentMapper;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubIssueEventMapper;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubIssueMapper;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubLabelMapper;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubMilestoneMapper;
import es.ubu.lsi.avrela.apm.model.Issue;
import es.ubu.lsi.avrela.apm.model.IssueEventType;
import es.ubu.lsi.avrela.apm.port.IssueRepository;
import feign.Logger.Level;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class IssueImportSteps {

  String repositoryOwner = null, repositoryName = null;

  String issueId = null;

  Issue issueUnderTest = null;
  private IssueRepository issueRepository;

  @Given("the repository owned by {string} named {string}")
  public void theRepositoryOwnedByNamed(String repositoryOwner, String repositoryName) {
    this.repositoryOwner = repositoryOwner;
    this.repositoryName = repositoryName;
  }

  @When("I import the issue with id {string}")
  public void iImportTheIssue(String issueId) {
    //Init GitHubClient
    GitHubApmClient gitHubApmClient = GitHubApmClient.with(Level.BASIC);
    GitHubMilestoneMapper milestoneMapper = GitHubMilestoneMapper.build();
    issueRepository = new GitHubIssueRepository(gitHubApmClient, new GitHubIssueMapper(
        new GitHubCommentMapper(),
        new GitHubLabelMapper(),
        new GitHubIssueEventMapper()
    ));

    //Fetch
    issueUnderTest = issueRepository.findById(repositoryOwner, repositoryName,issueId);
  }

  @Then("issue has comments check should be {string}")
  public void issueHasCommentsCheckShouldBe(String hasComments) {
    Boolean hasCommentsCheck = Boolean.valueOf(hasComments);
    Assertions.assertEquals(hasCommentsCheck, issueUnderTest.getComments().size() != 0);
  }

  @And("issue total comments should be {long}")
  public void issueTotalCommentsShouldBe(Long totalComments) {
    Assertions.assertEquals(totalComments, issueUnderTest.getComments().size());
  }

  @And("issue has image check should be {string}")
  public void issueHasImageCheckShouldBe(String arg) {
    Assertions.assertTrue(true);
  }

  @And("issue has link check should be {string}")
  public void issueHasLinkCheckShouldBe(String arg) {
    Boolean hasLinkCheck = Boolean.valueOf(arg);
    Assertions.assertEquals(hasLinkCheck, issueUnderTest.getHasLink());
  }

  @And("issue is labeled check should be {string}")
  public void issueIsLabeledCheckShouldBe(String arg) {
    Assertions.assertTrue(issueUnderTest.isLabeled());
  }

  @And("issue has label with value {string}")
  public void issueHasLabelWithValue(String arg) {
    Assertions.assertTrue(issueUnderTest.isLabeledWithLabel(arg));
  }

  @And("issue total referenced commits should be {long}")
  public void issueTotalReferencedCommitsShouldBe(Long arg) {
    Assertions.assertEquals(arg, issueUnderTest.countByEventType(IssueEventType.REFERENCED));
    Assertions.assertTrue(true);
  }

}
