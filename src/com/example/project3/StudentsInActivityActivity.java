package com.example.project3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class StudentsInActivityActivity extends Activity {
	
	private static final String TAG = "MenuActivity";
		  ListView lv;
		  EditText et;
		  String listview_array[] = { " " };
		  String listview_array2[] = {" "};
		  ArrayList<String> array_sort= new ArrayList<String>();
		  int textlength=0;
		  
		  TextView title;
		  
		  String listType;
	
		  private List<String> all_students = new ArrayList<String>();
		  
		
	private void initializeParse(){
		Parse.initialize(this, LoginHandler.link1, LoginHandler.link2);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentsinactivity);
		initializeParse();

		
		lv = (ListView) findViewById(R.id.ListView01);
		et = (EditText) findViewById(R.id.EditText01);
		
		 final ArrayAdapter<String> aAdapter = new  ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		// final ArrayAdapter<String> aAdapter2 = new  ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, activity_array);
			lv.setAdapter(aAdapter);
		lv.setAdapter(aAdapter);
		
		
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		     listType = extras.getString("ActivityType");
		 
		}
		
		
		//Students
		ParseQuery query = new ParseQuery(listType);
		List<ParseObject> queryList = new ArrayList<ParseObject>();
		try {
		queryList = query.find();
		for(ParseObject student : queryList) {
		if(student.getString("Name")!=null){
			String studentName = student.getString("Name");
			all_students.add(studentName);
		 aAdapter.add(studentName);
		 aAdapter.notifyDataSetChanged();

		}
		}
		}
		catch(com.parse.ParseException e) {
		e.printStackTrace();
		}
		
		
		
		
		
		title =(TextView)findViewById(R.id.listType);
		title.setText(listType);
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			
@Override
public void onItemClick(AdapterView<?> l, View v, int position, long id) {
			Object obj = lv.getAdapter().getItem(position);
			String str = obj.toString();
			Toast.makeText(getApplicationContext(), "You chose "+ str, Toast.LENGTH_LONG).show();	
				
			if(listType.equals("Student")){
				Intent intent = new Intent(getBaseContext(), StudentGraphingActivity.class);
				intent.putExtra("userName", str);
				startActivity(intent);
			}
				
			if(listType.equals("ActivityType")){
				Intent intent = new Intent(getBaseContext(), ActivityGraphingActivity.class);
				intent.putExtra("ActivityType", str);
				startActivity(intent);
			}
			}
	});
	
		et.addTextChangedListener(new TextWatcher(){
			public void afterTextChanged(Editable s){
				
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){
				// Abstract Method of TextWatcher Interface.
				}
			public void onTextChanged(CharSequence s, int start, int before, int count){
				textlength = et.getText().length();
				array_sort.clear();
				System.out.println("144");
				for (int i = 0; i < listview_array.length; i++){
					if (textlength <= listview_array[i].length()){
		String[] splitname = listview_array[i].split(" ");
		if(et.getText().toString().equalsIgnoreCase(
		(String)
		listview_array[i].subSequence(0,
		textlength)))
		{
		array_sort.add(listview_array[i]);
		                                                                                                }
		else if(et.getText().toString().contains(" ")==false){
			for(int ii = 0; ii<splitname.length; ii++){
				Log.d(TAG, splitname[ii]);
				if(et.getText().toString().equals(
						(String)
						splitname[ii].subSequence(0,
						textlength)))
						{
						Log.d(TAG, et.getText().toString() + "  should = " + splitname[ii].subSequence(0,  textlength));
						array_sort.add(listview_array[i]);
						}}}
		                                                                                }
		                                                               }
		lv.setAdapter(new ArrayAdapter<String>(StudentsInActivityActivity.this,android.R.layout.simple_list_item_1, array_sort));
		;
		}
		});
		}





	
	public void onGraphBtnClick(View v) {
		Intent intent = new Intent(this, ActivityGraphingActivity.class);
		intent.putExtra("ActivityType", listType);
		startActivity(intent);
	}


		



	

	}
        
        