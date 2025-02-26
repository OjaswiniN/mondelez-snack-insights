/* (C) 2023 Mondelez Insights */
package com.mondelez.service.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserMeta {
  String username;
  String email;
  String password;
  String location;
  String dob;
}
