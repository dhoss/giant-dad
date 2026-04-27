package in.stonecolddev.frontend;

import in.stonecolddev.user.UserRecordBuilder;
import in.stonecolddev.user.WorkoutTrackingRecord;
import in.stonecolddev.user.WorkoutTrackingRecordBuilder;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

@Controller
public class WorkoutTrackingController {


  @View("workouttracking/main")
  @Get("/workouts")
  public WorkoutTrackingRecord mainPage() {
    return WorkoutTrackingRecordBuilder.builder()
        .user(UserRecordBuilder.builder()
            .userName("devin")
            .build())
            .build();
  }

}