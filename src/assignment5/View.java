package assignment5;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class View
{
    //CONFIGS
    public static final double worldBoxScale = 0.94;
    public static final Paint worldBoxFillColor = javafx.scene.paint.Color.GRAY;
    public static final Paint worldBoxLineColor = javafx.scene.paint.Color.BLACK;


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
            case STAR:
                //draw fill
                double x1 = canvWidth * 0.2 + xOffset;
                double y1 = canvHeight * 0.47 + yOffset;

                double x2 = canvWidth * 0.4 + xOffset;
                double y2 = canvHeight * 0.42 + yOffset;

                double x3 = canvWidth * 0.5 + xOffset;
                double y3 = canvHeight * 0.2 + yOffset;

                double x4 = canvWidth * 0.6 + xOffset;
                double y4 = canvHeight * 0.42 + yOffset;

                double x5 = canvWidth * 0.8 + xOffset;
                double y5 = canvHeight * 0.47 + yOffset;

                double x6 = canvWidth * 0.65 + xOffset;
                double y6 = canvHeight * 0.6 + yOffset;

                double x7 = canvWidth * 0.7 + xOffset;
                double y7 = canvHeight * 0.85 + yOffset;

                double x8 = canvWidth * 0.5 + xOffset;
                double y8 = canvHeight * 0.7 + yOffset;

                double x9 = canvWidth * 0.3 + xOffset;
                double y9 = canvHeight * 0.85 + yOffset;

                double x10 = canvWidth * 0.35 + xOffset;
                double y10 = canvHeight * 0.6 + yOffset;

                double xCoord[] = {x1, x2, x3, x4, x5, x6, x7, x8, x9, x10};
                double yCoord[] = {y1, y2, y3, y4, y5, y6, y7, y8, y9, y10};

                gc.setFill(toBePainted.viewFillColor());
                gc.fillPolygon(xCoord, yCoord, 10);
//
                gc.setStroke(toBePainted.viewOutlineColor());
                gc.setLineWidth(1.5);
                gc.strokePolygon(xCoord, yCoord, 10);

                break;
            case SQUARE:
                //draw fill
                gc.setFill(toBePainted.viewFillColor());
                gc.fillOval(critterX, critterY, critterWidth, critterHeight);

                //draw outline
                gc.setStroke(toBePainted.viewOutlineColor());
                gc.strokeOval(critterX, critterY, critterWidth, critterHeight);

                break;
            case DIAMOND:
                //draw fill
                x1 = 0.25 * canvWidth + xOffset;
                y1 = 0.5 * canvHeight + yOffset;

                x2 = 0.5 * canvWidth + xOffset;
                y2 = 0.25 * canvHeight + yOffset;

                x3 = 0.75 * canvWidth + xOffset;
                y3 = 0.5 * canvHeight + yOffset;

                x4 = 0.5 * canvWidth + xOffset;
                y4 = 0.75 * canvHeight + yOffset;

                double xCoords[] = {x1, x2, x3, x4};
                double yCoords[] = {y1, y2, y3, y4};

                gc.setFill(toBePainted.viewFillColor());
                gc.fillPolygon(xCoords, yCoords, 4);

                gc.setStroke(toBePainted.viewOutlineColor());
                gc.strokePolygon(xCoords, yCoords, 4);
                break;


            case TRIANGLE:
                //draw fill
                x1 = 0.5 * canvWidth + xOffset;
                y1 = 0.25 * canvHeight + yOffset;

                x2 = 0.8 * canvWidth + xOffset;
                y2 = 0.8 * canvHeight + yOffset;

                x3 = 0.2 * canvWidth + xOffset;
                y3 = 0.8 * canvHeight + yOffset;

                double xCoordinates[] = {x1, x2, x3};
                double yCoordinates[] = {y1, y2, y3};

                gc.setFill(toBePainted.viewFillColor());
                gc.fillPolygon(xCoordinates, yCoordinates, 3);

                gc.setStroke(toBePainted.viewOutlineColor());
                gc.strokePolygon(xCoordinates, yCoordinates, 3);

                break;

            default:

                gc.setFill(Color.BLACK);
                gc.fillOval(critterX, critterY, critterWidth, critterHeight);
                break;
        }
    }
}
