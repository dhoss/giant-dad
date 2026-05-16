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
public record SaveWorkoutRequest(
//    LocalDate forDate,
    List<Lift> lifts//,
 //   OffsetDateTime createdOn,
 //   OffsetDateTime updatedOn
) {}