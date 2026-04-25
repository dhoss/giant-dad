package in.stonecolddev.api.program;

import in.stonecolddev.user.UserRecord;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Introspected
@RecordBuilder
@Serdeable
public record SaveWorkoutRequest(
    LocalDate forDay,
    UserRecord user,
    List<Lift> lifts,
    LocalDate scheduledDate,
    OffsetDateTime createdOn,
    OffsetDateTime updatedOn
) {
}