package com.black.healthbuddy;

import java.util.Calendar;

import com.black.healthbuddy.model.HealthBuddyDbAdapter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AddExerciseActivity extends ListActivity implements
		OnClickListener {
	private HealthBuddyDbAdapter mDbHelper;
	private Button menuButton;
	private Button searchButton;
	private Button deleteButton;
	private EditText delete_item;
	private EditText update_item;
	
	private Button updateExerciseButton;
	private Spinner s_exercise;
	private Spinner s_time;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_exercise);

		mDbHelper = new HealthBuddyDbAdapter(this);
		mDbHelper.open();
		fillData();

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

		// //////Duration Spinner
		ArrayAdapter<CharSequence> time_adapter = ArrayAdapter
				.createFromResource(this, R.array.duration_array,
						android.R.layout.simple_spinner_item);
		time_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);

		s_time = (Spinner) findViewById(R.id.spinner2);
		s_time.setAdapter(time_adapter);

		menuButton = (Button) findViewById(R.id.menu_button);
		menuButton.setOnClickListener(this);

		searchButton = (Button) findViewById(R.id.searchDB_button);
		searchButton.setOnClickListener(this);

		deleteButton = (Button) findViewById(R.id.delete);
		deleteButton.setOnClickListener(this);

		updateExerciseButton = (Button) findViewById(R.id.update);
		updateExerciseButton.setOnClickListener(this);

		delete_item = (EditText) findViewById(R.id.record_to_delete);
		update_item = (EditText) findViewById(R.id.record_to_update);
	}

	private void fillData() {
		// Get all of the notes from the database and create the item list
		String[] my_search = new String[] { "ExerciseTable.exerciseName",
				"UserExerciseLogTable._id", "UserExerciseLogTable.logDuration",
				"ExerciseTable.minimumDuration",
				"ExerciseTable.caloriesPerMinDuration",
				"UserExerciseLogTable.logFrequency" };

		Cursor c = mDbHelper
				.queryTable(
						"UserExerciseLogTable JOIN ExerciseTable ON (UserExerciseLogTable.exerciseId_FK = ExerciseTable._id)",
						my_search,
						"UserExerciseLogTable.logFrequency = 'Fridays'", null,
						null, null, null);
		startManagingCursor(c);

		String[] from = new String[] { "UserExerciseLogTable._id",
				"ExerciseTable.exerciseName",
				"UserExerciseLogTable.logDuration",
				"ExerciseTable.caloriesPerMinDuration" };
		int[] to = new int[] { R.id.text1, R.id.text2, R.id.text3, R.id.text4 };

		SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.row,
				c, from, to);
		setListAdapter(notes);
	}

	@Override
	public void onClick(View v) {
		if (menuButton.getId() == ((Button) v).getId()) {
			Intent toMenu = new Intent(this, MenuActivity.class);
			this.startActivity(toMenu);
		} else if (searchButton.getId() == ((Button) v).getId()) {
			Intent toSearch = new Intent(this, SearchExerciseDBActivity.class);
			this.startActivity(toSearch);
		} else if (deleteButton.getId() == ((Button) v).getId()) {
			// put code to delete entry here
			String delete_ID = delete_item.getText().toString();
			long row_ID = Long.parseLong(delete_ID);
			// Toast.makeText(this, delete_ID, Toast.LENGTH_LONG).show();
			mDbHelper.open();
			mDbHelper.deleteRecordInTable(row_ID, "UserExerciseLogTable");
			mDbHelper.close();
			// Toast.makeText( s_time.getContext(), "The day is " + weekday,
			// Toast.LENGTH_LONG).show();

			Intent toSelf = new Intent(this, AddExerciseActivity.class);
			this.startActivity(toSelf);
		} else if (updateExerciseButton.getId() == ((Button) v).getId()) {
			String update_ID = update_item.getText().toString();
			long row_ID = Long.parseLong(update_ID);

			
			Toast.makeText( this, "The day is " + row_ID, Toast.LENGTH_LONG).show();
 
			mDbHelper.open();

			int duration = Integer
					.parseInt(s_time.getSelectedItem().toString());
			// String day = Integer.toString(s_day.getSelectedItemPosition());

			String day2 = "";

			Calendar calendar = Calendar.getInstance();
			int weekday = calendar.get(Calendar.DAY_OF_WEEK);
			if (weekday == 1) {
				day2 = "Sundays";
			} else if (weekday == 2) {
				day2 = "Mondays";
			} else if (weekday == 3) {
				day2 = "Tuesdays";
			} else if (weekday == 4) {
				day2 = "Wednesdays";
			} else if (weekday == 5) {
				day2 = "Thursdays";
			} else if (weekday == 6) {
				day2 = "Fridays";
			} else if (weekday == 7) {
				day2 = "Saturdays";
			}
			Toast.makeText( this, "The day is " + day2, Toast.LENGTH_LONG).show();

			mDbHelper.createUserExerciseLog(
					s_exercise.getSelectedItemPosition() + 1,
					System.currentTimeMillis() % 86400000, -1, duration, day2, 1);
			mDbHelper.close();
//			mDbHelper.updateUserExerciseLog(row_ID,s_exercise.getSelectedItemPosition() + 1,
//					System.currentTimeMillis() % 86400000, -1, duration, day2, 1);
			Intent toSelf = new Intent(this, AddExerciseActivity.class);
			this.startActivity(toSelf);
		}
	}

}
