package com.black.healthbuddy.model;

import java.util.Calendar;
import java.util.Date;

import android.database.Cursor;

public class Nutrition /* implements Serializable */
{

	// data from the UserNutritionLogTable
	private long user_id;
	private long Log_id;
	private long startTime;
	private long finishTime;
	private String logFrequency;


	private String logPortion;

	// data from the NutritionTable
	private long Nutrition_id;
	private long ScanID;
	private String foodType;
	private String FoodOrNutrientName;
	private int foodMinPortion;
	private int caloriesPerMinPortion;
	private int numberOfContainers;
	private String containerType;
	private double measurement;
	private String measurementUnit;

	// data from the NutritionRecommendationTable
	private String foodGroupFromMyPlate;
	private int recommendedDailyAmount;
	private String unitOfMeasurment;

	public Nutrition() {
		// data from the UserNutritionLogTable
		user_id = -1;
		Log_id = -1;
		startTime = -1;
		finishTime = -1;
		logFrequency = "";
		logPortion = "";

		// data from the NutritionTable
		Nutrition_id = -1;
		ScanID = -1;
		foodType = "";
		FoodOrNutrientName = "";
		foodMinPortion = -1;
		caloriesPerMinPortion = -1;
		numberOfContainers = -1;
		containerType = "";
		measurement = -1;
		measurementUnit = "";

		// data from the NutritionRecommendationTable
		foodGroupFromMyPlate = "";
		recommendedDailyAmount = -1;
		unitOfMeasurment = "";

	}
	
	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getLog_id() {
		return Log_id;
	}

	public void setLog_id(long log_id) {
		Log_id = log_id;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public String getLogFrequency() {
		return logFrequency;
	}

	public void setLogFrequency(String logFrequency) {
		this.logFrequency = logFrequency;
	}

	public String getLogPortion() {
		return logPortion;
	}

	public void setLogPortion(String logPortion) {
		this.logPortion = logPortion;
	}

	public long getNutrition_id() {
		return Nutrition_id;
	}

	public void setNutrition_id(long nutrition_id) {
		Nutrition_id = nutrition_id;
	}

	public long getScanID() {
		return ScanID;
	}

	public void setScanID(long scanID) {
		ScanID = scanID;
	}

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

	public String getFoodOrNutrientName() {
		return FoodOrNutrientName;
	}

	public void setFoodOrNutrientName(String foodOrNutrientName) {
		FoodOrNutrientName = foodOrNutrientName;
	}

	public int getFoodMinPortion() {
		return foodMinPortion;
	}

	public void setFoodMinPortion(int foodMinPortion) {
		this.foodMinPortion = foodMinPortion;
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

	public String getFoodGroupFromMyPlate() {
		return foodGroupFromMyPlate;
	}

	public void setFoodGroupFromMyPlate(String foodGroupFromMyPlate) {
		this.foodGroupFromMyPlate = foodGroupFromMyPlate;
	}

	public int getRecommendedDailyAmount() {
		return recommendedDailyAmount;
	}

	public void setRecommendedDailyAmount(int recommendedDailyAmount) {
		this.recommendedDailyAmount = recommendedDailyAmount;
	}

	public String getUnitOfMeasurment() {
		return unitOfMeasurment;
	}

	public void setUnitOfMeasurment(String unitOfMeasurment) {
		this.unitOfMeasurment = unitOfMeasurment;
	}


	public void fillUpGivenNutLogId(long NutrientLog_id,
			HealthBuddyDbAdapter mDbHelper) {


		String sqlWhereStatment = "UserNutritionLogTable._id = "
				+ NutrientLog_id;
		
		//joins The User UserNutritionLogTable to the NutritionTable and then finally to the NutritionRecommendationTable uses a where statment to select a particular log
		Cursor c = mDbHelper
				.queryTable(
						"UserNutritionLogTable JOIN NutritionTable ON (UserNutritionLogTable.NutritionId_FK = NutritionTable._id)"
								+ "JOIN NutritionRecommendationTable ON (NutritionTable.NutritionRecommendationTable_FK = NutritionRecommendationTable.foodGroupFromMyPlate)",
						null, sqlWhereStatment, null, null,
						null, null);

		
		// data from the UserNutritionLogTable
		int iuser_id = c.getColumnIndexOrThrow("user_id_FK");
		int iLog_id = c.getColumnIndexOrThrow("UserNutritionLogTable._id");
		int istartTime = c.getColumnIndexOrThrow("startTime");
		int ifinishTime = c.getColumnIndexOrThrow("finishTime");
		int ilogFrequency = c.getColumnIndexOrThrow("logFrequency");
		int ilogPortion = c.getColumnIndexOrThrow("logPortion");

		// data from the NutritionTable
		int iNutrition_id = c.getColumnIndexOrThrow("NutritionTable._id");
		int iScanID = c.getColumnIndexOrThrow("ScanID");
		int ifoodType = c.getColumnIndexOrThrow("foodType");
		int iFoodOrNutrientName = c.getColumnIndexOrThrow("FoodOrNutrientName");
		int ifoodMinPortion = c.getColumnIndexOrThrow("foodMinPortion");
		int icaloriesPerMinPortion = c
				.getColumnIndexOrThrow("caloriesPerMinPortion");
		int inumberOfContainers = c.getColumnIndexOrThrow("numberOfContainers");
		int icontainerType = c.getColumnIndexOrThrow("containerType");
		int imeasurement = c.getColumnIndexOrThrow("measurement");
		int imeasurementUnit = c.getColumnIndexOrThrow("measurementUnit");

		// data from the NutritionRecommendedTable
		int ifoodGroupFromMyPlate = c
				.getColumnIndexOrThrow("foodGroupFromMyPlate");
		int irecommendedDailyAmount = c
				.getColumnIndexOrThrow("recommendedDailyAmount");
		int iunitOfMeasurment = c.getColumnIndexOrThrow("unitOfMeasurment");

		// we then iterate through the curser taking out the results
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			user_id = c.getLong(iuser_id);

			Log_id = c.getLong(iLog_id);
			startTime = c.getLong(istartTime);
			finishTime = c.getLong(ifinishTime);
			logFrequency = c.getString(ilogFrequency);
			logPortion = c.getString(ilogPortion);

			// data from the NutritionTable
			Nutrition_id = c.getLong(iNutrition_id);
			ScanID = c.getLong(iScanID);
			foodType = c.getString(ifoodType);
			FoodOrNutrientName = c.getString(iFoodOrNutrientName);
			foodMinPortion = c.getInt(ifoodMinPortion);
			caloriesPerMinPortion = c.getInt(icaloriesPerMinPortion);
			numberOfContainers = c.getInt(inumberOfContainers);
			containerType = c.getString(icontainerType);
			measurement = c.getInt(imeasurement);
			measurementUnit = c.getString(imeasurementUnit);

			// data from the UserNutritionLogTable
			foodGroupFromMyPlate = c.getString(ifoodGroupFromMyPlate);
			recommendedDailyAmount = c.getInt(irecommendedDailyAmount);
			unitOfMeasurment = c.getString(iunitOfMeasurment);

			// result = result + "Record No:" + c.getString(iRow) + " "
			// + c.getString(iFood_Name) + " Recommended Daily Amount: "
			// // + c.getString(irecommeded) + "\t";
			// }
			//
			// c.close(); // closing curser
			// int x = 0;
			//
			// x = 10 * 10;
			//
			c.close();

		}
	}

	public void fillupGivenNutrientIDandUserID(long log_id, long user_id_) {
		user_id = user_id_;
		log_id = -1;

		startTime = System.currentTimeMillis();
		
	}

}
