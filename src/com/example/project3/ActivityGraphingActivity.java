package com.example.project3;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.achartengine.GraphicalView;

import com.parse.FindCallback;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.Toast;

public class ActivityGraphingActivity extends Activity {
	String activity = "";
	private static final String TAG = "MenuActivity";
	private DatePicker graphStartDate;

	
	private int startDay;
	private int startMonth;
	private int startYear;
	

	//private String[] studentId;
	List<String> students = new ArrayList<String>();
	//private ArrayList<Integer> attendance = new ArrayList<Integer>();
	private int numberOfStudentsInActivity = 0;
	
	private TextView activityDisplay;
	
	Spinner spinnerSorting;
	
	private ArrayList<Integer> yData = new ArrayList<Integer>();
	private ArrayList<String> xData= new ArrayList<String>();
	
	final Calendar c = Calendar.getInstance();
	private Context context;
	
	private BarGraph bar;
	GraphicalView gview;
	LinearLayout layout;

	private boolean noFilter = true;
	private boolean genderFilterOn = false;
	private boolean gradeFilterOn = false;
	private String gradeLevel = "";
	
	public class onItemSelect implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			String filter = (String) spinnerSorting.getSelectedItem();
			if (filter.equals("Gender")) {
				genderFilterOn = true;
				gradeFilterOn = false;
				noFilter = false;
			}
			else if (!filter.equals("No Filter")) {
				gradeFilterOn = true;
				gradeLevel = filter;
				genderFilterOn = false;
				noFilter = false;
			}
			else {
				noFilter = true;
				gradeFilterOn = false;
				genderFilterOn = false;
			}
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private void initializeParse(){
		Parse.initialize(this, LoginHandler.link1, LoginHandler.link2);
	}

	private void findListOfStudents(String ActivityType){
		    numberOfStudentsInActivity = 0;
			ParseQuery query = new ParseQuery(ActivityType);
			List<ParseObject> queryList = new ArrayList<ParseObject>();
			try {
			queryList = query.find();
			for(ParseObject student : queryList) {
			if(student.getString("Name")!=null){
			students.add(student.getString("Name"));
			numberOfStudentsInActivity++;
			System.out.println(student.getString("Name"));
			}
			}
			}
			catch(com.parse.ParseException e) {
			e.printStackTrace();
			}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitygraphing);
		initializeParse();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		     activity = extras.getString("ActivityType");
		}
		Toast.makeText(getApplicationContext(), activity, Toast.LENGTH_LONG).show();
		Log.d(TAG, activity);
		
		activityDisplay =(TextView) findViewById(R.id.activityDisplay);
		activityDisplay.setText(activity);
	
		findListOfStudents(activity);

		
		
		
		xData = new ArrayList<String>();
		yData = new ArrayList<Integer>();
		
		xData.add("FAKE");
		yData.add(30);
		xData.add("FAKE");
		yData.add(60);
		xData.add("FAKE");
		yData.add(90);
		
		
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
	
	
	spinnerSorting = (Spinner) findViewById(R.id.spinnerSorting);
	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
			this, R.array.spinnerArray, android.R.layout.simple_spinner_item);	
	adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	spinnerSorting.setAdapter(adapter);
	spinnerSorting.setOnItemSelectedListener(new onItemSelect());
	}
	
	public void onStudentsBtnClick(View arg0){
		Intent intent = new Intent(this, StudentsInActivityActivity.class);
		intent.putExtra("ActivityType", activity);
		startActivity(intent);
	}
	
	public void onGraphBtnClick(View arg0){
		
		xData = new ArrayList<String>();
		yData = new ArrayList<Integer>();
		
		/********* Tabulating the attendance for each activity *********/
		final Calendar TempC = c;
		
		for(int i = 0; i <7; i++){
			//DO NOTHING IF NOT WEEKEND
			int total_absences = 0;
			if(TempC.get(Calendar.DAY_OF_WEEK)==2 || TempC.get(Calendar.DAY_OF_WEEK)==3){
			}
			else{
				String queryDate = "Absent_"+ TempC.get(Calendar.MONTH)+"_";
				if(TempC.get(Calendar.DAY_OF_MONTH) < 10){
					queryDate = queryDate + "0";
				}
				queryDate = queryDate + TempC.get(Calendar.DAY_OF_MONTH)+"_"+TempC.get(Calendar.YEAR);
				
				
				System.out.println("*******" + queryDate + "********");
				for (String student : students){
					ParseQuery query2 = new ParseQuery(activity).whereEqualTo("Name", student);
					//query2.whereEqualTo("Name", student);
					List<ParseObject> queryList = new ArrayList<ParseObject>();
					try{
						if (query2.find() != null){
							queryList = query2.find();}
						else{
							System.out.println("Student Not In Activity");
						}
						for (ParseObject studentFound : queryList){
							if(studentFound.getString(queryDate)!=null && !studentFound.getString(queryDate).equals("--")){
								total_absences++;
								System.out.println(total_absences + ": " + studentFound.get("Name") + " is absent on " + queryDate);
							}
						}
					}
					catch (com.parse.ParseException parseExcep){
					}
					catch(NullPointerException nullExcep){
					} 
				}
			}
			if(TempC.get(Calendar.DAY_OF_WEEK)!=2 && TempC.get(Calendar.DAY_OF_WEEK)!=3){
				yData.add(numberOfStudentsInActivity-total_absences);
			}
			xData.add(TempC.get(Calendar.MONTH) + "/" + TempC.get(Calendar.DAY_OF_MONTH));
			
			TempC.add(Calendar.DATE, 1);
		}
		for (int i = 0; i < yData.size(); i++){
			System.out.println(yData.get(i));}
		
		layout.removeView(gview);
		bar.setData(yData, xData, "Standard");
		
		 gview = bar.getView(context);

		 layout = (LinearLayout) findViewById(R.id.chart);
		 
		layout.addView(gview);
		
		}
	}
	


