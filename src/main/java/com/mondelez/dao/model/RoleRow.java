/* (C) 2023 Mondelez Insights */
package com.mondelez.dao.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class RoleRow {
  @NonNull Integer id;
  @NonNull String role;
}
