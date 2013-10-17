package com.dude5692.openweathermap;

import checkNetConnection.CheckInternetConection;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity 
{
	EditText enteredText;
	Button submitText;
	String city;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		findViews();
		onClickEvent();
	}

	void onClickEvent()
	{
		submitText.setOnClickListener(new View.OnClickListener() 
		{			
			@Override
			public void onClick(View v) 
			{
				city = enteredText.getText().toString().trim();
				if(city.equals(""))
				{
					Toast.makeText(MainActivity.this, "Enter Text", Toast.LENGTH_LONG).show();	
				}
				else
				{
					int proceed = CheckInternetConection.isInternetConnection(MainActivity.this);
					if(proceed == 1)
					{
						Intent i = new Intent(MainActivity.this,WeatherData.class);
						Bundle b = new Bundle();
						b.putString("CityName", city);
						i.putExtras(b);
						startActivity(i);
						MainActivity.this.finish();
					}
					else
					{
						Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
					}
					
				}			
			}
		});
	}
	void findViews()
	{
		enteredText = (EditText) findViewById(R.id.cityName1);
		submitText = (Button) findViewById(R.id.submitName);
	}
}
