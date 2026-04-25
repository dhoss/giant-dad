package in.stonecolddev.api.program;

import in.stonecolddev.user.UserRecordBuilder;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.UUID;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Controller("/api/workouts")
public class WorkoutController {
  private static final Logger log = LoggerFactory.getLogger(WorkoutController.class);

  private final WorkoutService workoutService;

  public WorkoutController(
      WorkoutService workoutService
  ) {
    this.workoutService = workoutService;
  }

  @Produces(APPLICATION_JSON)
  @Put("save")
  public HttpResponse<SaveWorkoutResponse> saveWorkout(@Body SaveWorkoutRequest saveWorkoutRequest) {
    // TODO: map this to a proper response
    return HttpResponse.ok(workoutService.save(saveWorkoutRequest)); // TODO: refactor with call to workoutService
  }

  @Produces(APPLICATION_JSON)
  @Get("/{userId}/{day}")
  public HttpResponse<GetWorkoutResponse> workoutForDay(@PathVariable UUID userId, @PathVariable LocalDate day) {
    return HttpResponse.ok(
        // TODO: rework this to use a switch statement
        //   isPresent() -> actual workout
        //   isEmpty() -> 404
        workoutService.workoutForDay(
            GetWorkoutRequestBuilder.builder()
                .user(
                    // TODO: retrieve user by publicId, map to internal id
                    UserRecordBuilder.builder()
                        .id(1)
                        .publicId(userId)
                        .build())
                .day(day)
                .build()
        ).orElseGet(() -> GetWorkoutResponseBuilder.builder().build())
    );
  }

}