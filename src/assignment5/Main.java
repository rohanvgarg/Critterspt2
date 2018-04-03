package assignment5;

import javafx.application.Application;
import javafx.scene.Scene;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import javafx.scene.control.ScrollPane;

public class Main extends Application {

    class SuperCanvas extends Canvas
    {
        public SuperCanvas() {
            widthProperty().addListener(evt -> redraw());
            heightProperty().addListener(evt -> redraw());
        }

        public SuperCanvas(double width, double height) {
            //setWidth(width);
            //setHeight(height);
            super(width, height);
            widthProperty().addListener(evt -> redraw());
            heightProperty().addListener(evt -> redraw());
        }

        private void redraw() {
            Main.screenHeight = getHeight();
            Main.screenWidth = getWidth();
            //Critter.displayWorld();
        }

        public double prefWidth(double height) {
            return getWidth();
        }

        public double prefHeight(double width) {
            return getHeight();
        }


        public boolean isResizable() {
            return true;
        }

    }

    //main variables for drawing

    static GridPane grid = new GridPane();

    public static SuperCanvas mainCanvas = null; // primary world canvas
    public static GraphicsContext mainGraphicsContext=null;
    public static int mainRows = 10;
    public static int mainCols = 10;
    public static double mainLineWidth=10;

    public static double screenHeight=Math.max(100, Math.min(Params.world_height*25, 1200));
    public static double screenWidth=Math.max(100, Math.min(Params.world_width*25, 1200));

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
    public void start(Stage primaryStage) {
        try {

            //Stage menuStage = new Stage();
            primaryStage.setTitle("menu");

            BorderPane pane = new BorderPane();

            Button startButton = new Button("Start!");
            startButton.setMaxHeight(100);
            startButton.setMaxWidth(100);
            pane.setCenter(startButton);

            Scene scene = new Scene(grid, 500, 500);
            primaryStage.setScene(scene);
            primaryStage.show();

            Scene menuScene = new Scene(pane, 1200, 720);
            primaryStage.setScene(menuScene);

            grid.setGridLinesVisible(true);


            // Paints the icons.
            //Painter.paint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
