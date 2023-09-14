package fetching.web.config;

import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class EntityManagerProducer {

  @PersistenceContext(unitName = "mysql-datasource")
  private EntityManager entityManager;

  @Produces
  private EntityManager produceEntityManager() {
    return entityManager;
  }
}