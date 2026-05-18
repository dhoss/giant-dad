package in.stonecolddev.api.program;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Controller("/api/workouts")
public class WorkoutController {
  private static final Logger log = LoggerFactory.getLogger(WorkoutController.class);

  @Inject
  private final SaveWorkoutReqToResMapper saveWorkoutReqToResMapper;

  public WorkoutController(
      SaveWorkoutReqToResMapper saveWorkoutReqToResMapper) {

    this.saveWorkoutReqToResMapper = saveWorkoutReqToResMapper;
  }

  @Produces(APPLICATION_JSON)
  @Put("save")
  public HttpResponse<SaveWorkoutResponse> saveWorkout(@Body SaveWorkoutRequest saveWorkoutRequest) {
    return HttpResponse.ok(
        saveWorkoutReqToResMapper.toSaveWorkoutResponse(saveWorkoutRequest)
            .withSaveResult(HttpStatus.CREATED)
            .withUpdatedOn(OffsetDateTime.now()));
  }

  @Produces(APPLICATION_JSON)
  @Get("/{day}")
  public HttpResponse<GetWorkoutResponse> workoutForDay(@PathVariable LocalDate day) {
    return HttpResponse.ok(
        GetWorkoutResponseBuilder.builder()
            .workout(
                WorkoutBuilder.builder()
                    .forDay(day)
                    .lifts(
                        List.of(
                            LiftBuilder.builder()
                                .name("squat")
                                .sets(Map.of(0, SetBuilder.builder().reps(1).weight(1).build(),
                                    1, SetBuilder.builder().reps(1).weight(1).build()))
                                .build()))
                    .build())
            .build());
  }

}