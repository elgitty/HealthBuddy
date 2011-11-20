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

import com.black.healthbuddy.model.HealthBuddyDbAdapter;

public class UserProfileActivity extends Activity implements OnClickListener {

	// private static final String EMPTY_STRING = "";

	// private TextView calorieBankTextView;
	private Button menuButton;
	private Button chartsButton;
	private Button editButton;

	// database object
	private HealthBuddyDbAdapter mDbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// get the data needed back from the database

		mDbHelper = new HealthBuddyDbAdapter(this);
		mDbHelper.open();
		
		//This method will do the following 
		/////////1		query the table UserProfileTable for the user with an id of number 1 which will return a cursor reference to that users record
		/////////2 		assign the curser values to an array of strings
		/////////3		create a new layout to hold the inflated xml
		/////////4		turn the user_profile xml into java code(inflating)
		/////////5 		assigning values from the queryed array of strings to the Layout variables
		/////////6		adding the view to the layout holder
		/////////7		then finally displaying the contents to the user
		fillinProfile(mDbHelper);

		mDbHelper.close();
		
		// wire the UI to the code
		editButton = (Button) findViewById(R.id.edit_profile_button);
		editButton.setOnClickListener(this);

		chartsButton = (Button) findViewById(R.id.charts_button);
		chartsButton.setOnClickListener(this);

		menuButton = (Button) findViewById(R.id.menu_button);
		menuButton.setOnClickListener(this);

	}


	// launch activities by button click, with intents
	@Override
	public void onClick(View v) {
		if (editButton.getId() == ((Button) v).getId()) {
			Intent toEditProfile = new Intent(this,
					EditUserProfileActivity.class);
			this.startActivity(toEditProfile);
		} else if (chartsButton.getId() == ((Button) v).getId()) {
			Intent toChart = new Intent(this, ChartMenuActivity.class);
			this.startActivity(toChart);
		} else if (menuButton.getId() == ((Button) v).getId()) {
			Intent toMenu = new Intent(this, MenuActivity.class);
			this.startActivity(toMenu);
		}
	}
	
	
	public void fillinProfile(HealthBuddyDbAdapter mDbHelper) 
	{
		
		/////////1		query the table UserProfileTable for the user with an id of number 1 which will return a cursor reference to that users record
		//query the database
		Cursor c = mDbHelper.queryTable("UserProfileTable", null,
				"UserProfileTable._id = 1", null, null, null, null);

		int i_id = c.getColumnIndexOrThrow("_id");
		int i_userName = c.getColumnIndexOrThrow("userName");
		int i_userAge = c.getColumnIndexOrThrow("userAge");
		int i_userHeight = c.getColumnIndexOrThrow("userHeight");
		int i_userWeight = c.getColumnIndexOrThrow("userWeight");
		int i_userGender = c.getColumnIndexOrThrow("userSex");
		int i_userCalories = c.getColumnIndexOrThrow("userCalorieBank");

		String[] userDetail = { "id", "name", "age", "height", "weight",
				"gender", "calorie_bank" };
		
		
		
		
		/////////2 		assign the curser values to an array of strings
		// we then iterate through the curser taking out the results
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			userDetail[0] = c.getString(i_id);
			userDetail[1] = c.getString(i_userName);
			userDetail[2] = c.getString(i_userAge);
			userDetail[3] = c.getString(i_userHeight);
			userDetail[4] = c.getString(i_userWeight);
			userDetail[5] = c.getString(i_userGender);
			userDetail[6] = c.getString(i_userCalories);
		}

		c.close(); // closing cursor

		// instantating the LayoutInflater class so we can inflate our xml and
		// turn them into java objects
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		
		
		
		/////////3		create a new layout to hold the inflated xml
		LinearLayout layoutHolder = new LinearLayout(this);
		// setting its orientation
		layoutHolder.setOrientation(LinearLayout.VERTICAL);

		
		
		
		/////////4		turn the user_profile xml into java code(inflating)
		View itemView = inflater.inflate(R.layout.user_profile, null);

		/////////5 		assigning values from the queryed array of strings to the Layout variables
		// ///////////////changing the default values from xml
		// finding the view with the id user_name and casting it into a TextView
		// object
		TextView user_name = (TextView) itemView.findViewById(R.id.user_name);
		// reassigning the xml value with R.id.user_name
		user_name.setText(userDetail[1]);

		// finding the view with the id user_age and casting it into a TextView
		// object
		TextView user_age = (TextView) itemView.findViewById(R.id.user_age);
		// reassigning the xml value with R.id.user_age
		user_age.setText(userDetail[2]);

		// finding the view with the id user_height and casting it into a TextView
		// object
		TextView user_height = (TextView) itemView
				.findViewById(R.id.user_height);
		// reassigning the xml value with R.id.user_height
		user_height.setText(userDetail[3]);

		// finding the view with the id user_weight and casting it into a TextView
		// object
		TextView user_weight = (TextView) itemView
				.findViewById(R.id.user_weight);
		// reassigning the xml value with R.id.user_weight
		user_weight.setText(userDetail[4]);

		// finding the view with the id user_gender and casting it into a TextView
		TextView user_gender = (TextView) itemView
				.findViewById(R.id.user_gender);
		// reassigning the xml value with R.id.user_gender
		user_gender.setText(userDetail[5]);

		// finding the view with the id user_calaries and casting it into a TextView
		TextView user_calories = (TextView) itemView
				.findViewById(R.id.user_calarie_bank);
		// reassigning the xml value with R.id.user_calarie_bank
		user_calories.setText(userDetail[6]);

		/////////6		adding the view to the layout holder
		layoutHolder.addView(itemView);

		/////////7		then finally displaying the contents to the user
		setContentView(layoutHolder);
	}

}
