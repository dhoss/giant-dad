package in.stonecolddev.user;

import io.micronaut.core.annotation.Introspected;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.OffsetDateTime;

@Introspected
@RecordBuilder
public record WorkoutTrackingRecord(
    UserRecord user,
    OffsetDateTime createdOn,
    OffsetDateTime updatedOn
) implements WorkoutTrackingRecordBuilder.With {}