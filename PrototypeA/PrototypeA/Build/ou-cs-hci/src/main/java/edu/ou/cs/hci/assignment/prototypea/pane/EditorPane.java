//******************************************************************************
// Copyright (C) 2019 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Tue Jan 28 09:28:34 2020 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20190203 [weaver]:	Original file.
// 20190220 [weaver]:	Adapted from swingmvc to fxmvc.
//
//******************************************************************************
//
//******************************************************************************

package edu.ou.cs.hci.assignment.prototypea.pane;

import java.io.File;
import java.net.MalformedURLException;
//import java.lang.*;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.animation.*;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Callback;
import edu.ou.cs.hci.assignment.prototypea.Controller;
import edu.ou.cs.hci.resources.Resources;

//******************************************************************************

/**
 * The <CODE>EditorPane</CODE> class.
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class EditorPane extends AbstractPane
{
	//**********************************************************************
	// Private Class Members
	//**********************************************************************

	private static final String	NAME = "Editor";
	private static final String	HINT = "Movie Metadata Editor";

	//**********************************************************************
	// Private Class Members (Effects)
	//**********************************************************************

	private static final Font		FONT_LARGE =
		Font.font("Serif", FontPosture.ITALIC, 24.0);

	private static final Font		FONT_SMALL =
		Font.font("Serif", FontPosture.ITALIC, 18.0);

	//**********************************************************************
	// Private Members
	//**********************************************************************

	// Layout (a few widgets)
	private Pane avgRevTitle;
	private Pane runtimeTitle;
	
	private TextField				title;
	private Spinner<Integer>		year;
	private TextField				director;
	private Spinner<Integer>		numRev;
	private Slider					runtime;
	private Slider					avgRev;
	
	private CheckBox				color;
	private CheckBox				animated;
	
	private CheckBox				picture;
	private CheckBox				directing;
	private CheckBox				cinematography;
	private CheckBox				acting;
	
	private CheckBox				action;
	private CheckBox				comedy;
	private CheckBox				documentary;
	private CheckBox				drama;
	private CheckBox				fantasy;
	private CheckBox				horror;
	private CheckBox				romance;
	private CheckBox				scifi;
	private CheckBox				thriller;
	private CheckBox				western;
	
	private RadioButton				ratingG;
	private RadioButton				ratingPG;
	private RadioButton				ratingPG13;
	private RadioButton				ratingR;
	
	private ImageView				poster;
	private Button					fileButton;
	private FileChooser				fileChooser;
	private TextField				path;
	private TextArea				summary;
	private TextArea				comments;

	
	// Handlers
	private final ActionHandler	actionHandler;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public EditorPane(Controller controller)
	{
		super(controller, NAME, HINT);
		
		actionHandler = new ActionHandler();

		setBase(buildPane());
	}

	//**********************************************************************
	// Public Methods (Controller)
	//**********************************************************************
	
	// this is necessary for the file chooser button to work - it needs a stage object
	public void setFileChooserWindow(Stage stage) {
		fileButton.setOnAction(
	            (final ActionEvent e) -> {
	                File file = fileChooser.showOpenDialog(stage);
	                if (file != null) {
	                    openFile(file);
	                }
	        });
	}
	
	// The controller calls this method when it adds a view.
	// Set up the nodes in the view with data accessed through the controller.
	public void	initialize()
	{
		title.setText(
				(String) controller.get("Title"));
		year.getValueFactory().setValue(
				(Integer) controller.get("Year"));
		director.setText(
				(String) controller.get("Director"));
		runtime.setValue(
				(Double)controller.get("Runtime"));
		updateTitledPane(runtimeTitle, String.format("Runtime: %3.0f min", runtime.getValue()));
		avgRev.setValue(
				(Double)controller.get("Average Review"));
		updateTitledPane(avgRevTitle, String.format("Average Review: %2.1f", avgRev.getValue()));
		numRev.getValueFactory().setValue(
				(Integer)controller.get("Number of Reviews"));
		
		color.setSelected(
				(Boolean)controller.get("Color"));
		animated.setSelected(
				(Boolean)controller.get("Animated"));
		
		ratingG.setSelected(
				(Boolean)controller.get("Rating G"));
		ratingPG.setSelected(
				(Boolean)controller.get("Rating PG"));
		ratingPG13.setSelected(
				(Boolean)controller.get("Rating PG-13"));
		ratingR.setSelected(
				(Boolean)controller.get("Rating R"));
		
		picture.setSelected(
				(Boolean)controller.get("Picture"));
		directing.setSelected(
				(Boolean)controller.get("Directing"));
		cinematography.setSelected(
				(Boolean)controller.get("Cinematography"));
		acting.setSelected(
				(Boolean)controller.get("Acting"));
		
		action.setSelected(
				(Boolean)controller.get("Action"));
		comedy.setSelected(
				(Boolean)controller.get("Comedy"));
		documentary.setSelected(
				(Boolean)controller.get("Documentary"));
		drama.setSelected(
				(Boolean)controller.get("Drama"));
		fantasy.setSelected(
				(Boolean)controller.get("Fantasy"));
		horror.setSelected(
				(Boolean)controller.get("Horror"));
		romance.setSelected(
				(Boolean)controller.get("Romance"));
		scifi.setSelected(
				(Boolean)controller.get("Sci-Fi"));
		thriller.setSelected(
				(Boolean)controller.get("Thriller"));
		western.setSelected(
				(Boolean)controller.get("Western"));
		
		try {
			Image i = new Image(
					(String) controller.get("Poster File Path"));
			poster.setImage(i);
		} catch (Exception e) {
			// do nothing
		}
				
		path.setText(
				(String) controller.get("Poster File Path"));
		comments.setText(
				(String) controller.get("Comments"));
		summary.setText(
				(String) controller.get("Summary"));

	}
	// The controller calls this method when it removes a view.
	// Unregister event and property listeners for the nodes in the view.
	public void	terminate()
	{
		title.setOnAction(null);
		year.valueProperty().removeListener(this::changeInteger);
		director.setOnAction(null);
		runtime.valueProperty().removeListener(this::changeDecimal);
		avgRev.valueProperty().removeListener(this::changeDecimal);
		numRev.valueProperty().removeListener(this::changeInteger);
		
		color.setOnAction(null);
		animated.setOnAction(null);
		
		ratingG.setOnAction(null);
		ratingPG.setOnAction(null);
		ratingPG13.setOnAction(null);
		ratingR.setOnAction(null);
		
		picture.setOnAction(null);
		directing.setOnAction(null);
		cinematography.setOnAction(null);
		acting.setOnAction(null);
		
		action.setOnAction(null);
		comedy.setOnAction(null);
		documentary.setOnAction(null);
		drama.setOnAction(null);
		fantasy.setOnAction(null);
		horror.setOnAction(null);
		romance.setOnAction(null);
		scifi.setOnAction(null);
		thriller.setOnAction(null);
		western.setOnAction(null);
		
		comments.setOnKeyTyped(null);
		fileButton.setOnAction(null);
		summary.setOnKeyTyped(null);
	}
	
	// The controller calls this method whenever something changes in the model.
	// Update the nodes in the view to reflect the change.
	public void	update(String key, Object value)
	{
		System.out.println("update " + key + " to " + value);
		
		if (key.equals("Title"))
			title.setText((String) value);
		else if (key.equals("Year"))
			year.getValueFactory().setValue((Integer) value);
		else if (key.equals("Director"))
			director.setText((String) value);
		else if (key.equals("Runtime")) {
			runtime.setValue((Double) value);
			updateTitledPane(runtimeTitle, String.format("Runtime: %3.0f min", value));
		}
		else if (key.equals("Average Review")) {
			avgRev.setValue((Double) value);
			updateTitledPane(avgRevTitle, String.format("Average Review: %2.1f", value));
		}
		else if (key.equals("Year"))
			numRev.getValueFactory().setValue((Integer) value);
		else if (key.equals("Color"))
			color.setSelected((Boolean) value);
		else if (key.equals("Animated"))
			animated.setSelected((Boolean) value);
		else if (key.equals("Rating G"))
			ratingG.setSelected((Boolean) value);
		else if (key.equals("Rating PG"))
			ratingPG.setSelected((Boolean) value);
		else if (key.equals("Rating PG-13"))
			ratingPG13.setSelected((Boolean) value);
		else if (key.equals("Rating R"))
			ratingR.setSelected((Boolean) value);
		else if (key.equals("Picture"))
			picture.setSelected((Boolean) value);
		else if (key.equals("Directing"))
			directing.setSelected((Boolean) value);
		else if (key.equals("Cinematography"))
			cinematography.setSelected((Boolean) value);
		else if (key.equals("Acting"))
			acting.setSelected((Boolean) value);
		else if (key.equals("Action"))
			action.setSelected((Boolean) value);
		else if (key.equals("Comedy"))
			comedy.setSelected((Boolean) value);
		else if (key.equals("Documentary"))
			documentary.setSelected((Boolean) value);
		else if (key.equals("Drama"))
			drama.setSelected((Boolean) value);
		else if (key.equals("Fantasy"))
			fantasy.setSelected((Boolean) value);
		else if (key.equals("Horror"))
			horror.setSelected((Boolean) value);
		else if (key.equals("Romance"))
			romance.setSelected((Boolean) value);
		else if (key.equals("Sci-Fi"))
			scifi.setSelected((Boolean) value);
		else if (key.equals("Thriller"))
			thriller.setSelected((Boolean) value);
		else if (key.equals("Western"))
			western.setSelected((Boolean) value);
		else if (key.equals("Comments"))
			comments.setText((String) value);
		else if (key.equals("Summary"))
			summary.setText((String) value);
		else if (key.equals("Poster File Path")) {
			path.setText((String) value);
			try {
				Image i = new Image(
						(String) controller.get("Poster File Path"));
				poster.setImage(i);
			} catch (Exception e) {
				// do nothing
			}
		}
	}

	//**********************************************************************
	// Private Methods (Layout)
	//**********************************************************************
	
    final int CNTR = 0;
    final int RGHT = 1;
    
	
	private Pane	buildPane()
	{
		int col = 16;
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));
	    
	    int left = 0;
	    int cntr = 0;
	    int rght = 0;
	    
	    title = createTextField(col);
	    grid.add(createTitledTopLeftPane(title, "Title"), CNTR, cntr++);
	    
	    runtime = createSlider(col, 0, 360, 0, 60, 6, true, true);
	    runtimeTitle = createTitledTopLeftPane(runtime, "Runtime: 0");
	    grid.add(runtimeTitle, RGHT, rght++);
	    
	    year = createSpinner(col, 1940, 2040, Calendar.getInstance().get(Calendar.YEAR), 1);
	    grid.add(createTitledTopLeftPane(year, "Year"), CNTR, cntr++);
	    
	    avgRev = createSlider(col, 0, 10, 5, 1, 3, true, true);
	    avgRevTitle = createTitledTopLeftPane(avgRev, "Average Review: 5.0");
	    grid.add(avgRevTitle, RGHT, rght++);
	    
	    director = createTextField(col);
	    grid.add(createTitledTopLeftPane(director, "Director"), CNTR, cntr++);
	    
	    numRev = createSpinner(col, 0, Integer.MAX_VALUE, 0, 1);
	    grid.add(createTitledTopLeftPane(numRev, "Number of Reviews"), RGHT, rght++);
		
	    grid.add(createColorAnimated(), CNTR, cntr++);
	    grid.add(createRating(), CNTR, cntr++);
	    grid.add(createAwards(), CNTR, cntr++);
	    
		grid.add(createGenres(), RGHT, rght++, 1, 3);
		rght += 2;

		VBox leftPane = new VBox();
		leftPane.getChildren().add(createPoster());
		leftPane.getChildren().add(createFileChooser());

		path = createTextField(col);
		path.setPrefHeight(4);
		Pane p1 = createTitledTopLeftPane(path, "Poster File Path...");
		p1.setPrefHeight(4);
		leftPane.getChildren().add(p1);
		
		summary = new TextArea();
		leftPane.getChildren().add(createTitledTopLeftPane(summary, "Summary", col, 10));
		
		HBox top = new HBox();
		top.getChildren().add(leftPane);
		top.getChildren().add(grid);
		top.setPadding(new Insets(0,0,10,0));
		
		VBox pane = new VBox();
		pane.getChildren().add(top);
		
		comments = new TextArea();
		pane.getChildren().add(createTitledTopLeftPane(comments, "Comments", col*3, 10));
			
		return pane;
	}

	//**********************************************************************
	// Private Methods (Widget Pane Creators)
	//**********************************************************************

	// Create a titled text field with the specified number of columns
	// Note that you will need to update the ActionListener to include
	// the 'toCreate' object
	private TextField createTextField(int columnCount) {
		TextField toCreate = new TextField();
		toCreate.setPrefColumnCount(columnCount);
		toCreate.setOnAction(actionHandler);
		return toCreate;
	}
	
	private TextArea createTextArea(int columns, int rows) {
		TextArea toCreate = new TextArea();
		toCreate.setPrefColumnCount(columns);
		toCreate.setPrefRowCount(rows);
		return toCreate;
	}
	
	// Create a titled spinner with the specified min, max, initial, and step.
	// Add this object in changeInteger
	private Spinner<Integer> createSpinner(int columnCount, int min, int max, int initial, int step)
	{
		Spinner<Integer> toCreate = new Spinner<Integer>(min, max, initial, step);

		toCreate.setEditable(true);
		toCreate.getEditor().setPrefColumnCount(columnCount);

		toCreate.valueProperty().addListener(this::changeInteger);
		return toCreate;
//		return createTitledTopLeftPane(toCreate, name);
	}
	
	// Create a titled slider with the specified min, max, and value.
	// other values auto-filled
	// Add this object in changeDouble
	private Slider createSlider(int columnCount, double min, double max, double value)
	{
		return createSlider(columnCount, min, max, value, 10, 2, true, true);
	}
	// Create a titled slider with the specified min, max, value, 
	// major tick unit, minor tick count, and other settings
	// Add this object in changeDouble
	private Slider createSlider(int columnCount,
			double min, double max, double value,
			double majorTickUnit, int minorTickCount,
			boolean showTickLabels, boolean showTickMarks)
	{
		Slider toCreate = new Slider(min, max, value);

		toCreate.setOrientation(Orientation.HORIZONTAL);
		toCreate.setMajorTickUnit(majorTickUnit);
		toCreate.setMinorTickCount(minorTickCount);
		toCreate.setShowTickLabels(showTickLabels);
		toCreate.setShowTickMarks(showTickMarks);

		toCreate.valueProperty().addListener(this::changeDecimal);
		
		return toCreate;
//		return createTitledTopLeftPane(toCreate, name);
	}
	
	int spacing = 7;
	private Pane createAwards() {
		VBox box = new VBox();
		box.setSpacing(spacing);
		picture 	= createCheckBox("Picture", box);
		directing 	= createCheckBox("Directing", box);
		cinematography = createCheckBox("Cinematography", box);
		acting 		= createCheckBox("Acting", box);
		return createTitledTopLeftPane(box, "Awards");
	}
	
	private Pane createGenres() {
		VBox box = new VBox();
		box.setSpacing(spacing);
		action 		= createCheckBox("Action", box);
		comedy 		= createCheckBox("Comedy", box);
		documentary = createCheckBox("Documentary", box);
		drama 		= createCheckBox("Drama", box);
		fantasy 	= createCheckBox("Fantasy", box);
		horror 		= createCheckBox("Horror", box);
		romance 	= createCheckBox("Romance", box);
		scifi 		= createCheckBox("Sci-Fi", box);
		thriller 	= createCheckBox("Thriller", box);
		western 	= createCheckBox("Western", box);
		return createTitledTopLeftPane(box, "Genre");
	}
	
	private Pane createRating() {
		VBox box = new VBox();
		box.setSpacing(spacing);
		ratingG 	= createRadioButton("G", box);
		ratingPG 	= createRadioButton("PG", box);
		ratingPG13 	= createRadioButton("PG-13", box);
		ratingR 	= createRadioButton("R", box);
		return createTitledTopLeftPane(box, "Rating");
	}
	
	private Pane createColorAnimated() {
		HBox hbox = new HBox();
		color = createCheckBox(color, "Color");
		color.setPadding(new Insets(0, 10, 0, 0));
		hbox.getChildren().add(color);
		animated = createCheckBox("Animated", hbox);
		return hbox;
	}
	
	private CheckBox createCheckBox(String name, Pane box) {
		CheckBox check = new CheckBox(name);
		check.setOnAction(actionHandler);
		box.getChildren().add(check);
		return check;
	}
	
	private CheckBox createCheckBox(CheckBox check, String name) {
		check = new CheckBox(name);
		check.setOnAction(actionHandler);
		return check;
	}
	
	private RadioButton createRadioButton(String name, Pane box) {
		RadioButton radio = new RadioButton(name);
		radio.setOnAction(actionHandler);
		box.getChildren().add(radio);
		return radio;
	}
	
	private ImageView createPoster() {
		int x = 200;
		int y = (int) (1.5*(double)x);
		poster = new ImageView();
		poster.setFitWidth(x);
		poster.setFitHeight(y);
		poster.setPreserveRatio(false);
		
		return poster;
	}
	
	private Node createFileChooser() {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Select Poster...");
		fileButton = new Button("Select Poster...");
		fileButton.setOnAction(actionHandler);
		return fileButton;
	}
	
	public static Pane createTitledTopLeftPane(Region region, String title, double width, double height) {
		StackPane sp = (StackPane) createTitledPane(region, title);
		sp.resize(width, height);
		sp.setAlignment(Pos.TOP_LEFT);
		return sp;
	}
	
	public static Pane createTitledTopLeftPane(Region region, String title) {
		StackPane sp = (StackPane) createTitledPane(region, title);
		sp.setAlignment(Pos.TOP_LEFT);
		return sp;
	}
	
	public static void updateTitledPane(Pane pane, String title)
	{
		TitledPane p = (TitledPane) pane.getChildren().get(0);
		p.setText(title);
	}
	
	//**********************************************************************
	// Private Methods (Property Change Handlers)
	//**********************************************************************

	private void	changeDecimal(ObservableValue<? extends Number> observable,
								  Number oldValue, Number newValue)
	{
		if (observable == runtime.valueProperty()) {
			controller.set("Runtime", newValue);
			updateTitledPane(runtimeTitle, String.format("Runtime: %3.0f", newValue));
		}
		else if (observable == avgRev.valueProperty()) { 
			controller.set("Average Review", newValue);
			updateTitledPane(avgRevTitle, String.format("Average Review: %1.1f", newValue));
		}
	}

	private void	changeInteger(ObservableValue<? extends Number> observable,
								  Number oldValue, Number newValue)
	{
		if (observable == year.valueProperty())
			controller.set("Year", newValue);
		else if (observable == numRev.valueProperty())
			controller.set("Number of Reviews", newValue);
	}
	
	
	private void openFile(File file) {
		Image i;
		try {
			i = new Image(file.toURI().toURL().toString());
			poster.setImage(i);
			path.setText(file.getAbsolutePath());
		} catch (MalformedURLException e) {
			System.err.println("ERROR: malformed url");
		}
	}
	//**********************************************************************
	// Inner Classes (Event Handlers)
	//**********************************************************************

	private final class ActionHandler
		implements EventHandler<ActionEvent>
	{
		public void	handle(ActionEvent e)
		{
			Object	source = e.getSource();

			if (source == title)
				controller.set("Title", title.getText());
//			else if (source == fileButton) {
//				fileChooser.showOpenDialog(controller);
//			}
			else if (source == color) 
				controller.set("Color", color.isSelected());
			else if (source == animated)
				controller.set("Animated", color.isSelected());
			
			else if (source == ratingG) {
				controller.set("Rating G", true);
				controller.set("Rating PG", false);
				controller.set("Rating PG-13", false);
				controller.set("Rating R", false);
			}
			else if (source == ratingPG) {
				controller.set("Rating G", false);
				controller.set("Rating PG", true);
				controller.set("Rating PG-13", false);
				controller.set("Rating R", false);
			}
			else if (source == ratingPG13) {
				controller.set("Rating G", false);
				controller.set("Rating PG", false);
				controller.set("Rating PG-13", true);
				controller.set("Rating R", false);
			}
			else if (source == ratingR) {
				controller.set("Rating G", false);
				controller.set("Rating PG", false);
				controller.set("Rating PG-13", false);
				controller.set("Rating R", true);
			}
			else if (source == picture)
				controller.set("Picture", picture.isSelected());
			else if (source == directing)
				controller.set("Directing", directing.isSelected());
			else if (source == cinematography)
				controller.set("Cinematography", cinematography.isSelected());
			else if (source == acting)
				controller.set("Acting", acting.isSelected());
			
			else if (source == action)
				controller.set("Action", action.isSelected());
			else if (source == comedy)
				controller.set("Comedy", comedy.isSelected());
			else if (source == documentary)
				controller.set("Documentary", documentary.isSelected());
			else if (source == drama)
				controller.set("Drama", drama.isSelected());
			else if (source == fantasy)
				controller.set("Fantasy", fantasy.isSelected());
			else if (source == horror)
				controller.set("Horror", horror.isSelected());
			else if (source == romance)
				controller.set("Romance", romance.isSelected());
			else if (source == scifi)
				controller.set("Sci-Fi", scifi.isSelected());
			else if (source == thriller)
				controller.set("Thriller", thriller.isSelected());
			else if (source == western)
				controller.set("Western", western.isSelected());
		}
	}
}

//******************************************************************************
