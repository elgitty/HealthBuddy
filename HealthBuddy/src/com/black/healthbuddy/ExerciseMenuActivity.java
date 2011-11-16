package com.black.healthbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.LinearLayout;


public class ExerciseMenuActivity extends Activity implements OnClickListener {

//    private TextView messageTextView;    
    private Button menuButton;
    private Button searchDBButton;
    private Button recordRouteButton;
    private Button locatorButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_menu);

        
        // wire the UI to the code	
		searchDBButton = (Button) findViewById(R.id.searchDB_button);
		searchDBButton.setOnClickListener(this);
		
		recordRouteButton = (Button) findViewById(R.id.routes_button);
		recordRouteButton.setOnClickListener(this);
		
	    locatorButton = (Button) findViewById(R.id.locator_button);
	    locatorButton.setOnClickListener(this);
	    
	    menuButton = (Button) findViewById(R.id.menu_button);
	    menuButton.setOnClickListener(this);
		
    }

    // launch activities by button click, with intents
	@Override
	public void onClick(View v) {
		if (searchDBButton.getId() == ((Button) v).getId()){
		Intent toDBSearch = new Intent(this, SearchExerciseDBActivity.class);
		this.startActivity(toDBSearch);
		}
		else if (recordRouteButton.getId() == ((Button) v).getId()){
			Intent toRecordRoute = new Intent(this, RecordRoutesActivity.class);
			this.startActivity(toRecordRoute);
		}
		else if (locatorButton.getId() == ((Button) v).getId()){
			Intent toLocator = new Intent(this, LocatorActivity.class);
			this.startActivity(toLocator);
		}
		else if (menuButton.getId() == ((Button) v).getId()){
			Intent toMenu = new Intent(this, MenuActivity.class);
			this.startActivity(toMenu);
		}
	}
}


