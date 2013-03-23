package com.example.project3;



import java.util.ArrayList;
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


public class MenuActivity extends Activity implements OnClickListener {

	
	private static final String TAG = "MenuActivity";
		  ListView lv;
		  EditText et;
		  String listview_array[] = { " " };
		  String listview_array2[] = {" "};
		  ArrayList<String> array_sort= new ArrayList<String>();
		  int textlength=0;
		  
		  TextView title;
		  
		  String listType;
		
	private void initializeParse(){
		Parse.initialize(this, LoginHandler.link1, LoginHandler.link2);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		initializeParse();

		
		lv = (ListView) findViewById(R.id.ListView01);
		et = (EditText) findViewById(R.id.EditText01);
		 final ArrayAdapter<String> aAdapter = new  ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		 final ArrayAdapter<String> aAdapter2 = new  ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listview_array2);
			lv.setAdapter(aAdapter);
		lv.setAdapter(aAdapter);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		     listType = extras.getString("ButtonClicked");
		 
		}
		
		
		//FOR STUDENTS
		if(listType.equals( "Student")){
		ParseQuery query = new ParseQuery("Student");
		query.findInBackground(new FindCallback() {
			public void done(List<ParseObject> objects, com.parse.ParseException e) {
				if (e == null) {
					listview_array= new String[objects.size()];
		        	 for (int i = 0; i < objects.size(); i++){
		        		 System.out.println(objects.get(i));
		        		 ParseObject temp = objects.get(i);
		        		 String user = temp.getString("Name");
		        		 listview_array[i] = user;
		        		 aAdapter.add(user);
		        		 aAdapter.notifyDataSetChanged();
		        		 
		        	 }
		        	 //Wont fill unless some action is made
		        	 
		        	 
		         } else {
		        	 return;
		         }				
			}
		});
		
		}
		//MAKE ANOTHER FOR ACTIVITIES
		else if(listType.equals("Activity")){
			ParseQuery query = new ParseQuery("Activity");
			query.findInBackground(new FindCallback() {
				public void done(List<ParseObject> objects, com.parse.ParseException e) {
					if (e == null) {
						listview_array= new String[objects.size()];
			        	 for (int i = 0; i < objects.size(); i++){
			        		 System.out.println(objects.get(i));
			        		 ParseObject temp = objects.get(i);
			        		 String user = temp.getString("DisplayName");
			        		 listview_array[i] = user;
			        		 aAdapter.add(user);
			        		 aAdapter.notifyDataSetChanged();
			        		 
			        	 }
			        	 //Wont fill unless some action is made
			        	 
			        	 
			         } else {
			        	 return;
			         }				
				}
			}); 
			
		}

		 title =(TextView)findViewById(R.id.listType);
		 title.setText(listType);
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position, //ListView l, View v, int position, long id
					long id) {
				Object obj = lv.getAdapter().getItem(position);
				String str = obj.toString();
				Toast.makeText(getApplicationContext(), "You chose "+ str, Toast.LENGTH_LONG).show();	
				
				if(listType.equals("Student")){
				Intent intent = new Intent(getBaseContext(), StudentGraphingActivity.class);
				intent.putExtra("userName", str);
				startActivity(intent);
				}
				
				if(listType.equals("Activity")){
					Intent intent = new Intent(getBaseContext(), ActivityGraphingActivity.class);
					intent.putExtra("ActivityType", str);
					startActivity(intent);
				}
			}
		});
		et.addTextChangedListener(new TextWatcher()
		{
		public void afterTextChanged(Editable s)
		{
		                                                                // Abstract Method of TextWatcher Interface.
		}
		public void beforeTextChanged(CharSequence s,
		int start, int count, int after)
		{
		// Abstract Method of TextWatcher Interface.
		}
		public void onTextChanged(CharSequence s,
		int start, int before, int count)
		{
		textlength = et.getText().length();
		array_sort.clear();
		for (int i = 0; i < listview_array.length; i++)
		{
		if (textlength <= listview_array[i].length())
		{
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
		lv.setAdapter(new ArrayAdapter<String>(MenuActivity.this,android.R.layout.simple_list_item_1, array_sort));
		;
		}
		});
		}





	@Override
	public void onClick(View v) {
		
	}


		



	

	}
        
        
        
        
        
    
