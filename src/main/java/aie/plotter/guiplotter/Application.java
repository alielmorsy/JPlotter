package aie.plotter.guiplotter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {

        Parent fxmlLoader = FXMLLoader.load(Application.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}