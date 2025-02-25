/* (C) 2023 Mondelez Insights */
package com.mondelez.dao;

import com.mondelez.dao.mappers.PermissionRowMapper;
import com.mondelez.dao.model.PermissionRow;
import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterRowMapper(PermissionRowMapper.class)
public interface PermissionDao {

  @SqlUpdate("INSERT INTO permissions (role_id, permission) VALUES (:roleId, :permissions)")
  @GetGeneratedKeys
  Integer insert(@BindBean PermissionRow row);

  @SqlQuery("SELECT r.* FROM permissions r WHERE r.role_id =:roleId")
  PermissionRow findBy(Integer roleId);

  @SqlQuery("SELECT r.* FROM permissions r")
  List<PermissionRow> findAll();

  @SqlQuery(value = "SELECT EXISTS (SELECT 1 FROM permissions r WHERE r.role_id =:roleId)")
  boolean exists(String roleId);
}
