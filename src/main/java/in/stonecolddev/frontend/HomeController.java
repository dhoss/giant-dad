package in.stonecolddev.frontend;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.ModelAndView;

@Controller
public class HomeController {


  @Get("/")
  public ModelAndView homePage() {
    return new ModelAndView("home/homepage", null);
  }

}