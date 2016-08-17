package com.shishoufuuin.deviantart;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;


public class Options extends Activity implements OnClickListener{
	private static final String URL_PREFIX_FRIENDS = "https://graph.facebook.com/me/friends?access_token=";
	
	private TextView textInstructionsOrLink;
	Button home, profile, diary, options, login, loginTwitter, logoutTwitter;
	ImageView pic;
	TextView welcome, counter;
	ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6;
	
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String choice = settings.getString("choice", "");
		
		
		if(choice.contains("White"))
		{
			setContentView(R.layout.changesettings); 
		}
		else if(choice.contains("Black"))
		{
			setContentView(R.layout.changesettings2); 
		}
		else if(choice.contains("Blue"))
		{
			setContentView(R.layout.changesettings3); 
		}
		else if(choice.contains("one"))
		{
			setContentView(R.layout.changesettings7);   
		}
		else if(choice.contains("two"))
		{
			setContentView(R.layout.changesettings5);  
		}
		else if(choice.contains("three"))
		{
			setContentView(R.layout.changesettings6); 
		}

		else 
		{
			setContentView(R.layout.changesettings); 

		}

	      Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
  
	        textInstructionsOrLink = (TextView) findViewById(R.id.textInstructionsOrLink);
	        counter = (TextView) findViewById(R.id.counter);
			home = (Button) findViewById(R.id.bHome);
			profile = (Button) findViewById(R.id.bProfile);
			diary = (Button) findViewById(R.id.bDate);
			options = (Button) findViewById(R.id.bOptions);
			login = (Button) findViewById(R.id.login);

			
			imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
			imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
			imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
			imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
			imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
			imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
		
			
			
			
			
			imageButton1.setOnClickListener(this);
			imageButton2.setOnClickListener(this);
			imageButton3.setOnClickListener(this);
			imageButton4.setOnClickListener(this);
			imageButton5.setOnClickListener(this);
			imageButton6.setOnClickListener(this);
	

			login.setOnClickListener(this);
			home.setOnClickListener(this);
			profile.setOnClickListener(this);
			diary.setOnClickListener(this);
			options.setOnClickListener(this);

	        Session session = Session.getActiveSession();
	        if (session == null) 
	        {
	            if (savedInstanceState != null) 
	            {
	                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
	            }
	            else if (savedInstanceState == null) 
		        {
	            session = new Session(this);
		        }
	            Session.setActiveSession(session);
	            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) 
	            {
	                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
	            }
	        }

	        updateView();

	        SharedPreferences scored = PreferenceManager.getDefaultSharedPreferences(this);
		 	int Score = scored.getInt("score", 0);
		 	counter.setText(String.valueOf(Score));
		 	
		 
	}
	

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		 SharedPreferences scored = PreferenceManager.getDefaultSharedPreferences(this);
		 	final int Score = scored.getInt("score", 0);

		switch(v.getId()){
		case R.id.login:
			Session session = Session.getActiveSession();
			if (!session.isOpened() && !session.isClosed()) 
		    {
		    	session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
		    } 
		    else 
		    {
		        Session.openActiveSession(this, true, statusCallback);
		    }
		
			break;
		case R.id.bHome:
			Intent a = new Intent(Options.this, MainActivity.class);
			startActivity(a);
			break;
		case R.id.bProfile:
			Intent b = new Intent(Options.this, Profile.class);
			startActivity(b);
			break;
		case R.id.bDate:
			Intent c = new Intent(Options.this, DateChooser.class);
			startActivity(c);
			break;
		case R.id.bOptions:
			Intent d = new Intent(Options.this, Options.class);
			startActivity(d);
			break;
		case R.id.imageButton1:
			new AlertDialog.Builder(this)
		    .setTitle("Background Change - Grey")
		    .setMessage("This is the default background. This costs 0 points to apply but you will lose your current background, do you still want to use it?")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
		        	
					String White = "";
					SavePreferences("choice", White);
					
					Intent d = new Intent(Options.this, Options.class);
					startActivity(d);
		        }
		     })
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	
		        }
		     })
		     .show();
			
			break;
		case R.id.imageButton2:
			if(Score < 1)
			{
				AlertDialog.Builder alertDialog = new Builder(
	        			Options.this);
	        	alertDialog.setCancelable(false);
	        	alertDialog.setTitle("Sorry");
				alertDialog.setMessage("Sorry this background costs 1 point, you do not have enough points.");
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
			else{
			new AlertDialog.Builder(this)
		    .setTitle("Background Change - Black")
		    .setMessage("Are you sure you want to use this background, it will cost you 1 point?")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
		        	int x = Score - 1;
					SaveScorePreferences("score", x);

					String Black = "Black";
					SavePreferences("choice", Black);
					
					Intent d = new Intent(Options.this, Options.class);
					startActivity(d);
		        }
		     })
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		           
		        }
		     })		     
		     .show();	
			}
			 break;
		case R.id.imageButton3:
			if(Score < 1)
			{
				AlertDialog.Builder alertDialog = new Builder(
	        			Options.this);
	        	alertDialog.setCancelable(false);
	        	alertDialog.setTitle("Sorry");
				alertDialog.setMessage("Sorry this background costs 1 point, you do not have enough points.");
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
			else{
			new AlertDialog.Builder(this)
		    .setTitle("Background Change - Blue")
		    .setMessage("Are you sure you want to use this background, it will cost you 1 point?")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
		        	int x = Score - 1;
					SaveScorePreferences("score", x);

					String Blue = "Blue";
					SavePreferences("choice", Blue);
					
					Intent d = new Intent(Options.this, Options.class);
					startActivity(d);
		        }
		     })
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            
		        }
		     })
		     .show();
			}
			break;
		case R.id.imageButton4:
			if(Score < 2)
			{
				AlertDialog.Builder alertDialog = new Builder(
	        			Options.this);
	        	alertDialog.setCancelable(false);
	        	alertDialog.setTitle("Sorry");
				alertDialog.setMessage("Sorry this background costs 2 points, you do not have enough points.");
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
			else{
			new AlertDialog.Builder(this)
		    .setTitle("Background Change - Citrus Fruit, Green/Yellow/Orange")
		    .setMessage("Are you sure you want to use this background, it will cost you 2 point?")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
		        	int x = Score - 2;
					SaveScorePreferences("score", x);

					String two = "two";
					SavePreferences("choice", two);
					
					Intent d = new Intent(Options.this, Options.class);
					startActivity(d);
		        }
		     })
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		          
		        }
		     })
		 .show();
			}
			break;
		case R.id.imageButton5:
			if(Score < 2)
			{
				AlertDialog.Builder alertDialog = new Builder(
	        			Options.this);
	        	alertDialog.setCancelable(false);
	        	alertDialog.setTitle("Sorry");
				alertDialog.setMessage("Sorry this background costs 2 points, you do not have enough points.");
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
			else{
			new AlertDialog.Builder(this)
		    .setTitle("Background Change - Very Red")
		    .setMessage("Are you sure you want to use this background, it will cost you 2 point?")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
		        	int x = Score - 2;
					SaveScorePreferences("score", x);

					String three = "three";
					SavePreferences("choice", three);
					
					Intent d = new Intent(Options.this, Options.class);
					startActivity(d);
		        }
		     })
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		         
		        }
		     })
			
		 .show();
			}
			
			break;
			
		case R.id.imageButton6:
			if(Score < 2)
			{
				AlertDialog.Builder alertDialog = new Builder(
	        			Options.this);
	        	alertDialog.setCancelable(false);
	        	alertDialog.setTitle("Sorry");
				alertDialog.setMessage("Sorry this background costs 2 points, you do not have enough points.");
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
			else{
			new AlertDialog.Builder(this)
		    .setTitle("Background Change - Plain Grey")
		    .setMessage("Are you sure you want to use this background, it will cost you 2 points?")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
		        	int x = Score - 2;
					SaveScorePreferences("score", x);

					String one = "one";
					SavePreferences("choice", one);
					
					Intent d = new Intent(Options.this, Options.class);
					startActivity(d);
		        }
		     })
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            
		        }
		     })
		     .show();
			}
			
			break;	
		}
	
	}

		
	private void updateView() {
        Session session = Session.getActiveSession();
        if (session.isOpened()) {
            textInstructionsOrLink.setText(URL_PREFIX_FRIENDS + session.getAccessToken());
            login.setText("logout");
            login.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickLogout(); }
            });
        } else {
            textInstructionsOrLink.setText("instructions");
            login.setText("login");
            login.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickLogin(); }
            });
        }
    }
	
	
    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        } else {
            Session.openActiveSession(this, true, statusCallback);
        }
    }

    private void onClickLogout() {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
    }
	
	
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
              updateView();
        }
    }
	  
	    @Override
	    public void onStart() {
	        super.onStart();
	        Session.getActiveSession().addCallback(statusCallback);
	    }

	    @Override
	    public void onStop() {
	        super.onStop();
	        Session.getActiveSession().removeCallback(statusCallback);
	    }

	    @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	    }

	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        Session session = Session.getActiveSession();
	        Session.saveSession(session, outState);
	    }
	    
    

		 private void SavePreferences(String key, String value){
	   
			 SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			 SharedPreferences.Editor editor = settings.edit();
			    editor.putString(key, value);
			    editor.commit();
			   }
		 
		 private void SaveScorePreferences(String key, int value){
			 SharedPreferences scored = PreferenceManager.getDefaultSharedPreferences(this);
			    SharedPreferences.Editor editor = scored.edit();
			    editor.putInt(key, value);
			    editor.commit();
		   }
	

	
}





