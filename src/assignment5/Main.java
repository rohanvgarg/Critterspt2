package assignment5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Painter;

import assignment5.Critter.CritterShape;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Main extends Application
{
    class SuperCanvas extends Canvas
    {
        public SuperCanvas()
        {
            widthProperty().addListener(evt -> redraw());
            heightProperty().addListener(evt -> redraw());
        }

        public SuperCanvas(double width, double height)
        {
            //setWidth(width);
            //setHeight(height);
            super(width, height);
            widthProperty().addListener(evt -> redraw());
            heightProperty().addListener(evt -> redraw());
        }

        private void redraw()
        {
            Main.screenHeight = getHeight();
            Main.screenWidth = getWidth();
            //Critter.displayWorld();
        }

        public double prefWidth(double height)
        {
            return getWidth();
        }

        public double prefHeight(double width)
        {
            return getHeight();
        }


        public boolean isResizable()
        {
            return true;
        }

    }


    public static String myPackage = assignment5.Critter.class.getPackage().toString().split(" ")[1];

    //main variables for drawing
    static GridPane grid = new GridPane();

    public static SuperCanvas mainCanvas = null; // primary world canvas
    public static GraphicsContext mainGraphicsContext = null;
    public static int mainRows = 10;
    public static int mainCols = 10;
    public static double mainLineWidth = 10;

    public static double screenHeight = Math.max(100, Math.min(Params.world_height * 25, 1200));
    public static double screenWidth = Math.max(100, Math.min(Params.world_width * 25, 1200));

    public static String thisPackage = Critter.class.getPackage().toString().split(" ")[1];

    //main variables for animation

    static GridPane animation;
    static Button animationButton;
    static Button stopAnimationButton;

    //main timing variables

    static Timer timer;
    static TimerTask startAnimation;

    //stats variable

    public static ComboBox<String> statsType;
    public static Label statsLabel;

    static ScrollPane scrollpane = new ScrollPane();

    static Button timeStepButton;

    static TextField numStepsTextField;



    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            Stage splash = new Stage();
            splash.setTitle("Splash");
            BorderPane pane = new BorderPane();
            Button startButton = new Button("Start!");
            startButton.setMaxHeight(100);
            startButton.setMaxWidth(100);
            pane.setCenter(startButton);
            Scene scene = new Scene(grid, 500, 500);


            Scene menuScene = new Scene(pane, 1200, 720);

            grid.setGridLinesVisible(true);


			splash.setScene(scene);
			splash.setScene(menuScene);
			splash.show();

            //launch game button
            startButton.setOnAction(new EventHandler<ActionEvent>()
            {
                public void handle(ActionEvent event)
                {
                    splash.hide();
                    Stage controls = new Stage();
                    controls.setTitle("Controller");
                    GridPane controlsGridPane = new GridPane();
                    controlsGridPane.setGridLinesVisible(true);

                    CrittersPane(controlsGridPane);

                    controls.setScene(new Scene(controlsGridPane));
                    controls.show();
                }
            });




            // Paints the icons.
            //Painter.paint();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }


    private static void CrittersPane(GridPane mainPane)
    {
        GridPane CrittersPane = new GridPane();
        CrittersPane.setHgap(5);
        CrittersPane.setVgap(5);
        CrittersPane.setPadding(new Insets(2, 2, 2, 2));

        Label mainLabel = new Label();
        mainLabel.setText("Spawn Menu");


        Label cType = new Label();
        cType.setText("Type");

        ComboBox<String> critterSelectComboBox = new ComboBox<String>();
        critterSelectComboBox.getItems().addAll(Util.getAllCritterClasses());

        Label critterQuantity = new Label();
        critterQuantity.setText("Quantity");

        Button makeButton1 = new Button();
        makeButton1.setText("Add 1");
        makeButton1.setOnAction(e -> makeCritterHandler(critterSelectComboBox.getValue(), 1));
        Button makeButton5 = new Button();
        makeButton5.setText("Add 5");
        makeButton1.setOnAction(e -> makeCritterHandler(critterSelectComboBox.getValue(), 5));
        Button makeButton10 = new Button();
        makeButton10.setText("Add 10");
        makeButton1.setOnAction(e -> makeCritterHandler(critterSelectComboBox.getValue(), 10));


        CrittersPane.add(mainLabel, 0, 0);
        CrittersPane.add(cType, 0, 1);
        CrittersPane.add(critterSelectComboBox, 1, 1);
        CrittersPane.add(critterQuantity, 0, 2);
        CrittersPane.add(makeButton1, 0, 3);
        CrittersPane.add(makeButton5, 1, 3);
        CrittersPane.add(makeButton10, 2, 3);

        mainPane.add(CrittersPane,0,0);

        //-------------------//

        GridPane timeStepGridPane = new GridPane();

        timeStepGridPane.setHgap(10);
        timeStepGridPane.setVgap(10);
        timeStepGridPane.setPadding(new Insets(150, 2, 10, 2));

        Label TimeStepLabel=new Label();
        TimeStepLabel.setText("Time Step Menu");
        timeStepGridPane.add(TimeStepLabel, 0,0);


        Slider slider = new Slider(0,100,50);
        GridPane sliderPane = new GridPane();
        //sliderPane.setHgap(10);
        //sliderPane.setVgap(10);
        //slider.setValue(50);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        //slider.setMinorTickCount(5);
        slider.setSnapToTicks(true);
        //slider.setBlockIncrement(10);
        sliderPane.add(slider,0,0);

        final Label numStep = new Label(
                Integer.toString((int)slider.getValue()));
        GridPane textOfSlider = new GridPane();
        textOfSlider.add(numStep,0,0);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                slider.setValue((int)new_val);
                numStep.setText(String.format("%.2f", new_val));
                }
    });

        mainPane.add(textOfSlider, 0, 5);



        mainPane.add(sliderPane, 0,2);

        timeStepButton = new Button();
        timeStepButton.setText("Step");
        timeStepButton.setOnAction(e->timeStepEventHandler((int)slider.getValue()));
        timeStepButton.setOnAction(e -> System.out.println(numStep));
        timeStepGridPane.add(timeStepButton, 0, 4);


        mainPane.add(timeStepGridPane,0,0);


    }


    private static void makeCritterHandler(String type, int quantity)
    {
        if (type == null)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Critter not chosen");
            alert.showAndWait();
            return;
        }

        try
        {
            for (int i = 0; i < quantity; i++)
            {
                assignment5.Critter.makeCritter(type);

            }
        } catch (assignment5.InvalidCritterException ex)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);

            alert.setContentText("Invalid Critter Exception thrown");

        }
        assignment5.Critter.displayWorld(grid);

    }

    private static void timeStepEventHandler(int numSteps){
        int step = numSteps;
        for(int i=0;i<step;i++){
            Critter.worldTimeStep();
        }
        Critter.displayWorld(grid);

        }
    }



