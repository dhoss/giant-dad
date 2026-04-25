package in.stonecolddev;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
public class GiantdadTest {

  private final EmbeddedApplication<?> application;

  public GiantdadTest(EmbeddedApplication<?> application) {
    this.application = application;
  }

  @Test
  public void smokeTest() {
    Assertions.assertTrue(application.isRunning());
  }

}