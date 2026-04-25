package in.stonecolddev.api.program;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.test.support.TestPropertyProvider;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

public abstract class AbstractDatabaseTest implements TestPropertyProvider {

  private static final String username = "giantdad";
  private static final String password = "giantdad";
  private static final String databaseName = "giantdad";

  public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
      .withUsername(username)
      .withPassword(password)
      .withDatabaseName(databaseName);

  @BeforeAll
  public static void start() {
    startDatabase();
  }

  @AfterAll
  public static void stop() {
    postgres.stop();
  }

  public static void startDatabase() {
    postgres.start();
    initDb(postgres.getJdbcUrl());
  }


  @Override
  public @NonNull Map<String, String> getProperties() {
    if (!postgres.isRunning())
      startDatabase();
    return Map.of("datasources.default.url", postgres.getJdbcUrl(),
        "datasources.default.username", postgres.getUsername(),
        "datasources.default.password", postgres.getPassword());
  }

  private static void initDb(String jdbcUrl) {

    Flyway flyway = Flyway.configure()
        .locations("classpath:db/migrations")
        .dataSource(jdbcUrl, username, password)
        .load();

    flyway.migrate();
  }

}