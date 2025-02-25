/* (C) 2023 Mondelez Insights */
package com.mondelez.dao.mappers;

import static com.mondelez.dao.mappers.Columns.*;

import com.mondelez.dao.model.ExtendedUserRow;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.NonNull;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class UserRowMapper implements RowMapper<ExtendedUserRow> {
  @Override
  public ExtendedUserRow map(@NonNull ResultSet results, @NonNull StatementContext context)
      throws SQLException {
    return new ExtendedUserRow(
        uuidOrThrow(results, ID),
        stringOrThrow(results, EMAIL),
        stringOrThrow(results, USERNAME),
        stringOrThrow(results, PASSWORD),
        stringOrThrow(results, LOCATION),
        stringOrThrow(results, DOB),
        timestampOrThrow(results, CREATED_AT),
        stringArrayOrThrow(results, ROLES),
        stringArrayOrThrow(results, PERMISSIONS));
  }
}
