package com.shishoufuuin.deviantart;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.json.JSONObject;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



public class Profile extends Activity implements View.OnClickListener{

	Button home, profile, diary, options, button1, savePreferences;
	EditText editText1,editText2, editText3, editText4;
	TextView textView1, textView2, textView3, textView4;
	//ImageView imageView1;
	ProfilePictureView profilePictureView;
	Bitmap bmp;
	public static String prefs = "MySharedData";
	SharedPreferences someData; 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		 SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			String choice = settings.getString("choice", "");
			
			
			if(choice.contains("White"))
			{
				setContentView(R.layout.details);   
			}
			else if(choice.contains("Black"))
			{
				setContentView(R.layout.details2);  
			}
			else if(choice.contains("Blue"))
			{
				setContentView(R.layout.details3); 
			}
			else if(choice.contains("one"))
			{
				setContentView(R.layout.details7);   
			}
			else if(choice.contains("two"))
			{
				setContentView(R.layout.details5);  
			}
			else if(choice.contains("three"))
			{
				setContentView(R.layout.details6); 
			}
		
			else 
			{
				setContentView(R.layout.details); 

			}

		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		editText3 = (EditText) findViewById(R.id.editText3);
		editText4 = (EditText) findViewById(R.id.editText4);
		
		textView1 = (TextView) findViewById(R.id.TextDate);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);
		textView4 = (TextView) findViewById(R.id.textView4);
		
		//imageView1 = (ImageView) findViewById(R.id.imageView1);
		savePreferences = (Button) findViewById(R.id.savePreferences);
		savePreferences.setOnClickListener(this);

	
		home = (Button) findViewById(R.id.bHome);
		profile = (Button) findViewById(R.id.bProfile);
		diary = (Button) findViewById(R.id.bDate);
		options = (Button) findViewById(R.id.bOptions);
		home.setOnClickListener(this);
		profile.setOnClickListener(this);
		diary.setOnClickListener(this);
		options.setOnClickListener(this);
		
		
		someData = getSharedPreferences(prefs, 0);
		String dataReturn = someData.getString("edit1", "");
		editText1.setText(dataReturn);
		
		String dataReturn2 = someData.getString("edit2", "");
		editText2.setText(dataReturn2);
		
		String dataReturn3 = someData.getString("edit3", "");
		editText3.setText(dataReturn3);
		
		String dataReturn4 = someData.getString("edit4", "");
		editText4.setText(dataReturn4);
	}
	
  
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bHome:
			Intent a = new Intent(Profile.this, MainActivity.class);
			startActivity(a);
			break;
		case R.id.bProfile:
			Intent b = new Intent(Profile.this, Profile.class);
			startActivity(b);
			break;
		case R.id.bDate:
 			Intent c = new Intent(Profile.this, DateChooser.class);
			startActivity(c);
			break;
		case R.id.bOptions:
			Intent d = new Intent(Profile.this, Options.class);
			startActivity(d);
			break;
		case R.id.savePreferences:
		
			String stringData1 = editText1.getText().toString();
			SharedPreferences.Editor editor = someData.edit();
			editor.putString("edit1", stringData1);
				
			String stringData2 = editText2.getText().toString();
			editor.putString("edit2", stringData2);

			String stringData3 = editText3.getText().toString();
			editor.putString("edit3", stringData3);
			
			String stringData4 = editText4.getText().toString();
			editor.putString("edit4", stringData4);
			editor.commit();
			
			
			SavePreferences("name", stringData2);
			
			AlertDialog.Builder alertDialog = new Builder(
        			Profile.this);
        	alertDialog.setCancelable(false);
        	alertDialog.setTitle("Saved");
			alertDialog.setMessage("You profile has been saved");
        	alertDialog.setPositiveButton("Ok",
        	        new DialogInterface.OnClickListener() {
        	         @Override
        	         public void onClick(DialogInterface dialog,
        	           int which) {
        	          // TODO Auto-generated method stub

        	         }
        	        });
        	alertDialog.show();
			
		}

		 	
	}
	
	
	private void SavePreferences(String key, String value){
		   
		 SharedPreferences saved = PreferenceManager.getDefaultSharedPreferences(this);
		 SharedPreferences.Editor editr = saved.edit();
		    editr.putString(key, value);
		    editr.commit();
		   }
	
	



}
