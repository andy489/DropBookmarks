package com.pros.bookmarks.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BookmarkDto {
  @NotBlank
  @Size(min = 3, max = 20, message = "must be between 3 and 20 characters long")
  private String name;

  @NotBlank
  @Size(min = 5, message = "must be between at least characters long")
  private String url;

  private String description;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
