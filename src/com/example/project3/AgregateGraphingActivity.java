package com.example.project3;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.achartengine.GraphicalView;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
	private ArrayList<Integer> y2Data = new ArrayList<Integer>();
	private ArrayList<String> xData= new ArrayList<String>();
	
	private Context context;
	final Calendar c = Calendar.getInstance();
	
	private TextView PossibleAttendance;
	private TextView ActualAttendance;
	
	String[] activities = {"Cooking", "Basketball", "Swimming", "Tennis"};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregategraphing);

	
	school = (TextView) findViewById(R.id.schoolName);
	PossibleAttendance = (TextView) findViewById(R.id.PossibleAttendance);
	ActualAttendance = (TextView) findViewById(R.id.ActualAttendance);
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


		int sum = 0;

		

		for (String student : getStudentList()) {
		
			boolean absent = false;

		for (String activity : StudentGraphingActivity.getStudentActivities(student)) {

		ParseQuery query = new ParseQuery(activity);

		query.whereEqualTo("Name", student);

		List<ParseObject> queryList = new ArrayList<ParseObject>();

		try {

		queryList = query.find();

		for(ParseObject s : queryList) {

		if(s.getString(queryDate)!=null && !s.getString(queryDate).equals("--")){

		Log.v("tag", student +"   " + queryDate +"      "+ activity +"     "+ s.getString(queryDate));

		absent = true;


		}

		}

		}

		catch(com.parse.ParseException e) {

		e.printStackTrace();

		}

		}

		if (!absent) {
			Log.v("STUDENT NOT ABSENT", student +"   " + queryDate );

		sum++;

		}

		}


		xData.add(TempC.get(Calendar.MONTH) + "/" + TempC.get(Calendar.DAY_OF_MONTH));
		y2Data.add(getStudentList().size());
		yData.add(sum);
		
		Log.v("TAAAAAAAG", sum +"");
		}

		 



		TempC.add(Calendar.DATE, 1);




		}

		layout.removeView(gview);

		bar.setData(yData, xData, y2Data, "Standard");


		gview = bar.getView(context);



		layout = (LinearLayout) findViewById(R.id.chart);

		 

		layout.addView(gview);


		PossibleAttendance.setText("Total Possible Attendance: " + xData.size()*getStudentList().size());

		int daysAttended=0;

		for(int i = 0; i<yData.size(); i++){

		daysAttended = daysAttended + yData.get(i);

		}

		ActualAttendance.setText("Total Actual Attendance: " + daysAttended);


		}
	
	public List<String> getStudentList() {

		ParseQuery query = new ParseQuery("Student");

		List<ParseObject> queryList = new ArrayList<ParseObject>();

		List<String> students = new ArrayList<String>();

		try {

		queryList = query.find();

		Log.v("students", queryList.size()+"");

		for(ParseObject student : queryList) {



		if(student.getString("Name")!=null){

		students.add(student.getString("Name"));

		}

		}

		}

		catch(com.parse.ParseException e) {

		e.printStackTrace();

		}

		return students;

		}
	}
	
	


