package com.pros.bookmarks.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import java.time.LocalDateTime;

@NamedQueries({
  @NamedQuery(name = "BookmarkEntity.findAll", query = "SELECT b FROM bookmarks b"),
  @NamedQuery(
      name = "BookmarkEntity.findAllByUserId",
      query = "SELECT b FROM bookmarks b WHERE b.owner.id = :id"),
  @NamedQuery(
      name = "BookmarkEntity.deleteById",
      query = "DELETE FROM bookmarks b WHERE b.id = :id"),
  @NamedQuery(
      name = "BookmarkEntity.findByNameAndUserId",
      query = "SELECT b FROM bookmarks b WHERE b.name LIKE :infix AND b.owner.id = :ownerId"),
  @NamedQuery(
      name = "BookmarkEntity.findByIdAndUserId",
      query = "SELECT b FROM bookmarks b WHERE b.id LIKE :id AND b.owner.id = :ownerId"),
})
@Entity(name = "bookmarks")
public class BookmarkEntity extends BaseEntity {
  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String url;

  private String description;

  @CreationTimestamp
  @Column(name = "create_time")
  private LocalDateTime createTime = LocalDateTime.now();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", referencedColumnName = "id")
  @JsonBackReference
  private UserEntity owner;

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public UserEntity getOwner() {
    return owner;
  }

  public void setOwner(UserEntity owner) {
    this.owner = owner;
  }
}
