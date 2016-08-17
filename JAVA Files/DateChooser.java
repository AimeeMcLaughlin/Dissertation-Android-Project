package com.shishoufuuin.deviantart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;



public class DateChooser extends Activity implements View.OnClickListener{
	
	CalendarView dateChoosers;
	 Button home, profile, diary, options;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String choice = settings.getString("choice", "");
		
		
		if(choice.contains("White"))
		{
			setContentView(R.layout.date);  
		}
		else if(choice.contains("Black"))
		{
			setContentView(R.layout.date2); 
		}
		else if(choice.contains("Blue"))
		{
			setContentView(R.layout.date3); 
		}
		else if(choice.contains("one"))
		{
			setContentView(R.layout.date7);   
		}
		else if(choice.contains("two"))
		{
			setContentView(R.layout.date5);  
		}
		else if(choice.contains("three"))
		{
			setContentView(R.layout.date6); 
		}
	
		else 
		{
			setContentView(R.layout.date); 

		}
		
		
		initialize();

	}
	
	public void initialize(){
		dateChoosers = (CalendarView) findViewById(R.id.calendarView1);
		home = (Button) findViewById(R.id.bHome);
		profile = (Button) findViewById(R.id.bProfile);
		diary = (Button) findViewById(R.id.bDate);
		options = (Button) findViewById(R.id.bOptions);
 
		home.setOnClickListener(this);
		profile.setOnClickListener(this);
		diary.setOnClickListener(this);
		options.setOnClickListener(this);
		dateChoosers.setOnDateChangeListener(this.mCalen);
		   
	}
	

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bHome:
			Intent a = new Intent(DateChooser.this, MainActivity.class);
			startActivity(a);
			break;
		case R.id.bProfile:
			Intent b = new Intent(DateChooser.this, Profile.class);
			startActivity(b);
			break;
		case R.id.bDate:
 			Intent c = new Intent(DateChooser.this, DateChooser.class);
			startActivity(c);
			break;
		case R.id.bOptions:
			Intent d = new Intent(DateChooser.this, Options.class);
			startActivity(d);
			break;
		}
	}
	
	private OnDateChangeListener mCalen = new OnDateChangeListener() {
	@Override
	public void onSelectedDayChange(CalendarView view, int year, int month,
			int dayOfMonth) {
		// TODO Auto-generated method stub

         Bundle dataBundle = new Bundle();

         dataBundle.putInt("year", year);
         dataBundle.putInt("month", month);
         dataBundle.putInt("dayOfMonth", dayOfMonth);
         
         Intent k = new Intent(DateChooser.this, Diary.class);
         k.putExtras(dataBundle);
         startActivity(k);
	}

	};



}


	