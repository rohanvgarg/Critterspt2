package assignment5;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import javafx.scene.shape.Shape;
import javafx.util.Duration;




import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class View
{
    //CONFIGS
    public static final double worldBoxScale = 0.94;
    // public static final double critterSpriteScale = 0.5;
    public static final Paint worldBoxFillColor = javafx.scene.paint.Color.GRAY;
    public static final Paint worldBoxLineColor = javafx.scene.paint.Color.BLACK;

    //SHAPES
    private final static double[][] triangle = {{0.50, 0.06}, {0.06, 0.78}, {0.94, 0.78}};
    private final static double[][] diamond = {{0.50, 0.06}, {0.25, 0.50}, {0.50, 0.94}, {0.75, 0.50}};
    private final static double[][] star = {{0.20, 0.95}, {0.50, 0.75}, {0.80, 0.95}, {0.68, 0.60}, {0.95, 0.40}, {0.62, 0.38}, {0.50, 0.05}, {0.38, 0.38}, {0.05, 0.40}, {0.32, 0.64}};

    //Animation
    protected static double animSpeed = 1.0;
    protected static boolean anim = false;
    protected static Timeline timeline;
    protected static KeyFrame moveBall;


    protected static void initKeyframe()
    {
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

         moveBall = new KeyFrame(Duration.seconds(1.0/animSpeed),
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {

                        Critter.worldTimeStep();
                        Critter.displayWorld(Main.displayCanvas);
                    }
                });

        timeline.getKeyFrames().add(moveBall);
        timeline.stop();

        /*
        KeyFrame animate = new KeyFrame(Duration.seconds(1.0 / animSpeed), event ->
        {
            System.out.println("TEST");
            if (anim == true)
            {
                Critter.worldTimeStep();
                Critter.displayWorld(Main.displayCanvas);
            }
        });

        timeline.getKeyFrames().add(animate);
        timeline.play();
        */

    }


    protected static void displayWorld(Canvas pane)
    {
        //size of each grid box
        double boxW = pane.getWidth() / Params.world_width;
        double boxH = pane.getHeight() / Params.world_height;

        //draw each grid box, not full size of the box
        double innerSquareWidth = boxW * View.worldBoxScale;
        double innerSquareHeight = boxH * View.worldBoxScale;

        //draw line color = "background" then boxes get drawn over this
        GraphicsContext gc = pane.getGraphicsContext2D();
        gc.setFill(View.worldBoxLineColor);
        gc.fillRect(0, 0, pane.getWidth(), pane.getHeight());

        //get some Critters to show
        Critter[][] toDisplay = Critter.getCritterWHMatrix();

        //set inner box color
        gc.setFill(View.worldBoxFillColor);

        for (int i = 0; i < toDisplay.length; i++)
        {
            for (int j = 0; j < toDisplay[i].length; j++)
            {
                //starting point of boxes
                double xOffset = i * boxW;
                double yOffset = j * boxH;

                //draw a smaller box centered inside the big box
                gc.fillRect(((boxW - innerSquareWidth) / 2) + xOffset, ((boxH - innerSquareHeight) / 2) + yOffset, innerSquareWidth, innerSquareHeight);
            }
        }

        for (int i = 0; i < toDisplay.length; i++)
        {
            for (int j = 0; j < toDisplay[i].length; j++)
            {
                Critter c = toDisplay[i][j];

                //only draw if there is a critter here
                if (c != null)
                {
                    //starting point of boxes
                    double xOffset = i * boxW;
                    double yOffset = j * boxH;

                    //size of the Critter Sprite to draw
                    double critterHeight = innerSquareHeight * 0.8;
                    double critterWidth = innerSquareWidth * 0.8;

                    //draw critter at box xOffset yOffset, centered in that box
                    drawCritter(gc, c, (boxW - critterWidth) / 2 + xOffset, (boxH - critterHeight) / 2 + yOffset, critterWidth, critterHeight, boxW, xOffset, boxH, yOffset);
                }
            }
        }

    }

    private static void drawCritter(GraphicsContext gc, Critter toBePainted, double critterX, double critterY, double critterWidth, double critterHeight, double canvWidth, double xOffset, double canvHeight, double yOffset)
    {
        Critter.CritterShape critShape = toBePainted.viewShape();
        gc.setStroke(toBePainted.viewOutlineColor());
        gc.setFill(toBePainted.viewFillColor());
        switch (critShape)
        {
            case CIRCLE:
                gc.fillOval(critterX, critterY, critterWidth, critterHeight);
                gc.strokeOval(critterX, critterY, critterWidth, critterHeight);
                break;

            case SQUARE:
                gc.fillRoundRect(critterX, critterY, critterWidth, critterHeight, critterWidth / 4.0, critterHeight / 4.0);
                gc.strokeRoundRect(critterX, critterY, critterWidth, critterHeight, critterWidth / 4.0, critterHeight / 4.0);
                break;

            case STAR:
                drawPolygon(gc, toBePainted, star, canvWidth, xOffset, canvHeight, yOffset);
                break;
            case DIAMOND:
                drawPolygon(gc, toBePainted, diamond, canvWidth, xOffset, canvHeight, yOffset);
                break;
            case TRIANGLE:
                drawPolygon(gc, toBePainted, triangle, canvWidth, xOffset, canvHeight, yOffset);
                break;

            default:

                break;
        }
    }

    private static void drawPolygon(GraphicsContext gc, Critter crit, double[][] shape, double canvWidth, double xOffset, double canvHeight, double yOffset)
    {
        double[] xCoords = new double[shape.length];
        double[] yCoords = new double[shape.length];

        for (int i = 0; i < shape.length; i++)
        {
            xCoords[i] = shape[i][0] * canvWidth + xOffset;
            yCoords[i] = shape[i][1] * canvHeight + yOffset;
        }
        gc.setFill(crit.viewFillColor());
        gc.fillPolygon(xCoords, yCoords, shape.length);

        gc.setStroke(crit.viewOutlineColor());
        gc.strokePolygon(xCoords, yCoords, shape.length);
    }
}
