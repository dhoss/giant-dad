package in.stonecolddev.api.program;


import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class WorkoutControllerIntegrationTest extends AbstractDatabaseTest {

  private final ZoneOffset utcTz = ZoneOffset.UTC;

  // "randomly" selected monday in 2026 UTC
  private final Clock clock = Clock.fixed(
      Instant.from(
          LocalDate.of(2026, 6, 15)
              .atStartOfDay()
              .atOffset(utcTz)
      ),
      utcTz
  );

  @Inject
  @Client("/")
  private HttpClient httpClient;

  @Test
  public void workoutForDay() {

    LocalDate day = LocalDate.now(clock);
    Integer privateUserId = 1;
    UUID publicUserId = UUID.randomUUID(); //TODO: this needs to be accurate, not random

    assertEquals(
        // TODO: build these in a method
        GetWorkoutResponseBuilder.builder()
            .workout(
                WorkoutRecordBuilder.builder()
                    .forDay(day)
                    .userId(privateUserId)
                    .lifts(
                        List.of(
                            LiftBuilder.builder()
                                .id(1)
                                .name("high bar barbell squat")
                                .sets(
                                    List.of(
                                        SetBuilder.builder()
                                            .id(1)
                                            .weight(250)
                                            .reps(5)
                                            .setNumber(1)
                                            .build(),
                                        SetBuilder.builder()
                                            .id(2)
                                            .weight(250)
                                            .reps(5)
                                            .setNumber(2)
                                            .build(),
                                        SetBuilder.builder()
                                            .id(3)
                                            .weight(250)
                                            .reps(5)
                                            .setNumber(3)
                                            .build(),
                                        SetBuilder.builder()
                                            .id(4)
                                            .weight(250)
                                            .reps(5)
                                            .setNumber(4)
                                            .build()))
                                .build(),
                            LiftBuilder.builder()
                                .id(2)
                                .name("bulgarian split squat")
                                .sets(
                                    List.of(
                                        SetBuilder.builder()
                                            .id(5)
                                            .weight(25)
                                            .reps(6)
                                            .setNumber(1)
                                            .build(),
                                        SetBuilder.builder()
                                            .id(6)
                                            .weight(25)
                                            .reps(6)
                                            .setNumber(2)
                                            .build(),
                                        SetBuilder.builder()
                                            .id(7)
                                            .weight(25)
                                            .reps(6)
                                            .setNumber(3)
                                            .build(),
                                        SetBuilder.builder()
                                            .id(8)
                                            .weight(25)
                                            .reps(6)
                                            .setNumber(4)
                                            .build()))
                                .build()))
                    .build())
            .build(),
        // TODO: make this a method
        httpClient.toBlocking()
            .retrieve(
                "/api/workouts/" + publicUserId + "/" + day,
                GetWorkoutResponse.class));
  }
}