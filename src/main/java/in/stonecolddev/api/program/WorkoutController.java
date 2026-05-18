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

@Controller("/api/workouts")
public class WorkoutController {
  private static final Logger log = LoggerFactory.getLogger(WorkoutController.class);

  @Inject
  private final SaveWorkoutReqToResMapper saveWorkoutReqToResMapper;

  public WorkoutController(
      SaveWorkoutReqToResMapper saveWorkoutReqToResMapper) {

    this.saveWorkoutReqToResMapper = saveWorkoutReqToResMapper;
  }

  @Produces(MediaType.APPLICATION_JSON)
  @Put("save")
  public HttpResponse<SaveWorkoutResponse> saveWorkout(@Body SaveWorkoutRequest saveWorkoutRequest) {
    return HttpResponse.ok(
        saveWorkoutReqToResMapper.toSaveWorkoutResponse(saveWorkoutRequest)
            .withSaveResult(HttpStatus.CREATED)
            .withUpdatedOn(OffsetDateTime.now()));
  }

}