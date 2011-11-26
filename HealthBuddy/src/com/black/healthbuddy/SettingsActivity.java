package com.black.healthbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class SettingsActivity extends Activity implements OnClickListener {

	private Button menuButton;
	private Button debugButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.settings);
		menuButton = (Button) findViewById(R.id.menu_button);
		menuButton.setOnClickListener(this);
		
		debugButton = (Button) findViewById(R.id.debug_button);
		debugButton.setOnClickListener(this);
		
	  }

    // launch activities by button click, with intents
	@Override
	public void onClick(View v) {
		if (menuButton.getId() == ((Button) v).getId()){
			Intent toMenu = new Intent(this,MenuActivity.class);
			this.startActivity(toMenu);
		}
		if (debugButton.getId() == ((Button) v).getId()) {
			Intent toDebug = new Intent(this, debugActivity.class);
			this.startActivity(toDebug);
		}
	}

}