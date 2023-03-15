package com.pros.bookmarks.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

  private String firstField;

  private String secondField;

  private String message;

  @Override
  public void initialize(FieldMatch constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);

    this.firstField = constraintAnnotation.firstField();
    this.secondField = constraintAnnotation.secondField();
    this.message = constraintAnnotation.message();
  }

  @Override
  public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
    boolean valid;

    String firstProperty = null;
    String secondProperty = "";

    try {
      firstProperty = BeanUtils.getProperty(o, firstField);
      secondProperty = BeanUtils.getProperty(o, secondField);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
    }

    if (firstProperty == null) {
      valid = secondProperty == null;
    } else {
      valid = firstProperty.equals(secondProperty);
    }

    if (!valid) {
      constraintValidatorContext
          .buildConstraintViolationWithTemplate(message)
          .addPropertyNode(secondField)
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
    }

    return valid;
  }
}
