package com.black.healthbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.LinearLayout;

//import android.widget.TextView;
//import android.widget.Toast;


public class SearchNutritionDBActivity extends Activity implements OnClickListener{

//    private TextView messageTextView;    
    private Button menuButton;
    private Button addButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the view defined by Main.xml
        setContentView(R.layout.exercise_db_result);
        
        // wire the UI to the code	
        addButton = (Button) findViewById(R.id.add_exercise_button);
        addButton.setOnClickListener(this);
	    
	    menuButton = (Button) findViewById(R.id.menu_button);
	    menuButton.setOnClickListener(this);
		
    }

    // launch activities by button click, with intents
	@Override
	public void onClick(View v) {
		if (addButton.getId() == ((Button) v).getId()){
			Intent toAdd = new Intent(this,AddExerciseActivity.class);
			this.startActivity(toAdd);
		}
		else if (menuButton.getId() == ((Button) v).getId()){
			Intent toMenu = new Intent(this,MenuActivity.class);
			this.startActivity(toMenu);
		}
	}
}


