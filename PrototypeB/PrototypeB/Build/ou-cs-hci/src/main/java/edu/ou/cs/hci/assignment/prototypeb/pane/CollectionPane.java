//******************************************************************************
// Copyright (C) 2019-2020 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Fri Feb 14 12:15:51 2020 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20190203 [weaver]:	Original file.
// 20190220 [weaver]:	Adapted from swingmvc to fxmvc.
// 20200212 [weaver]:	Overhauled for new PrototypeB in Spring 2020.
//
//******************************************************************************
//
//******************************************************************************

package edu.ou.cs.hci.assignment.prototypeb.pane;

//import java.lang.*;
import java.util.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.util.Callback;
import javafx.util.converter.*;
import edu.ou.cs.hci.assignment.prototypeb.*;
import edu.ou.cs.hci.resources.Resources;
import javafx.beans.property.*;
//******************************************************************************

/**
 * The <CODE>CollectionPane</CODE> class.
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class CollectionPane extends AbstractPane
{
	//**********************************************************************
	// Private Class Members
	//**********************************************************************

	private static final String	NAME = "Collection";
	private static final String	HINT = "Movie Collection Browser";

	//**********************************************************************
	// Private Class Members (Layout)
	//**********************************************************************

	private static final double	W = 32;		// Item icon width
	private static final double	H = W * 1.5;	// Item icon height

	private static final Insets	PADDING =
		new Insets(40.0, 20.0, 40.0, 20.0);

	//**********************************************************************
	// Private Members
	//**********************************************************************

	// Data
	private final List<String>			gdata;		// Genre strings
	private final List<String>			rdata;		// Rating strings
	private final List<List<String>>	mdata;		// Movie attributes

	// Collection
	private final List<Movie>			movies;	// Movie objects

	// Layout
	private TableView<Movie>			table;
	private SelectionModel<Movie>		smodel;

	// Add members for your summary widgets here...
	private Label 						sTitleRow1;
	private Label						sTitleRow2;
	private ImageView 					sPoster;
	private Label 						sYear;
	private Label 						sGenre;
	private Label 						sDirector;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public CollectionPane(Controller controller)
	{
		super(controller, NAME, HINT);

		// Load data sets from hardcoded file locations
		gdata = Resources.getLines("data/genres.txt");
		rdata = Resources.getLines("data/ratings.txt");
		mdata = Resources.getCSVData("data/movies.csv");

		// Convert the raw movie data into movie objects
		movies = new ArrayList<Movie>();

		for (List<String> item : mdata)
			movies.add(new Movie(item));

		// Construct the pane
		setBase(buildPane());
	}

	//**********************************************************************
	// Public Methods (Controller)
	//**********************************************************************

	// The controller calls this method when it adds a view.
	// Set up the nodes in the view with data accessed through the controller.
	public void	initialize()
	{
		smodel.selectedIndexProperty().addListener(this::changeIndex);

		int	index = (Integer)controller.get("selectedMovieIndex");

		smodel.select(index);
		
		if (index > 0 && index < movies.size()) {
		// Initialize your summary widgets here, using model properties...
		sTitleRow1.setText(movies.get(index).getTitle());
		sPoster.setImage(new Image(movies.get(index).getImage(), W*10, H*10, false, true));
		sYear.setText(""+movies.get(index).getYear());
		sDirector.setText(movies.get(index).getDirctor());
		
		int g = movies.get(index).getGenre();
		int n = 0;
		String data = "";
		for (String genre : gdata) {
			if ((g & (1 << n)) != 0)
				data += genre + ", ";
			++n;
		}
		data = data.substring(0, data.length() - 2);
		
		sGenre.setText(data);
		}
	}

	// The controller calls this method when it removes a view.
	// Unregister event and property listeners for the nodes in the view.
	public void	terminate()
	{
		smodel.selectedIndexProperty().removeListener(this::changeIndex);

		// Terminate your summary widgets here...
		
	}

	// The controller calls this method whenever something changes in the model.
	// Update the nodes in the view to reflect the change.
	public void	update(String key, Object value)
	{
		if ("selectedMovieIndex".equals(key))
		{
			int	index = (Integer)value;
			Movie	movie = movies.get(index);

			smodel.select(index);

			// Update your summary widgets here, using movie attributes...
			// 
			String t = movie.getTitle();
			String[] tt = new String[2];
			int i = 0;
			// if the title is (probably) to long to fit on one line, split it into two
			if (t.length() > 16) {
				// split at whitespace
				// +1 moves the space to the top line
				i = t.lastIndexOf(" ", 16) + 1;
				// -1 goes to 0 because of +1
				if (i == 0) {
					// uh oh, no whitespace - hard split
					i = 16;
					
				}
				tt[0] = t.substring(0, i);
				tt[1] = t.substring(i, t.length());
				sTitleRow1.setText(tt[0]);
				sTitleRow2.setText(tt[1]);
			}
			else {
				sTitleRow1.setText("");
				sTitleRow2.setText(t);
			}
			
			
			String icon = "edu/ou/cs/hci/resources/example/fx/icon/";
			sPoster.setImage(new Image(icon + movie.getImage(), W*5, H*5, false, true));
			sYear.setText(""+movie.getYear());
			sDirector.setText(movie.getDirctor());
			
			int g = movie.getGenre();
			int n = 0;
			String data = "";
			for (String genre : gdata) {
				if ((g & (1 << n)) != 0)
					data += genre + ", ";
				++n;
			}
			data = data.substring(0, data.length() - 2);
			
			sGenre.setText(data);
			
		}
	}

	//**********************************************************************
	// Private Methods (Layout)
	//**********************************************************************

	private Pane	buildPane()
	{
		Node	bregion = buildTableView();
		Node	tregion = buildCoverFlow();
		Node	lregion = buildLaterView();
		Node	rregion = buildMovieView();

		// Create a split pane to share space between the cover pane and table
		SplitPane	splitPane = new SplitPane();

		splitPane.setOrientation(Orientation.VERTICAL);
		splitPane.setDividerPosition(0, 0.1);	// Put divider at 50% T-to-B

		splitPane.getItems().add(tregion);		// Cover flow at the top...
		splitPane.getItems().add(bregion);		// ...table view at the bottom

//		StackPane	lpane = new StackPane(lregion);
//		StackPane	rpane = new StackPane(rregion);

		return new BorderPane(splitPane, null, rregion, null, lregion);
	}

	private TableView<Movie>	buildTableView()
	{
		// Create the table and grab its selection model
		table = new TableView<Movie>();
		smodel = table.getSelectionModel();

		// Set up some helpful stuff including single selection mode
		table.setEditable(true);
		table.setPlaceholder(new Text("No Data!"));
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		// Add columns for title and image
		table.getColumns().add(buildTitleColumn());
		table.getColumns().add(buildImageColumn());

		// TODONE #8: Uncomment these to add columns for your three attributes.
		table.getColumns().add(buildGenreColumn());
		table.getColumns().add(buildYearColumn());
		table.getColumns().add(buildDirectorColumn());

		// Put the movies into an ObservableList to use as the table model
		table.setItems(FXCollections.observableArrayList(movies));

		return table;
	}

	private Node	buildCoverFlow()
	{
		Label	label = new Label("this space reserved for cover flow (later)");

		label.setPadding(PADDING);

		return label;
	}

	private Node	buildLaterView()
	{
		Label	label = new Label("saving for later");

		label.setPadding(PADDING);

		return label;
	}

	// TODO #9: Build the layout for your movie summary here, showing the title,
	// image, and your three attributes. For any widgets you use, add members
	// and/or code to initialize(), terminate(), and update() above, as needed.
	// Keep in mind that the movie summary is meant for display, not editing.
	private Node	buildMovieView()
	{
		// The label is just a placeholder. Replace it with your own widgets!
		Insets pad = new Insets(10, 0, 10, 0);
		Font fBig = new Font(20);
		VBox pane = new VBox();
		
		sTitleRow1 = new Label("");
		sTitleRow1.setFont(fBig);
		sTitleRow1.setWrapText(false);
		sTitleRow1.setMaxWidth(150);
		sTitleRow1.setTextOverrun(OverrunStyle.CLIP);

		pane.getChildren().add(sTitleRow1);
		
		sTitleRow2 = new Label("Title");
		sTitleRow2.setFont(fBig);
		sTitleRow2.setWrapText(false);
		sTitleRow2.setMaxWidth(150);
		sTitleRow1.setTextOverrun(OverrunStyle.CLIP);

		pane.getChildren().add(sTitleRow2);
		
		sPoster = new ImageView();
		pane.getChildren().add(sPoster);
		
		sYear = new Label("Year");
		sYear.setPadding(pad);
		pane.getChildren().add(sYear);
		
		sGenre = new Label("Genre");
		sGenre.setPadding(pad);
		pane.getChildren().add(sGenre);
		
		sDirector = new Label("Director");
		sDirector.setPadding(pad);
		pane.getChildren().add(sDirector);
		
		pane.setPadding(PADDING);
		pane.setMinWidth(175);
//		pane.setPrefSize(100, 200);
		
		return pane;
	}

	//**********************************************************************
	// Private Methods (Table Columns)
	//**********************************************************************

	// This TableColumn displays titles, and allows editing.
	private TableColumn<Movie, String>	buildTitleColumn()
	{
		TableColumn<Movie, String>	column =
			new TableColumn<Movie, String>("Title");

		column.setEditable(true);
		column.setPrefWidth(250);
		column.setCellValueFactory(
			new PropertyValueFactory<Movie, String>("title"));
		column.setCellFactory(new TitleCellFactory());

		// Edits in this column update movie titles
		column.setOnEditCommit(new TitleEditHandler());

		return column;
	}

	// This TableColumn displays images, and does not allow editing.
	private TableColumn<Movie, String>	buildImageColumn()
	{
		TableColumn<Movie, String>	column =
			new TableColumn<Movie, String>("Image");

		column.setEditable(false);
		column.setPrefWidth(W + 8.0);
		column.setCellValueFactory(
			new PropertyValueFactory<Movie, String>("image"));
		column.setCellFactory(new ImageCellFactory());

		return column;
	}

	// TODO #7: Complete the TableColumn methods for your three attributes.
	// You must adapt the code to the column's attribute type in each case.
	
	// This table column shows genres, and allows editing
	private TableColumn<Movie, String>	buildGenreColumn()
	{
		TableColumn<Movie, String>	column =
			new TableColumn<Movie, String>("Genre");

		column.setEditable(true);
		column.setPrefWidth(200);
		column.setCellValueFactory(
			new Callback<TableColumn.CellDataFeatures<Movie, String>, ObservableValue<String>>() 
		{
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Movie, String> p) {
				int g = p.getValue().getGenre();
				int n = 0;
				String data = "";
				for (String genre : gdata) {
					if ((g & (1 << n)) != 0)
						data += genre + ", ";
					++n;
				}
				data = data.substring(0, data.length() - 2);
				return new SimpleStringProperty(data);
			}
		});
		
		column.setCellFactory(new GenreCellFactory());

		// Edits in this column update movie titles
		column.setOnEditCommit(new GenreEditHandler());

		return column;
	}
	

	private TableColumn<Movie, Integer>	buildYearColumn()
	{
		TableColumn<Movie, Integer>	column =
			new TableColumn<Movie, Integer>("Year");

		column.setEditable(true);
		column.setPrefWidth(W+8.0);
		column.setCellValueFactory(
			new PropertyValueFactory<Movie, Integer>("year"));
		column.setCellFactory(new YearCellFactory());

		column.setOnEditCommit(new YearEditHandler());

		return column;
	}

	private TableColumn<Movie, String>	buildDirectorColumn()
	{
		TableColumn<Movie, String>	column =
			new TableColumn<Movie, String>("Director");

		column.setEditable(true);
		column.setPrefWidth(100);
		column.setCellValueFactory(
			new PropertyValueFactory<Movie, String>("director"));
		column.setCellFactory(new DirectorCellFactory());

		column.setOnEditCommit(new DirectorEditHandler());

		return column;
	}

	//**********************************************************************
	// Private Methods (Change Handlers)
	//**********************************************************************

	private void	changeIndex(ObservableValue<? extends Number> observable,
								Number oldValue, Number newValue)
	{
		int	index = (Integer)newValue;

		controller.set("selectedMovieIndex", index);
	}

	//**********************************************************************
	// Inner Classes (Cell Factories)
	//**********************************************************************

	// This CellFactory creates Cells for the title column in the table.
	private final class TitleCellFactory
		implements Callback<TableColumn<Movie, String>,
							TableCell<Movie, String>>
	{
		public TableCell<Movie, String>	call(TableColumn<Movie, String> v)
		{
			return new TitleCell();
		}
	}

	// This CellFactory creates Cells for the image column in the table.
	private final class ImageCellFactory
		implements Callback<TableColumn<Movie, String>,
							TableCell<Movie, String>>
	{
		public TableCell<Movie, String>	call(TableColumn<Movie, String> v)
		{
			return new ImageCell();
		}
	}

	// TODO #6: Complete the CellFactory classes for your three attributes.
	// You must adapt the code to the column's attribute type in each case.

	private final class GenreCellFactory
		implements Callback<TableColumn<Movie, String>,
							TableCell<Movie, String>>
	{
		public TableCell<Movie, String>	call(TableColumn<Movie, String> v)
		{
			return new GenreCell();
		}
	}
	private final class YearCellFactory
		implements Callback<TableColumn<Movie, Integer>,
							TableCell<Movie, Integer>>
	{
		public TableCell<Movie, Integer>	call(TableColumn<Movie, Integer> v)
		{
			return new YearCell();
		}
	}

	private final class DirectorCellFactory
		implements Callback<TableColumn<Movie, String>,
							TableCell<Movie, String>>
	{
		public TableCell<Movie, String>	call(TableColumn<Movie, String> v)
		{
			return new DirectorCell();
		}
	}

	//**********************************************************************
	// Inner Classes (Cells)
	//**********************************************************************

	// Each Cell determines the contents of one row/column intersection in the
	// table. The code for each one maps its attribute object into text and/or
	// graphic in different ways.

	// To modify the styling of cells, use methods in the ancestor classes of
	// javafx.scene.control.TableCell, especially javafx.scene.control.Labeled
	// and javafx.scene.layout.Region. (You can also edit View.css. It currently
	// sets background-color and text-fill properties for entire rows of cells.)

	// To make a cell editable...although only shallowly at this point:
	// Extend a javafx.scene.control.cell.*TableCell class to allow editing.
	// Match a javafx.util.converter.*StringConverter to each attribute type.

	// This TableCell displays the title, and allows editing in a TextField.
	private final class TitleCell
		extends TextFieldTableCell<Movie, String>
	{
		public TitleCell()
		{
			super(new DefaultStringConverter());	// Since values are Strings
		}

		public void	updateItem(String value, boolean isEmpty)
		{
			super.updateItem(value, isEmpty);		// Prepare for setup

			if (isEmpty || (value == null))		// Handle special cases
			{
				setText(null);
				setGraphic(null);

				return;
			}

			// This cell shows the value of the title attribute as simple text.
			// If the title is too long, an ellipsis is inserted in the middle.
			String	title = value;

			setText(title);
			setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
			setGraphic(null);
		}
	}

	// This TableCell displays the image, and doesn't allow editing.
	private final class ImageCell
		extends TableCell<Movie, String>
	{
		public void	updateItem(String value, boolean isEmpty)
		{
			super.updateItem(value, isEmpty);		// Prepare for setup

			if (isEmpty || (value == null))		// Handle special cases
			{
				setText(null);
				setGraphic(null);
				setAlignment(Pos.CENTER);

				return;
			}

			// This cell uses the value of the posterFileName attribute
			// to show an image loaded from resources/example/fx/icon.
			String		posterFileName = value;
			ImageView	image = createFXIcon(posterFileName, W, H);

			setText(null);
			setGraphic(image);
			setAlignment(Pos.CENTER);
		}
	}

	// TODO #5: Complete the Cell classes for your three attributes.
	// You must adapt the code to the column's attribute type in each case.
	// Allow editing (shallowly) in at least one of the three columns.

	private final class GenreCell
		extends TextFieldTableCell<Movie, String>
	{
		public GenreCell()
		{
			super(new DefaultStringConverter());
		}
	
		public void	updateItem(String value, boolean isEmpty)
		{
			super.updateItem(value, isEmpty);		// Prepare for setup
	
			if (isEmpty || value == null)		// Handle special cases
			{
				setText(null);
				setGraphic(null);
				return;
			}
	
			// This cell shows the value of the genre attribute as plaintext
			setText(value);
			setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
			setGraphic(null);
		}
	}
	private final class YearCell
		extends TextFieldTableCell<Movie, Integer>
	{
		public YearCell()
		{
			super();	// Since values are Strings
		}
	
		public void	updateItem(int value, boolean isEmpty)
		{
			super.updateItem(value, isEmpty);		// Prepare for setup
			
			if (isEmpty)		// Handle special cases
			{
				setText(null);
				setGraphic(null);
				return;
			}

			// This cell shows the value of the genre attribute as plaintext
			setText(""+value);
			setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
			setGraphic(null);
		}
	}
	private final class DirectorCell
		extends TextFieldTableCell<Movie, String>
	{
		public DirectorCell()
		{
			super(new DefaultStringConverter());	// Since values are Strings
		}
	
		public void	updateItem(String value, boolean isEmpty)
		{
			super.updateItem(value, isEmpty);		// Prepare for setup
			
			if (isEmpty || (value == null))		// Handle special cases
			{
				setText(null);
				setGraphic(null);
				return;
			}

			// This cell shows the value of the genre attribute as plaintext
			setText(value);
			setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
			setGraphic(null);
		}
	}
	//**********************************************************************
	// Inner Classes (Table Column Edit Handlers)
	//**********************************************************************

	// This EventHander processes edits in the title column.
	private final class TitleEditHandler
		implements EventHandler<TableColumn.CellEditEvent<Movie, String>>
	{
		public void	handle(TableColumn.CellEditEvent<Movie, String> t)
		{
			// Get the movie for the row that was edited
			int	index = t.getTablePosition().getRow();
			Movie	movie = movies.get(index);

			// Set its title to the new value that was entered
			movie.setTitle(t.getNewValue());
		}
	}

	// No EventHander implemented, since the image column isn't editable.
	//private final class ImageEditHandler
	//{
	//}

	// TODONE #4: Add an EventHandler class for each of your editable columns.
	// You must adapt the code to the column's attribute type in each case.
	// Allow editing (shallowly) in at least one of the three columns.

	private final class GenreEditHandler
		implements EventHandler<TableColumn.CellEditEvent<Movie, String>>
	{
		public void	handle(TableColumn.CellEditEvent<Movie, String> t)
		{
			// Get the movie for the row that was edited
			int	index = t.getTablePosition().getRow();
			Movie	movie = movies.get(index);
			
			String s = t.getNewValue().toLowerCase();
			int g = movie.getGenre();
			int n = 0;
			for (String genre : gdata) {
				if (s.contains(genre.toLowerCase()))
					// set the bit to 1
					g = g | (1 << n);
				else
					// set the bit to 0
					g = g & ~(1 << n);
				++n;
			}
			
			// Set its title to the new value that was entered
			movie.setGenre(g);
		}
	}
	
	private final class YearEditHandler
		implements EventHandler<TableColumn.CellEditEvent<Movie, Integer>>
	{
		public void	handle(TableColumn.CellEditEvent<Movie, Integer> t)
		{
			// Get the movie for the row that was edited
			int	index = t.getTablePosition().getRow();
			Movie	movie = movies.get(index);

			// Set its title to the new value that was entered
			movie.setYear(t.getNewValue());
		}
	}

	private final class DirectorEditHandler
		implements EventHandler<TableColumn.CellEditEvent<Movie, String>>
	{
		public void	handle(TableColumn.CellEditEvent<Movie, String> t)
		{
			// Get the movie for the row that was edited
			int	index = t.getTablePosition().getRow();
			Movie	movie = movies.get(index);
	
			// Set its title to the new value that was entered
			movie.setDirector(t.getNewValue());
		}
	}
}

//******************************************************************************
