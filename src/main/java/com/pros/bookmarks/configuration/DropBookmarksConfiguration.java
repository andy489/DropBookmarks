package com.pros.bookmarks.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

public class DropBookmarksConfiguration extends Configuration {
  @Valid @NotNull private DataSourceFactory dataSourceFactory;

  @NotBlank private String authPassword;

  @JsonProperty("authPassword")
  public String getAuthPassword() {
    return authPassword;
  }

  @JsonProperty("authPassword")
  public void setAuthPassword(String authPassword) {
    this.authPassword = authPassword;
  }

  @JsonProperty("database")
  public DataSourceFactory getDataSourceFactory() {
    if (Objects.isNull(dataSourceFactory)) {
      dataSourceFactory = new DataSourceFactory();
    }
    return dataSourceFactory;
  }

  @JsonProperty("database")
  public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
    this.dataSourceFactory = dataSourceFactory;
  }
}
