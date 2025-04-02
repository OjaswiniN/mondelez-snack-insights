/* (C) 2023 Mondelez Insights */
package com.mondelez.api.mappers;

import com.mondelez.api.dto.UserSnacksLogRequest;
import com.mondelez.service.model.UserSnacksLog;
import java.util.UUID;

public class RequestMapper {

  public static UserSnacksLog toUserSnacksLog(UUID userId, UserSnacksLogRequest request) {
    return new UserSnacksLog(
        0,
        userId,
        request.getType(),
        request.getName(),
        request.getQuantity(),
        request.getTags(),
        request.getDate(),
        request.getReview(),
        request.getRating(),
        request.getImage(),
        System.currentTimeMillis());
  }
}
