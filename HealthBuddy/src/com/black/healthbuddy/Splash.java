package com.black.healthbuddy;

import com.black.healthbuddy.model.HealthBuddyDbAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {
	private HealthBuddyDbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		mDbHelper = new HealthBuddyDbAdapter(this);
		mDbHelper.open();
		mDbHelper.dropAndRecreateTables();
		mDbHelper.insertDataIntoTables();
		mDbHelper.close();

		Thread logoTimert = new Thread() {
			public void run() {
				try {
					int logoTimer = 0;
					while (logoTimer < 2500) {
						sleep(100);
						logoTimer = logoTimer + 100;
					}
					startActivity(new Intent(
							"com.black.healthbuddy.CLEARSCREEN"));

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				finally {
					finish();
				}
			}
		};
		logoTimert.start();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// mpSplash.release();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// mpSplash.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// mpSplash.start();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
