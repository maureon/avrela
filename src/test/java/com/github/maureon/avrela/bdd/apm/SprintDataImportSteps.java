package com.github.maureon.avrela.bdd.apm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.maureon.avrela.apm.adapter.github.GitHubApmClient;
import com.github.maureon.avrela.apm.adapter.github.GitHubSprintRepository;
import com.github.maureon.avrela.apm.adapter.github.mapper.GitHubMilestoneMapper;
import com.github.maureon.avrela.apm.model.IssueState;
import com.github.maureon.avrela.apm.model.Sprint;
import com.github.maureon.avrela.apm.port.SprintRepository;
import feign.Logger.Level;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SprintDataImportSteps {

  String repositoryOwner = null, repositoryName = null;

  ZonedDateTime  beginAt = null, endAt = null;

  SprintRepository sprintRepository = null;

  Sprint sprintUnderTest = null;

  @ParameterType(".*")
  public ZonedDateTime zoneddatetime(String zonedDateTime) {
    return ZonedDateTime.parse(zonedDateTime);
  }

  @Given("a repository owned by {string} named {string}")
  public void aRepositoryOwnedByNamed(String repositoryOwner, String repositoryName) {
    this.repositoryOwner = repositoryOwner;
    this.repositoryName = repositoryName;
  }

  @And("the sprint dates {zoneddatetime} and {zoneddatetime}")
  public void theDatesAnd(ZonedDateTime beginAt, ZonedDateTime endAt) {
    this.beginAt = beginAt;
    this.endAt = endAt;
  }

  @When("I import the sprint issues")
  public void iTryToImportTheRepository() {
    //Init GitHubClient
    GitHubApmClient gitHubApmClient = GitHubApmClient.with(Level.BASIC);
    GitHubMilestoneMapper milestoneMapper = GitHubMilestoneMapper.build();
    sprintRepository = new GitHubSprintRepository(gitHubApmClient, milestoneMapper);
    //Fetch
    var sprints = this.sprintRepository.findByDueOnBetween(this.repositoryOwner, this.repositoryName, this.beginAt, this.endAt);
    assertNotNull(sprints);
    assertEquals(1, sprints.size());
    sprintUnderTest = sprints.get(0);
  }

  @Then("total issues should be {long}")
  public void totalIssuesShouldBe(Long arg) {
    assertEquals(arg, sprintUnderTest.countIssues());
  }

  @And("total issues labeled as documentation should be {long}")
  public void totalIssuesLabeledAsDocumentationShouldBe(Long arg) {
    assertEquals(arg , sprintUnderTest.countIssuesByLabel("documentation"));
  }

  @And("total issues labeled as feature should be {long}")
  public void totalIssuesLabeledAsFeatureShouldBe(Long arg) {
    assertEquals(arg , sprintUnderTest.countIssuesByLabel("feature"));
  }

  @And("total issues labeled as testing should be {long}")
  public void totalIssuesLabeledAsTestingShouldBe(Long arg) {
    assertEquals(arg , sprintUnderTest.countIssuesByLabel("testing"));
  }

  @And("total issues with comments should be {long}")
  public void totalIssuesWithCommentsShouldBe(Long arg) {
    assertEquals(arg, sprintUnderTest.countIssuesByHasComments(true));
  }

  @And("total closed issues should be {long}")
  public void totalClosedIssuesShouldBe(Long arg) {
    assertEquals(arg, sprintUnderTest.countIssuesByState(IssueState.CLOSED));
  }

  @And("total issues labeled as bug should be {long}")
  public void totalIssuesLabeledAsBugShouldBe(Long arg) {
    assertEquals(arg , sprintUnderTest.countIssuesByLabel("bug"));
  }

  @And("total issues with task list should be {long}")
  public void totalIssuesWithTaskListShouldBe(Long arg) {
    assertEquals(arg, sprintUnderTest.countIssuesByHasTaskList(true));
  }
}
