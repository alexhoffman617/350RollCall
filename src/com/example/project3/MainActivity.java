package com.example.project3;

import android.os.Bundle;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.PushService;

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
		Parse.initialize(this, "cuoXWbqvBKs8SUrhnyKdyNWiMPZxuDBZ31ehltVI", "tl8VMcFHu7u3haym9KSbRKEP61MmxPDvmL06dxeo");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn=(Button)findViewById(R.id.button);
		btn.setOnClickListener((OnClickListener) this);
		
		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground();
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(this, MainMenuActivity.class);
		startActivity(intent);
	}


	

}
