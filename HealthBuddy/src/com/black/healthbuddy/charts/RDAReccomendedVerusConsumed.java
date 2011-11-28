/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.black.healthbuddy.charts;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

import com.black.healthbuddy.model.Exercise;
import com.black.healthbuddy.model.HealthBuddyDbAdapter;
import com.black.healthbuddy.model.HealthCalculations;
import com.black.healthbuddy.model.Nutrition;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;

/**
 * Sales demo bar chart.
 */
public class RDAReccomendedVerusConsumed extends AbstractDemoChart {

  private HealthBuddyDbAdapter mDbHelper;

/**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Sales horizontal bar chart";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The monthly sales for the last 2 years (horizontal bar chart)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Intent execute(Context context) {
	  

		// ////////Database stuff
		mDbHelper = new HealthBuddyDbAdapter(context);
		mDbHelper.open();

		// ///// query for all exercise logs
		Cursor c = mDbHelper
				.queryTable(
						"UserExerciseLogTable JOIN ExerciseTable ON (UserExerciseLogTable.exerciseId_FK  = ExerciseTable._id)",
						null, "UserExerciseLogTable.user_id_FK = 1", null,
						null, null, null);
		// ////////find how many rows where reteurned
		int numberOfRowsReturnened = c.getCount();

		// make array of objects to store exercisa data
		Exercise[] exerciseLogsArray;
		exerciseLogsArray = new Exercise[numberOfRowsReturnened];

		for (int i = 0; i < numberOfRowsReturnened; i++) {
			exerciseLogsArray[i] = new Exercise();
		}

		int iexerciseName = c.getColumnIndexOrThrow("exerciseName");
		int istartTime = c.getColumnIndexOrThrow("startTime");
		int iLogDuration = c.getColumnIndexOrThrow("logDuration");
		int iLogFrequency = c.getColumnIndexOrThrow("logFrequency");
		int icaloriesPerMinDuration = c
				.getColumnIndexOrThrow("caloriesPerMinDuration");

		int i = 0;
		// we then iterate through the curser taking out the results and
		// assigning to the objects.
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			exerciseLogsArray[i].setName(c.getString(iexerciseName));
			exerciseLogsArray[i].setStartTime(c.getInt(istartTime));
			exerciseLogsArray[i].setDuration(c.getInt(iLogDuration));

			exerciseLogsArray[i].setFrequency(c.getString(iLogFrequency));
			exerciseLogsArray[i].setCaloriesPerMinDuration(c
					.getDouble(icaloriesPerMinDuration));
			i++;
		}

		// query for all nutritional logs
		c.moveToFirst();
		c = mDbHelper
				.queryTable(
						"UserNutritionLogTable JOIN NutritionTable ON (UserNutritionLogTable.NutritionId_FK  = NutritionTable._id)",
						null, "UserNutritionLogTable.user_id_FK = 1", null,
						null, null, null);

		// ////////find how many rows where reteurned
		numberOfRowsReturnened = c.getCount();
		// This array will hold an array of doubles storeing each days calories
		// burnt
		// 0 means Wed onto Tues which is six
		// double[] weekExCalBurnt;
		// weekExCalBurnt = new double[7];

		// make array of objects to store nutritional data
		Nutrition[] nutrientLogsArray;
		nutrientLogsArray = new Nutrition[numberOfRowsReturnened];

		for (int j = 0; j < numberOfRowsReturnened; j++) {
			nutrientLogsArray[j] = new Nutrition();
		}

		// data from the UserNutritionLogTable
		int iLog_id;
		int iNutrition_id;
		int istartTime_n;
		int ilogFrequency;
		int iuser_id;

		// data from the NutritionTable
		int ifoodType_for_RDA;
		int ifoodType_for_display;
		int iFoodOrNutrientName;
		int icaloriesPerMinPortion;
		int inumberOfContainers;

		int icontainerType;
		int imeasurement;
		int imeasurementUnit;

		iLog_id = c.getColumnIndexOrThrow("_id");
		iNutrition_id = c.getColumnIndexOrThrow("NutritionId_FK");
		istartTime_n = c.getColumnIndexOrThrow("startTime");
		;
		ilogFrequency = c.getColumnIndex("logFrequency");
		iuser_id = c.getColumnIndex("user_id_FK");

		// data from the NutritionTable
		ifoodType_for_RDA = c.getColumnIndex("NutritionRecommendationTable_FK");
		ifoodType_for_display = c.getColumnIndex("foodType");
		iFoodOrNutrientName = c.getColumnIndex("FoodOrNutrientName");
		icaloriesPerMinPortion = c.getColumnIndex("caloriesPerMinPortion");
		inumberOfContainers = c.getColumnIndex("numberOfContainers");

		icontainerType = c.getColumnIndex("containerType");
		imeasurement = c.getColumnIndex("measurement");
		imeasurementUnit = c.getColumnIndex("measurementUnit");

		i = 0;
		// we then iterate through the curser taking out the results and
		// assigning to the objects.
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			nutrientLogsArray[i].setLog_id(c.getLong(iLog_id));
			nutrientLogsArray[i].setNutrition_id(c.getLong(iNutrition_id));
			nutrientLogsArray[i].setStartTime(c.getLong(istartTime_n));
			nutrientLogsArray[i].setLogFrequency(c.getString(ilogFrequency));
			nutrientLogsArray[i].setUser_id(c.getLong(iuser_id));
			nutrientLogsArray[i].setFoodType_for_RDA(c
					.getString(ifoodType_for_RDA));
			nutrientLogsArray[i].setFoodType_for_display(c
					.getString(ifoodType_for_display));
			nutrientLogsArray[i].setFoodOrNutrientName(c
					.getString(iFoodOrNutrientName));
			nutrientLogsArray[i].setCaloriesPerMinPortion(c
					.getInt(icaloriesPerMinPortion));
			nutrientLogsArray[i].setNumberOfContainers(c
					.getInt(inumberOfContainers));
			nutrientLogsArray[i].setContainerType(c.getString(icontainerType));
			nutrientLogsArray[i].setMeasurement(c.getInt(imeasurement));
			nutrientLogsArray[i].setMeasurementUnit(c
					.getString(imeasurementUnit));
			i++;
		}

		c.close(); // closing curser

		HealthCalculations calculations = new HealthCalculations();
		calculations.calculateweekExCalBurnt(exerciseLogsArray);

		calculations.calculateweekNuCalConsumed(nutrientLogsArray);
		calculations.calculateFoodTypeRecord(nutrientLogsArray);
		calculations.calculateReccommendedFoodType();
	

		// mDbHelper.sampletestQuerys();

		mDbHelper.close();
	  
	  
	  
	  
		
    String[] titles = new String[] { "Recommended Consumption", "Your Consumption" };
    List<double[]> values = new ArrayList<double[]>();
    values.add(HealthCalculations.getRda());
    values.add(calculations.getFoodTypeAverageConSumtion());
    int[] colors = new int[] { Color.RED, Color.BLUE };
    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
    renderer.setOrientation(Orientation.VERTICAL);
    setChartSettings(renderer, "Weeks Calories Consumed versus Calories Burnt", "FoodType", "percentage", 0,
        7, 0, .5, Color.GRAY, Color.LTGRAY);
    
    
   //Grain,1 Vegetable, 2 Fruit, 3 Protein, ,4 Dairy, 5 Snack
    renderer.setXLabels(1);
    renderer.setYLabels(10);
    renderer.addXTextLabel(1, "Grain");
    renderer.addXTextLabel(2, "Vegetables");
    renderer.addXTextLabel(3, "Fruit");
    renderer.addXTextLabel(4, "Protein");
    renderer.addXTextLabel(5, "Dairy");
    renderer.addXTextLabel(6, "Snack");

    int length = renderer.getSeriesRendererCount();
    for (int j = 0; j < length; j++) {
      SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(j);
      seriesRenderer.setDisplayChartValues(true);
    }
    return ChartFactory.getBarChartIntent(context, buildBarDataset(titles, values), renderer,
        Type.DEFAULT);
  }

}
