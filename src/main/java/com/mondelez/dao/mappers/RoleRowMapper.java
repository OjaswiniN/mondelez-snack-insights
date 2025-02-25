/* (C) 2023 Mondelez Insights */
package com.mondelez.dao.mappers;

import static com.mondelez.dao.mappers.Columns.*;

import com.mondelez.dao.model.RoleRow;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.NonNull;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class RoleRowMapper implements RowMapper<RoleRow> {

  @Override
  public RoleRow map(@NonNull ResultSet results, @NonNull StatementContext context)
      throws SQLException {
    return new RoleRow(integerOrThrow(results, ID), stringOrThrow(results, ROLE));
  }
}
