package in.stonecolddev.api.program;

import io.micronaut.context.annotation.Mapper;

@Mapper
public interface WorkoutToCalendarProgramMapper {

  @Mapper.Mapping(to="start", from="forDay")
  CalendarProgramResponse toCalendarProgramResponse(Workout workout);
}