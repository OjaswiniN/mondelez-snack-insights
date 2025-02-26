/* (C) 2023 Mondelez Insights */
package com.mondelez.service.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@ToString
public class LocalUser extends User {

  private Map<String, Object> attributes;
  private final com.mondelez.service.model.User user;

  public LocalUser(
      final String userID,
      final String password,
      final boolean enabled,
      final boolean accountNonExpired,
      final boolean credentialsNonExpired,
      final boolean accountNonLocked,
      final Collection<? extends GrantedAuthority> authorities,
      final com.mondelez.service.model.User user) {
    super(
        userID,
        password,
        enabled,
        accountNonExpired,
        credentialsNonExpired,
        accountNonLocked,
        authorities);
    this.user = user;
  }

  public static LocalUser create(
      com.mondelez.service.model.User user, Map<String, Object> attributes) {
    LocalUser localUser =
        new LocalUser(
            user.getEmail(),
            user.getPassword(),
            true,
            true,
            true,
            true,
            buildSimpleGrantedAuthorities(user.getRoles()),
            user);
    localUser.setAttributes(attributes);
    return localUser;
  }

  public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(
      final List<String> roles) {
    return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public com.mondelez.service.model.User getUser() {
    return user;
  }
}
