/* (C) 2023 Mondelez Insights */
package com.mondelez.dao.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
public class ExtendedUserRow extends UserRow {
  List<String> roles;
  List<String> permissions;

  public ExtendedUserRow(
      UUID id,
      String email,
      String username,
      String password,
      String location,
      String dob,
      Timestamp createdAt,
      List<String> roles,
      List<String> permissions) {
    super(id, email, username, password, location, dob, createdAt);
    this.roles = roles;
    this.permissions = permissions;
  }
}
