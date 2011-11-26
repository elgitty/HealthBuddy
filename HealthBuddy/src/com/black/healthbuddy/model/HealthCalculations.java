package com.black.healthbuddy.model;

public class HealthCalculations {
	// This array will hold an array of doubles storeing each days calories
	// burnt
	// 0 means Wed onto Tues which is six
	private double[] weekExCalBurnt;
	private double[] weekNuCalConsumed;
	private double[] weekCaloriesOffset;

	private double[][] foodtypesrecord;

	// 0 Grain,1 Vegetable, 2 Fruit, 3 Protein, ,4 Dairy, 5 Snack
	private double[] foodTypeAverageConSumtion;

//	private String[] ReccommendedFoodType;
	
//	private String[] FoodTypeLevels;
	
	private String LowFoodType;
	private String HighFoodType;

	// 0 Grain,1 Vegetable, 2 Fruit, 3 Protein, ,4 Dairy, 5 Snack
	private final static double[] RDA = { 0.26, 0.24, 0.18, 0.16, 0.13, 0.03 };
	private final static String[] FOODNAMES = {"Grain", "Vegetables", "Fruit", "Protein", "Dairy", "Snacks"}; 

	public HealthCalculations() {
		super();
		this.weekExCalBurnt = new double[7];
		this.weekNuCalConsumed = new double[7];
		this.weekCaloriesOffset = new double[7];
		
		// for 7 days and 4 food types
		this.foodtypesrecord = new double[7][6];
		this.foodTypeAverageConSumtion = new double[6];
//		this.ReccommendedFoodType = new String[6];

//		this.FoodTypeLevels = new String[6];
		this.LowFoodType = "";
		this.HighFoodType = "";

	}

	

	public void calculateweekExCalBurnt(Exercise[] exerciseLogsArray) {
		double WednesdayCalories = 0;
		double ThursdayCalories = 0;
		double FridayCalories = 0;
		double SaturdayCalories = 0;
		double SundayCalories = 0;
		double MondayCalories = 0;
		double TuesdayCalories = 0;
		
		for (int i = 0; i < exerciseLogsArray.length; i++) {
			if (exerciseLogsArray[i].getFrequency().equals("Wednesdays")) {
				WednesdayCalories = WednesdayCalories
						+ (exerciseLogsArray[i].getDuration() * exerciseLogsArray[i]
								.getCaloriesPerMinDuration());
			}
		
			if (exerciseLogsArray[i].getFrequency().equals("Thursdays")) {
				ThursdayCalories = ThursdayCalories
						+ (exerciseLogsArray[i].getDuration() * exerciseLogsArray[i]
								.getCaloriesPerMinDuration());
			}
			
			if (exerciseLogsArray[i].getFrequency().equals("Fridays")) {
				FridayCalories = FridayCalories
						+ (exerciseLogsArray[i].getDuration() * exerciseLogsArray[i]
								.getCaloriesPerMinDuration());
			}

			if (exerciseLogsArray[i].getFrequency().equals("Saturdays")) {
				SaturdayCalories = SaturdayCalories
						+ (exerciseLogsArray[i].getDuration() * exerciseLogsArray[i]
								.getCaloriesPerMinDuration());
			}

			if (exerciseLogsArray[i].getFrequency().equals("Sundays")) {
				SundayCalories = SundayCalories
						+ (exerciseLogsArray[i].getDuration() * exerciseLogsArray[i]
								.getCaloriesPerMinDuration());
			}
			
			if (exerciseLogsArray[i].getFrequency().equals("Mondays")) {
				MondayCalories = MondayCalories
						+ (exerciseLogsArray[i].getDuration() * exerciseLogsArray[i]
								.getCaloriesPerMinDuration());
			}
			
			if (exerciseLogsArray[i].getFrequency().equals("Tuesdays")) {
				TuesdayCalories = TuesdayCalories
						+ (exerciseLogsArray[i].getDuration() * exerciseLogsArray[i]
								.getCaloriesPerMinDuration());
			}

		}
		weekExCalBurnt[0] = WednesdayCalories;
		weekExCalBurnt[1] = ThursdayCalories;
		weekExCalBurnt[2] = FridayCalories;

		weekExCalBurnt[3] = SaturdayCalories;
		weekExCalBurnt[4] = SundayCalories;
		
		weekExCalBurnt[5] = MondayCalories;
		weekExCalBurnt[6] = TuesdayCalories;
		

	}

	public void calculateweekNuCalConsumed(Nutrition[] nutrientLogsArray) {
		double WednesdayCalorConsum = 0;
		double ThurCalorConsum = 0;
		double FridayCalorConsum = 0;
		double SaturdayCalorConsum = 0;
		double SunCalorConsum = 0;
		double MonCalorConsum = 0;
		double TuesCalorConsum = 0;

		for (int i = 0; i < nutrientLogsArray.length; i++) {
			if (nutrientLogsArray[i].getLogFrequency().equals("Wednesdays"))
				WednesdayCalorConsum = WednesdayCalorConsum
						+ nutrientLogsArray[i].getCaloriesPerMinPortion();

			if (nutrientLogsArray[i].getLogFrequency().equals("Thursdays"))
				ThurCalorConsum = ThurCalorConsum
						+ nutrientLogsArray[i].getCaloriesPerMinPortion();

			if (nutrientLogsArray[i].getLogFrequency().equals("Fridays"))
				FridayCalorConsum = FridayCalorConsum
						+ nutrientLogsArray[i].getCaloriesPerMinPortion();

			if (nutrientLogsArray[i].getLogFrequency().equals("Saturdays"))
				SaturdayCalorConsum = SaturdayCalorConsum
						+ nutrientLogsArray[i].getCaloriesPerMinPortion();

			if (nutrientLogsArray[i].getLogFrequency().equals("Sundays"))
				SunCalorConsum = SunCalorConsum
						+ nutrientLogsArray[i].getCaloriesPerMinPortion();

			if (nutrientLogsArray[i].getLogFrequency().equals("Mondays"))
				MonCalorConsum = MonCalorConsum
						+ nutrientLogsArray[i].getCaloriesPerMinPortion();

			if (nutrientLogsArray[i].getLogFrequency().equals("Tuesdays"))
				TuesCalorConsum = TuesCalorConsum
						+ nutrientLogsArray[i].getCaloriesPerMinPortion();
		}
		weekNuCalConsumed[0] = WednesdayCalorConsum;
		weekNuCalConsumed[1] = ThurCalorConsum;
		weekNuCalConsumed[2] = FridayCalorConsum;
		weekNuCalConsumed[3] = SaturdayCalorConsum;
		weekNuCalConsumed[4] = SunCalorConsum;
		weekNuCalConsumed[5] = MonCalorConsum;
		weekNuCalConsumed[6] = TuesCalorConsum;

	}

	public void calculateFoodTypeRecord(Nutrition[] nutrientLogsArray) {
		for (int i = 0; i < nutrientLogsArray.length; i++) {
			if (nutrientLogsArray[i].getLogFrequency().equals("Wednesdays")) {
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("G") != -1)
					foodtypesrecord[0][0] = foodtypesrecord[0][0] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("V") != -1)
					foodtypesrecord[0][1] = foodtypesrecord[0][1] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("F") != -1)
					foodtypesrecord[0][2] = foodtypesrecord[0][2] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("P") != -1)
					foodtypesrecord[0][3] = foodtypesrecord[0][3] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("D") != -1)
					foodtypesrecord[0][4] = foodtypesrecord[0][4] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("S") != -1)
					foodtypesrecord[0][5] = foodtypesrecord[0][5] + 1;
			}

			if (nutrientLogsArray[i].getLogFrequency().equals("Thursdays")) {
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("G") != -1)
					foodtypesrecord[1][0] = foodtypesrecord[1][0] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("V") != -1)
					foodtypesrecord[1][1] = foodtypesrecord[1][1] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("F") != -1)
					foodtypesrecord[1][2] = foodtypesrecord[1][2] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("P") != -1)
					foodtypesrecord[1][3] = foodtypesrecord[1][3] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("D") != -1)
					foodtypesrecord[1][4] = foodtypesrecord[1][4] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("S") != -1)
					foodtypesrecord[1][5] = foodtypesrecord[1][5] + 1;
			}

			if (nutrientLogsArray[i].getLogFrequency().equals("Fridays")) {
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("G") != -1)
					foodtypesrecord[2][0] = foodtypesrecord[2][0] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("V") != -1)
					foodtypesrecord[2][1] = foodtypesrecord[2][1] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("F") != -1)
					foodtypesrecord[2][2] = foodtypesrecord[2][2] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("P") != -1)
					foodtypesrecord[2][3] = foodtypesrecord[2][3] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("D") != -1)
					foodtypesrecord[2][4] = foodtypesrecord[2][4] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("S") != -1)
					foodtypesrecord[2][5] = foodtypesrecord[2][5] + 1;
			}

			if (nutrientLogsArray[i].getLogFrequency().equals("Saturdays")) {
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("G") != -1)
					foodtypesrecord[3][0] = foodtypesrecord[3][0] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("V") != -1)
					foodtypesrecord[3][1] = foodtypesrecord[3][1] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("F") != -1)
					foodtypesrecord[3][2] = foodtypesrecord[3][2] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("P") != -1)
					foodtypesrecord[3][3] = foodtypesrecord[3][3] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("D") != -1)
					foodtypesrecord[3][4] = foodtypesrecord[3][4] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("S") != -1)
					foodtypesrecord[3][5] = foodtypesrecord[3][5] + 1;
			}

			if (nutrientLogsArray[i].getLogFrequency().equals("Sundays")) {
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("G") != -1)
					foodtypesrecord[4][0] = foodtypesrecord[4][0] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("V") != -1)
					foodtypesrecord[4][1] = foodtypesrecord[4][1] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("F") != -1)
					foodtypesrecord[4][2] = foodtypesrecord[4][2] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("P") != -1)
					foodtypesrecord[4][3] = foodtypesrecord[4][3] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("D") != -1)
					foodtypesrecord[4][4] = foodtypesrecord[4][4] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("S") != -1)
					foodtypesrecord[4][5] = foodtypesrecord[4][5] + 1;
			}

			if (nutrientLogsArray[i].getLogFrequency().equals("Mondays")) {
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("G") != -1)
					foodtypesrecord[5][0] = foodtypesrecord[5][0] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("V") != -1)
					foodtypesrecord[5][1] = foodtypesrecord[5][1] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("F") != -1)
					foodtypesrecord[5][2] = foodtypesrecord[5][2] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("P") != -1)
					foodtypesrecord[5][3] = foodtypesrecord[5][3] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("D") != -1)
					foodtypesrecord[5][4] = foodtypesrecord[5][4] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("S") != -1)
					foodtypesrecord[5][5] = foodtypesrecord[5][5] + 1;
			}

			if (nutrientLogsArray[i].getLogFrequency().equals("Tuesdays")) {
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("G") != -1)
					foodtypesrecord[6][0] = foodtypesrecord[6][0] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("V") != -1)
					foodtypesrecord[6][1] = foodtypesrecord[6][1] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("F") != -1)
					foodtypesrecord[6][2] = foodtypesrecord[6][2] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("P") != -1)
					foodtypesrecord[6][3] = foodtypesrecord[6][3] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("D") != -1)
					foodtypesrecord[6][4] = foodtypesrecord[6][4] + 1;
				if (nutrientLogsArray[i].getFoodType_for_RDA().indexOf("S") != -1)
					foodtypesrecord[6][5] = foodtypesrecord[6][5] + 1;
			}

		}

	}

	public void calculateReccommendedFoodType() {
		double[] temp = new double[6];
		// totting up the number of food types
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (j == 0)
					temp[j] = temp[j] + foodtypesrecord[i][j];
				if (j == 1)
					temp[j] = temp[j] + foodtypesrecord[i][j];
				if (j == 2)
					temp[j] = temp[j] + foodtypesrecord[i][j];
				if (j == 3)
					temp[j] = temp[j] + foodtypesrecord[i][j];
				if (j == 4)
					temp[j] = temp[j] + foodtypesrecord[i][j];
				if (j == 5)
					temp[j] = temp[j] + foodtypesrecord[i][j];
				if (j == 6)
					temp[j] = temp[j] + foodtypesrecord[i][j];

			}
		}
		double sum = 0;
		for (int i = 0; i < temp.length; i++) {
			sum = sum + temp[i];
		}

		// find percentages
		for (int i = 0; i < temp.length; i++) {
			foodTypeAverageConSumtion[i] = temp[i] / sum;
		}

		double[] difference = new double[6];

		for (int i = 0; i < difference.length; i++) {
			difference[i] = foodTypeAverageConSumtion[i] - RDA[i];

		}
		int y = 0;


		int minValueElem = 0;
		int maxValueElem = 0;
		double minValue = 1000;
		double maxValue = -1000;

		for (int i = 1; i < difference.length; i++) {
			
		// find element with the lowest number
			if (difference[i] < minValue)
			{
				minValueElem = i;
				minValue = difference[i];
			}
				
		// find element with the highest number
			if (difference[i] > maxValue)
			{
				maxValueElem = i;
				maxValue = difference[i];
			}
		}
		
		
		if (minValue < -0.08){
			this.LowFoodType = "Your recent diet was low in " + FOODNAMES[minValueElem] + ".";
		}
		else
			this.LowFoodType = "Your recent diet was close to recommended daily amounts";
		
		if (maxValue > 0.08)
		{
			this.HighFoodType = "Your recent diet was high in " + FOODNAMES[maxValueElem];
		}
		else
			this.HighFoodType = "Your recent diet was close to recommended daily amounts";
		

		
		for (int i = 0; i < weekCaloriesOffset.length; i++) 
		{
			weekCaloriesOffset[i] = weekNuCalConsumed[i] - weekExCalBurnt[i];
			
		}
		int x = 0;
		// for (int i = 0; i < foodTypeAverageConSumtion.length; i++) {

		//
		//
		// }

	}

	// weekExCalBurnt =
	
	
	public double[] getWeekExCalBurnt() {
		return weekExCalBurnt;
	}

	public void setWeekExCalBurnt(double[] weekExCalBurnt) {
		this.weekExCalBurnt = weekExCalBurnt;
	}

	public double[] getWeekNuCalConsumed() {
		return weekNuCalConsumed;
	}

	public void setWeekNuCalConsumed(double[] weekNuCalConsumed) {
		this.weekNuCalConsumed = weekNuCalConsumed;
	}

	public double[] getWeekCaloriesOffset() {
		return weekCaloriesOffset;
	}

	public void setWeekCaloriesOffset(double[] weekCaloriesOffset) {
		this.weekCaloriesOffset = weekCaloriesOffset;
	}

	public double[][] getFoodtypesrecord() {
		return foodtypesrecord;
	}

	public void setFoodtypesrecord(double[][] foodtypesrecord) {
		this.foodtypesrecord = foodtypesrecord;
	}

	public double[] getFoodTypeAverageConSumtion() {
		return foodTypeAverageConSumtion;
	}

	public void setFoodTypeAverageConSumtion(double[] foodTypeAverageConSumtion) {
		this.foodTypeAverageConSumtion = foodTypeAverageConSumtion;
	}

	public String getLowFoodType() {
		return LowFoodType;
	}

	public void setLowFoodType(String lowFoodType) {
		LowFoodType = lowFoodType;
	}

	public String getHighFoodType() {
		return HighFoodType;
	}

	public void setHighFoodType(String highFoodType) {
		HighFoodType = highFoodType;
	}

	public static double[] getRda() {
		return RDA;
	}

	public static String[] getFoodnames() {
		return FOODNAMES;
	}
	
	public double getDaysCalBurnt (int index)
	{
		return weekExCalBurnt[index];
	}
	
	public double getDaysCalConsumed (int index)
	{
		return weekNuCalConsumed[index];
	}
	
	public double getDaysCalOffset (int index)
	{
		return weekCaloriesOffset[index];
	}

}
