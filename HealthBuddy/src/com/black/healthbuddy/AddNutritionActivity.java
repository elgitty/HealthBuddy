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

public class AddNutritionActivity extends ListActivity implements
		OnClickListener {
	private HealthBuddyDbAdapter mDbHelper;
	private Button menuButton;
	private Button searchButton;
	private Button deleteButton;
	private EditText delete_item;
	private Button refineButton;
	private Button updateButton;
	private Spinner s_meal;
	private Spinner s_foodtype;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_nutrition);
		mDbHelper = new HealthBuddyDbAdapter(this);
		mDbHelper.open();
		fillData();

		String search_condition = null;
		Bundle extras = getIntent().getExtras();
		if(extras !=null) {
			search_condition = extras.getString("criteria");
		}
		
		
		String[] my_search = new String[] { "FoodOrNutrientName", "_id" };
		Cursor c = mDbHelper.queryTable("NutritionTable", my_search, search_condition,
				null, null, null, null);
		startManagingCursor(c);
		String[] from = new String[] { "FoodOrNutrientName" };
		int[] to = new int[] { android.R.id.text1 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, c, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		s_meal = (Spinner) findViewById(R.id.search_spinner);
		s_meal.setAdapter(adapter);

//		c.close();

		ArrayAdapter<CharSequence> meal_adapter = ArrayAdapter
				.createFromResource(this, R.array.meal_array,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		s_foodtype = (Spinner) findViewById(R.id.meal_spinner);
		s_foodtype.setAdapter(meal_adapter);

		menuButton = (Button) findViewById(R.id.menu_button);
		menuButton.setOnClickListener(this);

		searchButton = (Button) findViewById(R.id.searchDB_button);
		searchButton.setOnClickListener(this);

		deleteButton = (Button) findViewById(R.id.delete);
		deleteButton.setOnClickListener(this);
		
		refineButton = (Button) findViewById(R.id.refine_button);
		refineButton.setOnClickListener(this);

		updateButton = (Button) findViewById(R.id.update);
		updateButton.setOnClickListener(this);
		delete_item = (EditText) findViewById(R.id.record_to_delete);
		
	}

	// *****************************************************************************************//

	private void fillData() {

		String day = "";

		Calendar calendar = Calendar.getInstance();
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		if (weekday == 1) {
			day = "Sundays";
		} else if (weekday == 2) {
			day = "Mondays";
		} else if (weekday == 3) {
			day = "Tuesdays";
		} else if (weekday == 4) {
			day = "Wednesdays";
		} else if (weekday == 5) {
			day = "Thursdays";
		} else if (weekday == 6) {
			day = "Fridays";
		} else if (weekday == 7) {
			day = "Saturdays";
		}

		// Get all of the notes from the database and create the item list
		String[] my_search = new String[] { "UserNutritionLogTable._id",
				"UserNutritionLogTable.logPortion",
				"NutritionTable.FoodOrNutrientName",
				"NutritionTable.caloriesPerMinPortion",
				"NutritionTable.foodMinPortion" };

		Cursor c = mDbHelper
				.queryTable(
						"UserNutritionLogTable JOIN NutritionTable ON (UserNutritionLogTable.NutritionId_FK = NutritionTable._id)",
						my_search, "UserNutritionLogTable.logFrequency = '"
								+ day + "'", null, null, null, null);
		startManagingCursor(c);

		String[] from = new String[] { "UserNutritionLogTable._id",
				"NutritionTable.FoodOrNutrientName",
				"NutritionTable.caloriesPerMinPortion" };
		int[] to = new int[] { R.id.text1, R.id.text2, R.id.text3 };

		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.row_food, c, from, to);
		setListAdapter(notes);
	}

	@Override
	public void onClick(View v) {
		if (menuButton.getId() == ((Button) v).getId()) {
			Intent toMenu = new Intent(this, MenuActivity.class);
			this.startActivity(toMenu);
		} else if (searchButton.getId() == ((Button) v).getId()) {
			Intent toSearch = new Intent(this, SearchNutritionDBActivity.class);
			this.startActivity(toSearch);
		} else if (deleteButton.getId() == ((Button) v).getId()) {
			// put code to delete entry here
			String delete_ID = delete_item.getText().toString();

			try {
				long row_ID = Long.parseLong(delete_ID);
				mDbHelper.open();
				mDbHelper.deleteRecordInTable(row_ID, "UserNutritionLogTable");
				mDbHelper.close();
				Intent toSelf = new Intent(this, AddNutritionActivity.class);
				this.startActivity(toSelf);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();   
				Toast.makeText(this, "You must specify an ID", Toast.LENGTH_LONG).show();
				}
			// 
			

		} else if (updateButton.getId() == ((Button) v).getId()) {
			String update_ID = delete_item.getText().toString();
			
			try {
			long row_ID = Long.parseLong(update_ID);

			mDbHelper.open();

//			int duration = Integer
//					.parseInt(s_time.getSelectedItem().toString());
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



			String[] my_search = new String[] { "NutritionTable.caloriesPerMinPortion"};

			Cursor c = mDbHelper
					.queryTable(
							"UserNutritionLogTable JOIN NutritionTable ON (UserNutritionLogTable.NutritionId_FK = NutritionTable._id)",
							my_search,
							"UserNutritionLogTable._id = "+(row_ID), null,
							null, null, null);
			startManagingCursor(c);
			
			long resultA = 0;

			//
		//	int i_minDur = c.getColumnIndexOrThrow("ExerciseTable.minimumDuration");
			int i_calPerMinPor = c.getColumnIndexOrThrow("NutritionTable.caloriesPerMinPortion");


			 for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			//		resultA = (int) (c.getInt(i_minDur)*c.getLong(i_calPerMinDur));
					resultA = (c.getLong(i_calPerMinPor));
			 }
			
			c.close();
			
					
			
			
			mDbHelper.updateUserNutritionLog(row_ID, (int) s_meal.getSelectedItemId(),
					System.currentTimeMillis() % 86400000, resultA, day2, 1, 1);
			mDbHelper.close();
			// mDbHelper.updateUserExerciseLog(,s_exercise.getSelectedItemPosition()
			// + 1,
			// System.currentTimeMillis() % 86400000, -1, duration, day2, 1);
			Intent toSelf = new Intent(this, AddNutritionActivity.class);

			this.startActivity(toSelf);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();   
				Toast.makeText(this, "You must specify an ID", Toast.LENGTH_LONG).show();
				}
		}
		else if (refineButton.getId() == ((Button) v).getId()) {
			Intent toSelf = new Intent(this, AddNutritionActivity.class);
			String mystring = s_foodtype.getSelectedItem().toString();
//			Toast.makeText( this, mystring, Toast.LENGTH_LONG).show();

		if (mystring.equals("All foods")){mystring = "null";}
		else mystring = "NutritionTable.foodType = '"+mystring+"'";
		
			toSelf.putExtra("criteria",mystring);

			this.startActivity(toSelf);
		}
	}

}
