package in.stonecolddev.api.program;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Introspected
@RecordBuilder
@Serdeable
public record Set(
    Integer id,
    Integer reps,
    Integer weight,
    Integer setNumber
) {
}
