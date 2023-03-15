package com.pros.bookmarks;

import com.pros.bookmarks.auth.DBAuthenticator;
import com.pros.bookmarks.configuration.DropBookmarksConfiguration;
import com.pros.bookmarks.dao.BookmarkEntityDAO;
import com.pros.bookmarks.dao.UserEntityDAO;
import com.pros.bookmarks.model.entity.BookmarkEntity;
import com.pros.bookmarks.model.entity.UserEntity;
import com.pros.bookmarks.model.validation.UniqueEmailValidator;
import com.pros.bookmarks.model.validation.UniqueUsernameValidator;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.migrations.MigrationsBundle;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.hibernate.SessionFactory;
import com.pros.bookmarks.resources.BookmarkResource;
import com.pros.bookmarks.resources.UserResource;
import com.pros.bookmarks.service.BookmarkService;
import com.pros.bookmarks.service.UserService;

public class DropBookmarksApplication extends Application<DropBookmarksConfiguration> {

  public static void main(final String[] args) throws Exception {
    //    PasswordEncryptor encryptor = new BasicPasswordEncryptor();
    //    IntStream.rangeClosed(1, 10)
    //        .boxed()
    //        .forEach(i -> System.out.println(encryptor.encryptPassword(i.toString())));
    new DropBookmarksApplication().run(args);
  }

  @Override
  public String getName() {
    return "DropBookmarks";
  }

  private static final HibernateBundle<DropBookmarksConfiguration> hibernateBundle =
      new HibernateBundle<>(UserEntity.class, BookmarkEntity.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(DropBookmarksConfiguration configuration) {
          return configuration.getDataSourceFactory();
        }
      };

  private static final MigrationsBundle<DropBookmarksConfiguration> migrationsBundle =
      new MigrationsBundle<>() {
        @Override
        public DataSourceFactory getDataSourceFactory(DropBookmarksConfiguration configuration) {
          return configuration.getDataSourceFactory();
        }
      };

  @Override
  public void initialize(final Bootstrap<DropBookmarksConfiguration> bootstrap) {
    bootstrap.addBundle(hibernateBundle);
    bootstrap.addBundle(migrationsBundle);
  }

  @Override
  public void run(final DropBookmarksConfiguration configuration, final Environment environment) {

    UserService userService =
        new UserService(new UserEntityDAO(hibernateBundle.getSessionFactory()));
    UserResource userResource = new UserResource(userService);

    BookmarkResource bookmarkResource =
        new BookmarkResource(
            new BookmarkService(
                new BookmarkEntityDAO(hibernateBundle.getSessionFactory()), userService));

    UserEntityDAO userEntityDAO = new UserEntityDAO(hibernateBundle.getSessionFactory());

    UniqueUsernameValidator uniqueUsernameValidator = new UniqueUsernameValidator(userEntityDAO);

    UniqueEmailValidator uniqueEmailValidator =
        new UniqueEmailValidator(new UserEntityDAO(hibernateBundle.getSessionFactory()));

    environment.jersey().register(userResource);
    environment.jersey().register(bookmarkResource);

    environment.jersey().register(uniqueUsernameValidator);
    environment.jersey().register(uniqueEmailValidator);

    final DBAuthenticator dbAuthenticator =
        new UnitOfWorkAwareProxyFactory(hibernateBundle)
            .create(
                DBAuthenticator.class,
                new Class<?>[] {UserEntityDAO.class, SessionFactory.class},
                new Object[] {userEntityDAO, hibernateBundle.getSessionFactory()});

    final AuthDynamicFeature dbAuthDynamicFeature =
        new AuthDynamicFeature(
            new BasicCredentialAuthFilter.Builder<UserEntity>()
                .setAuthenticator(dbAuthenticator)
                .setAuthorizer((userEntity, s, containerRequestContext) -> true)
                .setRealm("SECURITY REALM")
                .buildAuthFilter());

    environment.jersey().register(dbAuthDynamicFeature);

    environment.jersey().register(RolesAllowedDynamicFeature.class);
    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(UserEntity.class));
  }
}
