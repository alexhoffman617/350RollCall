package com.example.project3;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.achartengine.GraphicalView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
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
	private DatePicker graphStartDate;
	
	private List<String> activities;
	private int startDay;
	private int startMonth;
	private int startYear;
	
	
	private TextView nameDisplay;
	private BarGraph bar = new BarGraph();
	private GraphicalView gview;
	private LinearLayout layout;
	
	private ArrayList<Integer> yData;
	private ArrayList<String> xData;
	
	private Context context;
	final Calendar c = Calendar.getInstance();
	
	private TextView PossibleAttendance;
	private TextView ActualAttendance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentgraphing);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		     userName = extras.getString("userName");
		}
		
		Parse.initialize(this, LoginHandler.link1, LoginHandler.link2);
	
		nameDisplay =(TextView) findViewById(R.id.nameDisplay);
		nameDisplay.setText(userName);
	
		xData = new ArrayList<String>();
		yData = new ArrayList<Integer>();
		xData.add("Fake");
		yData.add(120);
		xData.add("Fake");
		yData.add(130);
		xData.add("Fake");
		yData.add(140);
		
		PossibleAttendance = (TextView) findViewById(R.id.PossibleAttendance);
		ActualAttendance = (TextView) findViewById(R.id.ActualAttendance);
		

		bar.setData(yData, xData, "Standard");
		
		 gview = bar.getView(this);
		 context = this;

		 layout = (LinearLayout) findViewById(R.id.chart);

		layout.addView(gview);
		
		activities =getStudentActivities(userName);
		

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
	
	
	
	public void onCommentsBtnClick(View arg0){
		Intent intent = new Intent(this, StudentComentsActivity.class);
		intent.putExtra("userName", userName);
		startActivity(intent);		
	}
	
	public void onGraphBtnClick(View arg0){
		final Calendar TempC = c;
		
		yData = new ArrayList<Integer>();
		xData = new ArrayList<String>();
		
		for(int i = 0; i <7; i++){
		if(TempC.get(Calendar.DAY_OF_WEEK)==2 || TempC.get(Calendar.DAY_OF_WEEK)==3){}
		else{
			String queryDate = "Absent_"+ TempC.get(Calendar.MONTH)+"_";
			if(TempC.get(Calendar.DAY_OF_MONTH) < 10) {
				queryDate = queryDate + "0";
			}
			queryDate = queryDate + TempC.get(Calendar.DAY_OF_MONTH)+"_"+TempC.get(Calendar.YEAR);
			
			
			boolean absent = false;
			 for (String activity : activities) {
				 
				 ParseQuery query = new ParseQuery(activity);
					query.whereEqualTo("Name", userName);
					List<ParseObject> queryList = new ArrayList<ParseObject>();
					
					try {
						queryList = query.find();
						for(ParseObject student : queryList) {
							if(student.getString(queryDate)!=null && !student.getString(queryDate).equals("--")){
							Log.v("tag", queryDate +"     "+ student.getString(queryDate));

								absent = true;
								
							}
						}
					}
					catch(com.parse.ParseException e) {
						e.printStackTrace();
					}

			}
			
					xData.add(TempC.get(Calendar.MONTH) + "/" + TempC.get(Calendar.DAY_OF_MONTH));
					 if (absent) {
						 yData.add(0);
					 }
					 else {
						 yData.add(1);
					 }
						
					 
				}
			 
			
			
		TempC.add(Calendar.DATE, 1);

		
		}
		layout.removeView(gview);
		bar.setData(yData, xData, "Standard");
		
		 gview = bar.getView(context);

		 layout = (LinearLayout) findViewById(R.id.chart);
		 
		layout.addView(gview);
		
		PossibleAttendance.setText("Total Possible Attendance: " + xData.size());
		int daysAttended=0;
		for(int i = 0; i<yData.size(); i++){
			daysAttended = daysAttended + yData.get(i);
		}
		ActualAttendance.setText("Total Actual Attendance: " + daysAttended);
		
	}

	public static List<String> getStudentActivities(String studentName) {
		ParseQuery query = new ParseQuery("Student");
		query.whereEqualTo("Name", studentName);
		List<ParseObject> queryList = new ArrayList<ParseObject>();
		List<String> activities = new ArrayList<String>();
		try {
			queryList = query.find();
			Log.v("activities", queryList.size()+"");
			for(ParseObject student : queryList) {
				if(student.getNumber("Cooking")!=null){
					activities.add("Cooking");
				}
				if(student.getNumber("Tennis")!=null){
					activities.add("Tennis");
				}
				if(student.getNumber("Basketball")!=null){
					activities.add("Basketball");
				}
				if(student.getNumber("Swimmming")!=null){
					activities.add("Swimming");
				}
	
			}
		}
		catch(com.parse.ParseException e) {
			e.printStackTrace();
		}
		return activities;
	}

}
