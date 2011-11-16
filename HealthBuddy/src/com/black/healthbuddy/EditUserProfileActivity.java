package com.black.healthbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.TextView;

public class EditUserProfileActivity extends Activity implements OnClickListener {
	
//    private static final String EMPTY_STRING = "";

//    private TextView calorieBankTextView;
    private Button menuButton;
    private Button saveButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_profile);
        
        // wire the UI to the code	
		saveButton = (Button) findViewById(R.id.save_profile_button); 
		saveButton.setOnClickListener(this);
	    
	    menuButton = (Button) findViewById(R.id.menu_button); 
	    menuButton.setOnClickListener(this);
		
    }

    // launch activities by button click, with intents
	@Override
	public void onClick(View v) {
		if (saveButton.getId() == ((Button) v).getId()){
			Intent toSave = new Intent(this, UserProfileActivity.class);
			this.startActivity(toSave);
		}
		else if (menuButton.getId() == ((Button) v).getId()){
			Intent toMenu = new Intent(this, MenuActivity.class);
			this.startActivity(toMenu);
		}
	}
}


