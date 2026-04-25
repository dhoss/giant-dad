package in.stonecolddev.api.program;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Introspected
@RecordBuilder
@Serdeable
public record GetWorkoutResponse(
    WorkoutRecord workout
) implements GetWorkoutResponseBuilder.With {}