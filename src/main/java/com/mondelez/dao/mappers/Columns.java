/* (C) 2023 Mondelez Insights */
package com.mondelez.dao.mappers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface Columns {

  //    COMMON COLUMNS
  String ID = "id";
  String USER_ID = "user_id";
  String NAME = "name";

  //  USER
  String USERNAME = "username";
  String EMAIL = "email";
  String PASSWORD = "password";
  String LOCATION = "location";
  String DOB = "dob";
  String CREATED_AT = "created_at";
  String ROLES = "roles";
  String PERMISSIONS = "permissions";
  String ROLE = "role";
  String ROLE_ID = "role_id";

  static UUID uuidOrThrow(final ResultSet results, final String column) throws SQLException {
    if (results.getObject(column) == null) {
      throw new IllegalArgumentException();
    }
    return results.getObject(column, UUID.class);
  }

  static Timestamp timestampOrThrow(final ResultSet results, final String column)
      throws SQLException {
    if (results.getObject(column) == null) {
      throw new IllegalArgumentException();
    }
    return results.getTimestamp(column);
  }

  static Date dateOrThrow(final ResultSet results, final String column) throws SQLException {
    if (results.getObject(column) == null) {
      throw new IllegalArgumentException();
    }
    return results.getDate(column);
  }

  static String stringOrNull(final ResultSet results, final String column) throws SQLException {
    if (results.getObject(column) == null) {
      return null;
    }
    return results.getString(column);
  }

  static String stringOrThrow(final ResultSet results, final String column) throws SQLException {
    if (results.getObject(column) == null) {
      throw new IllegalArgumentException();
    }
    return results.getString(column);
  }

  static boolean booleanOrThrow(final ResultSet results, final String column) throws SQLException {
    if (results.getObject(column) == null) {
      throw new IllegalArgumentException();
    }
    return results.getBoolean(column);
  }

  static List<String> stringArrayOrThrow(final ResultSet results, final String column)
      throws SQLException {
    if (results.getObject(column) == null) {
      throw new IllegalArgumentException();
    }
    return Arrays.asList((String[]) results.getArray(column).getArray());
  }

  static List<Integer> integerArrayOrEmpty(final ResultSet results, final String column)
      throws SQLException {
    List<Integer> result = new ArrayList<>();
    if (results.getObject(column) == null) {
      return result;
    }
    return Arrays.asList((Integer[]) results.getArray(column).getArray());
  }

  static Integer integerOrThrow(final ResultSet results, final String column) throws SQLException {
    if (results.getObject(column) == null) {
      throw new IllegalArgumentException();
    }
    return results.getInt(column);
  }
}
