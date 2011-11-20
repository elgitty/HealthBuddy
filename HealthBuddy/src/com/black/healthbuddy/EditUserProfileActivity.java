package com.black.healthbuddy;

/** @author Elena
 * EditUserProfileActivity class opens the edit profile UI. The user enters personal details, 
 * which are stored in DB and also sent to the user profile UI.
 * Note: using this class requires adding its name to the Android manifest: 
 * <activity android:name="EditUserProfileActivity"></activity> 
 * 
 * TODO: send data to DB. Handle this in MyApplication class.
 */


//@em: shared pref API
//import com.black.healthbuddy.model.ManageSharedPreferences;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


public class EditUserProfileActivity extends Activity implements OnClickListener {

	// @em: instead of starting a new activity, send data from this to the UserProfileActivity
//    private String name; 
//    private String sex = ""; // @em: must initialise before if statement
//    private int age;
//    private double height, weight; 
    
//    private TextView calorieBank;
    private Button menuButton;
    private Button saveButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_profile);         
        
        // @em: wire the UI to the code	
		saveButton = (Button) findViewById(R.id.save_profile_button); 
		saveButton.setOnClickListener(this);
	    
	    menuButton = (Button) findViewById(R.id.menu_button); 
	    menuButton.setOnClickListener(this);
		
    }

    // @em: launch activities by button click, with intents
  
	@Override
	public void onClick(View v) {
		// @em: display data to next page and save it to DB (TODO)
		if (saveButton.getId() == ((Button) v).getId()) {
			
			//Intent toSave = new Intent(this, UserProfileActivity.class);			
			//this.startActivity(toSave);
			
			// @em: log for testing
			Log.i("EUPA", "finish about to be called" ); // @em: ok
			
			// @em: instead of starting a new activity, send data from this to the UserProfileActivity
		    String name; 
		    String sex = ""; // @em: must initialise before if statement
		    int age;
		    double height, weight; 

			// @em: find user input and store it as Strings to variables above
		    // @em: TODO: must wrap this into exception handler if any element not provided by user
			EditText inputField = (EditText) findViewById(R.id.nameInput);			
			name = inputField.getText().toString();
			
/*	// @em: reinstate when problem with onActivityResult is fixed	
  
			inputField = (EditText) findViewById(R.id.ageInput);		
			age = Integer.parseInt(inputField.getText().toString());
			
			inputField = (EditText) findViewById(R.id.heightInput);
			height = Integer.parseInt(inputField.getText().toString());		
			
			inputField = (EditText) findViewById(R.id.weightInput);
			weight = Integer.parseInt(inputField.getText().toString());
			
			RadioButton rbMale = (RadioButton) findViewById(R.id.radio_male);			
			RadioButton rbFemale = (RadioButton) findViewById(R.id.radio_female);			
			if (rbMale.isChecked()) {
				sex = rbMale.getText().toString();
			}
			else if (rbFemale.isChecked()) {
				sex = rbFemale.getText().toString();
			}
*/
			MyApplication app = (MyApplication) this.getApplication();
			app.getUser().setName(name);
/*			app.getUser().setAge(age);
			app.getUser().setHeight(height);
			app.getUser().setWeight(weight);
			app.getUser().setSex(sex);
*/			

			// @em: finish editing activity
			//Intent toSave = new Intent(this, UserProfileActivity.class);
	        //setResult(Activity.RESULT_OK, toSave);
			//setResult(0); // @em: alternative to above. neither seems to work!
			finish();
			//Log.i("EUPA", "finish was called" ); // @em: ok

		}
		else if (menuButton.getId() == ((Button) v).getId())  {
			Intent toMenu = new Intent(this, MenuActivity.class);
			this.startActivity(toMenu);
		}
	}

}


