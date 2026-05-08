package in.stonecolddev.api.program;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.LocalDate;

@Introspected
@RecordBuilder
@Serdeable
public record CalendarProgram(
    String title,
    LocalDate start
) {}