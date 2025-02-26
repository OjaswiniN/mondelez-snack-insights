/* (C) 2023 Mondelez Insights */
package com.mondelez.service;

import com.mondelez.dao.UserDao;
import com.mondelez.exception.Exceptions;
import com.mondelez.service.mappers.ModelMapper;
import com.mondelez.service.model.LocalUser;
import com.mondelez.service.model.User;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("localUserDetailService")
public class LocalUserDetailService implements UserDetailsService {

  @Autowired private UserDao userDao;

  @Override
  @Transactional
  public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
    User user =
        userDao
            .findBy(email)
            .map(ModelMapper::toUser)
            .orElseThrow(() -> new UsernameNotFoundException("Incorrect email or password"));

    return createLocalUser(user);
  }

  @Transactional
  public LocalUser loadUserById(UUID id) {
    User user =
        userDao
            .findBy(id)
            .map(ModelMapper::toUser)
            .orElseThrow(
                () ->
                    new Exceptions.BadRequestException(String.format("User id %s not Found", id)));
    return createLocalUser(user);
  }

  private LocalUser createLocalUser(User user) {
    return new LocalUser(
        user.getEmail(),
        user.getPassword(),
        true,
        true,
        true,
        true,
        buildSimpleGrantedAuthorities(user.getRoles()),
        user);
  }

  public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(List<String> roles) {
    return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  //  private Optional<User> registerNewUser(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
  //    UserMeta userMeta =
  //        UserMeta.builder()
  //            .username(oAuth2UserInfo.getName())
  //            .email(oAuth2UserInfo.getEmail())
  //            .password(passwordEncoder.encode("changeit"))
  //            .enabled(true)
  //            .provider(toSocialProvider(registrationId).getProviderType())
  //            .providerUserId(oAuth2UserInfo.getId())
  //            .imageUrl(oAuth2UserInfo.getImageUrl())
  //            .build();
  //    Optional<RoleRow> roleRow = roleDao.findBy(Roles.ROLE_USER.name());
  //    UserRow userRow = MetaMapper.toUserRow(userMeta);
  //    userDao.insert(userRow);
  //    roleDao.insert(userRow.getId(), roleRow.get().getId());
  //    return userDao.findBy(userRow.getId()).map(ModelMapper::toUser);
  //  }
}
