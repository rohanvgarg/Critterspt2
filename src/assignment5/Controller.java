package assignment5;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller {

    protected static void makeController()
    {
        Stage controls = new Stage();
        controls.setTitle("Controller");
        GridPane controlsGridPane = new GridPane();

        Main.CrittersPane(controlsGridPane);
        Main.TimeStepPane(controlsGridPane);
        Main.QuitPane(controlsGridPane);

        controls.setScene(new Scene(controlsGridPane));
        controls.show();
    }
}
