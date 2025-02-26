/* (C) 2023 Mondelez Insights */
package com.mondelez.service.model;

import java.util.List;
import java.util.UUID;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSnacksLog {
  Integer id;
  @NonNull UUID userId;
  @NonNull String type;
  @NonNull String name;
  @NonNull Integer quantity;
  @NonNull List<String> tags;
  @NonNull String date;
  String review;
  Integer rating;
  @NonNull String image;
  @NonNull Long createdAt;
}
