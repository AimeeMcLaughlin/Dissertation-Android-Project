package com.shishoufuuin.deviantart;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.microsoft.windowsazure.mobileservices.*;
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
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{

	

	  Button home, profile, diary, options, upload, save, bPhoto;
	  ImageView takePhoto;
	  TextView theCounter, newCounter, blah, textView1, counter1, blahblah;
	  final static int cameraData = 0;
	  Intent i;
	  Bitmap bmp;
	  private MobileServiceClient mClient;
	  private Session.StatusCallback statusCallback = new SessionStatusCallback();
	  int counter = 0;

		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String choice = settings.getString("choice", "");
		
		
		
		if(choice.contains("White"))
		{
			setContentView(R.layout.activity_main); 
		}
		else if(choice.contains("Black"))
		{
			setContentView(R.layout.activity_main2);
		}
		else if(choice.contains("Blue"))
		{
			setContentView(R.layout.activity_main3);
		}
		else if(choice.contains("one"))
		{
			setContentView(R.layout.activity_main7);   
		}
		else if(choice.contains("two"))
		{
			setContentView(R.layout.activity_main5);  
		}
		else if(choice.contains("three"))
		{
			setContentView(R.layout.activity_main6); 
		}

		else 
		{
			setContentView(R.layout.activity_main); 

		}
		

		initialize();
		
		
		
		//FACEBOOK
		InputStream is = getResources().openRawResource(R.drawable.ic_launcher);
		bmp = BitmapFactory.decodeStream(is);
		
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

		Session session = Session.getActiveSession();
		//Session.openActiveSession(this, true, statusCallback);
		if (session != null && session.getState().isOpened())
		{
			 Log.i("sessionToken", session.getAccessToken());
			  
		}
		
		
	
		
		
	}
	
	public void initialize(){
		   home = (Button) findViewById(R.id.bHome);
		   profile = (Button) findViewById(R.id.bProfile);
		   diary = (Button) findViewById(R.id.bDate);
		   options = (Button) findViewById(R.id.bOptions);
		   upload = (Button) findViewById(R.id.bUpload);
		   save = (Button) findViewById(R.id.bSave);
		   bPhoto = (Button) findViewById(R.id.bTakePhoto);
		   
		   takePhoto = (ImageView) findViewById(R.id.ivTakePicture);
		   
		   bPhoto.setOnClickListener(this);
		   home.setOnClickListener(this);
		   profile.setOnClickListener(this);
		   diary.setOnClickListener(this);
		   options.setOnClickListener(this);
		   upload.setOnClickListener(this);
		   save.setOnClickListener(this);
		   
		   theCounter = (TextView) findViewById(R.id.theCounter);
		   newCounter = (TextView) findViewById(R.id.newCounter);
		   blah = (TextView) findViewById(R.id.blah);
		   blahblah = (TextView) findViewById(R.id.blahblah);
		   
		   counter1 = (TextView) findViewById(R.id.counter1);

		   try 
			{
				mClient = new MobileServiceClient(
						      "https://picturehealthy.azure-mobile.net/",
						      "nbjKsgYFRaSSKFPZozOhziRGyiQbWO81",
						      this);
			} 
			catch (MalformedURLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		   
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		SharedPreferences saved = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String savedname = saved.getString("name", "");
		
		Item item = new Item();
		
		switch(v.getId()){
		case R.id.bHome:
			Intent a = new Intent(MainActivity.this, MainActivity.class);
			startActivity(a);
			break;
		case R.id.bProfile:
			Intent b = new Intent(MainActivity.this, Profile.class);
			startActivity(b);
			break;
		case R.id.bDate:
			Intent c = new Intent(MainActivity.this, DateChooser.class);
			startActivity(c);
			break;
		case R.id.bOptions:
			Intent d = new Intent(MainActivity.this, Options.class);
			startActivity(d);
			break;
		case R.id.bTakePhoto:
			i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, cameraData);
			break;
		case R.id.bUpload:

			 SharedPreferences scored = PreferenceManager.getDefaultSharedPreferences(this);
			 	int Score = scored.getInt("newScore", 0);
				
		
			
			Request request =  Request.newUploadPhotoRequest(Session.getActiveSession(), bmp, new Request.Callback(){
	        		public void onCompleted(Response response) {

	        			if (response.getError() != null) { 
	        				Log.e("FRAGACTIVITY", response.getError().toString());
	        			} 
	        		}
	        	});
		      
	        	Bundle parameters = request.getParameters(); // <-- THIS IS IMPORTANT
	        	parameters.putString("message", "This photo was uploaded uploaded from PictureHealthy! A university of lincoln project. I've earned " + Score + "points.");
	        	request.setParameters(parameters);
	        	request.executeAsync();
		
	         	
	        	item.Text = "Facebook Post";
	        	item.Person = savedname;
				mClient.getTable(Item.class).insert(item, new TableOperationCallback<Item>() {
				      public void onCompleted(Item entity, Exception exception, ServiceFilterResponse response) {
				            if (exception == null) {
				                  // Insert succeeded
				            } else {
				                  // Insert failed
				            }
				      }
				});
				
				AlertDialog.Builder alertDialog = new Builder(
	        			MainActivity.this);
	        	alertDialog.setCancelable(false);
	        	alertDialog.setTitle("Uploaded");
				alertDialog.setMessage("You photograph has been uploaded to facebook!");
	        	alertDialog.setPositiveButton("Ok",
	        	        new DialogInterface.OnClickListener() {
	        	         @Override
	        	         public void onClick(DialogInterface dialog,
	        	           int which) {
	        	          // TODO Auto-generated method stub

	        	         }
	        	        });
	        	alertDialog.show();
	        	
	        	onSaveSharePhoto();
	        	
			break;

		case R.id.bSave:

			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{
				String folderPath = Environment.getExternalStorageDirectory() + "/pictureHealthyImages/";
				   File file = new File(folderPath);
				      if(!file.exists())
				      {
				            if(file.mkdirs());
				                      Log.d("MyTag","Successfully created folders");
				      }
				
				String out = new SimpleDateFormat("yyyy-M-d hh-mm-ss'.tsv'").format(new Date());
				
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

				//you can create a new file name "test.jpg" in sdcard folder.
				File f = new File(Environment.getExternalStorageDirectory()
				                        + "/pictureHealthyImages/" + out+".jpg");
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//write the bytes in file
				FileOutputStream fo = null;
				try {
					fo = new FileOutputStream(f);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					fo.write(bytes.toByteArray());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// remember close de FileOutput
				try {
					fo.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else
			{

				
				String folderPath = getFilesDir() + "/pictureHealthyImages/";
				   File file = new File(folderPath);
				      if(!file.exists())
				      {
				            if(file.mkdirs());
				                      Log.d("MyTag","Successfully created folders");
				      }
				
				      
				      
				String out = new SimpleDateFormat("yyyy-M-d hh-mm-ss'.tsv'").format(new Date());
				
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

				//you can create a new file name "test.jpg" in sdcard folder.
				File f = new File(getFilesDir() + "/pictureHealthyImages/" + out+".jpg"); 
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//write the bytes in file
				FileOutputStream fo = null;
				try {
					fo = new FileOutputStream(f);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					fo.write(bytes.toByteArray());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// remember close de FileOutput
				try {
					fo.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		
			
			
			SharedPreferences someData = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			String id = someData.getString("edit2", "empty");
		
			item.Text = "Item Saved";
			item.Person = savedname;
					
			mClient.getTable(Item.class).insert(item, new TableOperationCallback<Item>() {
			      public void onCompleted(Item entity, Exception exception, ServiceFilterResponse response) {
			            if (exception == null) {
			                  // Insert succeeded
			            } else {
			                  // Insert failed
			            }
			      }
			});
			
			
			
			AlertDialog.Builder alertDialog2 = new Builder(
        			MainActivity.this);
        	alertDialog2.setCancelable(false);
        	alertDialog2.setTitle("Saved");
			alertDialog2.setMessage("You photograph has been saved to the phone!");
        	alertDialog2.setPositiveButton("Ok",
        	        new DialogInterface.OnClickListener() {
        	         @Override
        	         public void onClick(DialogInterface dialog,
        	           int which) {
        	          // TODO Auto-generated method stub

        	         }
        	        });
        	alertDialog2.show();
        	
        	onSaveSharePhoto();
			
			break;
		}
	}
	
	 protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        Session session = Session.getActiveSession();
	        Session.saveSession(session, outState);
	    }
	  private class SessionStatusCallback implements Session.StatusCallback {
	        @Override
	        public void call(Session session, SessionState state, Exception exception) {
	            
	        }
	    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			Bundle extras = data.getExtras();
			bmp = (Bitmap) extras.get("data");
			takePhoto.setImageBitmap(bmp);
		}
		 	
	}
	
	 private void SavePreferences(String key, String value){
    
		    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		    SharedPreferences.Editor editor = sharedPreferences.edit();
		    editor.putString(key, value);
		    editor.commit();
		   }
	
	 private void LoadPreferences(){
		 	SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		 	if(sharedPreferences.getString("new", "") != null);
		 	{
		 		String strSaved = sharedPreferences.getString("new", "");
		 		theCounter.setText(strSaved);
		 	}
	
		   }
	
	 private void SaveScorePreferences(String key, int value){
		 SharedPreferences scored = PreferenceManager.getDefaultSharedPreferences(this);
		    SharedPreferences.Editor editor = scored.edit();
		    editor.putInt(key, value);
		    editor.commit();
		   }
	
	 private void LoadScorePreferences(){
		 SharedPreferences scored = PreferenceManager.getDefaultSharedPreferences(this);
		 	int Score = scored.getInt("score", 0);
			blah.setText(String.valueOf(Score));;
			int newscore = scored.getInt("newScore", 0);
			blahblah.setText(String.valueOf(newscore));;
		   }
	 
	 
	/* private void CountPreferences(String key, String value){
		   
		 SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		 SharedPreferences.Editor editor = settings.edit();
		    editor.putString(key, value);
		    editor.commit();
	}*/
		
	 
	 
	 private void SaveCountPreferences(String key, int value){
		 	SharedPreferences counter = getPreferences(MODE_PRIVATE);
		    SharedPreferences.Editor editor = counter.edit();
		    editor.putInt(key, value);
		    editor.commit();
		   }
	 private void LoadCountPreferences(){
		 	SharedPreferences counter = getPreferences(MODE_PRIVATE);
		 	int Counter = counter.getInt("counter", 0);
			counter1.setText(String.valueOf(Counter));;
		   }
	 
	 
 
	public void onSaveSharePhoto() {

	
		LoadScorePreferences();
		LoadPreferences();
		
		Date cDate = new Date(); 
		String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);	
		newCounter.setText(fDate);
		
		LoadCountPreferences();
		
		Integer counter = Integer.valueOf(counter1.getText().toString());
		
		counter++;
		
		SaveCountPreferences("counter", counter);
		
		
		if( Integer.valueOf(counter1.getText().toString()) >= 2)
		{
			if(!theCounter.getText().toString().equals(newCounter.getText().toString()))
			{
			

				int x = Integer.valueOf(blah.getText().toString());
			 
				int c = x + 1;
				SavePreferences("new", fDate);
			
				SaveScorePreferences("score", c);
		    
				SaveCountPreferences("counter", 0);
				
				 
				int b = Integer.valueOf(blahblah.getText().toString());
				
				int e = b + 1;
				
				SaveScorePreferences("newScore", e);
				

			}
			else
			{
				SaveCountPreferences("counter", 0);
				
			}
			
		}


		}

}

	



