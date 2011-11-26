package com.black.healthbuddy.model;

//import com.black.healthbuddy.model.HealthBuddyDbAdapter; 

//import java.io.Serializable;
//import java.util.ArrayList;
//import android.database.Cursor;

public class Exercise /* implements Serializable */{

	// data from the ExerciseTable
	// private long exercise_id;
	// private String exerciseName;
	// private long recommendedDailyDuration;
	// private long minimumDuration;
	// private long caloriesPerMinDuration;

	// @em: from UserExerciseLogTable and ExerciseReferenceTable
	private String exerciseName;
	private int startTime;
	private int exerciseLogDuration;
	private String exerciseLogFrequency;

	private double exerciseCaloriesPerMinDuration;

	public Exercise() {
		exerciseName = "";
		startTime = -1;
		exerciseLogDuration = -1;
		exerciseLogFrequency = "";
		exerciseCaloriesPerMinDuration = 0.0;
	}
	
	

	// @em: setters (this should be moved to a controller folder)
	public void setName(String name) {
		exerciseName = name;
	}

	public void setDuration(int duration) {
		exerciseLogDuration = duration;
	}

	public void setFrequency(String frequency) {
		exerciseLogFrequency = frequency;
	}

	public void setStartTime(int date) {
		startTime = date;
	}

	public void setCaloriesPerMinDuration(double caloriesPerMinDuration) {
		exerciseCaloriesPerMinDuration = caloriesPerMinDuration;
	}

	// @em: getters (this should be moved to a controller folder)
	public String getName() {
		return exerciseName;
	}

	public int getDuration() {
		return exerciseLogDuration;
	}

	public String getFrequency() {
		return exerciseLogFrequency;
	}

	public int setStartTime() {
		return startTime;
	}

	public double getCaloriesPerMinDuration() {
		return exerciseCaloriesPerMinDuration;
	}

}
