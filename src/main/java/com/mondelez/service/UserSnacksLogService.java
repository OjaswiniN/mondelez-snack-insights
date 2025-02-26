/* (C) 2023 Mondelez Insights */
package com.mondelez.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mondelez.Helper;
import com.mondelez.dao.UserSnacksLogDao;
import com.mondelez.dao.model.Row;
import com.mondelez.service.model.UserSnacksLog;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userSnacksLogService")
public class UserSnacksLogService {
  @Autowired UserSnacksLogDao userSnacksLogDao;

  public UserSnacksLog addLog(UserSnacksLog userSnacksLog) {
    Integer id = userSnacksLogDao.insert(Helper.toJsonString(userSnacksLog));
    Row row = userSnacksLogDao.findBy(id);
    UserSnacksLog log = Helper.fromJson(row.getJson(), new TypeReference<UserSnacksLog>() {});
    log.setId(row.getId());
    return log;
  }

  public List<UserSnacksLog> listBy(UUID userId) {
    List<Row> rows = userSnacksLogDao.findAllBy(userId);
    return rows.stream()
        .map(
            row -> {
              UserSnacksLog log =
                  Helper.fromJson(row.getJson(), new TypeReference<UserSnacksLog>() {});
              log.setId(row.getId());
              return log;
            })
        .collect(Collectors.toList());
  }

  public List<UserSnacksLog> list() {
    List<Row> rows = userSnacksLogDao.findAll();
    return rows.stream()
        .map(
            row -> {
              UserSnacksLog log =
                  Helper.fromJson(row.getJson(), new TypeReference<UserSnacksLog>() {});
              log.setId(row.getId());
              return log;
            })
        .collect(Collectors.toList());
  }
}
