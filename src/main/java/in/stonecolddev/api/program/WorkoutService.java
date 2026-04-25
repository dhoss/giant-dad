package in.stonecolddev.api.program;


import in.stonecolddev.user.UserRecord;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Optional;

@Singleton
public class WorkoutService {

  private static final Logger log = LoggerFactory.getLogger(WorkoutService.class);

  private final WorkoutRepository workoutRepository;

  public WorkoutService(
      WorkoutRepository workoutRepository
  ) {
    this.workoutRepository = workoutRepository;
  }

  public Optional<GetWorkoutResponse> workoutForDay(GetWorkoutRequest getWorkoutRequest) {
    UserRecord user = getWorkoutRequest.user();
    LocalDate day = getWorkoutRequest.day();
    log.info("Attempting to retrieve saved workout for user: {} date: {}", user, day);
    return workoutRepository.workoutForDay(user, day)
        .map(this::workoutRecordToGetResponse);
  }

  public SaveWorkoutResponse save(SaveWorkoutRequest saveWorkoutRequest) {
    return workoutRecordToSaveResponse(
        workoutRepository.save(
            saveWorkoutRequestToWorkoutRecord(saveWorkoutRequest)));
  }

  private SaveWorkoutResponse workoutRecordToSaveResponse(WorkoutRecord workoutRecord) {
    // TODO: add user ID, createdOn, updatedOn, saveResult, etc
    return SaveWorkoutResponseBuilder.builder()
        .forDay(workoutRecord.forDay())
        .liftsAdded(workoutRecord.lifts())
        .build();
  }

  private WorkoutRecord saveWorkoutRequestToWorkoutRecord(SaveWorkoutRequest saveWorkoutRequest) {
    return WorkoutRecordBuilder.builder()
        .forDay(saveWorkoutRequest.forDay())
        .userId(saveWorkoutRequest.user().id()) // TODO: migrate this to publicId
        //       implement id <-> publicId mapping
        .lifts(saveWorkoutRequest.lifts())
        .build();
  }

  private GetWorkoutResponse workoutRecordToGetResponse(WorkoutRecord workoutRecord) {
    return GetWorkoutResponseBuilder.builder()
        .workout(workoutRecord)
        .build();
  }

}