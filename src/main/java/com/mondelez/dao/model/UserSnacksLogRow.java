/* (C) 2023 Mondelez Insights */
package com.mondelez.dao.model;

import java.sql.Date;
import java.util.UUID;
import lombok.NonNull;
import lombok.Value;

@Value
public class UserSnacksLogRow {
  @NonNull Integer id;
  @NonNull UUID userId;
  @NonNull String type;
  @NonNull String name;
  @NonNull Integer quantity;
  @NonNull String mood;
  @NonNull Date date;
  @NonNull String time;
  String review;
  Integer rating;
}
