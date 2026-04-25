package in.stonecolddev.user;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Introspected
@RecordBuilder
@Serdeable // TODO: determine if this is appropriate or if a different object is better
public record UserRecord(
    Integer id,
    UUID publicId,
    String userName,
    String password,
    String email,
    Boolean isVerified,
    OffsetDateTime createdOn,
    OffsetDateTime updatedOn,
    OffsetDateTime lastHere
) implements UserRecordBuilder.With {
}