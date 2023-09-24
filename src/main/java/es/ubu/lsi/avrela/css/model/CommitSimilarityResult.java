package es.ubu.lsi.avrela.css.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommitSimilarityResult {

  private double commitFilesSimilarity;

  private double commitFilesSimilarityWeighted;

  private double messageSimilarity;

  private double messageSimilarityWeighted;

  private double result;

  public static CommitSimilarityResult emptyResult(){
    return CommitSimilarityResult.builder()
        .messageSimilarity(0d)
        .messageSimilarityWeighted(0d)
        .commitFilesSimilarity(0d)
        .commitFilesSimilarity(0d)
        .result(0d)
        .build();
  }

}
