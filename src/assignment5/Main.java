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


            primaryStage.setScene(scene);
            primaryStage.setScene(menuScene);
            primaryStage.show();


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
        critterSelectComboBox.getItems().addAll(getAllCritterClasses());

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

    }


    private static List<String> getAllCritterClasses()
    {
        File f = new File(".");

        List<File> cTypes = new ArrayList<File>();

        getAllClassFiles(cTypes, ".");

        ArrayList<String> cNames = new ArrayList<String>();
        for (int i = 0; i < cTypes.size(); i++)
        {
            try
            {
                String className = cTypes.get(i).getName().replace(".class", "");
                Class<?> testingClass = Class.forName(myPackage + "." + className);
                if (testingClass.newInstance() instanceof assignment5.Critter)
                {
                    cNames.add(className);
                }
            } catch (Exception e)
            {
                //TODO
                e.printStackTrace();
            }
        }
        return cNames;
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



    //called recursively to get all .class files
    private static void getAllClassFiles(List<File> f, String path)
    {
        File critter = new File(path);
        //get all the files from a directory
        File[] filesList = critter.listFiles();
        for (File file : filesList)
        {
            if (file.isDirectory())
            {
                getAllClassFiles(f, file.getAbsolutePath());
            }
            if (file.isFile() && file.getName().endsWith(".class"))
            {
                f.add(file);
            }
        }
    }

}


