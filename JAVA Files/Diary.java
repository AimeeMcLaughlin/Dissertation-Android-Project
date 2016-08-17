package com.shishoufuuin.deviantart;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FilenameFilter;


public class Diary extends Activity implements View.OnClickListener{
	
	   ImageAdapter myImageAdapter;
	   Button home, profile, diary, options;
	   TextView  TextDate, textdate2;
	   GridView gridview;

@Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       

       
       SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String choice = settings.getString("choice", "");
		
		
		if(choice.contains("White"))
		{
			setContentView(R.layout.picturelist);  
		}
		else if(choice.contains("Black"))
		{
			setContentView(R.layout.picturelist2); 
		}
		else if(choice.contains("Blue"))
		{
			setContentView(R.layout.picturelist3); 
		}
		else if(choice.contains("one"))
		{
			setContentView(R.layout.picturelist7);   
		}
		else if(choice.contains("two"))
		{
			setContentView(R.layout.picturelist5);  
		}
		else if(choice.contains("three"))
		{
			setContentView(R.layout.picturelist6); 
		}

		else 
		{
			setContentView(R.layout.picturelist);

		}
       
       TextDate = (TextView) findViewById(R.id.TextDate);
       textdate2 = (TextView) findViewById(R.id.textdate2);

       
       Bundle extras = getIntent().getExtras(); 
       if(extras !=null)
       {

           int year = extras.getInt("year");
           int month = extras.getInt("month");
           int dayOfMonth = extras.getInt("dayOfMonth");


           TextDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);

       }
      
       String theDate =  TextDate.getText().toString();
       
       textdate2.setText(theDate);
       
     
        home = (Button) findViewById(R.id.bHome);
	    profile = (Button) findViewById(R.id.bProfile);
	    diary = (Button) findViewById(R.id.bDate);
	    options = (Button) findViewById(R.id.bOptions);
		gridview  = (GridView) findViewById(R.id.gridView);
	   
		home.setOnClickListener(this);
		profile.setOnClickListener(this);
		diary.setOnClickListener(this);
		options.setOnClickListener(this); 
       
       myImageAdapter = new ImageAdapter(this);
       
       
       if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
       {
       
    	   String ExternalStorageDirectoryPath = Environment
    			   .getExternalStorageDirectory().getAbsolutePath();
       
    	   String targetPath = ExternalStorageDirectoryPath + "/pictureHealthyImages/";
       
    	   Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
    	   File targetDirector = new File(targetPath);
       
       
    	   File[] files = targetDirector.listFiles(new FilenameFilter() {
    		   public boolean accept(File dir, String name) {
    			   String theDate =  TextDate.getText().toString();
    			   return name.startsWith(theDate);
    		   }
    	   });
    	        
    	   for (File file : files){
    		   myImageAdapter.add(file.getAbsolutePath());
       		} 

    	   gridview.setAdapter(myImageAdapter);	   
    	
       }
   	
   		else
   		{
   			String folderPath = getFilesDir() + "/pictureHealthyImages/";
   			Toast.makeText(getApplicationContext(), folderPath, Toast.LENGTH_LONG).show();
   			File targetDirector = new File(folderPath);
     
     
   			File[] files = targetDirector.listFiles(new FilenameFilter() {
   				public boolean accept(File dir, String name) {
   					String theDate =  TextDate.getText().toString();
   					return name.startsWith(theDate);
   				}
   			});
  	        
   			for (File file : files){
   				myImageAdapter.add(file.getAbsolutePath());
     		} 

   			gridview.setAdapter(myImageAdapter);	   
  	
     
   		}
	}
  


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bHome:
			Intent a = new Intent(Diary.this, MainActivity.class);
			startActivity(a);
			break;
		case R.id.bProfile:
			Intent b = new Intent(Diary.this, Profile.class);
			startActivity(b);
			break;
		case R.id.bDate:
			Intent c = new Intent(Diary.this, DateChooser.class);
			startActivity(c);
			break;
		case R.id.bOptions:
			Intent d = new Intent(Diary.this, Options.class);
			startActivity(d);
			break;
		}
	}

	

public class ImageAdapter extends BaseAdapter {
    
		private Context mContext;
		ArrayList<String> itemList = new ArrayList<String>();
    
		public ImageAdapter(Context c) {
			mContext = c; 
		}
    
		void add(String path){
			itemList.add(path); 
		}

		@Override
		public int getCount() {
			return itemList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(20, 20, 20, 20);
			} else {
				imageView = (ImageView) convertView;
			}

			Bitmap bm = decodeSampledBitmapFromUri(itemList.get(position), 150, 150);

			imageView.setImageBitmap(bm);
			return imageView;
		}
 
		public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
  
			Bitmap bm = null;
			// First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);
      
			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
      
			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			bm = BitmapFactory.decodeFile(path, options); 
      
			return bm;   
		}
 
	public int calculateInSampleSize(
   
		BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
  
		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float)height / (float)reqHeight);    
			} else {
				inSampleSize = Math.round((float)width / (float)reqWidth);    
			}   
		}	
  
		return inSampleSize;    
	}

}
	

}

	
	


	

	
	
	



	
	

