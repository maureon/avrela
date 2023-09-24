package es.ubu.lsi.avrela.bdd.scm;

import es.ubu.lsi.avrela.apm.adapter.github.GitHubApmClient;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubCommitRepository;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubScmClient;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitFileMapper;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitMapper;
import es.ubu.lsi.avrela.scm.model.Commit;
import es.ubu.lsi.avrela.scm.port.CommitRepository;
import feign.Logger.Level;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;

public class CommitRangeImportSteps {

  private String repositoryOwner = null, repositoryName = null;

  private String branch;

  private ZonedDateTime  beginAt = null, endAt = null;

  private List<Commit> commitsUnderTest;


  @Given("the commit range belongs to the repository owned by {string} named {string}")
  public void theCommitRangeBelongsToTheRepositoryOwnedByNamed(String repoOwner, String repoName) {
    this.repositoryOwner = repoOwner;
    this.repositoryName = repoName;
  }

  @And("branch is {string}")
  public void branchIs(String branch) {
    this.branch = branch;
  }

  @And("range is {zoneddatetime} {zoneddatetime}")
  public void andRangeIs(ZonedDateTime startAt, ZonedDateTime endAt) {
    this.beginAt = startAt;
    this.endAt = endAt;

  }

  @When("I import the commit")
  public void iImportTheCommit() {
    GitHubScmClient gitHubScmClient = GitHubScmClient.with(Level.FULL);
    GitHubApmClient gitHubApmClient = GitHubApmClient.with(Level.FULL);
    GitHubCommitMapper commitMapper = new GitHubCommitMapper( new GitHubCommitFileMapper());
    CommitRepository commitRepository = new GitHubCommitRepository(gitHubScmClient, gitHubApmClient, commitMapper);

    commitsUnderTest = commitRepository.findCommitsByBranchAndDateRange(repositoryOwner, repositoryName, branch, beginAt, endAt);
  }

  @Then("the following commits should be present in same order")
  public void theFollowingCommitsShouldBePresentInSameOrder(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (int index = 0; index < commitsUnderTest.size() ; index++){
      Assertions.assertEquals(rows.get(index).get("CommitSHA"), commitsUnderTest.get(index).getSha(), "Sha at ["+index+"] must be eq" );
    }
  }
}
