package in.stonecolddev.api.program;

import io.micronaut.context.annotation.Mapper;

@Mapper
public interface SaveWorkoutReqToResMapper {

  @Mapper.Mapping(to="liftsAdded", from="lifts")
  SaveWorkoutResponse toSaveWorkoutResponse(SaveWorkoutRequest saveWorkoutRequest);
}