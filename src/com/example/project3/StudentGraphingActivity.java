package com.example.project3;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.achartengine.GraphicalView;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
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
	public static String userName = " ";
	private DatePicker graphStartDate;
	private static List<String> activities;
	private int startDay;
	private int startMonth;
	private int startYear;
	private TextView nameDisplay;
	private BarGraph bar = new BarGraph();
	private GraphicalView gview;
	private LinearLayout layout;
	private static ArrayList<Integer> yData;
	private static ArrayList<String> xData;
	private Context context;
	final Calendar c = Calendar.getInstance();
	private TextView PossibleAttendance;
	private TextView ActualAttendance;
	private TextView LastDayPresent;
	private String ldp = "FIND REAL LAST DAY PRESENT";
	private static ArrayList<String> list_Of_Activities = new ArrayList<String>();

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		System.out.println("START");
		
		System.out.println("END");
		
		setContentView(R.layout.studentgraphing);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			userName = extras.getString("userName");
		}
		Parse.initialize(this, LoginHandler.link1, LoginHandler.link2);
		nameDisplay =(TextView) findViewById(R.id.nameDisplay);
		nameDisplay.setText(userName);
		
		findActivities();
		getStudentActivities(userName);
		
		xData = new ArrayList<String>();
		yData = new ArrayList<Integer>();
		PossibleAttendance = (TextView) findViewById(R.id.PossibleAttendance);
		ActualAttendance = (TextView) findViewById(R.id.ActualAttendance);
		LastDayPresent = (TextView) findViewById(R.id.lastDayPresent);
		bar.setData(yData, xData, "Standard");
		gview = bar.getView(this);
		context = this;
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
	}
	public void onCommentsBtnClick(View arg0){
		Intent intent = new Intent(this, StudentComentsActivity.class);
		intent.putExtra("userName", userName);
		startActivity(intent);		
	}

	public void onGraphBtnClick(View arg0){
		System.out.println("ON GRAPH BUTTON CLICKED");
		
		tabulateAttendance(c, userName);
		
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
		float percentage = ((float)daysAttended/(float)xData.size())*100;
		ldp = lastDayPresent(userName);
		ActualAttendance.setText("Total Actual Attendance: " + daysAttended + " , " + (int)percentage + "%");
		LastDayPresent.setText("Last Day Present: " + ldp);
	}
	public static String lastDayPresent(String student) {
		List<String> acts = activities;
		Calendar currentLastDay = Calendar.getInstance();
		//////////////////REMOVE THESE WHEN POPULATED UP TO TODAY//////////////////////////////
		currentLastDay.set(Calendar.DAY_OF_MONTH, 5);
		currentLastDay.set(Calendar.MONTH, 4);
		currentLastDay.set(Calendar.YEAR, 2013);
		///////////////////////////////////////////////////////////////////////////////////////
		boolean absent = true;
		while (absent) {
			String date = "In_"+ currentLastDay.get(Calendar.MONTH)+"_";
			if(currentLastDay.get(Calendar.DAY_OF_MONTH) < 10) {
				date = date + "0";
			}
			date = date + currentLastDay.get(Calendar.DAY_OF_MONTH)+"_"+currentLastDay.get(Calendar.YEAR);
			for(String act : acts) {
				ParseQuery query = new ParseQuery(act);
				query.whereEqualTo("Name", student);
				List<ParseObject> queryList = new ArrayList<ParseObject>();
				try {
					queryList = query.find();
					for(ParseObject s : queryList) {
						if(s.getString(date)!=null && !s.getString(date).equals("--")){
							Log.v("tag", date +"     "+ s.getString(date));
							absent = false;
							String returnDate = "" + currentLastDay.get(Calendar.MONTH) +"/" +currentLastDay.get(Calendar.DATE) + "/" + currentLastDay.get(Calendar.YEAR);
							return returnDate;
						}
					}
				}
				catch(com.parse.ParseException e) {
					e.printStackTrace();
				}
			}
			currentLastDay.add(Calendar.DAY_OF_MONTH, -1);
		}
		String returnDate = "" + currentLastDay.get(Calendar.MONTH) +"/" +currentLastDay.get(Calendar.DATE) + "/" + currentLastDay.get(Calendar.YEAR);
		return returnDate;
	}
	
	public static List<String> getStudentActivities(String studentName) {
		
		ParseQuery query1 = new ParseQuery("Student");
		query1.whereEqualTo("Name", studentName);
		System.out.println("STUDENT IS" + studentName);
		List<ParseObject> queryList = new ArrayList<ParseObject>();
		activities = new ArrayList<String>();
		try {
			queryList = query1.find();
			Log.v("number of students found", queryList.size()+"");
			for(ParseObject student : queryList) {
				
				for (String activity: list_Of_Activities){
					if (student.getNumber(activity) != null){
						activities.add(activity);
					}
				}
			}
		}
		catch(com.parse.ParseException e) {
			e.printStackTrace();
		}
		return activities;
	}
	
	public static void findActivities(){
	
			ParseQuery query = new ParseQuery("Activity");
			List<ParseObject> queryList = new ArrayList<ParseObject>();
			list_Of_Activities = new ArrayList<String>();
			
			try {
				queryList = query.find();
			} catch (com.parse.ParseException e) {
				e.printStackTrace();
			}
			
			for (ParseObject sObject: queryList){
				if (sObject.getClassName() != null){
					list_Of_Activities.add(sObject.getString("DisplayName"));
					System.out.println("LIST OF ACTIVITIES IS" + list_Of_Activities);
				}
			}
	}
		public static void tabulateAttendance(Calendar c, String userName){
			final Calendar TempC = c;
			yData = new ArrayList<Integer>();
			xData = new ArrayList<String>();
			for(int i = 0; i <7; i++){
				boolean columnExists = true;
				if(TempC.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || TempC.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){}
				
				
				else{
					String queryDate = "In_"+ (TempC.get(Calendar.MONTH)+1) +"_";
					if(TempC.get(Calendar.DAY_OF_MONTH) < 10){
						queryDate = queryDate + "0";
					}
					queryDate = queryDate + TempC.get(Calendar.DAY_OF_MONTH)+"_"+TempC.get(Calendar.YEAR);
					
					boolean absent = true;
					for (String activity : activities) {
						ParseQuery query = new ParseQuery(activity);
						query.whereEqualTo("Name", userName);
						List<ParseObject> queryList = new ArrayList<ParseObject>();
						try {
							queryList = query.find();
							System.out.println("SIZE OF QUERY LIST IS " + queryList.size());
							for(ParseObject student : queryList) {
								System.out.println("STUDENT FOUND");
								System.out.println("STUDENT: " + student.getString("NAME"));
								System.out.println("ACTIVITY: " + activity);
								System.out.println("QUERY DATE: " + queryDate);
								System.out.println("VALUE IS" + student.getString(queryDate));
								if(student.getString(queryDate) == null){
									columnExists = false;
								}
								
								if(student.getString(queryDate)!=null && !student.getString(queryDate).equals("--")){
									System.out.println("PRESENT ON" + queryDate);
									Log.v("tag", queryDate +"     "+ student.getString(queryDate));
									absent = false;
								}
							}
						}
						catch(com.parse.ParseException e) {
							e.printStackTrace();
						}
					}
					if(TempC.get(Calendar.DAY_OF_WEEK)!=Calendar.SATURDAY && TempC.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY && columnExists){
					xData.add(TempC.get(Calendar.MONTH)+1 + "/" + TempC.get(Calendar.DAY_OF_MONTH) + "/" + TempC.get(Calendar.MONTH));
					if (absent) {
						yData.add(0);
					}
					else {
						yData.add(1);
					}
					}
					
				}
				TempC.add(Calendar.DATE, 1);
			}
			TempC.add(Calendar.DATE, -7);
		}
		
		public static Integer[] getYData(){
			Integer [] yDataArray = new Integer[yData.size()];
			for (int i = 0; i < yData.size(); i++){
				yDataArray[i] = yData.get(i);
			}
			return yDataArray;
		}
	
		public static ArrayList<String> getXData(){
			return xData;
		}
	
}