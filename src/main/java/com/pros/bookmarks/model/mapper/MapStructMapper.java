package com.pros.bookmarks.model.mapper;

import com.pros.bookmarks.model.dto.BookmarkDto;import com.pros.bookmarks.model.dto.UserDto;import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.pros.bookmarks.model.entity.BookmarkEntity;
import com.pros.bookmarks.model.entity.UserEntity;
import com.pros.bookmarks.model.view.BookmarkFullView;
import com.pros.bookmarks.model.view.BookmarkView;
import com.pros.bookmarks.model.view.UserView;

@Mapper
public interface MapStructMapper {
  MapStructMapper INSTANCE = Mappers.getMapper(MapStructMapper.class);

  BookmarkEntity toEntity(BookmarkDto bookmarkDto);

  UserEntity toEntity(UserDto userDto);

  default UserEntity toEntity(UserEntity userEntity, UserDto userDto) {
    userEntity.setUsername(userDto.getUsername());
    userEntity.setFullName(userDto.getFullName());
    userEntity.setPassword(userEntity.getPassword());

    return userEntity;
  }

  BookmarkView toView(BookmarkEntity bookmarkEntity);

  BookmarkFullView toFullView(BookmarkEntity bookmarkEntity);

  UserView toView(UserEntity userEntity);
}
