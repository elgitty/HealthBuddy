package com.black.healthbuddy;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CalendarActivity extends ListActivity implements OnClickListener {

	private Button menuButton;
	private Button exerciseButton;
	private Button foodButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);

		menuButton = (Button) findViewById(R.id.menu_button);
		menuButton.setOnClickListener(this);

		exerciseButton = (Button) findViewById(R.id.exercise_view_button);
		exerciseButton.setOnClickListener(this);

		foodButton = (Button) findViewById(R.id.food_view_button);
		foodButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (menuButton.getId() == ((Button) v).getId()) {
			Intent toMenu = new Intent(this, MenuActivity.class);
			this.startActivity(toMenu);
		} else if (foodButton.getId() == ((Button) v).getId()) {
			Intent toProfile = new Intent(this, AddNutritionActivity.class);
			this.startActivity(toProfile);
		} else if (exerciseButton.getId() == ((Button) v).getId()) {
			Intent toExercise = new Intent(this, AddExerciseActivity.class);
			this.startActivity(toExercise);
		}
	}
}
