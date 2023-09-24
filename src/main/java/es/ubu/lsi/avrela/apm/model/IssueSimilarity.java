package es.ubu.lsi.avrela.apm.model;

import es.ubu.lsi.avrela.css.model.IssueSimilarityResult;
import es.ubu.lsi.avrela.similarity.Jaccard;
import es.ubu.lsi.avrela.similarity.JaroWinklerDistance;
import java.util.EnumMap;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IssueSimilarity {

  public enum Feature {
    LABELS,
    STATE,
    ISSUE_NAME
  }

  public static double similarityOf(Issue a, Issue b, EnumMap<IssueSimilarity.Feature, Double> featureWeights) {
    double labelSimilarity = Jaccard.similarityOf(new HashSet<>(a.getLabels()), new HashSet<>(b.getLabels()));

    int stateSimilarity = ( a.getState() == b.getState()) ? 1 : 0;

    // Calculate issue names similarity using Jaro-Winkler distance.
    double issueNameSimilarity = JaroWinklerDistance.getDistance(a.getName(), b.getName());

    return ((labelSimilarity*featureWeights.get(IssueSimilarity.Feature.LABELS))
        +(stateSimilarity*featureWeights.get(IssueSimilarity.Feature.STATE))
        +(issueNameSimilarity*featureWeights.get(IssueSimilarity.Feature.ISSUE_NAME)) )/featureWeights.values().stream().mapToDouble(Double::doubleValue).sum();
  }

  public static IssueSimilarityResult similarityDetailOf(Issue a, Issue b, EnumMap<IssueSimilarity.Feature, Double> featureWeights) {

    // Issue label similarity
    double labelSimilarity = Jaccard.similarityOf(new HashSet<>(a.getLabels()), new HashSet<>(b.getLabels()));

    double labelSimilarityWeighted = labelSimilarity*featureWeights.get(Feature.LABELS);

    //State similarity
    double stateSimilarity = ( a.getState() == b.getState()) ? 1 : 0;

    double stateSimilarityWeighted = stateSimilarity*featureWeights.get(Feature.STATE);

    // Calculate issue names similarity using Jaro-Winkler distance.
    double issueNameSimilarity = JaroWinklerDistance.getDistance(a.getName(), b.getName());

    double issueNameSimilarityWeighted = issueNameSimilarity*featureWeights.get(Feature.ISSUE_NAME);

    double result = (labelSimilarityWeighted+stateSimilarityWeighted+issueNameSimilarityWeighted)/featureWeights.values().stream().mapToDouble(Double::doubleValue).sum();

    log.debug("Adjusted similarity of [{}] and [{}] is [{}]",a , b, result);

   return IssueSimilarityResult.builder()
       .labelFilesSimilarity(labelSimilarity)
       .labelFilesSimilarityWeighted(labelSimilarityWeighted)
       .stateSimilarity(stateSimilarity)
       .stateSimilarityWeighted(stateSimilarityWeighted)
       .nameSimilarity(issueNameSimilarity)
       .nameSimilarityWeighted(issueNameSimilarityWeighted)
       .result(result)
       .build();
  }
}
