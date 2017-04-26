package other;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

// It's easiest to run this class from the command line. For easy testing in IntelliJ we might need
// a JavaFX specific testing framework.
class JavaFxStuff extends Application {
  @Override
  public void start(Stage stage) {
    Label m = new Label("Hello Leah!");
    m.setFont(new Font(50));
    Scene s = new Scene(m);
    stage.setScene(s);
    stage.setTitle("Messing around with JavaFX");
    stage.show();
  }
}
