package com.pros.bookmarks.model.validation;

import com.pros.bookmarks.dao.UserEntityDAO;import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

  private final UserEntityDAO userEntityDAO;

  public UniqueUsernameValidator(UserEntityDAO userEntityDAO) {
    this.userEntityDAO = userEntityDAO;
  }

  @Override
  public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
    return userEntityDAO.findByUsername(username).isEmpty();
  }
}
