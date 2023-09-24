package com.github.maureon.avrela.similarity;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JaroWinklerDistanceTest {

  @Test
  public void nullStringsShouldResultInDistanceZero(){
    String s1 = null;
    String s2 = null;

    double distance = JaroWinklerDistance.getDistance(s1, s2);

    Assertions.assertEquals(0d, distance);
  }

  @Test
  public void identicalStringsShouldMatch() {
    String s1 = "Hello World";
    String s2 = "Hello World";

    double distance = JaroWinklerDistance.getDistance(s1, s2);

    Assertions.assertEquals(1.0, distance);
  }

  @Test
  public void differentStringsShouldNotMatch() {
    String s1 = "Hello World";
    String s2 = "Goodbye World";

    double distance = JaroWinklerDistance.getDistance(s1, s2);

    Assertions.assertEquals(0.7, distance, 0.1);
  }

  @Test
  public void slightlyDifferentStringsShouldNotMatch() {
    String s1 = "Hello World";
    String s2 = "Hullo Wurld";

    double distance = JaroWinklerDistance.getDistance(s1, s2);

    Assertions.assertEquals(0.9, distance, 0.1);
  }
}
