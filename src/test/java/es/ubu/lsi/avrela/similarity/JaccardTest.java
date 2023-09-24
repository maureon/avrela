package es.ubu.lsi.avrela.similarity;

import es.ubu.lsi.avrela.scm.model.CommitFile;
import java.util.ArrayList;
import java.util.List;

public class JaccardTest {


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
