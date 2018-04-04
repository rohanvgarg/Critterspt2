package assignment5;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class View
{
    //CONFIGS
    public static final double worldBoxScale = 0.94;
    public static final Paint worldBoxFillColor = Paint.valueOf("#3F51B5");
    public static final Paint worldBoxLineColor = Paint.valueOf("#9E9E9E");


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
}
