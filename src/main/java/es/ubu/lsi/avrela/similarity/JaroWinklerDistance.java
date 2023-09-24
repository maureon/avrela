package es.ubu.lsi.avrela.similarity;

/**
 * Implements the Jaro-Winkler distance algorithm,
 * which is a measure of the similarity between two strings.
 * All the credit goes to ChatGPT.
 */
public class JaroWinklerDistance {

  public static double getDistance(String s1, String s2) {

    //Null strings should result in zero value.
    if( s1 == null || s2 == null){
      return 0d;
    }


    int m = 0;
    int t = 0;
    int l1 = s1.length();
    int l2 = s2.length();
    int lmin = Math.min(l1, l2);
    int lmax = Math.max(l1, l2);

    if (lmin == 0) {
      return lmax == 0 ? 1.0 : 0.0;
    }

    int range = (lmax / 2) - 1;

    boolean[] s1Flags = new boolean[l1];
    boolean[] s2Flags = new boolean[l2];

    for (int i = 0; i < l1; i++) {
      int start = Math.max(0, i - range);
      int end = Math.min(i + range + 1, l2);

      for (int j = start; j < end; j++) {
        if (s1Flags[i] || s2Flags[j]) {
          continue;
        }

        if (s1.charAt(i) == s2.charAt(j)) {
          m++;
          s1Flags[i] = true;
          s2Flags[j] = true;
          break;
        }
      }
    }

    if (m == 0) {
      return 0.0;
    }

    int k = 0;
    for (int i = 0; i < l1; i++) {
      if (!s1Flags[i]) {
        continue;
      }

      while (!s2Flags[k]) {
        k++;
      }

      if (s1.charAt(i) != s2.charAt(k)) {
        t++;
      }
      k++;
    }

    double jaro = ((m / (double) l1) + (m / (double) l2) + ((m - t / 2.0) / m)) / 3.0;

    double jaroWinkler = jaro;
    if (jaro > 0.7) {
      int l = 0;
      int n = Math.min(4, lmin);
      while (l < n && s1.charAt(l) == s2.charAt(l)) {
        l++;
      }

      jaroWinkler = jaro + (l * 0.1 * (1.0 - jaro));
    }

    return jaroWinkler;
  }
}
