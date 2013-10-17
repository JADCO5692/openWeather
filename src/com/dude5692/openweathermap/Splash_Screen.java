package com.dude5692.openweathermap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class Splash_Screen extends Activity
{
	private int delayMillis = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_screen);
		
		new Handler().postDelayed(new Runnable() 
		{					
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub

				Intent i;
				i = new Intent(Splash_Screen.this,FirstPage.class)	;						

				startActivity(i);
				Splash_Screen.this.finish();
			}
		}, delayMillis);


	} //oncreate -----------------------------------------------
}
