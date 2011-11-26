package com.black.healthbuddy;

import java.util.Calendar;

import com.black.healthbuddy.model.HealthBuddyDbAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchExerciseDBActivity extends Activity implements
		OnClickListener {

	private Button menuButton;
	private Button addExerciseButton;
	private Spinner s_exercise;
	private Spinner s_time;
	private Spinner s_day;
	private HealthBuddyDbAdapter mDbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercise_db_result);

		////////Exercise Spinner
		mDbHelper = new HealthBuddyDbAdapter(this);
		mDbHelper.open();
		String[] my_search = new String[] { "exerciseName", "_id" };
		Cursor c = mDbHelper.queryTable("ExerciseTable", my_search, null, null,
				null, null, null);
		startManagingCursor(c);

		String[] from = new String[] { "exerciseName" };
		int[] to = new int[] { android.R.id.text1 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, c, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s_exercise = (Spinner) findViewById(R.id.spinner1);
		s_exercise.setAdapter(adapter);

		////////Exercise Spinner
		addExerciseButton = (Button) findViewById(R.id.add_exercise_button);
		addExerciseButton.setOnClickListener(this);

		menuButton = (Button) findViewById(R.id.menu_button);
		menuButton.setOnClickListener(this);

		////////Duration Spinner
		ArrayAdapter<CharSequence> time_adapter = ArrayAdapter.createFromResource(this, R.array.duration_array,
						android.R.layout.simple_spinner_item);
		time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

		s_time = (Spinner) findViewById(R.id.spinner2);
		s_time.setAdapter(time_adapter);

		
		////////Day Spinner
		ArrayAdapter<CharSequence> day_adapter = ArrayAdapter.createFromResource(this, R.array.day_array,
						android.R.layout.simple_spinner_item);
		day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

		s_day = (Spinner) findViewById(R.id.day_spinner);
		s_day.setAdapter(day_adapter);
		
		
//		int duration = Integer.parseInt(s_time.getSelectedItem().toString());
////
		Calendar calendar = Calendar.getInstance();

		  int weekday = calendar.get(Calendar.DAY_OF_WEEK);

		Toast.makeText( s_time.getContext(), "The day is " + weekday, Toast.LENGTH_LONG).show();
////		
//		
		
		mDbHelper.close();

	}

	@Override
	public void onClick(View v) {
		if (addExerciseButton.getId() == ((Button) v).getId()) {
			int duration = Integer.parseInt(s_time.getSelectedItem().toString());
//			String day = Integer.toString(s_day.getSelectedItemPosition());
			String day2 = s_day.getSelectedItem().toString();
			if (day2.equals("Today Only"))
			{
				Calendar calendar = Calendar.getInstance();
			  int weekday = calendar.get(Calendar.DAY_OF_WEEK);
			  if (weekday==1){day2 = "Sundays";}
			  else if (weekday==2){day2 = "Mondays";}
			  else  if (weekday==3){day2 = "Tuesdays";}
			  else if (weekday==4){day2 = "Wednesdays";}
			  else if (weekday==5){day2 = "Thursdays";}
			  else if (weekday==6){day2 = "Fridays";}
			  else if (weekday==7){day2 = "Saturdays";}
			}
//			
			
			

			//int duration = (Integer) s_time.getItemAtPosition(s_time.getSelectedItemPosition());
			//duration = ((Integer)s_time.getItemAtPosition(s_time.getSelectedItemPosition())).intValue();
	 		//duration = ((Number)s_time.getSelectedItem()).intValue();
			
			mDbHelper.open();
			mDbHelper.createUserExerciseLog(s_exercise.getSelectedItemPosition() + 1,
					System.currentTimeMillis() % 86400000,
					-1, duration, day2, 1);
			mDbHelper.close();

			Intent toAddExercise = new Intent(this, AddExerciseActivity.class);
			this.startActivity(toAddExercise);
		} else if (menuButton.getId() == ((Button) v).getId()) {
			Intent toMenu = new Intent(this, MenuActivity.class);
			this.startActivity(toMenu);
		}
	}
}
