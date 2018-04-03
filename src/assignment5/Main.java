package assignment5;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application
{
	public static SuperCanvas gridCanvas = null;//Main canvas which the world is displayed
	public static GraphicsContext gridGraphicsContext = null;




	private final static double scale = 0.5;

	class SuperCanvas extends Canvas
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


	public static String myPackage = assignment5.Critter.class.getPackage().toString().split(" ")[1];

	//main variables for drawing
	static GridPane grid = new GridPane();

	public static SuperCanvas mainCanvas = null; // primary world canvas
	public static GraphicsContext mainGraphicsContext = null;
	public static int mainRows = 10;
	public static int mainCols = 10;
	public static double mainLineWidth = 10;


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


			splash.setScene(scene);
			splash.setScene(menuScene);
			splash.show();

			//launch game button
			startButton.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event)
				{
					splash.hide();

					makeController();
					makeView(primaryStage);


				}
			});
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}

	private static void makeController()
	{
		Stage controls = new Stage();
		controls.setTitle("Controller");
		GridPane controlsGridPane = new GridPane();

		CrittersPane(controlsGridPane);
		QuitPane(controlsGridPane);

		controls.setScene(new Scene(controlsGridPane));
		controls.show();
	}
	private static void QuitPane(GridPane controlsGridPane)
	{
		GridPane quitButtonGridPane = new GridPane();
		quitButtonGridPane.setHgap(10);
		quitButtonGridPane.setVgap(10);
		quitButtonGridPane.setPadding(new Insets(10, 2, 10, 2));
		Button quitButton = new Button();
		quitButton.setText("Quit");
		quitButton.setOnAction(e -> System.exit(0));
		quitButtonGridPane.add(quitButton, 0, 0);
		controlsGridPane.add(quitButtonGridPane, 0, 5);
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

		mainPane.add(CrittersPane, 0, 0);

	}


	private void makeView(Stage primaryStage)
	{
		primaryStage.show();
		primaryStage.setTitle("Display");
		Group root = new Group();
		gridCanvas = new SuperCanvas(Util.screenWidth, Util.screenHeight);
		gridGraphicsContext = gridCanvas.getGraphicsContext2D();

		grid.getChildren().add(gridCanvas);
		gridCanvas.widthProperty().bind(grid.widthProperty());
		gridCanvas.heightProperty().bind(grid.heightProperty());
		primaryStage.setScene(new Scene(grid));

		makeGrid(null);
		grid.setGridLinesVisible(true);

		primaryStage.show();
	}



	private static void makeCritterHandler(String type, int quantity)
	{

		//todo
	}


	public static void makeGrid(Critter[][] world)
	{
		gridGraphicsContext.setFill(Color.SKYBLUE);
		gridGraphicsContext.fillRect(0, 0, Util.screenWidth, Util.screenHeight);
		gridGraphicsContext.setFill(Color.BLACK);


		if (grid == null)
		{
			return;
		}

		drawCritters(world, Util.widthBetween, Util.heightBetween);
		//statisticsEventHandler(statisticsComboBox.getValue());

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
				gridGraphicsContext.setFill(grid[i][j].viewFillColor());
				gridGraphicsContext.setStroke(grid[i][j].viewOutlineColor());
				gridGraphicsContext.setLineWidth(Util.gridLineWidth / 2);

				switch (val)
				{
					case CIRCLE:
					{
						gridGraphicsContext.strokeOval(j * widthBetweenLines + Util.gridLineWidth + scale * (widthBetweenLines - Util.gridLineWidth) / 2.0, i * heightBetweenLines + Util.gridLineWidth + scale * (heightBetweenLines - Util.gridLineWidth) / 2.0, (widthBetweenLines - Util.gridLineWidth) * scale, (heightBetweenLines - Util.gridLineWidth) * scale);
						gridGraphicsContext.fillOval(j * widthBetweenLines + Util.gridLineWidth + scale * (widthBetweenLines - Util.gridLineWidth) / 2.0, i * heightBetweenLines + Util.gridLineWidth + scale * (heightBetweenLines - Util.gridLineWidth) / 2.0, (widthBetweenLines - Util.gridLineWidth) * scale, (heightBetweenLines - Util.gridLineWidth) * scale);
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
		gridGraphicsContext.strokePolygon(xCoords, yCoords, shapeCoordinates.length);
		gridGraphicsContext.fillPolygon(xCoords, yCoords, shapeCoordinates.length);
	}
}


