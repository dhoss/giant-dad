package in.stonecolddev.user;

import io.micronaut.core.annotation.Introspected;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.OffsetDateTime;

@Introspected
@RecordBuilder
public record UserRecord(
    String userName,
    String password,
    String email,
    Boolean isVerified,
    OffsetDateTime createdOn,
    OffsetDateTime updatedOn,
    OffsetDateTime lastHere
) implements UserRecordBuilder.With {}