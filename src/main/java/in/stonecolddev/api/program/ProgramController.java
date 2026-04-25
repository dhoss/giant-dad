package in.stonecolddev.api.program;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
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
            WorkoutRecordBuilder.builder()
                .title("Squat Focus")
                .forDay(LocalDate.of(2026, 5, 4))
                .lifts(List.of())
                .build()
            ,WorkoutRecordBuilder.builder()
                .title("Bench Focus")
                .forDay(LocalDate.of(2026, 5, 6))
                .lifts(List.of())
                .build()
            ,WorkoutRecordBuilder.builder()
                .title("Deadlift Focus")
                .forDay(LocalDate.of(2026, 5, 8))
                .lifts(List.of())
                .build()
        ).stream().map(w -> workoutToCalendarProgramMapper.toCalendarProgramResponse(w)).toList());
  }

}