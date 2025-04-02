/* (C) 2023 Mondelez Insights */
package com.mondelez.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UserSnacksLogRequest {
  @NonNull String type;
  @NonNull String name;
  @NonNull Integer quantity;
  @NonNull List<String> tags;
  @NonNull String date;
  String review;
  Integer rating;
  @NonNull String image;
  Long createdAt;
}
