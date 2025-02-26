/* (C) 2023 Mondelez Insights */
package com.mondelez.service.mappers;

import com.mondelez.dao.model.*;
import com.mondelez.service.model.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class MetaMapper {

  private static Timestamp now() {
    return Timestamp.from(Instant.now());
  }

  // ROLE
  public static RoleRow toRoleRow(String role) {
    return new RoleRow(0, role);
  }

  // PERMISSION
  public static PermissionRow toPermissionRow(Integer roleId, List<String> permissions) {
    return new PermissionRow(0, roleId, permissions);
  }

  // USER
  public static UserRow toUserRow(UserMeta meta) {
    return new UserRow(
        UUID.randomUUID(),
        meta.getEmail(),
        meta.getUsername(),
        meta.getPassword(),
        meta.getLocation(),
        meta.getDob(),
        null);
  }
}
