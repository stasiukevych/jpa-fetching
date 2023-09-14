package fetching.web.service;

import fetching.model.User;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public class UserService {

  @Inject
  private EntityManager entityManager;

  public List<User> findAll() {
    return entityManager.createNamedQuery(User.FIND_ALL, User.class).getResultList();
  }

  public User findById(Long id) {
    return entityManager.find(User.class, id);
  }

  public boolean persist(User user) {
    entityManager.persist(user);
    return true;
  }

  public void checkLazyLoadingByUserId(Long id) {
    User user = entityManager.find(User.class, id);

    System.out.println("Before retrieving roles for user: " + user.toString());

    user.getRoles().forEach(System.out::println);
  }
}