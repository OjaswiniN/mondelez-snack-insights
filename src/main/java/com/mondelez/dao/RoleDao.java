/* (C) 2023 Mondelez Insights */
package com.mondelez.dao;

import com.mondelez.dao.mappers.RoleRowMapper;
import com.mondelez.dao.model.RoleRow;
import java.util.List;
import java.util.UUID;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterRowMapper(RoleRowMapper.class)
public interface RoleDao {

  @SqlUpdate("INSERT INTO roles (role) VALUES (:role)")
  @GetGeneratedKeys
  Integer insert(@BindBean RoleRow row);

  @SqlQuery("SELECT r.* FROM roles r WHERE r.role =:role")
  RoleRow findBy(String role);

  @SqlQuery("SELECT r.* FROM roles r")
  List<RoleRow> findAll();

  @SqlQuery(value = "SELECT EXISTS (SELECT 1 FROM roles r WHERE r.role =:role)")
  boolean exists(String role);

  @SqlUpdate(
      "INSERT INTO user_roles ("
          + "user_id, "
          + "role_id"
          + ") VALUES ("
          + ":userId, "
          + ":roleId)")
  void insert(UUID userId, Integer roleId);
}
