package com.pros.bookmarks.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.pros.bookmarks.model.validation.FieldMatch;
import com.pros.bookmarks.model.validation.UniqueEmail;
import com.pros.bookmarks.model.validation.UniqueUsername;

@FieldMatch(
    firstField = "password",
    secondField = "confirmPassword",
    message = "passwords mismatch")
public class UserDto {
  @NotBlank
  @Size(min = 3, max = 20, message = "must be between 3 and 20 characters long")
  @UniqueUsername(message = "must be unique")
  private String username;

  @NotBlank
  @Size(min = 3, max = 20, message = "must be between 3 and 20 characters long")
  private String fullName;

  @NotNull
  @Email
  @UniqueEmail(message = "must be unique")
  private String email;

  @NotBlank
  @Size(min = 3, max = 20, message = "must be between 3 and 20 characters long")
  private String password;

  private String confirmPassword;

  public UserDto() {}

  public UserDto(
      String username, String fullName, String email, String password, String confirmPassword) {
    this.username = username;
    this.fullName = fullName;
    this.email = email;
    this.password = password;
    this.confirmPassword = confirmPassword;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  @Override
  public String toString() {
    return "UserDto{"
        + "username='"
        + username
        + '\''
        + ", fullName='"
        + fullName
        + '\''
        + ", email='"
        + email
        + '\''
        + ", password='"
        + password
        + '\''
        + ", confirmPassword='"
        + confirmPassword
        + '\''
        + '}';
  }
}
