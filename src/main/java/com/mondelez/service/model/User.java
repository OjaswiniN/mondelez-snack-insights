/* (C) 2023 Mondelez Insights */
package com.mondelez.service.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class User {
  private UUID id;
  private String email;
  @Setter private String username;
  private String password;
  private String location;
  private String dob;
  private List<String> roles;
  private List<String> permissions;
  protected Timestamp createdDate;
}
