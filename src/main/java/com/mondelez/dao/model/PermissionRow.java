/* (C) 2023 Mondelez Insights */
package com.mondelez.dao.model;

import java.util.List;
import lombok.NonNull;
import lombok.Value;

@Value
public class PermissionRow {
  @NonNull Integer id;
  @NonNull Integer roleId;
  @NonNull List<String> permissions;
}
