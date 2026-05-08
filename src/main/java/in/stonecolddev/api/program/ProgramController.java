package in.stonecolddev.api.program;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;

@Controller("/api/programs")
public class ProgramController {

  @Inject
  private final WorkoutToCalendarProgramMapper workoutToCalendarProgramMapper;

  public ProgramController(WorkoutToCalendarProgramMapper workoutToCalendarProgramMapper) {
    this.workoutToCalendarProgramMapper = workoutToCalendarProgramMapper;
  }

  @Produces(MediaType.APPLICATION_JSON)
  @Get
  public HttpResponse<List<CalendarProgram>> programForCurrentMonth() {
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
        ).stream().map(w -> workoutToCalendarProgramMapper.toCalendarProgram(w)).toList());
  }

}