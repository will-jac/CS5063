//******************************************************************************
// Copyright (C) 2020 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Wed Feb 12 23:13:57 2020 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20200212 [weaver]:	Original file.
//
//******************************************************************************
//
//******************************************************************************

package edu.ou.cs.hci.assignment.prototypeb;

//import java.lang.*;
import java.util.List;
import javafx.beans.property.*;

//******************************************************************************

/**
 * The <CODE>Movie</CODE> class manages the attributes of a movie as a set of
 * properties. The properties are created in the constructor. This differs from
 * the lazy creation of properties described in the TableView API (in the Person
 * class example), because we also use the properties to store the results of
 * parsing the inputs when the application starts.
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Movie
{
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// Each attribute has a matching property of the corresponding type.

	private final SimpleStringProperty		title;
	private final SimpleStringProperty		image;

	// TODONE #0: Add members for the other 15 attributes.
	private final SimpleIntegerProperty		year;
	private final SimpleStringProperty		rating;
	private final SimpleIntegerProperty		runtime;
	
	private final SimpleBooleanProperty		awardPicture;
	private final SimpleBooleanProperty		awardDirecting;
	private final SimpleBooleanProperty		awardCinematography;
	private final SimpleBooleanProperty		awardActing;
	
	private final SimpleDoubleProperty		averageReviewScore;
	
	private final SimpleIntegerProperty		numberOfReviews;
	
	private final SimpleIntegerProperty		genre;

	private final SimpleStringProperty		director;
	
	private final SimpleBooleanProperty		isAnimated;
	private final SimpleBooleanProperty		isColor;
	
	
	private final SimpleStringProperty		summary;
	private final SimpleStringProperty		comments;
	
	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public Movie(List<String> item)
	{
		// Each attribute value must be calculated from its string.

		title = new SimpleStringProperty(item.get(0));
		image = new SimpleStringProperty(item.get(1));

		// TODONE #1: Create properties for the other attributes. For non-string
		// types, look for methods in the Boolean, Integer, and Double classes.
		year = new SimpleIntegerProperty(
				Integer.parseInt(item.get(2)));
		rating = new SimpleStringProperty(
				item.get(3));
		runtime = new SimpleIntegerProperty(
				Integer.parseInt(item.get(4)));
		
		awardPicture = new SimpleBooleanProperty(
				Boolean.getBoolean(item.get(5)));
		awardDirecting = new SimpleBooleanProperty(
				Boolean.getBoolean(item.get(6)));
		awardCinematography = new SimpleBooleanProperty(
				Boolean.getBoolean(item.get(7)));
		awardActing = new SimpleBooleanProperty(
				Boolean.getBoolean(item.get(8)));
		
		averageReviewScore = new SimpleDoubleProperty(
				Double.parseDouble(item.get(9)));
		numberOfReviews = new SimpleIntegerProperty(
				Integer.parseInt(item.get(10)));
		
		genre = new SimpleIntegerProperty(
				Integer.parseInt(item.get(11)));
		
		director = new SimpleStringProperty(
				item.get(12));
		
		isAnimated = new SimpleBooleanProperty(
				Boolean.getBoolean(item.get(13)));
		isColor = new SimpleBooleanProperty(
				Boolean.getBoolean(item.get(14)));
		
		summary = new SimpleStringProperty(
				item.get(15));
		comments = new SimpleStringProperty(
				item.get(16));

		// Hint for genres: An integer can be treated as a collection of
		// independently set bits. See genre code in EditorPane for examples.
	}

	//**********************************************************************
	// Public Methods (Getters and Setters)
	//**********************************************************************

	// Each attribute has methods to access and modify its value.

	public String	getTitle()
	{
		return title.get();
	}

	public void	setTitle(String v)
	{
		title.set(v);
	}

	public String	getImage()
	{
		return image.get();
	}

	public void	setImage(String v)
	{
		image.set(v);
	}

	// TODONE #2: Create access and modify methods for your three attributes.
	public int getYear() {
		return year.get();
	}
	public void setYear(int v) {
		year.set(v);
	}
	public String getRating() {
		return rating.get();
	}
	public void setRating(String v) {
		rating.set(v);
	}
	public int getRuntime() {
		return runtime.get();
	}
	public void setRuntime(int v) {
		runtime.set(v);
	}
	public boolean getAwardPicture() {
		return awardPicture.get();
	}
	public void setAwardPicture(boolean v) {
		awardPicture.set(v);
	}
	public boolean getAwardDirecting() {
		return awardDirecting.get();
	}
	public void setAwardDirecting(boolean v) {
		awardDirecting.set(v);
	}
	public boolean getAwardCinematography() {
		return awardCinematography.get();
	}
	public void setAwardCinematography(boolean v) {
		awardCinematography.set(v);
	}
	public boolean getAwardActing() {
		return awardActing.get();
	}
	public void setAwardActing(boolean v) {
		awardActing.set(v);
	}
	public double getAverageReviewScore() {
		return averageReviewScore.get();
	}
	public void setAverageReviewScore(double v) {
		averageReviewScore.set(v);
	}
	public int getNumberOfReviews() {
		return numberOfReviews.get();
	}
	public void setNumberOfReviews(int v) {
		numberOfReviews.set(v);
	}
	public int getGenre() {
		return genre.get();
	}
	public void setGenre(int v) {
		genre.set(v);
	}
	public String getDirctor() {
		return director.get();
	}
	public void setDirector(String v) {
		director.set(v);
	}
	public boolean getIsAnimated() {
		return isAnimated.get();
	}
	public void setIsAnimated(boolean v) {
		isAnimated.set(v);
	}
	public boolean getIsColor() {
		return isColor.get();
	}
	public void setIsColor(boolean v) {
		isColor.set(v);
	}	
	public String getSummary() {
		return summary.get();
	}
	public void setSummary(String v) {
		summary.set(v);
	}	
	public String getComments() {
		return comments.get();
	}
	public void setComments(String v) {
		comments.set(v);
	}	
}

//******************************************************************************
