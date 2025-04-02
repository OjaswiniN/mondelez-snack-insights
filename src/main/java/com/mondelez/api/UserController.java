/* (C) 2023 Mondelez Insights */
package com.mondelez.api;

import com.mondelez.annotation.CurrentUser;
import com.mondelez.api.mappers.ResponseMapper;
import com.mondelez.config.JwtTokenUtil;
import com.mondelez.dao.UserDao;
import com.mondelez.dao.model.ExtendedUserRow;
import com.mondelez.service.model.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@Validated
public class UserController
{

  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private UserDao userDao;

  @GetMapping("me")
  @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
    log.info("user => " + user);
    return ResponseEntity.ok(ResponseMapper.toUserResponse(user));
  }

  @GetMapping
  //  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> all(
      @CurrentUser LocalUser user, @RequestParam(name = "page", defaultValue = "0") Integer page) {
    List<ExtendedUserRow> rows = userDao.findAll("username", page, 20);
    return ResponseEntity.ok(rows);
  }

  //  @GetMapping("/users")
  //  @PreAuthorize("hasRole('ROLE_ADMIN')")
  //  public ResponseEntity<?> allUsers(
  //      @RequestParam(defaultValue = "created_at desc", name = "sort") String sort,
  //      @RequestParam(defaultValue = "0", name = "index") int index) {
  //    List<ManageUser> users = userService.findAll(sort, index, null);
  //    return ResponseEntity.ok(ResponseMapper. toManageUsersResponse(users));
  //  }

}
