package com.github.maureon.avrela.apm.adapter.github.model;


import com.google.gson.annotations.SerializedName;

public enum GitHubItemState {

  @SerializedName("open") OPEN,
  @SerializedName("closed") CLOSED

}
