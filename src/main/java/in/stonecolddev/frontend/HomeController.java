package in.stonecolddev.frontend;

import in.stonecolddev.user.UserRecord;
import in.stonecolddev.user.UserRecordBuilder;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.ModelAndView;

import java.util.Map;

@Controller
public class HomeController {


  @Get("/")
  public ModelAndView<UserRecord> homePage() {
    return new ModelAndView<>("home/homepage", UserRecordBuilder.builder().userName("devin").build());
  }

}