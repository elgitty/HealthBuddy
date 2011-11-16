package com.black.healthbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.TextView;

public class UserProfileActivity extends Activity implements OnClickListener {
	
//    private static final String EMPTY_STRING = "";

//    private TextView calorieBankTextView;
    private Button menuButton;
    private Button chartsButton;
    private Button editButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile); 
        
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
		if (editButton.getId() == ((Button) v).getId()){
		Intent toEditProfile = new Intent(this, EditUserProfileActivity.class); 
		this.startActivity(toEditProfile);
		}
		else if (chartsButton.getId() == ((Button) v).getId()){
			Intent toChart = new Intent(this, ChartMenuActivity.class);
			this.startActivity(toChart);
		}
		else if (menuButton.getId() == ((Button) v).getId()){
			Intent toMenu = new Intent(this, MenuActivity.class);
			this.startActivity(toMenu);
		}
	}
}


