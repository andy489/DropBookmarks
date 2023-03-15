package com.pros.bookmarks.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@NamedQueries({
  @NamedQuery(name = "UserEntity.findAll", query = "SELECT u FROM users u"),
  @NamedQuery(
      name = "UserEntity.findByUsername",
      query = "SELECT u FROM users u WHERE u.username = :username"),
  @NamedQuery(
      name = "UserEntity.findByEmail",
      query = "SELECT u FROM users u WHERE u.email = :email"),
  @NamedQuery(
      name = "UserEntity.findByEmailSuffix",
      query = "SELECT u FROM users u WHERE u.email LIKE :emailSuffix"),
  @NamedQuery(name = "UserEntity.deleteById", query = "DELETE FROM users u WHERE u.id = :id")
})
@NamedNativeQueries({
  @NamedNativeQuery(name = "UserEntity.setIdentityInsertOn", query = "SET IDENTITY_INSERT users ON")
})
public class UserEntity extends BaseEntity implements Principal {
  @Column(nullable = false, unique = true)
  private String username;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private Set<BookmarkEntity> bookmarks = new HashSet<>();

  public UserEntity() {}

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<BookmarkEntity> getBookmarks() {
    return bookmarks;
  }

  public void setBookmarks(Set<BookmarkEntity> bookmarks) {
    this.bookmarks = bookmarks;
  }

  @Override
  public String getName() {
    return "UserEntity";
  }

  @Override
  public boolean implies(Subject subject) {
    return Principal.super.implies(subject);
  }
}
