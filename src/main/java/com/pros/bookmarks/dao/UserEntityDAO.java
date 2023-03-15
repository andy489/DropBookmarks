package com.pros.bookmarks.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import com.pros.bookmarks.model.entity.UserEntity;
import java.util.List;
import java.util.Optional;

public class UserEntityDAO extends AbstractDAO<UserEntity> {
  public UserEntityDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<UserEntity> findAll() {
    return list(namedTypedQuery("UserEntity.findAll"));
  }

  public Optional<UserEntity> findById(Long id) {
    return Optional.ofNullable(get(id));
  }

  public Optional<UserEntity> findByUsername(String username) {
    return namedTypedQuery("UserEntity.findByUsername")
        .setParameter("username", username)
        .uniqueResultOptional();
  }

  public Optional<UserEntity> findByEmail(String email) {
    return namedTypedQuery("UserEntity.findByEmail")
        .setParameter("email", email)
        .uniqueResultOptional();
  }

  public List<UserEntity> findByEmailSuffix(String emailSuffix) {
    return list(
        namedTypedQuery("UserEntity.findByEmailSuffix")
            .setParameter("emailSuffix", "%" + emailSuffix));
  }

  public UserEntity save(UserEntity userEntity) {
    return persist(userEntity);
  }

  public Boolean deleteById(Long userId) {
    Optional<UserEntity> byId = findById(userId);

    if (byId.isPresent()) {
      namedQuery("UserEntity.deleteById").setParameter("id", userId).executeUpdate();
      return true;
    }

    return false;
  }

  public void setIdentityInsertOn() {
    namedQuery("UserEntity.setIdentityInsertOn");
  }

  public UserEntity updateUser(UserEntity userEntity) {
    return persist(userEntity);
  }
}
