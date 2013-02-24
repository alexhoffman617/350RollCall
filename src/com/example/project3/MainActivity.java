package com.example.project3;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn=(Button)findViewById(R.id.button);
		btn.setOnClickListener((OnClickListener) this);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
	}


	

}
