package com.pros.bookmarks.resources;

import com.pros.bookmarks.model.dto.UserDto;import com.pros.bookmarks.service.UserService;import io.dropwizard.hibernate.UnitOfWork;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

  private final UserService userService;

  public UserResource(UserService userService) {
    this.userService = userService;
  }

  @GET
  @Path("/")
  @UnitOfWork
  public Response getAll() {
    return Response.ok().entity(userService.getAll()).build();
  }

  @GET
  @Path("/{id}")
  @UnitOfWork
  public Response getById(@PathParam("id") Long userId) {
    return Response.ok().entity(userService.getById(userId)).build();
  }

  @GET
  @Path("/username/{username}")
  @UnitOfWork
  public Response getByUsername(@PathParam("username") String username) {
    return Response.ok().entity(userService.getByUsername(username)).build();
  }

  @GET
  @Path("/email")
  @UnitOfWork
  public Response getByEmailSuffix(@QueryParam("suffix") String emailSuffix) {
    return Response.ok().entity(userService.getByEmailSuffix(emailSuffix)).build();
  }

  @POST
  @Path("/")
  @UnitOfWork
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postUser(@Valid @NotNull UserDto userDto) {
    return Response.created(UriBuilder.fromResource(UserResource.class).build())
        .entity(userService.save(userDto))
        .build();
  }

  @DELETE
  @Path("/{id}")
  @UnitOfWork
  public Response deleteUser(@PathParam("id") Long userId) {
    if (userService.deleteById(userId)) {
      return Response.ok()
          .entity(String.format("User with id=%d was deleted successfully", userId))
          .build();
    }

    return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete user").build();
  }

  @PUT
  @Path("/{id}")
  @UnitOfWork
  @Consumes({MediaType.APPLICATION_JSON})
  public Response updateUser(@PathParam("id") Long userId, @Valid @NotNull UserDto userDto) {
    return Response.ok().entity(userService.updateById(userId, userDto)).build();
  }
}
