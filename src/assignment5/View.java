package assignment5;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class View
{

	protected static void makeView(Stage primaryStage)
	{
		primaryStage.show();
		primaryStage.setTitle("Display");
		Group root = new Group();
		Main.gridCanvas = new SuperCanvas(Util.screenWidth, Util.screenHeight);

		root.getChildren().add(Main.gridCanvas);
		Main.gridCanvas.widthProperty().bind(Main.grid.widthProperty());
		Main.gridCanvas.heightProperty().bind(Main.grid.heightProperty());
		primaryStage.setScene(new Scene(root));



		primaryStage.show();
	}

	static class SuperCanvas extends Canvas
	{
		public SuperCanvas()
		{
			widthProperty().addListener(evt -> redraw());
			heightProperty().addListener(evt -> redraw());
		}

		public SuperCanvas(double width, double height)
		{
			super(width, height);
			widthProperty().addListener(evt -> redraw());
			heightProperty().addListener(evt -> redraw());
		}

		private void redraw()
		{
			Util.screenHeight = getHeight();
			Util.screenWidth = getWidth();
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



	protected static void drawCritters(Critter[][] grid, double widthBetweenLines, double heightBetweenLines)
	{
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[0].length; j++)
			{
				if (grid[i][j] == null)
				{
					continue;
				}
				Critter.CritterShape val = grid[i][j].viewShape();
				Main.gridGraphicsContext.setFill(grid[i][j].viewFillColor());
				Main.gridGraphicsContext.setStroke(grid[i][j].viewOutlineColor());
				Main.gridGraphicsContext.setLineWidth(Util.gridLineWidth / 2);

				switch (val)
				{
					case CIRCLE:
					{
						Main.gridGraphicsContext.strokeOval(j * widthBetweenLines + Util.gridLineWidth + Main.scale * (widthBetweenLines - Util.gridLineWidth) / 2.0, i * heightBetweenLines + Util.gridLineWidth + Main.scale * (heightBetweenLines - Util.gridLineWidth) / 2.0, (widthBetweenLines - Util.gridLineWidth) * Main.scale, (heightBetweenLines - Util.gridLineWidth) * Main.scale);
						Main.gridGraphicsContext.fillOval(j * widthBetweenLines + Util.gridLineWidth + Main.scale * (widthBetweenLines - Util.gridLineWidth) / 2.0, i * heightBetweenLines + Util.gridLineWidth + Main.scale * (heightBetweenLines - Util.gridLineWidth) / 2.0, (widthBetweenLines - Util.gridLineWidth) * Main.scale, (heightBetweenLines - Util.gridLineWidth) * Main.scale);
						break;
					}
					case DIAMOND:
					{
						drawPolygon(Util.diamond, i, j, widthBetweenLines, heightBetweenLines);
						break;
					}
					case STAR:
					{
						drawPolygon(Util.star, i, j, widthBetweenLines, heightBetweenLines);
						break;
					}
					case TRIANGLE:
					{
						drawPolygon(Util.triangle, i, j, widthBetweenLines, heightBetweenLines);
						break;
					}
					case SQUARE:
					{
						drawPolygon(Util.square, i, j, widthBetweenLines, heightBetweenLines);
						break;
					}
					default:
					{
						break;
					}
				}

			}
		}
	}

	private static void drawPolygon(double[][] shapeCoordinates, int row, int col, double widthBetweenLines, double heightBetweenLines)
	{
		double[] xCoords = new double[shapeCoordinates.length];
		double[] yCoords = new double[shapeCoordinates.length];
		double horizontalSize = widthBetweenLines - Util.gridLineWidth;
		double verticalSize = heightBetweenLines - Util.gridLineWidth;
		double horizontalOffset = widthBetweenLines * col + Util.gridLineWidth;
		double verticalOffset = heightBetweenLines * row + Util.gridLineWidth;
		for (int i = 0; i < shapeCoordinates.length; i++)
		{
			xCoords[i] = shapeCoordinates[i][0] * horizontalSize + horizontalOffset;
			yCoords[i] = shapeCoordinates[i][1] * verticalSize + verticalOffset;
		}
		Main.gridGraphicsContext.strokePolygon(xCoords, yCoords, shapeCoordinates.length);
		Main.gridGraphicsContext.fillPolygon(xCoords, yCoords, shapeCoordinates.length);
	}

}
