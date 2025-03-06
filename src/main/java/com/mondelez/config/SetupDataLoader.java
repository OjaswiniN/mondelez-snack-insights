/* (C) 2023 Mondelez Insights */
package com.mondelez.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mondelez.Helper;
import com.mondelez.api.dto.UserSnacksLogRequest;
import com.mondelez.api.mappers.RequestMapper;
import com.mondelez.common.Roles;
import com.mondelez.dao.PermissionDao;
import com.mondelez.dao.RoleDao;
import com.mondelez.dao.UserDao;
import com.mondelez.dao.model.RoleRow;
import com.mondelez.dao.model.UserRow;
import com.mondelez.service.UserSnacksLogService;
import com.mondelez.service.mappers.MetaMapper;
import com.mondelez.service.model.UserMeta;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

@Component
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private boolean alreadySetup = false;
  @Autowired private RoleDao roleDao;
  @Autowired private PermissionDao permissionDao;
  @Autowired private UserDao userDao;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private UserSnacksLogService userSnacksLogService;

  @Override
  @Transactional
  public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
    if (alreadySetup) return;
    createRolesIfNotFound(Roles.ROLE_GUEST.name(), Collections.singletonList("canRead"));
    createRolesIfNotFound(
        Roles.ROLE_USER.name(), Arrays.asList("canAdd", "canDelete", "canEdit", "canRead"));
    createRolesIfNotFound(
        Roles.ROLE_ADMIN.name(), Arrays.asList("canAdd", "canDelete", "canEdit", "canRead"));
    createAdmin();
    createGuest();
    createDummyUsers();
    alreadySetup = true;
  }

  private void createAdmin() {
    UserMeta userMeta =
        new UserMeta(
            "Admin", "admin@mondelez.com", passwordEncoder.encode("1234"), "default", "01-01-0001");
    if (userDao.exists(userMeta.getEmail())) return;
    UserRow userRow = MetaMapper.toUserRow(userMeta);
    RoleRow roleRow = roleDao.findBy(Roles.ROLE_ADMIN.name());
    userDao.insert(userRow);
    roleDao.insert(userRow.getId(), roleRow.getId());
  }

  private void createGuest() {
    UserMeta userMeta =
        new UserMeta(
            "guest",
            "guest@mondelez.com",
            passwordEncoder.encode("Guest@123"),
            "default",
            "01-01-0001");
    if (userDao.exists(userMeta.getEmail())) return;
    UserRow userRow = MetaMapper.toUserRow(userMeta);
    RoleRow roleRow = roleDao.findBy(Roles.ROLE_GUEST.name());
    userDao.insert(userRow);
    roleDao.insert(userRow.getId(), roleRow.getId());
  }

  private void createDummyUsers() {
    List<UserMeta> userMetas = Collections.emptyList();
    try {
      URL fileUrl = ResourceUtils.getURL("classpath:data/mock_users.json");
      userMetas = Helper.fromJsonFile(fileUrl, new TypeReference<List<UserMeta>>() {});
    } catch (IOException e) {
      log.error("Unable to load mock users: ", e);
    }
    log.info("Generating mock users with snacks data");
    userMetas.forEach(
        userMeta -> {
          log.info("user:{}", userMeta);
          if (userDao.exists(userMeta.getEmail())) return;
          userMeta.setPassword(passwordEncoder.encode(userMeta.getPassword()));
          UserRow userRow = MetaMapper.toUserRow(userMeta);
          RoleRow roleRow = roleDao.findBy(Roles.ROLE_USER.name());
          userDao.insert(userRow);
          roleDao.insert(userRow.getId(), roleRow.getId());
          List<UserSnacksLogRequest> requests = Helper.generateRandomSnackRecordRequests(100);
          requests.forEach(
              request ->
                  userSnacksLogService.addLog(
                      RequestMapper.toUserSnacksLog(userRow.getId(), request)));
        });
  }

  private void createRolesIfNotFound(final String role, List<String> permission) {
    if (!roleDao.exists(role)) {
      Integer roleId = roleDao.insert(MetaMapper.toRoleRow(role));
      permissionDao.insert(MetaMapper.toPermissionRow(roleId, permission));
    }
  }
}
