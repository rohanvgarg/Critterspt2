package assignment5;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class Main extends Application
{
    //main variables for animation
    //main variables for drawing
    static GridPane grid = new GridPane();


    //View Component
    public static Canvas displayCanvas;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("View");

        displayCanvas = new Canvas();
        Pane displayPane = new Pane(displayCanvas);
        Scene world = new Scene(displayPane, 700, 700);
        GraphicsContext gc = displayCanvas.getGraphicsContext2D();
        gc.fillRect(0, 0, 100, 100);

        displayCanvas.widthProperty().bind(world.widthProperty());
        displayCanvas.heightProperty().bind(world.heightProperty());

        Controller.makeController();

        Critter.displayWorld(displayCanvas);

        primaryStage.setScene(world);

        View.initKeyframe();

        primaryStage.show();
        primaryStage.setOnHiding(e -> System.exit(0));


        //If resize
        world.heightProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                Critter.displayWorld(displayCanvas);
            }
        });
        world.widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                Critter.displayWorld(displayCanvas);
            }
        });
    }
}