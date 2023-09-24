package es.ubu.lsi.avrela.css.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IssueSimilarityResult {

  private double labelFilesSimilarity;

  private double labelFilesSimilarityWeighted;

  private double stateSimilarity;

  private double stateSimilarityWeighted;

  private double nameSimilarity;

  private double nameSimilarityWeighted;

  private double result;

  public static IssueSimilarityResult emptyResult(){
    return IssueSimilarityResult.builder()
        .labelFilesSimilarity(0d)
        .labelFilesSimilarityWeighted(0d)
        .stateSimilarity(0d)
        .stateSimilarityWeighted(0d)
        .nameSimilarity(0d)
        .nameSimilarityWeighted(0d)
        .result(0d)
        .build();
  }

}
