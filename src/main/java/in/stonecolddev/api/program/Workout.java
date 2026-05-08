package in.stonecolddev.api.program;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.LocalDate;
import java.util.List;

@Introspected
@RecordBuilder
@Serdeable
public record Workout(
    LocalDate forDay,
    String title,
    List<Lift> lifts
) {}