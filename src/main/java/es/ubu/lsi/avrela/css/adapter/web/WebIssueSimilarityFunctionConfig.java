package es.ubu.lsi.avrela.css.adapter.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebIssueSimilarityFunctionConfig {

  private Double labelWeight;

  private Double stateWeight;

  private Double issueNameWeight;

}
