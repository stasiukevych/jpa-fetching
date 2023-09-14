package fetching.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "ROLES")
@NamedQuery(name = Role.FIND_ALL, query = "select r from Role r")
public class Role {

  public static final String FIND_ALL = "Role.findAll";

  @Id
  @GeneratedValue
  @Column(name = "role_id")
  private Long id;

  private String role;

  public Role() {

  }

  public Role(String role) {
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Role role1 = (Role) o;
    return Objects.equals(id, role1.id) && Objects.equals(role, role1.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, role);
  }

  @Override
  public String toString() {
    return "Role{" +
        "id=" + id +
        ", role='" + role + '\'' +
        '}';
  }
}