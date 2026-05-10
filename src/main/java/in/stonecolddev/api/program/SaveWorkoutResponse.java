package in.stonecolddev.api.program;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpStatus;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Introspected
@RecordBuilder
@Serdeable
public record SaveWorkoutResponse(
    HttpStatus saveResult,
    LocalDate forDate,
    List<Lift> liftsAdded,
    OffsetDateTime createdOn,
    OffsetDateTime updatedOn
) implements SaveWorkoutResponseBuilder.With {}