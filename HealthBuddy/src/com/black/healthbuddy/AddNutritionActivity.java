package com.black.healthbuddy;

import com.black.healthbuddy.model.HealthBuddyDbAdapter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class AddNutritionActivity extends ListActivity implements
		OnClickListener {
	private HealthBuddyDbAdapter mDbHelper;
	private Button menuButton;
	private Button searchButton;
	private Button deleteButton;
	private EditText delete_item;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_nutrition);
		mDbHelper = new HealthBuddyDbAdapter(this);
		mDbHelper.open();
		fillData();

		menuButton = (Button) findViewById(R.id.menu_button);
		menuButton.setOnClickListener(this);

		searchButton = (Button) findViewById(R.id.searchDB_button);
		searchButton.setOnClickListener(this);

		deleteButton = (Button) findViewById(R.id.delete);
		deleteButton.setOnClickListener(this);

		delete_item = (EditText) findViewById(R.id.record_to_delete);
	}
//*****************************************************************************************//
	
	
	
	
	private void fillData() {
		// Get all of the notes from the database and create the item list
		String[] my_search = new String[] { "UserNutritionLogTable._id",
				"UserNutritionLogTable.logPortion", "NutritionTable.FoodOrNutrientName",
				"NutritionTable.caloriesPerMinPortion",
				"NutritionTable.foodMinPortion" };

		Cursor c = mDbHelper
				.queryTable(
						"UserNutritionLogTable JOIN NutritionTable ON (UserNutritionLogTable.NutritionId_FK = NutritionTable._id)",
						my_search, null, null, null, null, null);
		startManagingCursor(c);

		String[] from = new String[] { "UserNutritionLogTable._id",
				"NutritionTable.FoodOrNutrientName",
				"UserNutritionLogTable.logPortion",
				"NutritionTable.caloriesPerMinPortion" };
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
			Intent toSearch = new Intent(this, SearchNutritionDBActivity.class);
			this.startActivity(toSearch);
		} else if (deleteButton.getId() == ((Button) v).getId()) {
			// put code to delete entry here
			String delete_ID = delete_item.getText().toString();
			long row_ID = Long.parseLong(delete_ID);
	//		Toast.makeText(this, delete_ID, Toast.LENGTH_LONG).show();
			mDbHelper.open();
			mDbHelper.deleteRecordInTable(row_ID, "UserNutritionLogTable");
			mDbHelper.close();
			//Toast.makeText( s_time.getContext(), "The day is " + weekday, Toast.LENGTH_LONG).show();

			Intent toSelf = new Intent(this, AddNutritionActivity.class);
			this.startActivity(toSelf);
		}
	}

}
