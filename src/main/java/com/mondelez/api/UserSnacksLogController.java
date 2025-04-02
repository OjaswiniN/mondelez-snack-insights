/* (C) 2023 Mondelez Insights */
package com.mondelez.api;

import com.mondelez.annotation.CurrentUser;
import com.mondelez.api.dto.UserSnacksLogRequest;
import com.mondelez.api.mappers.RequestMapper;
import com.mondelez.service.UserSnacksLogService;
import com.mondelez.service.model.LocalUser;
import com.mondelez.service.model.UserSnacksLog;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/snacks")
public class UserSnacksLogController
{
  @Autowired UserSnacksLogService userSnacksLogService;

  @PostMapping
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<UserSnacksLog> addLog(
      @CurrentUser LocalUser user, @RequestBody @Validated UserSnacksLogRequest request) {
    UserSnacksLog userSnacksLog = RequestMapper.toUserSnacksLog(user.getUser().getId(), request);
    userSnacksLog = userSnacksLogService.addLog(userSnacksLog);
    return ResponseEntity.ok(userSnacksLog);
  }

  @GetMapping
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<UserSnacksLog>> listLogsBy(@CurrentUser LocalUser user) {
    List<UserSnacksLog> snacksLogs = userSnacksLogService.listBy(user.getUser().getId());
    return ResponseEntity.ok(snacksLogs);
  }

  @GetMapping("/all")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<UserSnacksLog>> listLogs() {
    List<UserSnacksLog> snacksLogs = userSnacksLogService.list();
    return ResponseEntity.ok(snacksLogs);
  }
}
