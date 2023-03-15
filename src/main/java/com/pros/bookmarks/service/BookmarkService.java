package com.pros.bookmarks.service;

import com.pros.bookmarks.dao.BookmarkEntityDAO;import com.pros.bookmarks.model.dto.BookmarkDto;import com.pros.bookmarks.model.entity.BookmarkEntity;import com.pros.bookmarks.model.mapper.MapStructMapper;
import com.pros.bookmarks.model.view.BookmarkFullView;
import com.pros.bookmarks.model.view.BookmarkView;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookmarkService {

  private final BookmarkEntityDAO bookmarkEntityDAO;
  private final UserService userService;

  public BookmarkService(BookmarkEntityDAO bookmarkEntityDAO, UserService userService) {
    this.bookmarkEntityDAO = bookmarkEntityDAO;
    this.userService = userService;
  }

  public List<BookmarkFullView> getAll() {
    return bookmarkEntityDAO.findAll().stream()
        .map(MapStructMapper.INSTANCE::toFullView)
        .collect(Collectors.toList());
  }

  public List<BookmarkView> getAllByUserId(Long userId) {
    return bookmarkEntityDAO.findAllByUserId(userId).stream()
        .map(MapStructMapper.INSTANCE::toView)
        .collect(Collectors.toList());
  }

  public List<BookmarkView> getAllByNameAndUserId(String name, Long ownerId) {
    return bookmarkEntityDAO.findByNameAndUserId(name, ownerId).stream()
        .map(MapStructMapper.INSTANCE::toView)
        .collect(Collectors.toList());
  }

  public BookmarkFullView save(BookmarkDto bookmarkDto, Long userId) {
    BookmarkEntity bookmarkEntity = MapStructMapper.INSTANCE.toEntity(bookmarkDto);
    bookmarkEntity.setOwner(userService.getByIdEntity(userId));

    return MapStructMapper.INSTANCE.toFullView(bookmarkEntityDAO.save(bookmarkEntity));
  }

  public BookmarkFullView save(BookmarkEntity bookmarkEntity) {
    return MapStructMapper.INSTANCE.toFullView(bookmarkEntityDAO.save(bookmarkEntity));
  }

  public Optional<BookmarkEntity> getByIdAndUserId(Long id, Long ownerId) {
    return bookmarkEntityDAO.findByIdAndUserId(id, ownerId);
  }

  public Boolean deleteById(Long id) {
    return bookmarkEntityDAO.deleteById(id);
  }
}
