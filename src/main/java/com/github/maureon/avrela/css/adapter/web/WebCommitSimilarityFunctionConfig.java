package com.github.maureon.avrela.css.adapter.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebCommitSimilarityFunctionConfig {

  private Double filesWeight;

  private Double messageWeight;

}
