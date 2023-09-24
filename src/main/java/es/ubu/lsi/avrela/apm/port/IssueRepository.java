package es.ubu.lsi.avrela.apm.port;

import es.ubu.lsi.avrela.apm.model.Issue;

public interface IssueRepository {

  Issue findById(String repoOwner, String repoName, String issueId);

}
