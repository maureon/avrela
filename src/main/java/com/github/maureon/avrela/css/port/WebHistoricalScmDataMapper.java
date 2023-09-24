package com.github.maureon.avrela.css.port;

import com.github.maureon.avrela.css.adapter.web.WebHistoricalScmData;
import com.github.maureon.avrela.scm.model.HistoricalScmData;

public class WebHistoricalScmDataMapper {

  public WebHistoricalScmData toDto(HistoricalScmData domain) {
    WebHistoricalScmData result = WebHistoricalScmData.builder()
        .repoOwner(domain.getRepoOwner())
        .repoName(domain.getRepoName())
        .branch(domain.getBranch())
        .commits(domain.getCommits())
        .build();
    result.setStartAt(domain.getStartAt());
    result.setEndAt(domain.getEndAt());
    return result;
  }
}
