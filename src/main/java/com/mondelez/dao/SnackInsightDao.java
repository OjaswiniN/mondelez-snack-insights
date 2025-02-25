/* (C) 2023 Mondelez Insights */
package com.mondelez.dao;

import com.fasterxml.jackson.databind.JsonNode;
import org.jdbi.v3.json.Json;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface SnackInsightDao {
  @SqlQuery("<query>")
  @Json
  JsonNode query(@Define("query") String query);

  @SqlQuery(
      "SELECT COUNT(*) AS user_count\n"
          + "FROM users u\n"
          + "JOIN user_roles ur ON u.id = ur.user_id\n"
          + "JOIN roles r ON ur.role_id = r.id\n"
          + "WHERE r.role = 'ROLE_USER';\n")
  int usersCount();

  @SqlQuery("SELECT SUM(quantity) FROM user_snacks_log;")
  int totalQuantity();

  @SqlQuery("SELECT COUNT(id) FROM user_snacks_log;")
  int totalPurchase();
}
