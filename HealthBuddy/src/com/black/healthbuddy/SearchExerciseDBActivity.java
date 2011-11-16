package com.black.healthbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;


public class SearchExerciseDBActivity extends Activity implements OnClickListener {

//    private TextView messageTextView;    
    private Button menuButton;
    private Button addExerciseButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_db_result);
        
        // wire the UI to the code	
        addExerciseButton = (Button) findViewById(R.id.add_exercise_button);
        addExerciseButton.setOnClickListener(this);
	    
	    menuButton = (Button) findViewById(R.id.menu_button);
	    menuButton.setOnClickListener(this);
		
    }

    // launch activities by button click, with intents
	@Override
	public void onClick(View v) {
		if (addExerciseButton.getId() == ((Button) v).getId()){
			Intent toAddExercise = new Intent(this,AddExerciseActivity.class);
			this.startActivity(toAddExercise);
		}
		else if (menuButton.getId() == ((Button) v).getId()){
			Intent toMenu = new Intent(this,MenuActivity.class);
			this.startActivity(toMenu);
		}
	}
}


