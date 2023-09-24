package es.ubu.lsi.avrela.bdd.scm;

import es.ubu.lsi.avrela.apm.adapter.github.GitHubApmClient;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubCommitRepository;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubScmClient;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitFileMapper;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitMapper;
import es.ubu.lsi.avrela.scm.model.Commit;
import es.ubu.lsi.avrela.scm.port.CommitRepository;
import feign.Logger.Level;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
public class CommitImportSteps {

  private String repoOwner;

  private String repoName;

  private Commit commitUnderTest;

  @Given("commits are imported from the repository owned by {string} named {string}")
  public void commitsAreImportedFromTheRepositoryOwnedByNamed(String repoOwner, String repoName) {
    log.info("Finding out a commit with coordinates [{}]", repoOwner + " " + repoName);
    this.repoOwner = repoOwner;
    this.repoName = repoName;
  }

  @When("I import the commit {string}")
  public void iImportTheCommit(String commitSha) {
    //Init GitHubClient
    GitHubScmClient gitHubScmClient = GitHubScmClient.with(Level.FULL);
    GitHubApmClient gitHubApmClient = GitHubApmClient.with(Level.FULL);
    GitHubCommitMapper commitMapper = new GitHubCommitMapper( new GitHubCommitFileMapper());
    CommitRepository commitRepository = new GitHubCommitRepository(gitHubScmClient, gitHubApmClient, commitMapper);

    commitUnderTest = commitRepository.findCommit(repoOwner, repoName, commitSha);

  }

  @Then("total files changed should be {long}")
  public void totalFilesChangedShouldBeTotalFilesChanged(Long totalFileChanged) {
    Assertions.assertEquals(totalFileChanged, commitUnderTest.getTotalFilesChanged() );
  }

  @And("total additions should be {long}")
  public void totalAdditionsShouldBeTotalAdditions(Long totalAdditions) {
    Assertions.assertEquals(totalAdditions, commitUnderTest.getTotalAdditions());
    Assertions.assertEquals(totalAdditions, commitUnderTest.getTotalAdditions());
  }

  @And("total deletions should be {long}")
  public void totalDeletionsShouldBeTotalDeletions(Long totalDeletions) {
    Assertions.assertEquals(totalDeletions, commitUnderTest.getTotalDeletions());
  }

  @And("commit author should be {string}")
  public void commitAuthorShouldBe(String author) {
    Assertions.assertEquals(author, commitUnderTest.getAuthor());
  }

  @And("commit message should be {string}")
  public void commitMessageShouldBe(String message) {
    Assertions.assertEquals(message, commitUnderTest.getMessage());
  }
}
