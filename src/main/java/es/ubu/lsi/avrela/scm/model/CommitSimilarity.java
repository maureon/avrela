package es.ubu.lsi.avrela.scm.model;

import es.ubu.lsi.avrela.css.model.CommitSimilarityResult;
import es.ubu.lsi.avrela.similarity.Jaccard;
import es.ubu.lsi.avrela.similarity.JaroWinklerDistance;
import java.util.EnumMap;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CommitSimilarity {

  public enum Feature {
    FILES,
    MESSAGE
  }

  public static double similarityOf(
      Commit a, Commit b, EnumMap<CommitSimilarity.Feature, Double> featureWeights) {

    // Commit files similarity using commit file names
    double commitFilesSimilarity = Jaccard.similarityOf(new HashSet<>(a.getFiles()), new HashSet<>(b.getFiles()));

    double commitFilesSimilarityWeighted = commitFilesSimilarity*featureWeights.get(CommitSimilarity.Feature.FILES);

    // Calculate issue names similarity using Jaro-Winkler distance.
    double messageSimilarity = JaroWinklerDistance.getDistance(a.getMessage(), b.getMessage());

    double messageSimilarityWeighted = messageSimilarity*featureWeights.get(Feature.MESSAGE);

    double result = (commitFilesSimilarityWeighted+messageSimilarityWeighted)/featureWeights.values().stream().mapToDouble(Double::doubleValue).sum();

    log.debug("Adjusted similarity of [{}] and [{}] is [{}]",a , b, result);

    return result;
  }

  public static CommitSimilarityResult similarityDetailOf(
      Commit a, Commit b, EnumMap<CommitSimilarity.Feature, Double> featureWeights) {

    // Commit files similarity using commit file names
    double commitFilesSimilarity = Jaccard.similarityOf(new HashSet<>(a.getFiles()), new HashSet<>(b.getFiles()));

    double commitFilesSimilarityWeighted = commitFilesSimilarity*featureWeights.get(CommitSimilarity.Feature.FILES);

    // Calculate issue names similarity using Jaro-Winkler distance.
    double messageSimilarity = JaroWinklerDistance.getDistance(a.getMessage(), b.getMessage());

    double messageSimilarityWeighted = messageSimilarity*featureWeights.get(Feature.MESSAGE);

    double result = (commitFilesSimilarityWeighted+messageSimilarityWeighted)/featureWeights.values().stream().mapToDouble(Double::doubleValue).sum();

    log.debug("Adjusted similarity of [{}] and [{}] is [{}]",a , b, result);

    return CommitSimilarityResult.builder()
        .commitFilesSimilarity(commitFilesSimilarity)
        .commitFilesSimilarityWeighted(commitFilesSimilarityWeighted)
        .messageSimilarity(messageSimilarity)
        .messageSimilarityWeighted(messageSimilarityWeighted)
        .result(result)
        .build();
  }

}
