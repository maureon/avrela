package com.github.maureon.avrela.apm.port;

import com.github.maureon.avrela.apm.model.Issue;

public interface IssueRepository {

  Issue findById(String repoOwner, String repoName, String issueId);

}
