package com.example.project3;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.achartengine.GraphicalView;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.annotation.SuppressLint;
import android.app.Activity;
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
	private static final String TAG = "MenuActivity";
	private DatePicker graphStartDate;
	private DatePicker graphEndDate;
	
	private List<String> activities;
	private HashMap<String, Boolean> absences = new HashMap<String, Boolean>();	
	private int startDay;
	private int startMonth;
	private int startYear;
	
	private int endDay;
	private int endMonth;
	private int endYear;
	
	private TextView nameDisplay;
	
	ArrayList<Integer> yData;
	ArrayList<String> xData;
	
	
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
	
		xData = new ArrayList<String>();
		yData = new ArrayList<Integer>();
		xData.add("Fake");
		yData.add(100);
		
		
		
		BarGraph bar = new BarGraph();

		bar.setData(yData, xData, "Standard");
		
		GraphicalView gview = bar.getView(this);

		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);

		layout.addView(gview);
		
		activities =getStudentActivities(userName);
		
	/*	
	
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
	*/
	graphStartDate = (DatePicker) findViewById(R.id.graphDateStart);
	
	
	final Calendar c = Calendar.getInstance();

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
			final Calendar TempC = c;
			
			yData = new ArrayList<Integer>();
			xData = new ArrayList<String>();
			
			for(int i = 0; i <7; i++){
			if(TempC.get(Calendar.DAY_OF_WEEK)==6 || TempC.get(Calendar.DAY_OF_WEEK)==7){}
			else{
				String queryDate = "Absent_"+ TempC.get(Calendar.DAY_OF_MONTH)+"_"+ TempC.get(Calendar.MONTH)+"_"+TempC.get(Calendar.YEAR);
				//Toast.makeText(getApplicationContext(), queryDate, Toast.LENGTH_LONG).show();
				
				
				boolean absent = false;
				 for (String activity : activities) {
					 
					 ParseQuery query = new ParseQuery(activity);
						query.whereEqualTo("Name", userName);
						List<ParseObject> queryList = new ArrayList<ParseObject>();
						
						try {
							queryList = query.find();
							for(ParseObject student : queryList) {
								if(student.getString(queryDate)!=null){
									System.out.println("setting to absent");
									absent = true;
									
								}
							}
						}
						catch(com.parse.ParseException e) {
							e.printStackTrace();
						}

				}
				
						xData.add(TempC.get(Calendar.MONTH) + "/" + TempC.get(Calendar.DAY_OF_MONTH));
						Log.v("x", TempC.get(Calendar.MONTH) + "/" + TempC.get(Calendar.DAY_OF_MONTH));
						 if (absent) {
							 yData.add(0);
							 Log.v("y", "0");
						 }
						 else {
							 yData.add(1);
							 Log.v("y", "1");
						 }
							
						 
					}
				 
				
				


			}
			TempC.add(Calendar.DATE, 1);
			}
		
		
	});
	
	graphStartDate.setCalendarViewShown(false);
	
	}
	
	
	
	public void onCommentsBtnClick(View arg0){
		Intent intent = new Intent(this, StudentComentsActivity.class);
		intent.putExtra("userName", userName);
		startActivity(intent);		
	}

	public List<String> getStudentActivities(String studentName) {
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
