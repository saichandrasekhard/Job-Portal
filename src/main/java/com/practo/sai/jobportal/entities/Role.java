package com.practo.sai.jobportal.entities;
// Generated Oct 17, 2016 1:36:19 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Role generated by hbm2java
 */
@Entity
@Table(name = "role", catalog = "job_portal",
    uniqueConstraints = @UniqueConstraint(columnNames = "role_name"))
public class Role implements java.io.Serializable {

  private Integer RId;
  private String roleName;
  // private Set<UserRole> userRoles = new HashSet<UserRole>(0);

  public Role() {}

  public Role(String roleName) {
    this.roleName = roleName;
  }

  // public Role(String roleName, Set<UserRole> userRoles) {
  // this.roleName = roleName;
  // this.userRoles = userRoles;
  // }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)

  @Column(name = "r_id", unique = true, nullable = false)
  public Integer getRId() {
    return this.RId;
  }

  public void setRId(Integer RId) {
    this.RId = RId;
  }

  @Column(name = "role_name", unique = true, nullable = false)
  public String getRoleName() {
    return this.roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  // @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
  // public Set<UserRole> getUserRoles() {
  // return this.userRoles;
  // }
  //
  // public void setUserRoles(Set<UserRole> userRoles) {
  // this.userRoles = userRoles;
  // }

}
