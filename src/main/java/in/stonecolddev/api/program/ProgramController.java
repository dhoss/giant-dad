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

@Controller("/api/programs")
public class ProgramController {
  private static final Logger log = LoggerFactory.getLogger(ProgramController.class);

  @Inject
  private final WorkoutToCalendarProgramMapper workoutToCalendarProgramMapper;

  @Inject
  private final SaveWorkoutReqToResMapper saveWorkoutReqToResMapper;

  public ProgramController(
      WorkoutToCalendarProgramMapper workoutToCalendarProgramMapper,
      SaveWorkoutReqToResMapper saveWorkoutReqToResMapper) {

    this.workoutToCalendarProgramMapper = workoutToCalendarProgramMapper;
    this.saveWorkoutReqToResMapper = saveWorkoutReqToResMapper;
  }

  @Produces(MediaType.APPLICATION_JSON)
  @Get
  public HttpResponse<List<CalendarProgramResponse>> programForCurrentMonth() {
    return HttpResponse.ok(
        List.of(
            WorkoutBuilder.builder()
                .title("Squat Focus")
                .forDay(LocalDate.of(2026, 5, 4))
                .lifts(List.of())
                .build()
            ,WorkoutBuilder.builder()
                .title("Bench Focus")
                .forDay(LocalDate.of(2026, 5, 6))
                .lifts(List.of())
                .build()
            ,WorkoutBuilder.builder()
                .title("Deadlift Focus")
                .forDay(LocalDate.of(2026, 5, 8))
                .lifts(List.of())
                .build()
        ).stream().map(w -> workoutToCalendarProgramMapper.toCalendarProgramResponse(w)).toList());
  }

  // TODO: move this to workout api controller
  @Produces(MediaType.APPLICATION_JSON)
  @Put("save")
  public HttpResponse<SaveWorkoutResponse> saveWorkout(@Body SaveWorkoutRequest saveWorkoutRequest) {
    log.info("**** SAVEWORKOUT REQUEST {}", saveWorkoutRequest);
    return HttpResponse.ok(
        saveWorkoutReqToResMapper.toSaveWorkoutResponse(saveWorkoutRequest)
            .withSaveResult(HttpStatus.CREATED)
            .withUpdatedOn(OffsetDateTime.now()));
  }

}