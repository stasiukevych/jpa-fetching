package fetching.web.endpoint;


import fetching.model.Role;
import fetching.model.User;
import fetching.web.service.UserService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("users")
public class UserController {

  @Inject
  private UserService userService;

  @GET
  @Path("all")
  public Response getAll() {
    return Response.ok(userService.findAll().toString()).build();
  }

  @GET
  @Path("byId/{id}")
  public Response byId(@PathParam("id") Long id) {
    return Response.ok(userService.findById(id).toString()).build();
  }

  @GET
  @Path("create/{userName}/{userPhone}")
  public Response create(@PathParam("userName") String userName,
      @PathParam("userPhone") String userPhone) {
    User user = new User();
    user.setUserName(userName);
    user.setPhone(userPhone);

    user.setRoles(List.of(new Role("dev"), new Role("admin"), new Role("tester")));

    userService.persist(user);
    return Response.ok("Was created: " + user).build();
  }

  @GET
  @Transactional
  @Path("checkLazyLoading/{id}")
  public Response checkLazyLoading(@PathParam("id") Long id) {
    userService.checkLazyLoadingByUserId(id);

    return Response.ok("Was checked lazy loading for: " + userService.findById(id).toString())
        .build();
  }
}