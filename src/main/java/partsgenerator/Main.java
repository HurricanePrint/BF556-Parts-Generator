package partsgenerator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
            Main.class.getResource("/partsgenerator/PartsWindow.fxml")
        );

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("Parts Generator v3.0");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}