package com.black.healthbuddy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.black.healthbuddy.model.Exercise;
import com.black.healthbuddy.model.HealthBuddyDbAdapter;
import com.black.healthbuddy.model.HealthCalculations;
import com.black.healthbuddy.model.Nutrition;

public class MenuActivity extends Activity implements OnClickListener {

	// private static final String EMPTY_STRING = "";

	// private TextView calorieBankTextView;
	// private TextView adviceTextView;
	// private TextView messageTextView;
	private Button exerciseButton;
	private Button nutritionButton;
	private Button chartsButton;
	private Button userButton;
	private Button calendarDayViewButton;
	private Button settingsButton;

	// database object
	private HealthBuddyDbAdapter mDbHelper;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.menu);

		// ////////Database stuff
		mDbHelper = new HealthBuddyDbAdapter(this);
		mDbHelper.open();
		mDbHelper.dropAndRecreateTables();
		mDbHelper.insertDataIntoTables();

		// //query for all nutritional logs
		//
		// Cursor c =
		// this.queryTable(
		// "UserNutritionLogTable JOIN NutritionTable ON (UserNutritionLogTable.NutritionId_FK  = NutritionTable._id)",
		// null,
		// null,
		// null, null, null, null);

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

		// instantating the LayoutInflater class so we can inflate our xml and
		// turn them into java objects
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		// ///////3 create a new layout to hold the inflated xml
		LinearLayout layoutHolder = new LinearLayout(this);
		// setting its orientation
		layoutHolder.setOrientation(LinearLayout.VERTICAL);

		// ///////4 turn the user_profile xml into java code(inflating)
		View itemView = inflater.inflate(R.layout.menu, null);

		// ///////////////changing the default values from xml
		// finding the view with the id user_name and casting it into a TextView
		// object

		TextView offset = (TextView) itemView.findViewById(R.id.offset);
		// reassigning the xml value with R.id.user_name
		offset.setText(Double.toString(calculations.getDaysCalOffset(0)));

		TextView consumed = (TextView) itemView.findViewById(R.id.consumed);
		// reassigning the xml value with R.id.user_name
		consumed.setText(Double.toString(calculations.getDaysCalConsumed(0)));

		TextView burnt = (TextView) itemView.findViewById(R.id.burnt);
		// reassigning the xml value with R.id.user_name
		burnt.setText(Double.toString(calculations.getDaysCalBurnt(0)));

		TextView calorieOffset = (TextView) itemView
				.findViewById(R.id.message_text_view_Low_Advice);
		// reassigning the xml value with R.id.user_name
		calorieOffset.setText(calculations.getHighFoodType());

		TextView highAdvice = (TextView) itemView
				.findViewById(R.id.message_text_view_High_Advice);
		// reassigning the xml value with R.id.user_name
		highAdvice.setText(calculations.getLowFoodType());

		TextView lowAdvice = (TextView) itemView
				.findViewById(R.id.message_text_view_Low_Advice);
		// reassigning the xml value with R.id.user_name
		lowAdvice.setText(calculations.getHighFoodType());

		// ///////6 adding the view to the layout holder
		layoutHolder.addView(itemView);

		// ///////7 then finally displaying the contents to the user
		setContentView(layoutHolder);

		// wire the UI to the code
		userButton = (Button) findViewById(R.id.user_button);
		userButton.setOnClickListener(this);

		exerciseButton = (Button) findViewById(R.id.exercise_button);
		exerciseButton.setOnClickListener(this);

		nutritionButton = (Button) findViewById(R.id.nutrition_button);
		nutritionButton.setOnClickListener(this);

		chartsButton = (Button) findViewById(R.id.charts_button);
		chartsButton.setOnClickListener(this);

		calendarDayViewButton = (Button) findViewById(R.id.day_view_button);
		calendarDayViewButton.setOnClickListener(this);

		settingsButton = (Button) findViewById(R.id.settings_button);
		settingsButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (userButton.getId() == ((Button) v).getId()) {
			Intent toProfile = new Intent(this, UserProfileActivity.class);
			this.startActivity(toProfile);
		} else if (exerciseButton.getId() == ((Button) v).getId()) {
			Intent toExercise = new Intent(this, ExerciseMenuActivity.class);
			this.startActivity(toExercise);
		} else if (nutritionButton.getId() == ((Button) v).getId()) {
			Intent toNutrition = new Intent(this, NutritionMenuActivity.class);
			this.startActivity(toNutrition);
		} else if (chartsButton.getId() == ((Button) v).getId()) {
			Intent toCharts = new Intent(this, ChartMenuActivity.class);
			this.startActivity(toCharts);
		} else if (calendarDayViewButton.getId() == ((Button) v).getId()) {
			Intent toCalendar = new Intent(this, CalendarDayViewActivity.class);
			this.startActivity(toCalendar);
		} else if (settingsButton.getId() == ((Button) v).getId()) {
			Intent toSettings = new Intent(this, SettingsActivity.class);
			this.startActivity(toSettings);
		}

	}
}
