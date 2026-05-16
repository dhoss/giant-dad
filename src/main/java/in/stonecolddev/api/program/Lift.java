package in.stonecolddev.api.program;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.Map;

@Introspected
@RecordBuilder
@Serdeable
public record Lift(
    String name,
    Map<Integer, Set> sets,
    String weightMeasurement // TODO: make an enum
) {}