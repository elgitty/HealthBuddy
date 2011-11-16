package com.black.healthbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CalendarMonthViewActivity extends Activity implements OnClickListener {

    private Button calendarDayViewButton;
    private Button calendarWeekViewButton;
    private Button calendarMonthViewButton;
    private Button menuButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_month_view);

        // wire the UI to the code	
        calendarDayViewButton = (Button) findViewById(R.id.day_view_button);
        calendarDayViewButton.setOnClickListener(this);
		
        calendarWeekViewButton = (Button) findViewById(R.id.week_view_button);
        calendarWeekViewButton.setOnClickListener(this);
		
        calendarMonthViewButton = (Button) findViewById(R.id.month_view_button);
        calendarMonthViewButton.setOnClickListener(this);
	    
	    menuButton = (Button) findViewById(R.id.menu_button);
	    menuButton.setOnClickListener(this);
        	
    }


    // launch activities by button click, with intents
	@Override
	public void onClick(View v) {
		if (calendarDayViewButton.getId() == ((Button) v).getId()){
		Intent toDayView = new Intent(this, CalendarDayViewActivity.class);
		this.startActivity(toDayView);
		}
		else if (calendarWeekViewButton.getId() == ((Button) v).getId()){
			Intent toWeekView = new Intent(this, CalendarWeekViewActivity.class);
			this.startActivity(toWeekView);
		}
		else if (calendarMonthViewButton.getId() == ((Button) v).getId()){
			Intent toMonthView = new Intent(this, CalendarMonthViewActivity.class);
			this.startActivity(toMonthView);
		}
		else if (menuButton.getId() == ((Button) v).getId()){
			Intent toMenu = new Intent(this, MenuActivity.class);
			this.startActivity(toMenu);
		}
	}

    
}