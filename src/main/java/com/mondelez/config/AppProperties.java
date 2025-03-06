/* (C) 2023 Mondelez Insights */
package com.mondelez.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Getter
public class AppProperties {
  private final Auth auth = new Auth();

  @Getter
  @Setter
  public static class Auth {
    private String tokenIssuer;
    private String tokenSecret;
    private long tokenExpirationMS;
  }
}
