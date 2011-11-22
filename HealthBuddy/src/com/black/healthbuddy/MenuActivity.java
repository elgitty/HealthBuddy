package com.black.healthbuddy;

import com.black.healthbuddy.model.HealthBuddyDbAdapter;
import com.black.healthbuddy.model.Nutrition;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
		setContentView(R.layout.menu);

		///////////Database stuff
		mDbHelper = new HealthBuddyDbAdapter(this);
		mDbHelper.open();
		////////testing code 
		mDbHelper.dropAndRecreateTables();
		mDbHelper.insertDataIntoTables();

		mDbHelper.sampletestQuerys();

		/////////testing code to show part of nutrition class working
		Nutrition nutritiona = new Nutrition();
		Nutrition nutritionb = new Nutrition();
		Nutrition nutritionc = new Nutrition();
		nutritiona.fillUpGivenNutLogId(1, mDbHelper);
		nutritionb.fillUpGivenNutLogId(2, mDbHelper);
		nutritionc.fillUpGivenNutLogId(3, mDbHelper);
		


		mDbHelper.close();

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
