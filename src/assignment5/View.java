package assignment5;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import javafx.scene.shape.Shape;

public class View
{
    //CONFIGS
    public static final double worldBoxScale = 0.94;
    public static final Paint worldBoxFillColor = javafx.scene.paint.Color.GRAY;
    public static final Paint worldBoxLineColor = javafx.scene.paint.Color.BLACK;


    //SHAPE constants
    private final static double[][] triangle = {{0.50, 0.06}, {0.06, 0.78}, {0.94, 0.78}};
    private final static double[][] square = {{0.12, 0.12}, {0.12, 0.88}, {0.88, 0.88}, {0.88, 0.12}};
    private final static double[][] diamond = {{0.50, 0.06}, {0.25, 0.50}, {0.50, 0.94}, {0.75, 0.50}};
    private final static double[][] star = {{0.20, 0.95}, {0.50, 0.75}, {0.80, 0.95}, {0.68, 0.60}, {0.95, 0.40}, {0.62, 0.38}, {0.50, 0.05}, {0.38, 0.38}, {0.05, 0.40}, {0.32, 0.64}};

    //Shapes on screen
    private static Shape[][] Shapes = new Shape[Params.world_width][Params.world_height];

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

        //get Critters to show
        Critter[][] toDisplay = Critter.getCritterWHMatrix();

        //set box color
        gc.setFill(View.worldBoxFillColor);

        for (int i = 0; i < toDisplay.length; i++)
        {
            for (int j = 0; j < toDisplay[i].length; j++)
            {
                //starting point of boxes
                double xOffset = i * boxW;
                double yOffset = j * boxH;

                gc.fillRect(((boxW - innerSquareWidth) / 2) + xOffset, ((boxH - innerSquareHeight) / 2) + yOffset, innerSquareWidth, innerSquareHeight);
            }
        }

        for (int i = 0; i < toDisplay.length; i++)
        {
            for (int j = 0; j < toDisplay[i].length; j++)
            {
                Critter c = toDisplay[i][j];
                if (c != null)
                {
                    //starting point of boxes
                    double xOffset = i * boxW;
                    double yOffset = j * boxH;

                    double critterHeight = innerSquareHeight * 0.75;
                    double critterWidth = innerSquareWidth * 0.75;
                    double critterX = (boxW - critterWidth) / 2 + xOffset;
                    double critterY = (boxH - critterHeight) / 2 + yOffset;

                    drawCritter(gc, c, critterX, critterY, critterWidth, critterHeight, boxW, xOffset, boxH, yOffset);
                }
            }
        }

    }

    private static void drawCritter(GraphicsContext gc, Critter toBePainted, double critterX, double critterY, double critterWidth, double critterHeight, double canvWidth, double xOffset, double canvHeight, double yOffset)
    {
        Critter.CritterShape critShape = toBePainted.viewShape();
        switch (critShape)
        {
            case CIRCLE:
                //draw fill
                gc.setFill(toBePainted.viewFillColor());
                gc.fillOval(critterX, critterY, critterWidth, critterHeight);

                //draw outline
                gc.setStroke(toBePainted.viewOutlineColor());
                gc.strokeOval(critterX, critterY, critterWidth, critterHeight);

                break;

            case SQUARE:
                //draw fill
                gc.setFill(toBePainted.viewFillColor());
                gc.fillRoundRect(critterX, critterY, critterWidth, critterHeight, critterWidth/4.0,critterHeight/4.0);

                //draw outline
                gc.setStroke(toBePainted.viewOutlineColor());
                gc.strokeRoundRect(critterX, critterY, critterWidth, critterHeight, critterWidth/4.0,critterHeight/4.0);

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
