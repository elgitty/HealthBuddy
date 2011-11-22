package com.black.healthbuddy;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import com.black.healthbuddy.service.IStepService;
import com.black.healthbuddy.service.IStepServiceCallback;
import com.black.healthbuddy.service.StepDetector;
import com.black.healthbuddy.service.StepService;
import com.black.healthbuddy.utilities.MessageUtilities;
import com.black.healthbuddy.R;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;



public class HealthBuddyActivity extends Activity {
	
	private static final Logger logger = Logger.getLogger(HealthBuddyActivity.class.getSimpleName());

	private static ToggleButton startStopButton = null;
	private static ArrayList<String> sensArrayList = null;
	private static ArrayAdapter<CharSequence> modesAdapter = null;
	private static TextView text = null;

	private static PowerManager powerManager = null;
	private static WakeLock wakeLock = null;

	public static IStepService mService = null;
	public static Intent stepServiceIntent = null;
	
	private static int sensitivity = 30;

	

    /** Called when the activity is first created */ 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the view defined by Main.xml
        setContentView(R.layout.main);      
        
    }
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStart() {
		super.onStart();

		if (!wakeLock.isHeld()) wakeLock.acquire();

		//Bind without starting the service
		try {
			bindService(stepServiceIntent, mConnection, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPause() {
		super.onPause();

		if (wakeLock.isHeld()) wakeLock.release();

		unbindStepService();
	}

	private OnItemSelectedListener sensListener = new OnItemSelectedListener() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		    CharSequence seq = modesAdapter.getItem(arg2);
			String sensString = String.valueOf(seq);
			if (sensString!=null) {
				sensitivity = Integer.parseInt(sensString);
			    StepDetector.setSensitivity(sensitivity);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			//Ignore
		}
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {
				if (mService!=null && mService.isRunning()) {
					MessageUtilities.confirmUser(   HealthBuddyActivity.this, 
							"Exit App without stopping pedometer?", 
							yesExitClick, 
							null);
				} else {
					stop();

					finish();
				}
			} catch (RemoteException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private OnCheckedChangeListener startStopListener = new OnCheckedChangeListener() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			boolean serviceIsRunning = false;
			try {
				if (mService!=null) serviceIsRunning = mService.isRunning();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if (isChecked && !serviceIsRunning) {            
				start();
			} else if (!isChecked && serviceIsRunning) {
				MessageUtilities.confirmUser(   HealthBuddyActivity.this, 
						"Stop the pedometer?", 
								yesStopClick, 
								noStopClick);
			}
		}
	};

	private DialogInterface.OnClickListener yesStopClick = new DialogInterface.OnClickListener(){
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onClick(DialogInterface dialog, int which) {
			stop();
		}
	};    

	private static final DialogInterface.OnClickListener noStopClick = new DialogInterface.OnClickListener(){
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (mService!=null)
				try {
					startStopButton.setChecked(mService.isRunning());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
		}
	};

	private DialogInterface.OnClickListener yesExitClick = new DialogInterface.OnClickListener(){
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onClick(DialogInterface dialog, int which) {
			unbindStepService();
			
			finish();
		}
	};

	private void start() {
		logger.info("start");

		startStepService();
		bindStepService();
	}  

	private void stop() {
		logger.info("stop");

		unbindStepService();
		stopStepService();
	}

	private void startStepService() {
		try {
			startService(stepServiceIntent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stopStepService() {
		try {
			stopService(stepServiceIntent);
		} catch (Exception e) {
			//Ignore
		}
	}
	
	private void bindStepService() {
		try {
			bindService(stepServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void unbindStepService() {
		try {
			unbindService(mConnection);
		} catch (Exception e) {
			//Ignore
		}
	}

    private static final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int current = msg.arg1;
            text.setText("Steps = "+current);
        }
    };
	
	private static final IStepServiceCallback.Stub mCallback = new IStepServiceCallback.Stub() {
		@Override
		public IBinder asBinder() {
			return mCallback;
		}

		@Override
		public void stepsChanged(int value) throws RemoteException {
			logger.info("Steps="+value);
			Message msg = handler.obtainMessage();
	        msg.arg1 = value;
	        handler.sendMessage(msg);
		}
	};

	private static final ServiceConnection mConnection = new ServiceConnection() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			logger.info("onServiceConnected()");
			mService = IStepService.Stub.asInterface(service);
			try {
				mService.registerCallback(mCallback);
				mService.setSensitivity(sensitivity);
				startStopButton.setChecked(mService.isRunning());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onServiceDisconnected(ComponentName className) {
			logger.info("onServiceDisconnected()");
			try {
				startStopButton.setChecked(mService.isRunning());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			mService = null;
		}
	};
        
}

