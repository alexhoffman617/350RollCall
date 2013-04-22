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

public class AgregateGraphingActivity extends Activity {
	String userName = "";
	private static final String TAG = "MenuActivity";
	private DatePicker graphStartDate;

	private int startDay;
	private int startMonth;
	private int startYear;

	Spinner spinnerAggregate;

	private TextView school;

	private BarGraph bar;
	private GraphicalView gview;
	private LinearLayout layout;

	private ArrayList<String> xData = new ArrayList<String>();
	private ArrayList<Integer> yData = new ArrayList<Integer>();
	private ArrayList<Integer> yDataMax = new ArrayList<Integer>();
	
	
	private ArrayList<String> x2Data = new ArrayList<String>();
	private ArrayList<Integer> y2Data = new ArrayList<Integer>();
	private ArrayList<Integer> y2DataMax = new ArrayList<Integer>();
	
	
	private Context context;
	final Calendar c = Calendar.getInstance();

	private TextView PossibleAttendance;
	private TextView ActualAttendance;

	private boolean noFilter = true;
	private boolean genderFilterOn = false;
	private boolean gradeFilterOn = false;
	private int gradeLevel = 3;
	
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
		PossibleAttendance = (TextView) findViewById(R.id.PossibleAttendance);
		ActualAttendance = (TextView) findViewById(R.id.ActualAttendance);
		school.setText("Agregate Data");

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

		graphStartDate.init(startYear, startMonth, startDay,
				new OnDateChangedListener() {

					@Override
					public void onDateChanged(DatePicker arg0, int arg1,
							int arg2, int arg3) {
						// Toast.makeText(getApplicationContext(), "arg 1: " +
						// arg1 + "    arg2: " + arg2 + "    arg3: "+ arg3,
						// Toast.LENGTH_LONG).show();

						c.set(arg1, arg2, arg3);
						// Toast.makeText(getApplicationContext(),
						// c2.get(Calendar.DAY_OF_MONTH)+ " " +
						// c2.get(Calendar.MONTH) + " " +c2.get(Calendar.YEAR),
						// Toast.LENGTH_LONG).show();
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
			tabulateAttendance(studentsInGrade, xData, yData, yDataMax);
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
			tabulateAttendance(studentsFemale, xData, yData, yDataMax);
			System.out.println("MALE TABULATE ATTENDANCE");
			tabulateAttendance(studentsMale, x2Data, y2Data, y2DataMax);
	}

		if (noFilter){
			tabulateAttendance(allStudents, xData, yData, yDataMax);
		}
		
		
		layout.removeView(gview);
		
		
		if(noFilter || gradeFilterOn){
			bar.setData(yData, xData, yDataMax, "Standard");
		}
		
		if (genderFilterOn){
			bar.setData(yData, xData, y2Data, "Compare");
		}

		
		gview = bar.getView(context);
		layout = (LinearLayout) findViewById(R.id.chart);
		layout.addView(gview);
		PossibleAttendance.setText("Total Possible Attendance: " + xData.size()
				* allStudents.size());
		int totalPossibleAttendance = xData.size() * allStudents.size();
		int daysAttended = 0;
		for (int i = 0; i < yData.size(); i++) {
			daysAttended = daysAttended + yData.get(i);
		}
		float percentage = ((float) daysAttended / (float) totalPossibleAttendance) * 100;
		ActualAttendance.setText("Total Actual Attendance: " + daysAttended
				+ " , " + percentage + "%");
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

	public void tabulateAttendance(List<String> studentsList, List<String> xDataTemp, List<Integer> yDataTemp, List<Integer> yDataMaxTemp) {
		final Calendar TempC = c;
		
		yDataMaxTemp = new ArrayList<Integer>();
		yDataTemp = new ArrayList<Integer>();
		xDataTemp = new ArrayList<String>();
		
		for (int i = 0; i < 7; i++) {
			if (TempC.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| TempC.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			} else {
				String queryDate = "In_" + TempC.get(Calendar.MONTH) + 1 + "_";
				if (TempC.get(Calendar.DAY_OF_MONTH) < 10) {
					queryDate = queryDate + "0";
				}
				queryDate = queryDate + TempC.get(Calendar.DAY_OF_MONTH) + "_"
						+ TempC.get(Calendar.YEAR);
				int sum = 0;
				for (String student : studentsList) {
					boolean absent = false;
					for (String activity : StudentGraphingActivity
							.getStudentActivities(student)) {
						ParseQuery query = new ParseQuery(activity);
						query.whereEqualTo("Name", student);
						List<ParseObject> queryList = new ArrayList<ParseObject>();
						try {
							queryList = query.find();
							for (ParseObject s : queryList) {
								if (s.getString(queryDate) != null
										&& !s.getString(queryDate).equals("--")) {
									Log.v("tag",
											student + "   " + queryDate
													+ "      " + activity
													+ "     "
													+ s.getString(queryDate));
									absent = false;
								}
							}
						} catch (com.parse.ParseException e) {
							e.printStackTrace();
						}
					}
					if (!absent) {
						Log.v("STUDENT NOT ABSENT", student + "   " + queryDate);
						sum++;
					}
				}
				xDataTemp.add(TempC.get(Calendar.MONTH) + 1 + "/"
						+ TempC.get(Calendar.DAY_OF_MONTH) + "/"
						+ TempC.get(Calendar.YEAR));
				yDataMaxTemp.add(allStudents.size());
				yDataTemp.add(sum);
				Log.v("TAAAAAAAG", sum + "");
			}
			TempC.add(Calendar.DATE, 1);
		}

		TempC.add(Calendar.DATE, -7);
	}
}
