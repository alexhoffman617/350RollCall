package com.example.project3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


/***
 * Handles interaction with online Parse database.
 * In general, methods convert to ActivityObject or StudentObject
 *
 */
public class ParseHandler {

	/**
	 * given a name, adds an activity to Parse datastore
	 * @param name
	 */

	public static void addActivity(String name){
        ParseObject activity = new ParseObject("Activity");
        ParseQuery query = new ParseQuery("Activity");
        int id = 0;
//        try {
//			id = query.count()+1;
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        System.out.println("In addActi 1");
        StringTokenizer tokenizer = new StringTokenizer(name);
        String className = tokenizer.nextToken();
        activity.put("ClassName", className);
        activity.put("DisplayName", name);
        activity.put("ID", id);
        activity.put("count", 0);
        activity.saveInBackground();
        
        System.out.println("In addActi 2");
        ParseObject newActivity = new ParseObject(className);
        newActivity.put("schoolYear", "2011-2012");
        newActivity.put("program", name);
        newActivity.saveInBackground();
	}

	public static void signUpUser(String username, /*String email,*/ String password, final Context context) {
			System.out.println("ENTER Flag 1");
			ParseUser user = new ParseUser();
			user.setUsername(username);
			user.setPassword(password);
			System.out.println(username);
			System.out.println(password);
		
			System.out.println("Before Sign up");
			
				user.signUpInBackground(new SignUpCallback() {
					CharSequence text = "";
				   public void done(ParseException e) {
				     if (e == null) {
				       System.out.println("signUpUser YES!");
				    text = "Registration successful!";
				     } else {
				       System.out.println("signUpUser failed");
			         text = "Already have one";
				     }
				     int duration = Toast.LENGTH_SHORT;
			    		Toast toast = Toast.makeText(context, text, duration);
			    		toast.show();
				   
				   }
				 });
			
	
	}
	
	public static ParseUser checkLogin(String username, String password){
	//	try {
//			ParseUser user = Parseser.logIn(username, password);
//			if(user!=null){
//				return user;
			System.out.println(username+" "+password);
		    System.out.print("Check Begin!!!!!!!");
			ParseUser.logInInBackground(username, password, null);
					/*new LogInCallback() {
				
				@Override
				public void done(ParseUser arg0, ParseException arg1) {
					// TODO Auto-generated method stub
					
					ParseUser user =ParseUser.getCurrentUser();
					//System.out.println(user);
					
					if (user==null){
						System.out.print("Void Done - user is empty");
					}
					System.out.println(user.isAuthenticated());
					System.out.flush();
				}
			});*/
			ParseUser user = ParseUser.getCurrentUser();
			System.out.print("Check Finished");
			/*try{
					user = user.fetch();
			}
			catch(ParseException pe)
			{
				System.out.println("Parse Exception");
			}
			if (user==null){
				System.out.print("User is Empty");
			}*/
			System.out.flush();
			return user;
//		}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return null;
	}
	
	
	static class RetriveQuery extends AsyncTask<String, Void, ParseObject>{

		@Override
		protected ParseObject doInBackground(String... arg) {
			ParseQuery query = new ParseQuery(arg[1]);
			query.whereEqualTo("ClassName", arg[0]);
			try {
				ParseObject a = query.getFirst();
				return a;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
}
	