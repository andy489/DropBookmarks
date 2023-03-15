package com.pros.bookmarks.model.validation;

import com.pros.bookmarks.dao.UserEntityDAO;import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  private final UserEntityDAO userEntityDAO;

  public UniqueEmailValidator(UserEntityDAO userEntityDAO) {
    this.userEntityDAO = userEntityDAO;
  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
    return userEntityDAO.findByEmail(email).isEmpty();
  }
}
