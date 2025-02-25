/* (C) 2023 Mondelez Insights */
package com.mondelez.dao.mappers;

import static com.mondelez.dao.mappers.Columns.*;

import com.mondelez.dao.model.PermissionRow;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.NonNull;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class PermissionRowMapper implements RowMapper<PermissionRow> {
  @Override
  public PermissionRow map(@NonNull ResultSet results, @NonNull StatementContext context)
      throws SQLException {
    return new PermissionRow(
        integerOrThrow(results, ID),
        integerOrThrow(results, ROLE_ID),
        stringArrayOrThrow(results, PERMISSIONS));
  }
}
