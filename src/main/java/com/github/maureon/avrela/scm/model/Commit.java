package com.github.maureon.avrela.scm.model;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/** Models a commit. */
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Getter
@Builder
public class Commit {

  /** Unique identifier generated by Git. */
  @ToString.Include
  private String sha;

  /** Date. */
  private ZonedDateTime date;

  /** Author. */
  private String author;

  /** Message. */
  @ToString.Include
  private String message;

  /** Associated issue ids. */
  private Set<String> associatedIssues;

  private List<CommitFile> files;

  public Long getTotalFilesChanged() {
    if (this.files == null){
      return 0l;
    }
    return Long.valueOf(files.size());
  }

  public Long getTotalAdditions() {
    if (this.files == null){
      return 0l;
    }
    return this.files.stream()
        .collect(Collectors.summingLong(CommitFile::getAdditions));
  }

  public Long getTotalDeletions() {
    if (this.files == null){
      return 0l;
    }
    return this.files.stream()
        .collect(Collectors.summingLong(CommitFile::getDeletions));
  }

  public Boolean hasAssociatedIssues(){
    if (this.associatedIssues == null || this.associatedIssues.isEmpty()){
      return false;
    }
    return true;
  }

  public static Commit emptyCommit(){
    return Commit.builder()
        .sha("No commit to compare")
        .message("No commit to compare")
        .files(Collections.emptyList())
        .build();
  }
}
