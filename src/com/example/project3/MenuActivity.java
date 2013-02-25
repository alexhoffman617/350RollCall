package com.example.project3;



import java.util.ArrayList;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class MenuActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "MenuActivity";
		  ListView lv;
		  EditText et;
		  String listview_array[] = { "Alex Hoffman", "John Jacob Jinglehimerschmit", "Fake Name", "A", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p" };
		  ArrayList<String> array_sort= new ArrayList<String>();
		  int textlength=0;
		
		

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		lv = (ListView) findViewById(R.id.ListView01);
		et = (EditText) findViewById(R.id.EditText01);
		lv.setAdapter(new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, listview_array));
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position, //ListView l, View v, int position, long id
					long id) {
				Object obj = lv.getAdapter().getItem(position);
				String str = obj.toString();
				Toast.makeText(getApplicationContext(), "You chose "+ str, Toast.LENGTH_LONG).show();	
				Intent intent = new Intent(getBaseContext(), GraphingActivity.class);
				intent.putExtra("userName", str);
				startActivity(intent);
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
		lv.setAdapter(new ArrayAdapter<String>
		(MenuActivity.this,
		android.R.layout.simple_list_item_1, array_sort));
		;
		}
		});
		}





	@Override
	public void onClick(View v) {
		
	}


		



	
	/*
	protected void onClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Object obj = this.getListAdapter().getItem(position);
		String str = obj.toString();
		Toast.makeText(this, "You chose"+ str, Toast.LENGTH_LONG).show();
		
	}*/
	}
        
        
        
        
        
    
