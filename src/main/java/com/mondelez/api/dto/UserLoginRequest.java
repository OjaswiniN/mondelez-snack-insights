/* (C) 2023 Mondelez Insights */
package com.mondelez.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {
  @NotBlank String username;
  @NotBlank String password;
}
