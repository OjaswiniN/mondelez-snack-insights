/* (C) 2023 Mondelez Insights */
package com.mondelez.api.dto;

import java.sql.Timestamp;
import java.util.List;
import lombok.Value;

@Value
public class UserResponse {
  String id, name, email, location;
  String dob;
  List<String> roles;
  List<String> permissions;
  Timestamp joinDate;
}
