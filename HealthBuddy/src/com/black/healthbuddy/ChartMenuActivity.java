package com.black.healthbuddy;




import com.black.healthbuddy.charts.CalBurntConsumedChart;
import com.black.healthbuddy.charts.IDemoChart;
import com.black.healthbuddy.charts.RDAReccomendedVerusConsumed;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChartMenuActivity extends Activity implements OnClickListener {

	private Button exerciseButton;
	private Button nutritionButton;
	private Button healthButton;
	private Button menuButton;

	private IDemoChart[] mCharts = new IDemoChart[] { new CalBurntConsumedChart(),
			new RDAReccomendedVerusConsumed() };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart_menu);

		// wire the UI to the code


		nutritionButton = (Button) findViewById(R.id.nutrition_chart_button);
		nutritionButton.setOnClickListener(this);
		
		exerciseButton = (Button) findViewById(R.id.exercise_chart_button);
		exerciseButton.setOnClickListener(this);

		healthButton = (Button) findViewById(R.id.health_chart_button);
		healthButton.setOnClickListener(this);

		menuButton = (Button) findViewById(R.id.menu_button);
		menuButton.setOnClickListener(this);

	}

	// implement interface methods

	@Override
	public void onClick(View v) {
		if (nutritionButton.getId() == ((Button) v).getId()) {
			// Intent toNutritionChart = new Intent(this,
			// NutritionChartActivity.class);
			// this.startActivity(toNutritionChart);

			// Intent toExerciseChart = new Intent(this,
			// ExerciseChartActivity.class);

			Intent intent = null;
			intent = mCharts[0].execute(this);
			startActivity(intent);
			// this.startActivity(toExerciseChart);
			
			
		} else if	(exerciseButton.getId() == ((Button) v).getId()) {
			// Intent toExerciseChart = new Intent(this,
			// ExerciseChartActivity.class);

			Intent intent = null;
			intent = mCharts[1].execute(this);
			startActivity(intent);
			// this.startActivity(toExerciseChart);
		} else if (healthButton.getId() == ((Button) v).getId()) {
			// Intent toHealthChart = new Intent(this,
			// HealthChartActivity.class);
			// this.startActivity(toHealthChart);
			Intent intent = null;
			intent = mCharts[0].execute(this);
			startActivity(intent);

		} else if (menuButton.getId() == ((Button) v).getId()) {
			Intent toMenu = new Intent(this, MenuActivity.class);
			this.startActivity(toMenu);
		}

	}
}
