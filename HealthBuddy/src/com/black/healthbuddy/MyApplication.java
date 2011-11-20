package com.black.healthbuddy;

/** @author Elena
 * MyApplication class changes the default Application methods onCreate and onTerminate.
 * The purpose is to have a place to create model objects that are needed throughout the application.
 * These include the user profile, the user exercise and nutrition logs, and the database.
 * Note: using this class requires adding its name after the label <application
 * to the Android manifest: android:name="MyApplication" 
 * 
 * TODO: send and retrieve data to/from DB
 */

import android.app.Application;
import android.util.Log;

import com.black.healthbuddy.model.HealthBuddyDbAdapter;
import com.black.healthbuddy.model.User;
import com.black.healthbuddy.model.Exercise;
import com.black.healthbuddy.model.Nutrition;

// @em: to create an application object that contains all data (user profile, exercise and nutrition logs)
public class MyApplication extends Application {
	 
	public static final String APP_NAME = "HealthBuddy";  

	// @em: create a user object
	User userProfile = new User();
	
	// @em: TODO create an Exercise object
	// @em: TODO create a Nutrition object
	
    // @em: create a database object
//    private HealthBuddyDbAdapter mDbHelper; // @em
	//private DataHelper dataHelper;   
	    
	@Override
	public void onCreate() {
		super.onCreate();
	    Log.d(APP_NAME, "APPLICATION onCreate");
	    //this.dataHelper = new DataHelper(this);   
//	    this.mDbHelper = new HealthBuddyDbAdapter(this); // @em  
//	    mDbHelper.open(); // @em
	    //mDbHelper.dropAndRecreateTables(); // @em
	    
	 // @em: operation to retrieve data from DB goes here?
//	    mDbHelper.sampletestQuerys(); // @em
	}
	    
	@Override
	public void onTerminate() {
		Log.d(APP_NAME, "APPLICATION onTerminate");      
		super.onTerminate();      
		// @em: operation to save data to DB goes here?
        
//        mDbHelper.insertDataIntoTables(); // @em
        
//		mDbHelper.close(); // @em
		
	}
	
	// @em: this method returns all the user data
	public User getUser() {
		return userProfile;
	}
/*	
	public HealthBuddyDbAdapter getDataHelper() {
		return this.mDbHelper;
	//public DataHelper getDataHelper() {
	//	return this.dataHelper;
	}
	
	public void setDataHelper(HealthBuddyDbAdapter dataHelper) {
		this.mDbHelper = dataHelper;
	//public void setDataHelper(DataHelper dataHelper) {
	//   	this.dataHelper = dataHelper;
	}
*/

}
