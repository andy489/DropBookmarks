package com.pros.bookmarks.model.view;

import java.util.Set;

public class UserView {
  private Long id;
  private String username;
  private String fullName;
  private String email;
  private Set<BookmarkView> bookmarks;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<BookmarkView> getBookmarks() {
    return bookmarks;
  }

  public void setBookmarks(Set<BookmarkView> bookmarks) {
    this.bookmarks = bookmarks;
  }
}
