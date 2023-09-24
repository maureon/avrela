package com.github.maureon.avrela.apm.adapter.web.mapper;

import com.github.maureon.avrela.apm.adapter.web.WebHistoricalApmData;
import com.github.maureon.avrela.apm.model.HistoricalApmData;

public class WebHistoricalApmDataMapper {

  public WebHistoricalApmData toDto(HistoricalApmData domain){
    WebHistoricalApmData result = WebHistoricalApmData.builder()
        .repoOwner(domain.getRepoOwner())
        .repoName(domain.getRepoName())
        .sprints(domain.getSprints())
        .build();
    result.setStartAt(domain.getStartAt());
    result.setEndAt(domain.getEndAt());
    return result;
  }

  public HistoricalApmData toDomain(WebHistoricalApmData dto){
    HistoricalApmData result = HistoricalApmData.builder()
        .repoOwner(dto.getRepoOwner())
        .repoName(dto.getRepoName())
        .sprints(dto.getSprints())
        .build();
    result.setStartAt(dto.getStartAt());
    result.setEndAt(dto.getEndAt());
    return result;
  }

}
