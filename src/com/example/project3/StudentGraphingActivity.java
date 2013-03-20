package com.example.project3;


import java.util.Calendar;

import org.achartengine.GraphicalView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StudentGraphingActivity extends Activity {
	public String userName = " ";
	private static final String TAG = "MenuActivity";
	private DatePicker graphStartDate;
	private DatePicker graphEndDate;
	
	private int startDay;
	private int startMonth;
	private int startYear;
	
	private int endDay;
	private int endMonth;
	private int endYear;
	
	private TextView nameDisplay;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentgraphing);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		     userName = extras.getString("userName");
		}

	
		nameDisplay =(TextView) findViewById(R.id.nameDisplay);
		nameDisplay.setText(userName);
	

		int[] yData = {124, 135 , 413, 356, 234, 123, 342, 134, 123, 643, 234, 274};
		String[] xData = {"2/1", "2/2", "2/3", "2/4", "2/5", "2/8", "2/9", "2/10", "2/11", "2/12", "2/14", "2/15"};
		BarGraph bar = new BarGraph();

		bar.setData(yData, xData);
		
		GraphicalView gview = bar.getView(this);

		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);

		layout.addView(gview);
		
		
	
	graphEndDate = (DatePicker) findViewById(R.id.graphDateEnd);
	
	
	final Calendar c = Calendar.getInstance();
	endYear = c.get(Calendar.YEAR);
	endMonth = c.get(Calendar.MONTH);
	endDay = c.get(Calendar.DAY_OF_MONTH);
	
	graphEndDate.init(endYear, endMonth, endDay, new OnDateChangedListener(){

		@Override
		public void onDateChanged(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			
		}
		
	});
	
	graphEndDate.setCalendarViewShown(false);
	
	graphStartDate = (DatePicker) findViewById(R.id.graphDateStart);
	
	
	final Calendar c2 = Calendar.getInstance();

	c2.add(c2.DAY_OF_MONTH, -7);

	startYear = c2.get(Calendar.YEAR);
	startMonth = c2.get(Calendar.MONTH);
	startDay = c2.get(Calendar.DAY_OF_MONTH);
	
	graphEndDate.init(startYear, startMonth, startDay, new OnDateChangedListener(){
 
		@Override
		public void onDateChanged(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			
		}
		
	});
	
	graphStartDate.setCalendarViewShown(false);
	
	}
	
	
	
	public void onCommentsBtnClick(View arg0){
		Intent intent = new Intent(this, StudentComentsActivity.class);
		intent.putExtra("userName", userName);
		startActivity(intent);		
	}



}
