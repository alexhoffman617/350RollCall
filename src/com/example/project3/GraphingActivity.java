package com.example.project3;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class GraphingActivity extends Activity {
	String userName = "";
	private static final String TAG = "MenuActivity";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		     userName = extras.getString("userName");
		}
		Toast.makeText(getApplicationContext(), userName, Toast.LENGTH_LONG).show();
		Log.d(TAG, userName);
	}
	
	

}
