/* (C) 2023 Mondelez Insights */
package com.mondelez.service.mappers;

import com.mondelez.dao.model.*;
import com.mondelez.dao.model.ExtendedUserRow;
import com.mondelez.service.model.*;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {

  // Role
  public static Role toRole(RoleRow roleRow) {
    return new Role(roleRow.getRole());
  }

  public static List<Role> toRoles(List<RoleRow> roleRows) {
    return roleRows.stream().map(ModelMapper::toRole).collect(Collectors.toList());
  }

  public static User toUser(ExtendedUserRow userRow) {
    return new User(
        userRow.getId(),
        userRow.getEmail(),
        userRow.getUsername(),
        userRow.getPassword(),
        userRow.getLocation(),
        userRow.getDob(),
        userRow.getRoles(),
        userRow.getPermissions(),
        userRow.getCreatedAt());
  }
}
