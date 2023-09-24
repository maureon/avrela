package com.github.maureon.avrela.apm.adapter.github.model;

import com.google.gson.annotations.SerializedName;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GitHubComment {

  private String id;

  private String body;

  private GitHubUser user;

  @SerializedName("created_at")
  private ZonedDateTime createdAt;

  @SerializedName("updated_at")
  private ZonedDateTime updatedAt;

}
