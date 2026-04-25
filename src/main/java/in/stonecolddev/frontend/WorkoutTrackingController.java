package in.stonecolddev.frontend;

import in.stonecolddev.user.UserRecord;
import in.stonecolddev.user.UserRecordBuilder;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

import java.util.UUID;

@Controller
public class WorkoutTrackingController {


  // TODO: read in base URL as config parameter and pass it to template

  @View("workouttracking/main")
  @Get("/workouts")
  public UserRecord mainPage() {
    // TODO: implement this
    return UserRecordBuilder.builder()
        .userName("devin")
        .publicId(UUID.fromString("88787f1b-26e9-415f-bf78-e71259565700"))
        .build();
  }

}