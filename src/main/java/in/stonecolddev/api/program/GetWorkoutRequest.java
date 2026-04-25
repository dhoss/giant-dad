package in.stonecolddev.api.program;

import in.stonecolddev.user.UserRecord;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.LocalDate;

@Introspected
@RecordBuilder
@Serdeable
public record GetWorkoutRequest(
    UserRecord user,
    LocalDate day
) implements GetWorkoutRequestBuilder.With {
}