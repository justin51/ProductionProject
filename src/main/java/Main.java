import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class to start the application
 *
 * @author Justin Kenney
 */
public class Main extends Application {

  /**
   * Main method that starts program execution
   *
   * @param args starting arguments
   */
  public static void main(String[] args) {

    launch(args);
  }

  /**
   * The method required for starting an FXML application
   *
   * @param primaryStage
   * @throws Exception
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

    Scene scene = new Scene(root, 480, 275);

    primaryStage.setTitle("Production Project");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
