/* CRITTERS MODEL Main.java
 * EE422C Project 5 submission by
 * Yuan Chang
 * YC23988
 * 15500
 * Rohan Garg
 * RG42255
 * 15500
 * Slip days used: <0>
 * Spring 2018
 */


package assignment5;

import javafx.application.Application;

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
    protected static Canvas displayCanvas;

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
        world.heightProperty().addListener((observable, oldValue, newValue) -> Critter.displayWorld(displayCanvas));
        world.widthProperty().addListener((observable, oldValue, newValue) -> Critter.displayWorld(displayCanvas));
    }
}