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
	
	private TextField				comments;
	private ImageView				poster;
	
	private Button					fileButton;
	private FileChooser				fileChooser;
	
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
	public void setFileChooserWindow(Stage stage) {
		fileButton.setOnAction(
	            (final ActionEvent e) -> {
	                File file = fileChooser.showOpenDialog(stage);
	                if (file != null) {
	                    openFile(file);
	                }
	        });
	}
	
	//TODO
	// The controller calls this method when it adds a view.
	// Set up the nodes in the view with data accessed through the controller.
	public void	initialize()
	{
		
		
		
		// Widget Gallery, Slider
//		slider.setValue((Double)controller.get("myDouble"));

		// Widget Gallery, Spinner
//		spinner.getValueFactory().setValue((Integer)controller.get("myInt"));

		// Widget Gallery, Text Field
//		textField.setText((String)controller.get("myString"));
	}
	//TODO
	// The controller calls this method when it removes a view.
	// Unregister event and property listeners for the nodes in the view.
	public void	terminate()
	{
		// Widget Gallery, Slider
//		slider.valueProperty().removeListener(this::changeDecimal);

		// Widget Gallery, Spinner
//		spinner.valueProperty().removeListener(this::changeInteger);

		// Widget Gallery, Text Field
//		textField.setOnAction(null);
	}
	//TODO
	// The controller calls this method whenever something changes in the model.
	// Update the nodes in the view to reflect the change.
	public void	update(String key, Object value)
	{
		//System.out.println("update " + key + " to " + value);

//		if ("myDouble".equals(key))
//		{
//			slider.setValue((Double)value);
//		}
//		else if ("myInt".equals(key))
//		{
//			spinner.getValueFactory().setValue((Integer)value);
//		}
//		else if ("myString".equals(key))
//		{
//			textField.setText((String)value);
//		}
	}

	//**********************************************************************
	// Private Methods (Layout)
	//**********************************************************************
	
    final int CNTR = 0;
    final int RGHT = 1;
    
	//TODO
	private Pane	buildPane()
	{
		int col = 15;
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));
	    
	    int left = 0;
	    int cntr = 0;
	    int rght = 0;
	    
	    grid.add(createTextField(title, "Title", col), 
	    		CNTR, cntr++);
	    grid.add(createSlider(runtime, "Runtime", col, 0, 360, 0),
	    		RGHT, rght++);
	    grid.add(createSpinner(year, "Year", col, 1940, 2040, Calendar.getInstance().get(Calendar.YEAR), 1),
	    		CNTR, cntr++);
	    grid.add(createSlider(avgRev, "Average Review", col, 0, 10, 5),
	    		RGHT, rght++);
	    grid.add(createTextField(director, "Director", col),
	    		CNTR, cntr++);
	    grid.add(createSpinner(numRev, "Number of Reviews", col, 0, Integer.MAX_VALUE, 0, 1),
	    		RGHT, rght++);
		
	    grid.add(createColorAnimated(), CNTR, cntr++);
	    grid.add(createRating(), CNTR, cntr++);
	    grid.add(createAwards(), CNTR, cntr++);
	    
		grid.add(createGenres(), RGHT, rght++, 1, 3);
		rght += 2;

		VBox leftPane = new VBox();
		leftPane.getChildren().add(createPoster());
		leftPane.getChildren().add(createFileChooser());
		
		HBox top = new HBox();
		top.getChildren().add(leftPane);
		top.getChildren().add(grid);
		top.setPadding(new Insets(0,0,10,0));
		
		VBox pane = new VBox();
		pane.getChildren().add(top);
		pane.getChildren().add(createTextField(comments, "Comments", 3*col));
		
//		grid.add(createPoster(), LEFT, left++, 1, left+1);
//		left++;
//		grid.add(createFileChooser(), LEFT, left++);
		// TODO: wrap this is some kind of scroll bar
//		grid.add(createTextField(comments, "Comments", 3*col),
//				LEFT, cntr++, 3, 1);
		
		return pane;
	}

	//**********************************************************************
	// Private Methods (Widget Pane Creators)
	//**********************************************************************

	// Create a titled text field with the specified number of columns
	// Note that you will need to update the ActionListener to include
	// the 'toCreate' object
	private Pane createTextField(TextField toCreate, String name, int columnCount) {
		toCreate = new TextField();
		toCreate.setPrefColumnCount(columnCount);
		toCreate.setOnAction(actionHandler);
		return createTitledTopLeftPane(toCreate, name);
	}
	
	// Create a titled spinner with the specified min, max, initial, and step.
	// Add this object in changeInteger
	private Pane createSpinner(Spinner<Integer> toCreate, String name, int columnCount,
			int min, int max, int initial, int step)
	{
		toCreate = new Spinner<Integer>(min, max, initial, step);

		toCreate.setEditable(true);
		toCreate.getEditor().setPrefColumnCount(columnCount);

		toCreate.valueProperty().addListener(this::changeInteger);

		return createTitledTopLeftPane(toCreate, name);
	}
	
	// Create a titled slider with the specified min, max, and value.
	// other values auto-filled
	// Add this object in changeDouble
	private Pane createSlider(Slider toCreate, String name, int columnCount,
			double min, double max, double value)
	{
		return createSlider(toCreate, name, columnCount, min, max, value, 
				10, 2, true, true);
	}
	// Create a titled slider with the specified min, max, value, 
	// major tick unit and minor tick count.
	// other values auto-filled
	// Add this object in changeDouble
	private Pane createSlider(Slider toCreate, String name, int columnCount,
			double min, double max, double value,
			double majorTickUnit, int minorTickCount)
	{
		return createSlider(toCreate, name, columnCount, min, max, value, 
				majorTickUnit, minorTickCount, true, true);
	}
	// Create a titled slider with the specified min, max, value, 
	// major tick unit, minor tick count, and other settings
	// Add this object in changeDouble
	private Pane createSlider(Slider toCreate, String name, int columnCount,
			double min, double max, double value,
			double majorTickUnit, int minorTickCount,
			boolean showTickLabels, boolean showTickMarks)
	{
		toCreate = new Slider(min, max, value);

		toCreate.setOrientation(Orientation.HORIZONTAL);
		toCreate.setMajorTickUnit(majorTickUnit);
		toCreate.setMinorTickCount(minorTickCount);
		toCreate.setShowTickLabels(showTickLabels);
		toCreate.setShowTickMarks(showTickMarks);

		toCreate.valueProperty().addListener(this::changeDecimal);
		
		
		return createTitledTopLeftPane(toCreate, name);
	}
	
	int spacing = 7;
	private Pane createAwards() {
		VBox box = new VBox();
		box.setSpacing(spacing);
		createCheckBox(picture, "Picture", box);
		createCheckBox(directing, "Directing", box);
		createCheckBox(cinematography, "Cinematography", box);
		createCheckBox(acting, "Acting", box);
		return createTitledTopLeftPane(box, "Awards");
	}
	
	private Pane createGenres() {
		VBox box = new VBox();
		box.setSpacing(spacing);
		createCheckBox(action, "Action", box);
		createCheckBox(comedy, "Comedy", box);
		createCheckBox(documentary, "Documentary", box);
		createCheckBox(drama, "Drama", box);
		createCheckBox(fantasy, "Fantasy", box);
		createCheckBox(horror, "Horror", box);
		createCheckBox(romance, "Romance", box);
		createCheckBox(scifi, "Sci-Fi", box);
		createCheckBox(thriller, "Thriller", box);
		createCheckBox(western, "Western", box);
		return createTitledTopLeftPane(box, "Genre");
	}
	
	private Pane createRating() {
		VBox box = new VBox();
		box.setSpacing(spacing);
		createRadioButton(ratingG, "G", box);
		createRadioButton(ratingPG, "PG", box);
		createRadioButton(ratingPG13, "PG-13", box);
		createRadioButton(ratingR, "R", box);
		return createTitledTopLeftPane(box, "Rating");
	}
	
	private Pane createColorAnimated() {
		HBox hbox = new HBox();
		color = createCheckBox(color, "Color");
		color.setPadding(new Insets(0, 10, 0, 0));
		hbox.getChildren().add(color);
		createCheckBox(animated, "Animated", hbox);
		return hbox;
	}
	
	private void createCheckBox(CheckBox check, String name, Pane box) {
		check = new CheckBox(name);
		check.setOnAction(actionHandler);
		box.getChildren().add(check);
	}
	
	private CheckBox createCheckBox(CheckBox check, String name) {
		check = new CheckBox(name);
		check.setOnAction(actionHandler);
		return check;
	}
	
	private void createRadioButton(RadioButton radio, String name, Pane box) {
		radio = new RadioButton(name);
		radio.setOnAction(actionHandler);
		box.getChildren().add(radio);
	}
	
	private Node createPoster() {
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
	
	public static Pane createTitledTopLeftPane(Region region, String title) {
		StackPane sp = (StackPane) createTitledPane(region, title);
		sp.setAlignment(Pos.TOP_LEFT);
		return sp;
	}
	
	//**********************************************************************
	// Private Methods (Property Change Handlers)
	//**********************************************************************

	private void	changeDecimal(ObservableValue<? extends Number> observable,
								  Number oldValue, Number newValue)
	{
//		if (observable == slider.valueProperty())
//			controller.set("myDouble", newValue);
	}

	private void	changeInteger(ObservableValue<? extends Number> observable,
								  Number oldValue, Number newValue)
	{
		if (observable == year.valueProperty())
			controller.set("year", newValue);
	}
	
	
	private void openFile(File file) {
		Image i;
		try {
			i = new Image(file.toURI().toURL().toString());
			poster.setImage(i);
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

//			if (source == title)
//				controller.set("Title", textField.getText());
//			else if (source == fileButton) {
//				fileChooser.showOpenDialog(controller);
//			}
		}
	}
}

//******************************************************************************
