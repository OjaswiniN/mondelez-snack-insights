/* (C) 2023 Mondelez Insights */
package com.mondelez.dao.model;

import java.sql.Timestamp;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class UserRow {
  UUID id;
  String email;
  String username;
  String password;
  String location;
  String dob;
  Timestamp createdAt;
}
