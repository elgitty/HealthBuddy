package com.black.healthbuddy.model;

import java.util.Calendar;
import java.util.Date;

import android.database.Cursor;

public class Nutrition /* implements Serializable */
{

	// data from the UserNutritionLogTable
	private long Log_id;
	private long Nutrition_id;
	private long startTime;
	private String logFrequency;
	private long user_id;
	
	// data from the NutritionTable
	private String foodType_for_RDA;
	private String foodType_for_display;
	private String FoodOrNutrientName;
	private int caloriesPerMinPortion;
	private int numberOfContainers;
	
	
	private String containerType;
	private double measurement;
	private String measurementUnit;


	public Nutrition() {
		// data from the UserNutritionLogTable
		user_id = -1;
		Log_id = -1;
		startTime = -1;
		
		logFrequency = "";
		
		foodType_for_RDA = "";
		foodType_for_display = "";

		// data from the NutritionTable
		Nutrition_id = -1;
		
		FoodOrNutrientName = "";
		caloriesPerMinPortion = -1;
		numberOfContainers = -1;
		containerType = "";
		measurement = -1;
		measurementUnit = "";
	}
	
	
	
	
	
	
	
	
	
	public long getLog_id() {
		return Log_id;
	}


	public void setLog_id(long log_id) {
		Log_id = log_id;
	}


	public long getNutrition_id() {
		return Nutrition_id;
	}


	public void setNutrition_id(long nutrition_id) {
		Nutrition_id = nutrition_id;
	}


	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	public String getLogFrequency() {
		return logFrequency;
	}


	public void setLogFrequency(String logFrequency) {
		this.logFrequency = logFrequency;
	}


	public long getUser_id() {
		return user_id;
	}


	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}


	public String getFoodType_for_RDA() {
		return foodType_for_RDA;
	}


	public void setFoodType_for_RDA(String foodType_for_RDA) {
		this.foodType_for_RDA = foodType_for_RDA;
	}


	public String getFoodType_for_display() {
		return foodType_for_display;
	}


	public void setFoodType_for_display(String foodType_for_display) {
		this.foodType_for_display = foodType_for_display;
	}


	public String getFoodOrNutrientName() {
		return FoodOrNutrientName;
	}


	public void setFoodOrNutrientName(String foodOrNutrientName) {
		FoodOrNutrientName = foodOrNutrientName;
	}


	public int getCaloriesPerMinPortion() {
		return caloriesPerMinPortion;
	}


	public void setCaloriesPerMinPortion(int caloriesPerMinPortion) {
		this.caloriesPerMinPortion = caloriesPerMinPortion;
	}


	public int getNumberOfContainers() {
		return numberOfContainers;
	}


	public void setNumberOfContainers(int numberOfContainers) {
		this.numberOfContainers = numberOfContainers;
	}


	public String getContainerType() {
		return containerType;
	}


	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}


	public double getMeasurement() {
		return measurement;
	}


	public void setMeasurement(double measurement) {
		this.measurement = measurement;
	}


	public String getMeasurementUnit() {
		return measurementUnit;
	}


	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}




	

}