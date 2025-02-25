/* (C) 2023 Mondelez Insights */
package com.mondelez.dao;

import com.mondelez.dao.mappers.UserRowMapper;
import com.mondelez.dao.model.ExtendedUserRow;
import com.mondelez.dao.model.UserRow;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterRowMapper(UserRowMapper.class)
public interface UserDao {
  @SqlUpdate(
      "INSERT INTO users ("
          + "id, "
          + "email, "
          + "username, "
          + "password, "
          + "location, "
          + "dob "
          + ") VALUES ("
          + ":id, "
          + ":email, "
          + ":username, "
          + ":password, "
          + ":location, "
          + ":dob::date)")
  void insert(@BindBean UserRow userRow);

  @SqlUpdate("UPDATE users SET username =:username, image_url =:imageUrl WHERE id =:id")
  void update(
      @Bind("username") String username, @Bind("imageUrl") String imageUrl, @Bind("id") UUID id);

  @SqlQuery(
      "SELECT u.*, "
          + "ARRAY(Select r.role FROM roles r WHERE r.id=ur.role_id) as roles, "
          + "p.permission as permissions "
          + "FROM users u "
          + "INNER JOIN user_roles ur ON ur.user_id=u.id "
          + "INNER JOIN permissions p ON p.role_id=ur.role_id "
          + "WHERE u.email =:email")
  Optional<ExtendedUserRow> findBy(@Bind("email") String email);

  @SqlQuery(
      "SELECT u.*, "
          + "ARRAY(Select r.role FROM roles r WHERE r.id=ur.role_id) as roles, "
          + "p.permission as permissions "
          + "FROM users u "
          + "INNER JOIN user_roles ur ON ur.user_id=u.id "
          + "INNER JOIN permissions p ON p.role_id=ur.role_id "
          + "WHERE u.id =:id")
  Optional<ExtendedUserRow> findBy(@Bind("id") UUID id);

  @SqlQuery(
      "SELECT u.*, "
          + "ARRAY(Select r.role FROM roles r WHERE r.id=ur.role_id) as roles, "
          + "p.permission as permissions "
          + "FROM users u "
          + "INNER JOIN user_roles ur ON ur.user_id=u.id "
          + "INNER JOIN permissions p ON p.role_id=ur.role_id "
          + "ORDER BY <sort> OFFSET (:limit * :offset) LIMIT :limit")
  List<ExtendedUserRow> findAll(
      @Define("sort") String sort, @Bind("offset") int offset, @Bind("limit") Integer limit);

  @SqlQuery("SELECT EXISTS (SELECT 1 FROM users u WHERE u.email = :email)")
  boolean exists(@Bind("email") String email);

  @SqlQuery("SELECT EXISTS (SELECT 1 FROM users u WHERE u.id =:userId)")
  boolean exists(@Bind("userId") UUID userId);
}
