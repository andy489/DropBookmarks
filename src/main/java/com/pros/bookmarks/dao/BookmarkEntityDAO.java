package com.pros.bookmarks.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import com.pros.bookmarks.model.entity.BookmarkEntity;
import java.util.List;
import java.util.Optional;

public class BookmarkEntityDAO extends AbstractDAO<BookmarkEntity> {

  public BookmarkEntityDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<BookmarkEntity> findAll() {
    return list(namedTypedQuery("BookmarkEntity.findAll"));
  }

  public List<BookmarkEntity> findAllByUserId(Long userId) {
    return list(namedTypedQuery("BookmarkEntity.findAllByUserId").setParameter("id", userId));
  }

  public List<BookmarkEntity> findByNameAndUserId(String name, Long ownerId) {
    return list(
        namedTypedQuery("BookmarkEntity.findByNameAndUserId")
            .setParameter("infix", "%" + name + "%")
            .setParameter("ownerId", ownerId));
  }

  public BookmarkEntity save(BookmarkEntity bookmarkEntity) {
    return persist(bookmarkEntity);
  }

  public Optional<BookmarkEntity> findByIdAndUserId(Long id, Long ownerId) {
    return namedTypedQuery("BookmarkEntity.findByIdAndUserId")
        .setParameter("id", id)
        .setParameter("ownerId", ownerId)
        .uniqueResultOptional();
  }

  public Boolean deleteById(Long id) {
    try {
      namedQuery("BookmarkEntity.deleteById").setParameter("id", id).executeUpdate();
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}
