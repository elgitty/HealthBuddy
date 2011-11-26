package com.black.healthbuddy;

import java.util.Calendar;

import com.black.healthbuddy.model.HealthBuddyDbAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
//import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;

//import android.widget.TextView;
//import android.widget.Toast;

public class SearchNutritionDBActivity extends Activity implements
		OnClickListener {

	// private TextView messageTextView;
	private Button menuButton;
	private Button addButton;
	private Button refineButton;
	private Spinner s_meal;
	private Spinner s_foodtype;
	private Spinner s_day;
	private Spinner s_portion;
	private HealthBuddyDbAdapter mDbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the view defined by Main.xml
		setContentView(R.layout.nutrition_db_result);

		mDbHelper = new HealthBuddyDbAdapter(this);
		mDbHelper.open();

		String[] my_search = new String[] { "FoodOrNutrientName", "_id" };
		Cursor c = mDbHelper.queryTable("NutritionTable", my_search, null,
				null, null, null, null);
		startManagingCursor(c);
		String[] from = new String[] { "FoodOrNutrientName" };
		int[] to = new int[] { android.R.id.text1 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, c, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		s_meal = (Spinner) findViewById(R.id.search_spinner);
		s_meal.setAdapter(adapter);

		// wire the UI to the code
		addButton = (Button) findViewById(R.id.add_nutrition_button);
		addButton.setOnClickListener(this);

		refineButton = (Button) findViewById(R.id.refine_button);
		refineButton.setOnClickListener(this);

		menuButton = (Button) findViewById(R.id.menu_button);
		menuButton.setOnClickListener(this);

		ArrayAdapter<CharSequence> day_adapter = ArrayAdapter
				.createFromResource(this, R.array.day_array,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		s_day = (Spinner) findViewById(R.id.day_spinner);
		s_day.setAdapter(day_adapter);

		ArrayAdapter<CharSequence> meal_adapter = ArrayAdapter
				.createFromResource(this, R.array.meal_array,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		s_foodtype = (Spinner) findViewById(R.id.meal_spinner);
		s_foodtype.setAdapter(meal_adapter);

		ArrayAdapter<CharSequence> portion_adapter = ArrayAdapter
				.createFromResource(this, R.array.portion_array,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		s_portion = (Spinner) findViewById(R.id.portion_spinner);
		s_portion.setAdapter(portion_adapter);
		mDbHelper.close();
	}

	// launch activities by button click, with intents
	@Override
	public void onClick(View v) {
		if (addButton.getId() == ((Button) v).getId()) {
			int portion = Integer.parseInt(s_portion.getSelectedItem().toString());
			String day = s_day.getSelectedItem().toString();
			if (day.equals("Today Only"))
			{
				Calendar calendar = Calendar.getInstance();
			  int weekday = calendar.get(Calendar.DAY_OF_WEEK);
			  if (weekday==1){day = "Sundays";}
			  else if (weekday==2){day = "Mondays";}
			  else  if (weekday==3){day = "Tuesdays";}
			  else if (weekday==4){day = "Wednesdays";}
			  else if (weekday==5){day = "Thursdays";}
			  else if (weekday==6){day = "Fridays";}
			  else if (weekday==7){day = "Saturdays";}
			}
//			String meal = s_meal.getSelectedItem().toString();


			//int duration = (Integer) s_time.getItemAtPosition(s_time.getSelectedItemPosition());
			//duration = ((Integer)s_time.getItemAtPosition(s_time.getSelectedItemPosition())).intValue();
	 		//duration = ((Number)s_time.getSelectedItem()).intValue();
			mDbHelper.open();
			mDbHelper.createUserNutritionLog(s_meal.getSelectedItemPosition() + 1, System.currentTimeMillis() % 86400000, -1, day, portion, 1);
		//	createUserExerciseLog(s_exercise.getSelectedItemPosition() + 1,
		//			System.currentTimeMillis() % 86400000,	-1, duration, day2, 1);
			mDbHelper.close();
			
			
			
			Intent toAdd = new Intent(this, AddNutritionActivity.class);
			this.startActivity(toAdd);
		} else if (menuButton.getId() == ((Button) v).getId()) {
			Intent toMenu = new Intent(this, MenuActivity.class);
			this.startActivity(toMenu);
		}
	}
}
