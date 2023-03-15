package com.pros.bookmarks.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.pros.bookmarks.model.dto.BookmarkDto;import com.pros.bookmarks.model.entity.BookmarkEntity;import com.pros.bookmarks.model.entity.UserEntity;import com.pros.bookmarks.model.view.BookmarkFullView;import com.pros.bookmarks.service.BookmarkService;import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

@Path("/bookmark")
@Produces(MediaType.APPLICATION_JSON)
public class BookmarkResource {

  public static final String WRONG_BODY_DATA_FORMAT = "Wrong body data format";

  private static final Logger LOGGER = LoggerFactory.getLogger(BookmarkResource.class);

  private final BookmarkService bookmarkService;

  public BookmarkResource(BookmarkService bookmarkService) {

    this.bookmarkService = bookmarkService;
  }
  @GET
  @Path("/")
  @UnitOfWork
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAll() {
    return Response.ok().entity(bookmarkService.getAll()).build();
  }

  @GET
  @Path("/my")
  @UnitOfWork
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllByUser(@Auth UserEntity userEntity) {
    return Response.ok().entity(bookmarkService.getAllByUserId(userEntity.getId())).build();
  }

  @GET
  @Path("/search")
  @UnitOfWork
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllByNameAndUserId(
      @QueryParam("infix") String infix, @Auth UserEntity userEntity) {
    return Response.ok()
        .entity(bookmarkService.getAllByNameAndUserId(infix, userEntity.getId()))
        .build();
  }

  @POST
  @Path("/")
  @UnitOfWork
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addBookmark(
      @Valid @NotNull BookmarkDto bookmarkDto, @Auth UserEntity userEntity) {
    BookmarkFullView bookmarkToSave = bookmarkService.save(bookmarkDto, userEntity.getId());

    return Response.created(UriBuilder.fromResource(BookmarkResource.class).build())
        .entity(bookmarkToSave)
        .build();
  }

  @DELETE
  @Path("/{id}")
  @UnitOfWork
  @Produces(MediaType.TEXT_PLAIN)
  public Response deleteBookmark(@PathParam("id") Long id, @Auth UserEntity userEntity) {
    if (findBookmark(id, userEntity).isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Failed to delete bookmark")
          .build();
    }

    if (bookmarkService.deleteById(id)) {
      return Response.ok()
          .entity(String.format("Bookmark with id=%d was deleted successfully", id))
          .build();
    }

    return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete bookmark").build();
  }

  /**
   * A method to modify an existing bookmark data.
   *
   * @param id the id of the bookmark to be modified.
   * @param jsonData Modifications in JSON format.
   * @param userEntity Authenticated user with whose bookmarks we work.
   * @return Bookmark with modified fields or throws an exception if bookmark was not found.
   */
  @PUT
  @Path("/{id}")
  @UnitOfWork
  public Response updateBookmark(
      @PathParam("id") Long id, String jsonData, @Auth UserEntity userEntity) {

    Optional<BookmarkEntity> bookmarkEntity = findBookmark(id, userEntity);

    if (bookmarkEntity.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Failed to delete bookmark")
          .build();
    }

    BookmarkEntity bookmark = bookmarkEntity.get();

    // Update bookmark data
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> changeMap = null;
    try {
      changeMap = objectMapper.readValue(jsonData, new TypeReference<Map<String, String>>() {});
      purgeMap(changeMap);
      BeanUtils.populate(bookmark, changeMap);
      return Response.ok().entity(bookmarkService.save(bookmark)).build();
    } catch (IOException | IllegalAccessException | InvocationTargetException ex) {
      LOGGER.warn(WRONG_BODY_DATA_FORMAT, ex);
      throw new WebApplicationException(WRONG_BODY_DATA_FORMAT, ex, Response.Status.BAD_REQUEST);
    } finally {
      if (changeMap != null) {
        changeMap.clear();
      }
    }
  }

  /**
   * A method to remove null and empty values from the change map. Necessary if not fields in the
   * changed object are filled.
   *
   * @param changeMap map of object field values.
   */
  protected void purgeMap(final Map<String, String> changeMap) {
    changeMap.remove("id");
    changeMap.entrySet().removeIf(entry -> Strings.isNullOrEmpty(entry.getValue()));
  }

  private Optional<BookmarkEntity> findBookmark(Long id, @Auth UserEntity user) {
    return bookmarkService.getByIdAndUserId(id, user.getId());
  }
}
