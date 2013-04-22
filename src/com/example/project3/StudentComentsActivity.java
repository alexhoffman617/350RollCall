package com.example.project3;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StudentComentsActivity extends Activity {
	String userName;
	private static final String TAG = "MenuActivity";
	private DatePicker graphStartDate;

	
	private int startDay;
	private int startMonth;
	private int startYear;
	

	
	private TextView nameDisplay;
	private ListView lv;
	private EditText et;
	private ArrayList<String> comments = new ArrayList<String>();
	private ArrayList<String> comments2 = new ArrayList<String>();
	
	private Calendar c = Calendar.getInstance();
	
	private TextView PossibleAttendance;
	private TextView ActualAttendance;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentcomments);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		     userName = extras.getString("userName");
		}

	
		nameDisplay =(TextView) findViewById(R.id.nameDisplay);
		nameDisplay.setText(userName);
	
//		PossibleAttendance = (TextView) findViewById(R.id.PossibleAttendance);
//		ActualAttendance = (TextView) findViewById(R.id.ActualAttendance);
	

	
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
	
	
	comments.add("PLACEHOLDER");
	
	lv = (ListView) findViewById(R.id.comments);
	et = (EditText) findViewById(R.id.EditText01);
	lv.setAdapter(new ArrayAdapter<String>(this,
	android.R.layout.simple_list_item_1, comments));
	
	
	
	
	}
	
	public void onGraphBtnClick(View arg0){
		Intent intent = new Intent(this, StudentGraphingActivity.class);
		intent.putExtra("userName", userName);
		startActivity(intent);		
	}
	
	public void onDateChangeClick(View arg0){
		final Calendar TempC = c;
		
		comments = new ArrayList<String>();

		List<String> activities = StudentGraphingActivity.getStudentActivities(userName);


		for(int i = 0; i <7; i++){

		if(TempC.get(Calendar.DAY_OF_WEEK)==2 || TempC.get(Calendar.DAY_OF_WEEK)==3){}

		else{

		String queryDate = "Comment_"+ TempC.get(Calendar.MONTH)+"_";

		if(TempC.get(Calendar.DAY_OF_MONTH) < 10) {

		queryDate = queryDate + "0";

		}

		queryDate = queryDate + TempC.get(Calendar.DAY_OF_MONTH)+"_"+TempC.get(Calendar.YEAR);


		//boolean absent = false;

		for (String activity : activities) {

		 

		ParseQuery query = new ParseQuery(activity);

		query.whereEqualTo("Name", userName);

		List<ParseObject> queryList = new ArrayList<ParseObject>();


		try {

		queryList = query.find();

		for(ParseObject student : queryList) {

		if(student.getString(queryDate)!=null && !student.getString(queryDate).equals("--")){

		Log.v("tag", queryDate +"     "+ student.getString(queryDate));

		String date = TempC.get(Calendar.MONTH)+"/"+TempC.get(Calendar.DAY_OF_MONTH)+"/"+TempC.get(Calendar.YEAR);

		comments.add("On "+date + " in " + activity +" - " + student.getString(queryDate));

		}

		}

		}

		catch(com.parse.ParseException e) {

		e.printStackTrace();

		}
		
		



		}

		 

		}

		 



		TempC.add(Calendar.DATE, 1);




		}
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, comments2));
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, comments));
		lv.refreshDrawableState();
	}
	
	

}
