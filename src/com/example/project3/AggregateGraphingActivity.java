package com.example.project3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.achartengine.GraphicalView;

import com.example.project3.ActivityGraphingActivity.onItemSelect;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AggregateGraphingActivity extends Activity {
	String userName = "";
	private static final String TAG = "MenuActivity";
	private DatePicker graphStartDate;

	private int startDay;
	private int startMonth;
	private int startYear;

	Spinner spinnerAggregate;

	private TextView school;

	private BarGraph bar;
	private GraphicalView gView;
	private LinearLayout layout;
	
	
	private Context context;
	final Calendar c = Calendar.getInstance();

	private TextView possibleAttendance;
	private TextView actualAttendance;

	private boolean noFilter = true;
	private boolean genderFilterOn = false;
	private boolean gradeFilterOn = false;
	private int gradeLevel = 3;
	
	private int studentsFromFilter;

	
	private List<String> allStudents = new ArrayList<String>();

	String[] activities = { "Cooking", "Basketball", "Swimming", "Tennis" };

	public class onItemSelect implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			String filter = (String) spinnerAggregate.getSelectedItem();
			if (filter.equals("Gender")) {
				genderFilterOn = true;
				gradeFilterOn = false;
				noFilter = false;
			} else if (!filter.equals("No Filter")) {
				gradeFilterOn = true;
				gradeLevel = Integer.parseInt(filter);
				genderFilterOn = false;
				noFilter = false;
			} else {
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

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregategraphing);

		findAllStudents();
		
		school = (TextView) findViewById(R.id.schoolName);
		possibleAttendance = (TextView) findViewById(R.id.PossibleAttendance);
		actualAttendance = (TextView) findViewById(R.id.ActualAttendance);
		school.setText("Aggregate Data");

		ArrayList<String> xDataDefault = new ArrayList<String>();
		ArrayList<Integer> yDataDefault = new ArrayList<Integer>();

		bar = new BarGraph();
		
		bar.setData(yDataDefault, xDataDefault, "Standard");
		context = this;
		gView = bar.getView(this);
		layout = (LinearLayout) findViewById(R.id.chart);
		layout.addView(gView);

		graphStartDate = (DatePicker) findViewById(R.id.graphDateStart);

		c.add(c.DAY_OF_MONTH, -7);

		startYear = c.get(Calendar.YEAR);
		startMonth = c.get(Calendar.MONTH);
		startDay = c.get(Calendar.DAY_OF_MONTH);

		graphStartDate.init(startYear, startMonth, startDay,
				new OnDateChangedListener() {

					@Override
					public void onDateChanged(DatePicker arg0, int arg1,
							int arg2, int arg3) {
						c.set(arg1, arg2, arg3);
					}

				});

		graphStartDate.setCalendarViewShown(false);

		spinnerAggregate = (Spinner) findViewById(R.id.spinnerAggregate);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.spinnerArray,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerAggregate.setAdapter(adapter);
		spinnerAggregate.setOnItemSelectedListener(new onItemSelect());

	}

	public void onGraphBtnClick(View arg0) {
		ArrayList<String> calendarDates = new ArrayList<String>();
		ArrayList<Integer> numberOfAttendees = new ArrayList<Integer>();
	
		ArrayList<String> x2Data = new ArrayList<String>();
		ArrayList<Integer> numberOfStudents = new ArrayList<Integer>();
		
		
		
		if (gradeFilterOn){
			ArrayList<String> studentsInGrade = new ArrayList<String>();
			for (String s: allStudents){
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
			tabulateAttendance(studentsInGrade, calendarDates, numberOfAttendees);
			studentsFromFilter = studentsInGrade.size();
	}
		if (genderFilterOn){
			ArrayList<String> studentsFemale = new ArrayList<String>();
			ArrayList<String> studentsMale = new ArrayList<String>();
			for (String s: allStudents){
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
			tabulateAttendance(studentsFemale, calendarDates, numberOfAttendees);
			System.out.println("MALE TABULATE ATTENDANCE");
			tabulateAttendance(studentsMale, x2Data, numberOfStudents);
			
			studentsFromFilter = studentsFemale.size() + studentsMale.size();
	}

		if (noFilter){
			tabulateAttendance(allStudents, calendarDates, numberOfAttendees);
			System.out.println("X DATA MIDWAY IS" + calendarDates);
			System.out.println("Y DATA MIDWAY IS" + numberOfAttendees);
			studentsFromFilter = allStudents.size();
		}
		
		
		layout.removeView(gView);
		
		System.out.println("NO FILTER SHOULD BE ON");
		if(noFilter || gradeFilterOn){
			System.out.println("NO FILTER");
			System.out.println("Y DATA FINAL IS" + numberOfAttendees);
			System.out.println("X DATA FINAL IS" + calendarDates);
			bar.setData(numberOfAttendees, calendarDates, "Standard");
		}
		
		if (genderFilterOn){
			bar.setData(numberOfAttendees, calendarDates, numberOfStudents, "Compare");
		}

		
		gView = bar.getView(context);
		layout = (LinearLayout) findViewById(R.id.chart);
		layout.addView(gView);
		possibleAttendance.setText("Total Possible Attendance: " + studentsFromFilter*calendarDates.size() + " of " + calendarDates.size()
				* allStudents.size());
		int daysAttended = 0;
		for (int i = 0; i < numberOfAttendees.size(); i++) {
			daysAttended = daysAttended + numberOfAttendees.get(i);
		}
		
		if(genderFilterOn){
			for(int j = 0; j< numberOfStudents.size(); j++){
				daysAttended = daysAttended + numberOfStudents.get(j);
			}
		}
		
		float percentage = ((float) daysAttended / ((float) studentsFromFilter*calendarDates.size())) * 100;
		actualAttendance.setText("Total Actual Attendance: " + daysAttended
				+ " , " + (int)percentage + "%");
	}

	public void findAllStudents() {

		ParseQuery query = new ParseQuery("Student");
		List<ParseObject> queryList = new ArrayList<ParseObject>();
		List<String> students = new ArrayList<String>();
		try {
			queryList = query.find();
			Log.v("students", queryList.size() + "");
			for (ParseObject student : queryList) {
				if (student.getString("Name") != null) {
					students.add(student.getString("Name"));
				}
			}
		} catch (com.parse.ParseException e) {
			e.printStackTrace();
		}
		
		allStudents = students;
	}

	public void tabulateAttendance(List<String> studentsList, ArrayList<String> xDataTemp, ArrayList<Integer> yDataTemp) {
		final Calendar TempC = c;
		
		yDataTemp.clear();
		xDataTemp.clear();

		
			if (TempC.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || TempC.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			} 
			
			else {
				
				//FIND QUERY DATE
				String queryDate = "In_"+ (TempC.get(Calendar.MONTH)+1) +"_";
				if(TempC.get(Calendar.DAY_OF_MONTH) < 10){
					queryDate = queryDate + "0";
				}
				queryDate = queryDate + TempC.get(Calendar.DAY_OF_MONTH)+"_"+TempC.get(Calendar.YEAR);
				
				
				
				ArrayList<Integer[]> allData = new ArrayList<Integer[]>();
			
				StudentGraphingActivity.findActivities();
				System.out.println("ACTIVITIES found");
				
				for (String student: studentsList){
					System.out.println("FOR LOOP ENTERED");
					StudentGraphingActivity.getStudentActivities(student);
					System.out.println("STUDENTACTIVITIES FOUND");
					StudentGraphingActivity.tabulateAttendance(c, student);
					System.out.println("ATTENDANCE TABULATED");
					allData.add(StudentGraphingActivity.getYData());
				}
				
				
				
				
				for(int j = 0; j < allData.get(0).length; j++){
					int studentsAttending = 0;
					for (int k = 0; k < allData.size(); k++){
						studentsAttending += (allData.get(k))[j];
					}
					yDataTemp.add(studentsAttending);
				}
				
					ArrayList<String> xDataFromStudent = StudentGraphingActivity.getXData();
				for (int l = 0; l < xDataFromStudent.size(); l++ ){
					xDataTemp.add(xDataFromStudent.get(l));
				}
					
				System.out.println("Y DATA IS " + yDataTemp);
				System.out.println("X DATA IS" + xDataTemp);

			}

	}
}
