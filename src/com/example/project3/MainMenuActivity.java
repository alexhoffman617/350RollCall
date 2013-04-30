package com.example.project3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends Activity{

	Button studentBtn;
	Button activitiesBtn;
	Button aggregateBtn;
	
	
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.mainmenu);
	

	}


	public void onStudentBtnClick(View arg0) {
		
		
		Intent intent = new Intent(this, MenuActivity.class);
		intent.putExtra("ButtonClicked", "Students" );
		startActivity(intent);		
	}
	
	public void onActivityBtnClick(View arg0) {
		
		
		Intent intent = new Intent(this, MenuActivity.class);
		intent.putExtra("ButtonClicked", "Activity" );
		startActivity(intent);		
	}
	
	public void onAggregateBtnClick(View arg0) {
		
		
		Intent intent = new Intent(this, AggregateGraphingActivity.class);
		intent.putExtra("ButtonClicked", "Aggregate" );
		startActivity(intent);		
	}
	
}
