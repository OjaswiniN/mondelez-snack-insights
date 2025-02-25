/* (C) 2023 Mondelez Insights */
package com.mondelez.dao;

import com.mondelez.dao.mappers.Columns;
import com.mondelez.dao.model.Row;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

@RegisterRowMapper(UserSnacksLogDao.Mapper.class)
public interface UserSnacksLogDao {

  @SqlQuery("INSERT INTO user_snacks_log (json) VALUES (:json :: jsonb) RETURNING id")
  Integer insert(@Bind("json") String json);

  @SqlQuery("SELECT id, json FROM user_snacks_log WHERE id =:id")
  Row findBy(@Bind("id") Integer id);

  @SqlQuery("SELECT id, json FROM user_snacks_log WHERE user_id =:userId ")
  List<Row> findAllBy(@Bind("userId") UUID userId);

  @SqlQuery("SELECT id, r.json FROM user_snacks_log r")
  List<Row> findAll();

  class Mapper implements RowMapper<Row> {
    @Override
    public Row map(@NonNull ResultSet results, @NonNull StatementContext context)
        throws SQLException {
      return new Row(results.getInt("id"), Columns.stringOrThrow(results, "json"));
    }
  }
}
