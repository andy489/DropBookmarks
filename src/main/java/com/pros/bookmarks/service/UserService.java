package com.pros.bookmarks.service;

import com.pros.bookmarks.dao.UserEntityDAO;import com.pros.bookmarks.model.dto.UserDto;import com.pros.bookmarks.model.entity.UserEntity;import com.pros.bookmarks.model.mapper.MapStructMapper;import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;
import com.pros.bookmarks.model.view.UserView;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class UserService {

  private final UserEntityDAO userEntityDAO;
  private final PasswordEncryptor encryptor = new BasicPasswordEncryptor();

  public UserService(UserEntityDAO userEntityDAO) {
    this.userEntityDAO = userEntityDAO;
  }

  public List<UserView> getAll() {
    return userEntityDAO.findAll().stream()
        .map(MapStructMapper.INSTANCE::toView)
        .collect(Collectors.toList());
  }

  public UserView getById(Long userId) {
    return MapStructMapper.INSTANCE.toView(
        userEntityDAO.findById(userId).orElseThrow(NoSuchElementException::new));
  }

  public UserView getByUsername(String username) {
    return MapStructMapper.INSTANCE.toView(
        userEntityDAO.findByUsername(username).orElseThrow(NoSuchElementException::new));
  }

  public List<UserView> getByEmailSuffix(String emailSuffix) {
    return userEntityDAO.findByEmailSuffix(emailSuffix).stream()
        .map(MapStructMapper.INSTANCE::toView)
        .collect(Collectors.toList());
  }

  public UserView save(UserDto userDto) {
    userDto.setPassword(encryptor.encryptPassword(userDto.getPassword()));
    UserEntity userEntity = MapStructMapper.INSTANCE.toEntity(userDto);
    return MapStructMapper.INSTANCE.toView(userEntityDAO.save(userEntity));
  }

  public Boolean deleteById(Long id) {
    return userEntityDAO.deleteById(id);
  }

  public UserView updateById(Long userId, UserDto userDto) {
    UserEntity foundUser = userEntityDAO.findById(userId).orElseThrow();

    if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
      throw new IllegalArgumentException();
    }

    foundUser.setPassword(encryptor.encryptPassword(userDto.getPassword()));
    MapStructMapper.INSTANCE.toEntity(foundUser, userDto);

    return MapStructMapper.INSTANCE.toView(userEntityDAO.updateUser(foundUser));
  }

  public UserEntity getByIdEntity(Long userId) {
    return userEntityDAO.findById(userId).orElseThrow(NoSuchElementException::new);
  }
}
