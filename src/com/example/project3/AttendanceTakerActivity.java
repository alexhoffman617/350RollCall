package com.example.project3;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;

public class AttendanceTakerActivity extends Activity {

    private static final int LOGIN_DIALOG = 0;
    public static final int ACTIVITY_Attendance = 1;
    public static final int ACTIVITY_Roster = 2;
    public static final int ACTIVITY_AddActivity = 3;
    public static final int ACTIVITY_ActivitiesList = 4;
    public static final int ACTIVITY_EditActivities = 5;
    public static final int ACTIVITY_ProfileActivity = 6;
    public static final int ACTIVITY_RegistrationActivity = 7;
    public static final int ACTIVITY_AddStudent = 8;
    public static final int ACTIVITY_ActivityHome = 9;
    public static final int ACTIVITY_StudentRoster = 10;
    public static final int ACTIVITY_RemoveStudent = 11;
	public static final int ACTIVITY_EditProfile = 12;
	public static final int ACTIVITY_RemoveActivity = 13;
	public static final int ACTIVITY_ViewScreen  =  14;
	public static final int ACTIVITY_ViewStudents =15;
	public static final int ACTIVITY_AddNewStudent =16;
	public static final int ACTIVITY_RemoveStudentMian =17;
	public static final int ACTIVITY_SendEmailActivity =18;
	public static final int ACTIVITY_EditFileActivity =19;
	public static int DatabaseCode=0;
	 private Spinner mySpinner;     
	 private ArrayAdapter<String> adapter;  
     private List<String> insts = new ArrayList<String>(); 
     private String logindb;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_taker);

        showDialog(LOGIN_DIALOG);
        //initializes connection with Parse
    //    EditText db= (EditText) findViewById (R.id.codeField);
       // DatabaseCode=Integer.parseInt(db.getText().toString());
     //   System.out.println("Text!!!!!!!!!!!!!!!!!!!" +db.getText().toString());
       // Parse.initialize(this, "cuoXWbqvBKs8SUrhnyKdyNWiMPZxuDBZ31ehltVI", "tl8VMcFHu7u3haym9KSbRKEP61MmxPDvmL06dxeo");
       // Parse.initialize(this, "A0tjEhE7hfkY0eZfxjkoq2DlapSWC7LKbWi7fh68", "fNMw4Yy1b018m5K4P5x7ytwiqCCtO4MldOXPWkFW"); 
        
        insts.add("Comegys");  
        insts.add("Wilson");     
        insts.add("Lea");     
        insts.add("Sayre");     
        insts.add("UCHS");     
        insts.add("Huey");     
        insts.add("West");     
        insts.add("Bartrams");   
        insts.add("SOTF");
        insts.add("New 1");     
        insts.add("New 2");   
        insts.add("New 3");
        insts.add("New 4");   
        insts.add("New 5");

        mySpinner = (Spinner)findViewById(R.id.spinner1);     
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, insts);     
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     
        mySpinner.setAdapter(adapter);     
        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){     
            @SuppressWarnings("unchecked")  
            public void onItemSelected(AdapterView arg0, View arg1, int arg2, long arg3) {     
                System.out.println("您选择的是："+ adapter.getItem(arg2));     
                logindb=adapter.getItem(arg2);
                arg0.setVisibility(View.VISIBLE);     
            }     
            @SuppressWarnings("unchecked")  
            public void onNothingSelected(AdapterView arg0) {     
                // TODO Auto-generated method stub     
    
                arg0.setVisibility(View.VISIBLE);     
            }     
        });     
  
        mySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){     
        public void onFocusChange(View v, boolean hasFocus) {     
        // TODO Auto-generated method stub     
            v.setVisibility(View.VISIBLE);     
        }     
        });     
    
    
    }
    
    public void onLoginClick(View view) {
      //   EditText db= (EditText) findViewById (R.id.codeField);

       // 		 Integer.parseInt(db.getText().toString());
    	logindb = "New 1";
    	chooseDB(logindb);
        System.out.println("DatabaseCode First Time"+DatabaseCode);
        new LoginHandler().LoginDB(DatabaseCode);
		Parse.initialize(this, LoginHandler.link1, LoginHandler.link2);

         
    	EditText u = (EditText) findViewById (R.id.usernameField);
    	EditText p = (EditText) findViewById (R.id.passwordField);
    	String username = u.getText().toString();
    	String password = p.getText().toString();
    	System.out.println("name:"+username);
    	System.out.println("psw:"+password);
    	
    	
    	ParseUser userObject = ParseHandler.checkLogin(username, password);
    	
    	if(isOnline())
    	{
    	   	if(userObject!=null){
	    		System.out.println("Main Act Has Object");
	    		Intent i = new Intent(this, MainMenuActivity.class);
	    		startActivity(i);
		    	//startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_ViewScreen);
	
	    	}
	    	else{
	    		System.out.println("Main Act NO Object");
	    		Context context = getApplicationContext();
	     		CharSequence text = "Login Failed! Incorrect username or password.";
	    		int duration = Toast.LENGTH_SHORT;
	    		Toast toast = Toast.makeText(context, text, duration);
	    		toast.show();
	    	}
    	}
    	else {
    		
    		Intent i = new Intent(this, MainMenuActivity.class);
    		//startActivityForResult(i, AttendanceTakerActivity.ACTIVITY_ViewScreen);
    		startActivity(i);
    	}
    }
    
//    public void onRegisterClick(View view) {
//    	Intent i = new Intent(this, RegistrationScreenActivity.class);
//    	startActivity(i);
//    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	super.onActivityResult(requestCode, resultCode, intent);
    }

    public void chooseDB(String logindb){
    	if(logindb.equals("Comegys") )
            DatabaseCode=1;
    	else if (logindb.equals("Wilson"))
    		DatabaseCode=2;
    	else if (logindb.equals("Lea"))
    		DatabaseCode=3;
    	else if (logindb.equals("Sayre"))
    		DatabaseCode=4;
    	else if (logindb.equals("UCHS"))
    		DatabaseCode=5;
    	else if (logindb.equals("Huey"))
    		DatabaseCode=6;
    	else if (logindb.equals("West"))
    		DatabaseCode=7;
    	else if (logindb.equals("Bartrams"))
    		DatabaseCode=8;
    	else if (logindb.equals("SOTF"))
    		DatabaseCode=9;
    	else if (logindb.equals("New 1"))
    		DatabaseCode=10;
    	else if (logindb.equals("New 2"))
    		DatabaseCode=11;
    	else if (logindb.equals("New 3"))
    		DatabaseCode=12;
    	else if (logindb.equals("New 4"))
    		DatabaseCode=13;
    	else if (logindb.equals("New 5"))
    		DatabaseCode=14;
    	
    }
    
  //Added by Arvind 10/22/2012 - To Check Connectivity
  	 public boolean isOnline() { 
  		 boolean haveConnectedWifi = false;
  		 boolean haveConnectedMobile = false;
   	     ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
  		 NetworkInfo[] netInfo = cm.getAllNetworkInfo();
  		    for (NetworkInfo ni : netInfo) {
  		        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
  		            if (ni.isConnected())
  		                haveConnectedWifi = true;
  		        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
  		            if (ni.isConnected())
  		                haveConnectedMobile = true;
  		    }
  		    return haveConnectedWifi || haveConnectedMobile;
  		} 
    
    

}

