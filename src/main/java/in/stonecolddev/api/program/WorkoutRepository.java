package in.stonecolddev.api.program;

import in.stonecolddev.user.UserRecord;
import jakarta.inject.Singleton;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;

@Singleton
public class WorkoutRepository {

  private static final Logger log = LoggerFactory.getLogger(WorkoutRepository.class);

  private final Jdbi jdbi;

  public WorkoutRepository(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public WorkoutRecord save(WorkoutRecord workoutRecord) {
    Integer dow = dayOfWeekFromLocalDate(workoutRecord.forDay());
    Integer userId = workoutRecord.userId();
    List<Lift> liftsAdded = new ArrayList<>();
    for (Lift lift : workoutRecord.lifts()) {
      for (Set set : lift.sets()) {
        jdbi.withHandle(handle ->
            handle.createUpdate(
                    """
                           insert into users_workouts(
                               users_id
                             , lifts_id
                             , set_number
                             , reps
                             , weight
                             , scheduled_date
                             , created_on
                           )
                           select
                               :user_id
                             , l.id as "lift_id"
                             , :set_number
                             , :reps
                             , :weight
                             , :scheduled_date
                             , now()
                           from lifts l
                           left join workouts w on w.id = l.workouts_id
                           where w.day_of_week = :day_of_week
                           and l.name = :lift_name
                           on conflict (
                               users_id
                             , lifts_id
                             , set_number
                             , scheduled_date
                             , ((created_on at time zone 'UTC')::date))
                           do update
                              set   reps = :reps
                                  , weight = :weight
                                  , updated_on = now()
                        """)
                .bind("user_id", userId)
                .bind("set_number", set.setNumber())
                .bind("reps", set.reps())
                .bind("weight", set.weight())
                .bind("day_of_week", dow)
                .bind("lift_name", lift.name())
                .bind("scheduled_date", workoutRecord.forDay())
                .execute()
        );
      }
      liftsAdded.add(lift);
    }

    return workoutRecord;
  }

  // TODO: this needs to be refactored to pass the query
  //       and resultset builder to a method and get a list of lifts back
  private List<Lift> retrieveSavedLiftsForDay(UserRecord user, LocalDate day) {
    log.info("Retrieving saved workout for user {} on date {}", user, day);
    return jdbi.withHandle(handle ->
        handle.createQuery(
                """
                     select
                     	  u.user_name
                     	, w.name as "workout_name"
                     	, w.day_of_week as "workout_day"
                     	, l.id as "lift_id"
                     	, l.name as "lift_name"
                     	, l.lift_order
                     	, s.id as "set_id"
                     	, upws.set_number
                     	, upws.reps
                     	, upws.weight
                     	, upws.created_on
                     	, upws.scheduled_date
                     from
                     	users_workouts upws
                     left join users u on
                     	u.id = upws.users_id
                     left join lifts l on
                     	l.id = upws.lifts_id
                     left join workouts w on
                     	w.id = l.workouts_id
                     left join sets s on
                     	s.id = upws.lifts_id
                     where
                     	upws.scheduled_date = :scheduled_date
                     order by
                     	l.lift_order asc, upws.set_number asc
                    """
            )
            .bind("scheduled_date", day)
            .bind("user_id", user.id())
            // TODO: move this to a WorkoutRecord builder method
            .reduceResultSet(new LinkedHashMap<Integer, Lift>(), (liftAccumulator, rs, ctx) -> {
              String liftName = rs.getString("lift_name");
              Integer liftId = rs.getInt("lift_id");
              Lift lift = LiftBuilder.builder().id(liftId).name(liftName).build();

              if (liftAccumulator.containsKey(liftId)) {
                lift = liftAccumulator.get(liftId);
              } else {
                liftAccumulator.put(liftId, lift);
              }

              Integer setId = rs.getInt("set_id");
              if (!rs.wasNull()) {
                List<Set> currentSets = Optional.ofNullable(lift.sets()).orElseGet(ArrayList::new);
                Set set = SetBuilder.builder()
                    .id(setId)
                    .weight(rs.getInt("weight"))
                    .reps(rs.getInt("reps"))
                    .setNumber(rs.getInt("set_number"))
                    .build();
                currentSets.add(set);
                liftAccumulator.put(liftId, lift.withSets(currentSets));
              }

              return liftAccumulator;
            })
            .values()
            .stream()
            .toList());
  }

  private List<Lift> retrieveProgrammedLiftsForDay(UserRecord user, LocalDate day) {
    log.info("Retrieving programmed workout for user {} for date {}", user, day);
    return jdbi.withHandle(handle ->
        handle.createQuery(
                """
                    select
                        pw.name as "workout_name"
                      , pw.day_of_week as "workout_day"
                      , pwl.id as "lift_id"
                      , pwl.name as "lift_name"
                      , pwl.description as "lift_description"
                      , pwl.lift_order
                      , pwls.id as "set_id"
                      , pwls.set_number
                      , pwls.reps
                      , pwls.weight
                      , u.user_name
                      , utp.started_on
                      , utp.completed_on
                    from programs p
                    left join workouts pw on pw.programs_id = p.id
                    left join lifts pwl on pwl.workouts_id = pw.id
                    left join users_programs utp on utp.programs_id = p.id
                    left join sets pwls on pwls.lifts_id = pwl.id
                    left join users u on u.id = :user_id
                    where pw.day_of_week = :day_of_week
                    and p.id = u.current_program_id
                    and utp.started_on <= now()
                    order by pwl.lift_order asc--, pwls.set_number asc;
                    """
            )
            .bind("day_of_week", dayOfWeekFromLocalDate(day))
            .bind("user_id", user.id())
            .reduceResultSet(new LinkedHashMap<Integer, Lift>(), (liftAccumulator, rs, ctx) -> {
              String liftName = rs.getString("lift_name");
              Integer liftId = rs.getInt("lift_id");
              Lift lift = LiftBuilder.builder().id(liftId).name(liftName).build();

              if (liftAccumulator.containsKey(liftId)) {
                lift = liftAccumulator.get(liftId);
              } else {
                liftAccumulator.put(liftId, lift);
              }

              Integer setId = rs.getInt("set_id");
              if (!rs.wasNull()) {
                List<Set> currentSets = Optional.ofNullable(lift.sets()).orElseGet(ArrayList::new);
                Set set = SetBuilder.builder()
                    .id(setId)
                    .weight(rs.getInt("weight"))
                    .reps(rs.getInt("reps"))
                    .setNumber(rs.getInt("set_number"))
                    .build();
                currentSets.add(set);
                liftAccumulator.put(liftId, lift.withSets(currentSets));
              }

              return liftAccumulator;
            })
            .values()
            .stream()
            .toList());
  }

  public Optional<WorkoutRecord> workoutForDay(UserRecord user, LocalDate day) {
    return retrieveSavedOrProgrammedWorkoutForDay(user, day);
  }

  private Optional<WorkoutRecord> retrieveSavedOrProgrammedWorkoutForDay(
      UserRecord user, LocalDate day) {

    List<Lift> lifts =
        Optional.of(retrieveSavedLiftsForDay(user, day))
            .filter(l -> !l.isEmpty())
            .orElseGet(() -> retrieveProgrammedLiftsForDay(user, day));

    if (lifts.isEmpty())
      return Optional.empty();
    return Optional.of(buildWorkoutRecordForDay(user, day, lifts));
  }

  private WorkoutRecord buildWorkoutRecordForDay(
      UserRecord user, LocalDate day, List<Lift> lifts) {

    return WorkoutRecordBuilder.builder()
        .userId(user.id())
        .forDay(day)
        .lifts(lifts)
        .build();
  }

  private Integer dayOfWeekFromLocalDate(LocalDate day) {
    // Mon (1) -> Workout 1
    // Wed (3) -> Workout 2
    // Fri (5) -> Workout 3
    return Map.of(1, 1, 3, 2, 5, 3).get(day.getDayOfWeek().getValue());
  }
}