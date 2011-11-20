package com.black.healthbuddy.model;

//import com.black.healthbuddy.model.HealthBuddyDbAdapter; 
//import com.black.healthbuddy.model.Exercise;
//import com.black.healthbuddy.model.Nutrition;

//import android.database.Cursor;
//import java.io.Serializable;
//import java.util.ArrayList;


public class User /*implements Serializable */ {
	
	// @em: from UserProfileTable and UserRecommendationTable
	private String userName;
	private String userSex;
	private int userAge;
	private double userHeight, userWeight;  
	private int bmr, calorieNeed;// @em: basic metabolic rate will inform on calorie need 
	private int userCalorieBank;    // this means we can do without the UserRecommendationTable!
									// instead add filed for calorieNeed to UserProfileTable
	// @em: Use for advice and userCalorieBank calculation.
//	private int recommendedCalorieBank;
//	private int userTypeID;
	
	// @em: instantiate objects of type Nutrition and Exercise
//	private ArrayList<Nutrition> nutritionLogList;
//	private ArrayList<Exercise> exerciseLogList;

	// @em: setters (this should be moved to a controller folder)
	public void setName( String name ) {
		userName = name;
	}
	
	public void setSex( String sex ) {
		userSex = sex;
	}
	
	public void setAge( int age ) {
		userAge = age;
	}
	
	public void setHeight( double height ) {
		userHeight = height;
	}
	
	public void setWeight(double weight ) {
		userWeight = weight;
	}
	
	public void setCalorieBank( int calorieBank ) {
		userCalorieBank = calorieBank;
	}
	
	// @em: getters (this should be moved to a controller folder)
	public String getName(){
		return userName;
	}
	
	public String getSex() {
		return userSex;
	}
	
	public int getAge() {
		return userAge;
	}
	
	public double getHeight() {
		return userHeight;
	}
	
	public double getWeight() {
		return userWeight;
	}
	
	public int getCalorieBank() {
		return userCalorieBank;
	}	
	
/*	@em: this section to calculate bmr, recommendedCalorieBank = calorieNeed
    BMR: Basal Metabolic Rate, is the number of calories you'd burn if you stayed in bed all day
	http://www.bmi-calculator.net/bmr-calculator/bmr-formula.php
	
	Women: BMR = 655 + ( 9.6 x weight in kilos ) + ( 1.8 x height in cm ) - ( 4.7 x age in years )
	Men: BMR = 66 + ( 13.7 x weight in kilos ) + ( 5 x height in cm ) - ( 6.8 x age in years )
	
  	Harris Benedict Formula(http://www.bmi-calculator.net/bmr-calculator/harris-benedict-equation/)
	To determine your total daily calorie needs, multiply your BMR by the appropriate 
	activity factor, as follows:
	
	If you are sedentary (little or no exercise) : Calorie-Calculation = BMR x 1.2
	
	If you are lightly active (light exercise/sports 1-3 days/week) : 
	Calorie-Calculation = BMR x 1.375
	
	If you are moderately active (moderate exercise/sports 3-5 days/week) : 
	Calorie-Calculation = BMR x 1.55
	
	If you are very active (hard exercise/sports 6-7 days a week) : 
	Calorie-Calculation = BMR x 1.725
	
	If you are extra active (very hard exercise/sports & physical job or 2x training) : 
	Calorie-Calculation = BMR x 1.9
*/
	public int calculateCalorieNeed() {
		if (userSex == "male") {
			bmr = (int) (66 + ( 13.7 * userWeight ) + ( 5 * userHeight ) - ( 6.8 * userAge ));
			calorieNeed = (int) (bmr * 1.55); // @em: temp hardcode for our user (moderately active)
		}
		else if (userSex == "female") {
			bmr = (int) (655 + ( 9.6 * userWeight ) + ( 1.8 * userHeight ) - ( 4.7 * userAge ));
			calorieNeed = (int) (bmr * 1.55); // @em: temp hardcode for our user (moderately active)
		}
		return calorieNeed;
	}	
	
	// @em TODO: calculate calorieBank based on calorieNeed - calories from food + calories from exercise
		
	// @em TODO: update calorieBank
	
	// @em TODO: display updated calorieBank to widget on Menu (not in this class)

	
}


