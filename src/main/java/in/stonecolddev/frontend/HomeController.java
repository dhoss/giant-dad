package in.stonecolddev.frontend;

import in.stonecolddev.user.UserRecord;
import in.stonecolddev.user.UserRecordBuilder;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

@Controller
public class HomeController {


  @View("home/homepage")
  @Get("/")
  public UserRecord homePage() {
    return UserRecordBuilder.builder()
            .userName("devin")
            .build();
  }

}