package com.black.healthbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ChartMenuActivity extends Activity implements OnClickListener{
	
  private Button exerciseButton;
  private Button nutritionButton;
  private Button healthButton;
  private Button menuButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.chart_menu);

      // wire the UI to the code
		exerciseButton = (Button) findViewById(R.id.exercise_chart_button);
		exerciseButton.setOnClickListener(this);
		
	    nutritionButton = (Button) findViewById(R.id.nutrition_chart_button);
		nutritionButton.setOnClickListener(this);
		
	    healthButton = (Button) findViewById(R.id.health_chart_button);
	    healthButton.setOnClickListener(this);
	    
	    menuButton = (Button) findViewById(R.id.menu_button);
	    menuButton.setOnClickListener(this);

  }
  
  
  // implement interface methods
  
	@Override
	public void onClick(View v) {
		if (exerciseButton.getId() == ((Button) v).getId()){
			Intent toExerciseChart = new Intent(this, ExerciseChartActivity.class); 
			this.startActivity(toExerciseChart);
		}
		else if (nutritionButton.getId() == ((Button) v).getId()){
			Intent toNutritionChart = new Intent(this, NutritionChartActivity.class); 
			this.startActivity(toNutritionChart);
		}
		else if (healthButton.getId() == ((Button) v).getId()){
			Intent toHealthChart = new Intent(this, HealthChartActivity.class); 
			this.startActivity(toHealthChart);
		}
		else if (menuButton.getId() == ((Button) v).getId()){
			Intent toMenu = new Intent(this, MenuActivity.class);
			this.startActivity(toMenu);
		}

	}
}


