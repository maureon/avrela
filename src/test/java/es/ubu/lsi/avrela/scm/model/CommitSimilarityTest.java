package es.ubu.lsi.avrela.scm.model;

import es.ubu.lsi.avrela.scm.model.CommitSimilarity.Feature;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommitSimilarityTest {

 @Test
 public void commitSimilarityShouldWork () {

   Assertions.assertEquals(
       0.97
       , CommitSimilarity.similarityOf(
           commitABCDE(commitFiles1()),
           commitABCDEFG(commitFiles1()),
           files1Message1()
       )
       , 0.1
   );

   Assertions.assertEquals(
       0.96
       , CommitSimilarity.similarityOf(
           commitABCDE(commitFiles1()),
           commitABCDEFG(commitFiles1()),
           files0dot5Message1()
       )
       ,0.1
   );

   Assertions.assertEquals(
       0.4
       ,CommitSimilarity.similarityOf(
           commitABCDE(commitFiles9()),
           commitABCDEFG(commitFiles1()),
           files1Message1()
       )
       ,0.1
   );

   Assertions.assertEquals(
       0.71
       , CommitSimilarity.similarityOf(
           commitABCDE(commitFiles1234()),
           commitABCDEFG(commitFiles1()),
           files0dot5Message1()
       )
       , 0.1
   );
 }

  private  EnumMap<Feature, Double> files1Message1() {
    EnumMap<Feature, Double> result = new EnumMap<>(Feature.class);
    result.put(Feature.FILES, 1.0);
    result.put(Feature.MESSAGE, 1.0);
    return result;
  }

  private  EnumMap<Feature, Double> files0dot5Message1() {
    EnumMap<Feature, Double> result = new EnumMap<>(Feature.class);
    result.put(Feature.FILES, 0.5);
    result.put(Feature.MESSAGE, 1.0);
    return result;
  }

  private Commit commitABCDE(List<CommitFile> commitFiles) {
    return Commit.builder()
        .message("ABCDE")
        .files(commitFiles)
        .build();
  }

  private Commit commitABCDEFG(List<CommitFile> commitFiles) {
    return  Commit.builder()
        .message("ABCDEFG")
        .files(commitFiles)
        .build();
  }

  private  List<CommitFile> commitFiles12345() {
    List<CommitFile> result = new ArrayList<>();
    result.add(CommitFile.builder().name("1").build());
    result.add(CommitFile.builder().name("2").build());
    result.add(CommitFile.builder().name("3").build());
    result.add(CommitFile.builder().name("4").build());
    result.add(CommitFile.builder().name("5").build());
    return result;
  }

  private  List<CommitFile> commitFiles1234() {
    List<CommitFile> result = new ArrayList<>();
    result.add(CommitFile.builder().name("1").build());
    result.add(CommitFile.builder().name("2").build());
    result.add(CommitFile.builder().name("3").build());
    result.add(CommitFile.builder().name("4").build());
    return result;
  }

  private  List<CommitFile> commitFiles1() {
    List<CommitFile> result = new ArrayList<>();
    result.add(CommitFile.builder().name("1").build());
    return result;
  }

  private  List<CommitFile> commitFiles9() {
    List<CommitFile> result = new ArrayList<>();
    result.add(CommitFile.builder().name("9").build());
    return result;
  }
    
 
  
}
