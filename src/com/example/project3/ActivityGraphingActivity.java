package com.example.project3;


import java.util.ArrayList;
import java.util.Calendar;

import org.achartengine.GraphicalView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
	

	
	private TextView activityDisplay;
	
	Spinner spinnerSorting;
	
	private Context context;
	final Calendar c = Calendar.getInstance();
	
	private ArrayList<Integer> yData = new ArrayList<Integer>();
	private ArrayList<String> xData= new ArrayList<String>();
	
	public class onItemSelect implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitygraphing);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		     activity = extras.getString("ActivityType");
		}
		Toast.makeText(getApplicationContext(), activity, Toast.LENGTH_LONG).show();
		Log.d(TAG, activity);
		
		activityDisplay =(TextView) findViewById(R.id.activityDisplay);
		activityDisplay.setText(activity);
	
		
		xData = new ArrayList<String>();
		yData = new ArrayList<Integer>();
		xData.add("Fake");
		yData.add(120);
		xData.add("Fake");
		yData.add(130);
		xData.add("Fake");
		yData.add(140);

		
		BarGraph bar = new BarGraph();

		bar.setData(yData, xData, "Standard");
		context = this;

		GraphicalView gview = bar.getView(this);

		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);

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
	
	
	

}
