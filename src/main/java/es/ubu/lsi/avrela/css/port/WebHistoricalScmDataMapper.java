package es.ubu.lsi.avrela.css.port;

import es.ubu.lsi.avrela.css.adapter.web.WebHistoricalScmData;
import es.ubu.lsi.avrela.scm.model.HistoricalScmData;

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
