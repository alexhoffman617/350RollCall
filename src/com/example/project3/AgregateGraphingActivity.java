package com.example.project3;


import java.util.ArrayList;
import java.util.Calendar;

import org.achartengine.GraphicalView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AgregateGraphingActivity extends Activity {
	String userName = "";
	private static final String TAG = "MenuActivity";
	private DatePicker graphStartDate;

	
	private int startDay;
	private int startMonth;
	private int startYear;

	
	private TextView school;
	
	private BarGraph bar;
	private GraphicalView gview;
	private LinearLayout layout;
	
	private ArrayList<Integer> yData = new ArrayList<Integer>();
	private ArrayList<String> xData= new ArrayList<String>();
	
	private Context context;
	final Calendar c = Calendar.getInstance();
	
	private TextView PossibleAttendance;
	private TextView ActualAttendance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregategraphing);

	
	school = (TextView) findViewById(R.id.schoolName);
	school.setText("Agregate Data");
	
	xData = new ArrayList<String>();
	yData = new ArrayList<Integer>();
	xData.add("Fake");
	yData.add(120);
	xData.add("Fake");
	yData.add(130);
	xData.add("Fake");
	yData.add(140);

	
	bar = new BarGraph();

	bar.setData(yData, xData, "Standard");
	context = this;
	gview = bar.getView(this);
	layout = (LinearLayout) findViewById(R.id.chart);
	layout.addView(gview);
	

	
	graphStartDate = (DatePicker) findViewById(R.id.graphDateStart);
	
	


	c.add(c.DAY_OF_MONTH, -7);

	startYear = c.get(Calendar.YEAR);
	startMonth = c.get(Calendar.MONTH);
	startDay = c.get(Calendar.DAY_OF_MONTH);
	
	graphStartDate.init(startYear, startMonth, startDay, new OnDateChangedListener(){
		 
		@Override
		public void onDateChanged(DatePicker arg0, int arg1, int arg2, int arg3) {
			//Toast.makeText(getApplicationContext(), "arg 1: " + arg1 + "    arg2: " + arg2 + "    arg3: "+ arg3, Toast.LENGTH_LONG).show();

			c.set(arg1, arg2+1, arg3);
			//Toast.makeText(getApplicationContext(), c2.get(Calendar.DAY_OF_MONTH)+ " " + c2.get(Calendar.MONTH) + " " +c2.get(Calendar.YEAR), Toast.LENGTH_LONG).show();
		}
		
});
	
	graphStartDate.setCalendarViewShown(false);
	
	}
	
	
	}
	
	


