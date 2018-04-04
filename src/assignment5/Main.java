package assignment5;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application
{

    //CONTROLLER SHIT
    public static ComboBox<String> critterSelectComboBox;
	protected final static double scale = 0.5;
	public static View.SuperCanvas gridCanvas = null;//Main canvas which the world is displayed
	public static GraphicsContext gridGraphicsContext = null;
	public static String myPackage = assignment5.Critter.class.getPackage().toString().split(" ")[1];
	public static View.SuperCanvas mainCanvas = null; // primary world canvas
	public static GraphicsContext mainGraphicsContext = null;
	public static int mainRows = 10;
	public static int mainCols = 10;
	public static double mainLineWidth = 10;
	public static String thisPackage = Critter.class.getPackage().toString().split(" ")[1];
	public static ComboBox<String> statsType;
	public static Label statsLabel;
	static Button timeStepButton;
	static TextField numStepsTextField;

	//main variables for animation
	//main variables for drawing
	static GridPane grid = new GridPane();
	static GridPane animation;
	static Button animationButton;

	//main timing variables
	static Button stopAnimationButton;
	static Timer timer;

	//stats variable
	static TimerTask startAnimation;
	static ScrollPane scrollpane = new ScrollPane();


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
        primaryStage.show();


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