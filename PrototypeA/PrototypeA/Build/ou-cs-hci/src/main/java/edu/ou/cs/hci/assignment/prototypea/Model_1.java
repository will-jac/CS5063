//******************************************************************************
// Copyright (C) 2019-2020 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Wed Feb 20 19:34:56 2019 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20190203 [weaver]:	Original file.
// 20190220 [weaver]:	Adapted from swingmvc to fxmvc.
//
//******************************************************************************
//
//******************************************************************************

package edu.ou.cs.hci.assignment.prototypea;

import java.util.Calendar;
import java.util.Date;
//import java.lang.*;
import java.util.HashMap;
import javafx.application.Platform;

//******************************************************************************

/**
 * The <CODE>Model</CODE> class.
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Model
{
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// Master of the program, manager of the data, mediator of all updates
	private final Controller				controller;

	// Easy, extensible way to store multiple simple, independent parameters
	private final HashMap<String, Object>	properties;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public Model(Controller controller)
	{
		this.controller = controller;

		properties = new HashMap<String, Object>();

		// Parameters accessed and/or modified by EditorPane controls
		
		// Metadata options:
		properties.put("Title", "Title");
		properties.put("Year", Calendar.getInstance().get(Calendar.YEAR));
		properties.put("Director", "Director");
		properties.put("Runtime", 0.0);
		properties.put("Average Review", 5.0);
		properties.put("Number of Reviews", 0);
		properties.put("Color", true);
		properties.put("Animated", false);
		properties.put("Rating G", true);
		properties.put("Rating PG", false);
		properties.put("Rating PG-13", false);
		properties.put("Rating R", false);
//		HashMap<String, Boolean> awards = new HashMap<String, Boolean>();
		properties.put("Picture", false);
		properties.put("Directing", false);
		properties.put("Cinematography", false);
		properties.put("Acting", false);
//		properties.put("Awards", awards);
//		HashMap<String, Boolean> genre = new HashMap<String, Boolean>();
		properties.put("Action", false);
		properties.put("Comedy", false);
		properties.put("Documentary", false);
		properties.put("Drama", false);
		properties.put("Fantasy", false);
		properties.put("Horror", false);
		properties.put("Romance", false);
		properties.put("Sci-Fi", false);
		properties.put("Thriller", false);
		properties.put("Western", false);
//		properties.put("Genre", genre);
		properties.put("Poster", null);
		properties.put("Poster File Path", null);
		properties.put("Comments", "");
		properties.put("Summary", "");

	}

	//**********************************************************************
	// Public Methods (Controller)
	//**********************************************************************

	public Object getValue(String key)
	{
		return properties.get(key);
	}

	public void	setValue(String key, Object value)
	{
		if (properties.containsKey(key) &&
			properties.get(key).equals(value))
		{
			System.out.println("  model: value not changed");
			return;
		}

		Platform.runLater(new Updater(key, value));
	}

	public void	trigger(String name)
	{
		System.out.println("  model: (not!) calculating function: " + name);
	}

	//**********************************************************************
	// Inner Classes
	//**********************************************************************

	private class Updater
		implements Runnable
	{
		private final String	key;
		private final Object	value;

		public Updater(String key, Object value)
		{
			this.key = key;
			this.value = value;
		}

		public void	run()
		{
			properties.put(key, value);
			controller.update(key, value);
		}
	}
}

//******************************************************************************
