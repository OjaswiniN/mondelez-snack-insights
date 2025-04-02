/* (C) 2023 Mondelez Insights */
package com.mondelez.api.mappers;

import com.mondelez.api.dto.UserResponse;
import com.mondelez.service.model.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;

public class ResponseMapper {

  // USER
  public static UserResponse toUserResponse(LocalUser localUser) {
    List<String> roles =
        localUser.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    User user = localUser.getUser();
    return new UserResponse(
        user.getId().toString(),
        user.getUsername(),
        user.getEmail(),
        user.getLocation(),
        user.getDob(),
        roles,
        user.getPermissions(),
        user.getCreatedDate());
  }
}
