package com.example.project3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends Activity implements OnClickListener{

	Button studentBtn;
	Button activitiesBtn;
	Button aggragateBtn;
	
	
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.mainmenu);
	studentBtn=(Button)findViewById(R.id.studentButton);
	studentBtn.setOnClickListener((OnClickListener) this);
	}


	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);		
	}
	
}
