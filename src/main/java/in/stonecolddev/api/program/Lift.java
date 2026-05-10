package in.stonecolddev.api.program;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.List;

@Introspected
@RecordBuilder
@Serdeable
public record Lift(
    String name,
    List<Set> sets,
    String weightMeasurement // TODO: make an enum
) {

  @Introspected
  @RecordBuilder
  @Serdeable
  public record Set(Integer reps, Integer weight){};

}