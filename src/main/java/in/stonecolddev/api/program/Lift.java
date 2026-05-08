package in.stonecolddev.api.program;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Introspected
@RecordBuilder
@Serdeable
public record Lift(
    String name,
    Integer reps,
    Integer weight,
    String weightMeasurement // TODO: make an enum
) {}