package in.stonecolddev.user;

import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.OffsetDateTime;

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