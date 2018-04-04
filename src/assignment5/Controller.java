package assignment5;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.control.Alert.AlertType;
public class Controller
{

    protected static void makeController()
    {
        Stage controls = new Stage();
        controls.setTitle("Controller");
        GridPane controlsGridPane = new GridPane();

        CrittersPane(controlsGridPane);
        TimeStepPane(controlsGridPane);
        QuitPane(controlsGridPane);

        controls.setScene(new Scene(controlsGridPane));
        controls.show();
    }


    protected static void QuitPane(GridPane controlsGridPane)
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

    protected static void CrittersPane(GridPane mainPane)
    {
        GridPane CrittersPane = new GridPane();
        CrittersPane.setHgap(5);
        CrittersPane.setVgap(5);
        CrittersPane.setPadding(new Insets(2, 2, 2, 2));

        Label mainLabel = new Label();
        mainLabel.setText("Spawn Menu");


        Label cType = new Label();
        cType.setText("Type");

        ObservableList<String> CritterList = Util.getAllCritterClasses();
        Main.critterSelectComboBox = new ComboBox(CritterList);
        Main.critterSelectComboBox.setValue(CritterList.get(0));

        Label critterQuantity = new Label();
        critterQuantity.setText("Quantity");

        Button makeButton1 = new Button();
        makeButton1.setText("Add 1");
        makeButton1.setOnAction(e -> makeCritterHandler(Main.critterSelectComboBox.getValue(), 1));
        Button makeButton5 = new Button();
        makeButton5.setText("Add 5");
        makeButton1.setOnAction(e -> makeCritterHandler(Main.critterSelectComboBox.getValue(), 5));
        Button makeButton10 = new Button();
        makeButton10.setText("Add 10");
        makeButton1.setOnAction(e -> makeCritterHandler(Main.critterSelectComboBox.getValue(), 10));

        CrittersPane.add(mainLabel, 0, 0);
        CrittersPane.add(cType, 0, 1);
        CrittersPane.add(Main.critterSelectComboBox, 1, 1);
        CrittersPane.add(critterQuantity, 0, 2);
        CrittersPane.add(makeButton1, 0, 3);
        CrittersPane.add(makeButton5, 1, 3);
        CrittersPane.add(makeButton10, 2, 3);

        mainPane.add(CrittersPane, 0, 0);
    }

    protected static void TimeStepPane(GridPane mainPane)
    {
        GridPane timeStepGridPane = new GridPane();

        timeStepGridPane.setHgap(10);
        timeStepGridPane.setVgap(10);
        timeStepGridPane.setPadding(new Insets(150, 2, 10, 2));

        Label TimeStepLabel = new Label();
        TimeStepLabel.setText("Time Step Menu");
        timeStepGridPane.add(TimeStepLabel, 0, 0);


        final Slider slider = new Slider(0, 100, 50);
        final Label sliderValue = new Label("Number of Time Steps:");
        final Color textColor = Color.BLACK;

        slider.setValue(50);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setSnapToTicks(true);


        slider.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val)
            {
                sliderValue.setText(String.format("%.2f", new_val));
            }
        });

        GridPane.setConstraints(slider, 1, 1);
        mainPane.getChildren().add(slider);

        sliderValue.setTextFill(textColor);
        GridPane.setConstraints(sliderValue, 0, 1);
        mainPane.getChildren().add(sliderValue);


        /*
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
		sliderPane.add(slider, 0, 0);

		final int numStep = (int) slider.getValue();
		GridPane numStepPane = new GridPane();
		numStepPane.add(numStep, 0,0);

		GridPane textOfSlider = new GridPane();
		textOfSlider.add(numStep, 0, 0);

		slider.valueProperty().addListener((ov, old_val, new_val) ->
		{
		    //int new_valright = (int) new_val;
			//slider.setValue(new_valright);
			numStep = (int)new_val;
		});

        //numStep.setTextFill(Color.BLACK);
        GridPane.setConstraints(numStep, 2, 1);
        grid.getChildren().add(numStep);

		mainPane.add(textOfSlider, 0, 5);
		mainPane.add(sliderPane, 0, 2);
		*/

        Main.timeStepButton = new Button();
        Main.timeStepButton.setText("Step");
        Main.timeStepButton.setOnAction(e -> timeStepEventHandler((int) slider.getValue()));
        //System.out.println((int)slider.getValue());
        //timeStepButton.setOnAction(e -> System.out.println((int)slider.getValue()));
        timeStepGridPane.add(Main.timeStepButton, 0, 4);


        mainPane.add(timeStepGridPane, 0, 1);
    }

    private static void timeStepEventHandler(int numSteps)
    {
        int step = numSteps;
        for (int i = 0; i < step; i++)
        {
            Critter.worldTimeStep();
        }
        Critter.displayWorld(Main.displayCanvas);
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
        assignment5.Critter.displayWorld(Main.displayCanvas);

    }
}
