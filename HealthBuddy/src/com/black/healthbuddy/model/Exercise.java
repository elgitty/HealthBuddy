package com.black.healthbuddy.model;

//import com.black.healthbuddy.model.HealthBuddyDbAdapter; 

//import java.io.Serializable;
//import java.util.ArrayList;
//import android.database.Cursor;


public class Exercise /* implements Serializable */ {
	
	// @em: from UserExerciseLogTable and ExerciseReferenceTable
	private String exerciseName;
	private int exerciseLogDuration;
	private String exerciseLogFrequency;
	private String exerciseLogDate;
	
	// @em: Use for advice and userCalorieBank calculation.
	private int exerciseMinDuration; // @em: min on which to calculate calories. maybe not needed
	private String exerciseCaloriesPerMinDuration;
	
	// @em: setters (this should be moved to a controller folder)
	public void setName( String name ) {
		exerciseName = name;
	}

	public void setDuration( int duration ) {
		exerciseLogDuration = duration;
	}

	public void setFrequency( String frequency ) {
		exerciseLogFrequency = frequency;
	}
	
	public void setDate( String date ) {
		exerciseLogDate = date;
	}
	
	public void setMinDuration( int minDuration ) {
		exerciseMinDuration = minDuration;
	}

	public void setCaloriesPerMinDuration( String caloriesPerMinDuration ) {
		exerciseCaloriesPerMinDuration = caloriesPerMinDuration;
	}
	
	// @em: getters (this should be moved to a controller folder)
	public String getName(){
		return exerciseName;
	}
	
	public int getDuration() {
		return exerciseLogDuration;
	}

	public String getFrequency() {
		return exerciseLogFrequency;
	}
	
	public String getDate() {
		return exerciseLogDate;
	}
	
	public int getMinDuration() {
		return exerciseMinDuration;
	}

	public String getCaloriesPerMinDuration() {
		return exerciseCaloriesPerMinDuration;
	}



}

