package es.ubu.lsi.avrela.similarity;

import java.util.HashSet;
import java.util.Set;

public class Jaccard {

  /**
   * The Jaccard similarity measures the similarity between two sets of data.
   *
   * @param a first set of data
   * @param b second set of data
   * @return jaccard similarity.
   */
  public static double similarityOf(Set<Object> a, Set<Object> b) {
    if (a == null || b == null) {
      return 0.0;
    }
    double result;
    Set<Object> intersection = new HashSet<>(a);
    intersection.retainAll(b);
    int intersectionSize = intersection.size();
    Set<Object> union = new HashSet<>(a);
    union.addAll(b);
    int unionSize = union.size();
    result = (double) intersectionSize / unionSize;
    return (result);
  }
}
