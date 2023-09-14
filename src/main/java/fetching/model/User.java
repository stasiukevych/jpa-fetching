package fetching.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS")
@NamedQuery(name = User.FIND_ALL, query = "select u from User u")
public class User {

  public static final String FIND_ALL = "User.findAll";

  @Id
  @GeneratedValue
  @Column(name = "user_id")
  private Long id;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "user_phone")
  private String phone;

  @OneToMany(
      fetch = FetchType.LAZY,
      orphanRemoval = true,
      cascade = CascadeType.ALL
  )
  @JoinTable(
      name = "user_on_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles;

  public Long getId() {
    return id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(userName, user.userName)
        && Objects.equals(phone, user.phone) && Objects.equals(roles, user.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userName, phone, roles);
  }

  /*
    incorrect toString in case of lazy initializing,
    because if we will use roles in toString hibernate
    select this during creation of entity (it can find/named query etc.).
   */
//  @Override
//  public String toString() {
//    return "User{" +
//        "id=" + id +
//        ", userName='" + userName + '\'' +
//        ", phone='" + phone + '\'' +
//        ", roles=" + roles +
//        '}';
//  }


  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", userName='" + userName + '\'' +
        ", phone='" + phone + '\'' +
        '}';
  }
}