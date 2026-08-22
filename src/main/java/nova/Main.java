package nova;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * JavaFX application for NOVA.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello World!");

        Scene scene = new Scene(helloWorld);

        stage.setScene(scene);
        stage.show();
    }
}