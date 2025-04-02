/* (C) 2023 Mondelez Insights */
package com.mondelez.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserSignupRequest {
  @NotBlank String username;
  @NotBlank String email;
  @NotBlank String password;
  @NotBlank String location;
  @NotBlank String dob;
}
