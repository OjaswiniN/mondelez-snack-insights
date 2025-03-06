/* (C) 2023 Mondelez Insights */
package com.mondelez.config;

import com.mondelez.dao.*;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.SqlStatements;
import org.jdbi.v3.jackson2.Jackson2Plugin;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.stringtemplate4.StringTemplateEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
@Slf4j
public class JdbiConfig {
  @Bean
  public Jdbi jdbi(
      DataSource ds,
      List<SqlObjectPlugin> sqlObjectPlugins,
      List<PostgresPlugin> postgresPlugins,
      List<Jackson2Plugin> jackson2Plugins,
      List<RowMapper<?>> rowMappers) {
    TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(ds);
    Jdbi jdbi = Jdbi.create(proxy);

    jdbi.getConfig(SqlStatements.class).setTemplateEngine(new StringTemplateEngine());

    // Register all available plugins
    sqlObjectPlugins.forEach(jdbi::installPlugin);
    postgresPlugins.forEach(jdbi::installPlugin);
    jackson2Plugins.forEach(jdbi::installPlugin);

    // Register all available rowMappers
    rowMappers.forEach(jdbi::registerRowMapper);

    return jdbi;
  }

  @Bean
  public SqlObjectPlugin sqlObjectPlugin() {
    return new SqlObjectPlugin();
  }

  @Bean
  public PostgresPlugin postgresObjectPlugin() {
    return new PostgresPlugin();
  }

  @Bean
  public Jackson2Plugin jackson2Plugin() {
    return new Jackson2Plugin();
  }

  @Bean
  public RoleDao initRoleDao(Jdbi jdbi) {
    return jdbi.onDemand(RoleDao.class);
  }

  @Bean
  public PermissionDao initPermissionDao(Jdbi jdbi) {
    return jdbi.onDemand(PermissionDao.class);
  }

  @Bean
  public UserDao initUserDao(Jdbi jdbi) {
    return jdbi.onDemand(UserDao.class);
  }

  @Bean
  public UserSnacksLogDao initUserSnacksLogDao(Jdbi jdbi) {
    return jdbi.onDemand(UserSnacksLogDao.class);
  }

  @Bean
  public SnackInsightDao initSnackInsightDao(Jdbi jdbi) {
    return jdbi.onDemand(SnackInsightDao.class);
  }
}
