package assignment5;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.*;

import javafx.scene.control.Alert.AlertType;

public class Controller
{
    private static TextArea stats;
    private static Slider slider;
    private static Label curSpeed;
    public static String myPackage = Critter.class.getPackage().toString().split(" ")[1];


    /**
     * Makes the Controller Window
     */
    protected static void makeController()
    {
        Stage controls = new Stage();
        controls.setResizable(false);
        controls.setTitle("Controller");
        GridPane controlsGridPane = new GridPane();

        CrittersPane(controlsGridPane);
        TimeStepPane(controlsGridPane);
        setSeedPane(controlsGridPane);
        logPane(controlsGridPane);
        AnimationPane(controlsGridPane);
        QuitPane(controlsGridPane);

        controlsGridPane.setGridLinesVisible(true);
        controlsGridPane.setPadding(new Insets(5, 5, 5, 5));
        controls.setScene(new Scene(controlsGridPane));

        controls.show();
        controls.setOnHiding(e -> System.exit(0));
    }


    /**
     * Positions the quit button on the Controller
     * @param controlsGridPane
     */
    protected static void QuitPane(GridPane controlsGridPane)
    {
        GridPane quitButtonGridPane = new GridPane();
        quitButtonGridPane.setHgap(10);
        quitButtonGridPane.setVgap(10);
        quitButtonGridPane.setPadding(new Insets(5, 5, 5, 5));
        Button quitButton = new Button();
        quitButton.setText("Quit");
        quitButton.setOnAction(e -> System.exit(0));
        quitButtonGridPane.add(quitButton, 0, 0);
        controlsGridPane.add(quitButtonGridPane, 0, 7);
    }

    /**
     * Positions the Critter controls on the Controller
     * @param mainPane
     */
    protected static void CrittersPane(GridPane mainPane)
    {
        GridPane CrittersPane = new GridPane();
        CrittersPane.setHgap(5);
        CrittersPane.setVgap(5);
        CrittersPane.setPadding(new Insets(5, 5, 5, 5));

        Label mainLabel = new Label();
        mainLabel.setText("Critter Menu");

        Label cType = new Label();
        cType.setText("Type");

        ObservableList<String> CritterList = Util.getAllCritterClasses();
        ComboBox critterSelectComboBox = new ComboBox(CritterList);
        critterSelectComboBox.setValue(CritterList.get(0));

        Button makeButton1 = new Button();
        makeButton1.setText("Add 1");
        makeButton1.setOnAction(e -> makeCritterHandler((String) critterSelectComboBox.getValue(), 1));

        Button makeButton5 = new Button();
        makeButton5.setText("Add 5");
        makeButton5.setOnAction(e -> makeCritterHandler((String) critterSelectComboBox.getValue(), 5));

        Button makeButton10 = new Button();
        makeButton10.setText("Add 10");
        makeButton10.setOnAction(e -> makeCritterHandler((String) critterSelectComboBox.getValue(), 10));

        Button runStatsButton = new Button();
        runStatsButton.setText("Run Stats");
        runStatsButton.setOnAction(e -> runStatsEventHandler((String) critterSelectComboBox.getValue()));

        CrittersPane.add(mainLabel, 0, 0);

        GridPane subPane1 = new GridPane();
        subPane1.add(cType, 0, 0);
        subPane1.add(critterSelectComboBox, 1, 0);
        subPane1.setHgap(5);
        subPane1.setVgap(5);

        GridPane subPane2 = new GridPane();
        subPane2.add(makeButton1, 0, 0);
        subPane2.add(makeButton5, 1, 0);
        subPane2.add(makeButton10, 2, 0);
        subPane2.add(runStatsButton, 3, 0);
        subPane2.setHgap(5);
        subPane2.setVgap(5);

        CrittersPane.add(subPane1, 0, 1);
        CrittersPane.add(subPane2, 0, 2);


        mainPane.add(CrittersPane, 0, 0);
    }

    /**
     * Positions the log on the Controller
     * @param mainPane
     */
    protected static void logPane(GridPane mainPane)
    {
        stats = new TextArea("Statistics displayed here");

        mainPane.add(stats, 0, 6, 1, 1);
    }

    /**
     * Positions the seed controls on the Controller
     * @param mainPane
     */
    protected static void setSeedPane(GridPane mainPane)
    {
        GridPane seedPane = new GridPane();

        Label seedPaneLabel = new Label();
        seedPaneLabel.setText("Seed Menu");

        seedPane.add(seedPaneLabel, 0, 0);

        seedPane.setVgap(10);
        seedPane.setHgap(10);
        seedPane.setPadding(new Insets(5, 5, 5, 5));

        Button seedButton = new Button("Set Seed");

        TextField seedField = new TextField("Enter Seed");
        seedField.setPromptText("Enter Seed");

        seedButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                try
                {
                    int newSeed = Integer.parseInt(seedField.getText());
                    Critter.setSeed(newSeed);
                    stats.appendText("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                    stats.appendText("\nSeed set!\n");
                } catch (Exception e)
                {
                    seedField.clear();
                    stats.appendText("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                    stats.appendText("\nBad seed!\n");

                }

            }
        });
        seedPane.add(seedButton, 0, 1);
        seedPane.add(seedField, 1, 1);
        mainPane.add(seedPane, 0, 2);
    }

    /**
     * Positions the time step controls on the Controller
     * @param mainPane
     */
    protected static void TimeStepPane(GridPane mainPane)
    {
        GridPane timeStepGridPane = new GridPane();

        timeStepGridPane.setHgap(10);
        timeStepGridPane.setVgap(10);
        timeStepGridPane.setPadding(new Insets(5, 5, 5, 5));

        Label TimeStepLabel = new Label();
        TimeStepLabel.setText("Time Step Menu");
        timeStepGridPane.add(TimeStepLabel, 0, 0);

        ToggleGroup group = new ToggleGroup();

        Label quantitySteps = new Label();
        quantitySteps.setText("Quantity of steps");
        timeStepGridPane.add(quantitySteps, 0, 1);

        RadioButton rb1 = new RadioButton();
        rb1.setText("Step 1");
        rb1.setUserData(new Integer(1));
        rb1.setSelected(true);
        rb1.setToggleGroup(group);
        timeStepGridPane.add(rb1, 1, 1);

        RadioButton rb5 = new RadioButton();
        rb5.setText("Step 5");
        rb5.setUserData(new Integer(5));
        rb5.setToggleGroup(group);
        timeStepGridPane.add(rb5, 2, 1);

        RadioButton rb20 = new RadioButton();
        rb20.setText("Step 20");
        rb20.setUserData(new Integer(20));
        rb20.setToggleGroup(group);
        timeStepGridPane.add(rb20, 3, 1);

        Button timeStepButton = new Button();
        timeStepButton.setText("Step");
        timeStepButton.setOnAction(e -> timeStepEventHandler((int) group.getSelectedToggle().getUserData()));
        timeStepGridPane.add(timeStepButton, 0, 2);

        mainPane.add(timeStepGridPane, 0, 1);
    }

    /**
     * Positions the animation controls on the controller
     * @param mainPane
     */
    protected static void AnimationPane(GridPane mainPane)
    {
        GridPane animsPane = new GridPane();

        animsPane.setHgap(10);
        animsPane.setVgap(10);
        animsPane.setPadding(new Insets(5, 5, 5, 5));

        Label animsLabel = new Label();
        animsLabel.setText("Animation Menu");
        animsPane.add(animsLabel, 0, 0);

        Label speedLabel = new Label();
        speedLabel.setText("Animation Speed");
        animsPane.add(speedLabel, 1, 1);

         slider = new Slider(1, 10, 1);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(1.0);
        slider.setSnapToTicks(true);
        animsPane.add(slider, 2, 1);

        curSpeed = new Label(String.valueOf(((Double) (slider.getValue())).intValue()));

        animsPane.add(curSpeed, 3, 1);

        slider.valueProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                View.animSpeed = (Double) newValue;
                View.animSpeed = Math.floor(View.animSpeed);
                curSpeed.setText(String.valueOf(((Double) (slider.getValue())).intValue()));
                View.initKeyframe();
            }
        });
        Button animateButton = new Button();
        animateButton.setText("Start Animation");
        animateButton.setOnAction(e -> animateButtonEventHandler(animateButton));
        animsPane.add(animateButton, 0, 1);

        mainPane.add(animsPane, 0, 5);
    }

    /**
     * Event handler for the animate button
     * @param but
     */
    private static void animateButtonEventHandler(Button but)
    {
        View.anim = !View.anim;

        if(View.anim == true)
        {
            but.setText("Stop animating");
            View.timeline.play();
            slider.setDisable(true);

        }
        else
        {
            but.setText("Start animating");
            View.timeline.pause();
            slider.setDisable(false);
        }
    }

    /**
     * Events handler for the runStats button
     * @param text
     */
    private static void runStatsEventHandler(String text)
    {
        String displayString;
        List<Critter> critterList;

        try
        {
            critterList = Critter.getInstances(text);
            try
            {
                Class<?> inClass = Class.forName(myPackage + "." + text);
                java.lang.reflect.Method inMethod = inClass.getMethod("runStats", List.class);

                displayString = (String) inMethod.invoke(null, critterList);

                stats.appendText("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

                stats.appendText("\n" + displayString + "\n");
            } catch (Exception ex)
            {
                ex.printStackTrace();
                throw new InvalidCritterException(text);
            }

        } catch (InvalidCritterException ex)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Critter Exception");
            alert.showAndWait();
            return;

        }

    }

    /**
     * Events handler for the time step button
     * @param numSteps
     */
    private static void timeStepEventHandler(int numSteps)
    {
        for (int i = 0; i < numSteps; i++)
        {
            Critter.worldTimeStep();
        }
        Critter.displayWorld(Main.displayCanvas);
    }

    /**
     * Events handler for the make Critter buttons
     * @param type
     * @param quantity
     */
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
