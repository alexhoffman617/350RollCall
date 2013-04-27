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
	
	private ArrayList<Integer> y2Data = new ArrayList<Integer>();
	private ArrayList<String> x2Data= new ArrayList<String>();
	
	final Calendar c = Calendar.getInstance();
	private Context context;
	
	private BarGraph bar;
	GraphicalView gview;
	LinearLayout layout;

	private TextView PossibleAttendanceAct;
	private TextView ActualAttendanceAct;
	
	private boolean noFilter = true;
	private boolean genderFilterOn = false;
	private boolean gradeFilterOn = false;
	private int gradeLevel = 3;
	
	private int studentsFromFilter;
	
	
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
				gradeLevel = Integer.parseInt(filter);
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
		    students = new ArrayList<String>();
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
	
	@SuppressLint("NewApi")
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
		
		PossibleAttendanceAct = (TextView) findViewById(R.id.PossibleAttendance);
		ActualAttendanceAct = (TextView) findViewById(R.id.ActualAttendance);

		
		xData = new ArrayList<String>();
		yData = new ArrayList<Integer>();
		
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

			c.set(arg1, arg2, arg3);
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
		
		if (gradeFilterOn){
				ArrayList<String> studentsInGrade = new ArrayList<String>();
				for (String s: students){
					ParseQuery query = new ParseQuery("Student");
					query.whereEqualTo("Name", s);
					List<ParseObject> queryList = new ArrayList<ParseObject>();
					
					try {
						queryList = query.find();
					} catch (com.parse.ParseException e) {
						e.printStackTrace();
					}
					for (ParseObject sObject: queryList){
						if (sObject.getNumber("Parse_1112GradeLevel").intValue() == gradeLevel){
							System.out.println("QueryList contains" + sObject.get("Name"));
							studentsInGrade.add((String) sObject.get("Name"));
						}
					}
				}
				tabulateAttendance(studentsInGrade, xData, yData);
				studentsFromFilter = studentsInGrade.size();
		}
		if (genderFilterOn){
			ArrayList<String> studentsFemale = new ArrayList<String>();
			ArrayList<String> studentsMale = new ArrayList<String>();
			for (String s: students){
				ParseQuery query = new ParseQuery("Student");
				query.whereEqualTo("Name", s);
				List<ParseObject> queryList = new ArrayList<ParseObject>();
				
				try {
					queryList = query.find();
				} catch (com.parse.ParseException e) {
					e.printStackTrace();
				}
				for (ParseObject sObject: queryList){
					if (sObject.get("Gender").equals("Female")){
						System.out.println("QueryList contains" + sObject.get("Name"));
						studentsFemale.add((String) sObject.get("Name"));
					}
					else{
						studentsMale.add((String)sObject.get("Name"));
					}
				}
			}
			for (int i =0; i<studentsFemale.size(); i++){
				System.out.println("Female is " + studentsFemale.get(i));
			}
			
			for (int i =0; i<studentsMale.size(); i++){
				System.out.println("Male is " + studentsMale.get(i));
			}
			
			System.out.println("FEMALE TABULATE ATTENDANCE");
			tabulateAttendance(studentsFemale, xData, yData);
			System.out.println("MALE TABULATE ATTENDANCE");
			tabulateAttendance(studentsMale, x2Data, y2Data);
			
			studentsFromFilter = studentsMale.size() + studentsFemale.size();
	}
		
		if (noFilter){
			tabulateAttendance(students, xData, yData);
			studentsFromFilter = students.size() ;
		}
		
		/********* Tabulating the attendance for each activity *********/
		
		layout.removeView(gview);
		
		if(noFilter || gradeFilterOn){
			bar.setData(yData, xData, "Standard");
		}
		
		if (genderFilterOn){
			bar.setData(yData, xData, y2Data, "Compare");
		}
		

		 gview = bar.getView(context);

		 layout = (LinearLayout) findViewById(R.id.chart);
		 
		layout.addView(gview);
		
		if(PossibleAttendanceAct != null) {

		PossibleAttendanceAct.setText("Total Possible Attendance: " + studentsFromFilter*5 + " of " + xData.size() *students.size());
		}
		if (ActualAttendanceAct != null) {
			int totalPossibleAttendance = xData.size() *students.size();
			int daysAttended = 0;
			for (int i = 0; i < yData.size(); i++) {
				daysAttended += yData.get(i);
			}
			if(genderFilterOn){
				for(int j = 0; j< y2Data.size(); j++){
					daysAttended = daysAttended + y2Data.get(j);
				}
			}
			float percentage = ((float) daysAttended/((float) studentsFromFilter*5))*100;
			ActualAttendanceAct.setText("Total Actual Attendance: " + daysAttended
				+ " , " + (int)percentage + "%");
		}
		
		}
	
	public void tabulateAttendance(List<String> studentsList, ArrayList<String> xDataForGraph, ArrayList<Integer> yDataForGraph){
		final Calendar TempC = c;
		
		for(int i = 0; i <7; i++){
			//DO NOTHING IF NOT WEEKEND
			int total_absences = 0;
			boolean columnExists = true;
			if(TempC.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || TempC.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			}
			else{
				String queryDate = "Absent_"+ (TempC.get(Calendar.MONTH)+1) +"_";
				if(TempC.get(Calendar.DAY_OF_MONTH) < 10){
					queryDate = queryDate + "0";
				}
				queryDate = queryDate + TempC.get(Calendar.DAY_OF_MONTH)+"_"+TempC.get(Calendar.YEAR);
				
				
				System.out.println("*******" + queryDate + "********");
				for (String student : studentsList){
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
							if(studentFound.getString(queryDate)==null){
								columnExists = false;
							}
							if(studentFound.getString(queryDate)!=null && !studentFound.getString(queryDate).equals("--")){
								total_absences++;
								System.out.println(total_absences + ": " + studentFound.get("Name") + " is absent on " + queryDate);
							}
						System.out.println("TOTAL ABSENCES:" + total_absences);
						}
					}
					catch (com.parse.ParseException parseExcep){
					}
					catch(NullPointerException nullExcep){
					} 
				}
			}
			
			if(TempC.get(Calendar.DAY_OF_WEEK)!=Calendar.SATURDAY && TempC.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY && columnExists){
				System.out.println("size of array" + studentsList.size());
				yDataForGraph.add(studentsList.size()-total_absences);
				xDataForGraph.add((TempC.get(Calendar.MONTH)+1) + "/" + TempC.get(Calendar.DAY_OF_MONTH)+ "/" + TempC.get(Calendar.YEAR));
			}

			
			TempC.add(Calendar.DATE, 1);
		}
		TempC.add(Calendar.DATE, -7);
	}
	}
	


