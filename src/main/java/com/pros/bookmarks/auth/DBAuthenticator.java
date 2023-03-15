package com.pros.bookmarks.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;
import com.pros.bookmarks.dao.UserEntityDAO;
import com.pros.bookmarks.model.entity.UserEntity;
import java.util.Optional;

public class DBAuthenticator implements Authenticator<BasicCredentials, UserEntity> {

  /**
   * Reference to User DAO to check whether the user with credentials specified exists in the
   * application's backing database.
   */
  private final UserEntityDAO userEntityDAO;
  /**
   * Hibernate session factory; Necessary for the authenticate method to work, which doesn't work as
   * described in the documentation.
   */
  private final SessionFactory sessionFactory;
  /** A helper class for password encryption; Thread-safe. */
  private final PasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

  /**
   * A constructor to initialize DAO.
   *
   * @param userEntityDAO The DAO for the User object necessary to look for users by their
   *     credentials.
   * @param sessionFactory Hibernate session factory; temporary solution as database authentication
   *     doesn't work as described in documentation.
   */
  public DBAuthenticator(final UserEntityDAO userEntityDAO, final SessionFactory sessionFactory) {
    this.userEntityDAO = userEntityDAO;
    this.sessionFactory = sessionFactory;
  }

  /**
   * Implementation of the authenticate method.
   *
   * @param credentials An instance of the BasicCredentials class containing username and password.
   * @return An Optional containing the user characterized by credentials or an empty optional
   *     otherwise.
   * @throws AuthenticationException throws an exception in the case of authentication problems.
   */
  @UnitOfWork
  @Override
  public final Optional<UserEntity> authenticate(BasicCredentials credentials)
      throws AuthenticationException {
    try (Session session = sessionFactory.openSession()) {
      Optional<UserEntity> result;
      ManagedSessionContext.bind(session);

      result = userEntityDAO.findByUsername(credentials.getUsername());

      if (result.isEmpty()) {
        return result;
      } else {
        if (passwordEncryptor.checkPassword(
            credentials.getPassword(), result.get().getPassword())) {
          session.setCacheMode(CacheMode.IGNORE);
          return result;
        } else {
          return Optional.empty();
        }
      }

    } catch (Exception e) {
      throw new AuthenticationException(e);
    } finally {
      ManagedSessionContext.unbind(sessionFactory);
    }
  }
}
