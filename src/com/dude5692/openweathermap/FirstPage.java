package com.dude5692.openweathermap;

import org.json.JSONArray;
import org.json.JSONObject;

import checkNetConnection.CheckInternetConection;

import StaticData.DateWiseWeather;
import StaticData.ProjectURL;
import StaticData.jSOn;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class FirstPage extends Activity 
{
	Button current,city,currentCity;
	Location loc;
	ProjectURL proURL;
	String cityName; 

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first_page);

		findViews();
		onClickEvent();		
	}

	void findViews()
	{
		current = (Button) findViewById(R.id.currentLoc);
		city = (Button) findViewById(R.id.cityDetails);
		currentCity = (Button) findViewById(R.id.currentCity);
		proURL = new ProjectURL();
	}

	void onClickEvent()	
	{
		current.setOnClickListener(new View.OnClickListener() 
		{		
			@Override
			public void onClick(View v) 
			{
				int proceed = CheckInternetConection.isInternetConnection(FirstPage.this);
				if(proceed == 1)
				{
					Intent i = new Intent(FirstPage.this,CurrentWeather.class);
					startActivity(i);
					FirstPage.this.finish();
				}
				else
				{
					Toast.makeText(FirstPage.this, "No Internet Connection", Toast.LENGTH_LONG).show();
				}

			}
		});
		city.setOnClickListener(new View.OnClickListener() 
		{			
			@Override
			public void onClick(View v) 
			{
				int proceed = CheckInternetConection.isInternetConnection(FirstPage.this);
				if(proceed == 1)
				{
					Intent i = new Intent(FirstPage.this,MainActivity.class);
					startActivity(i);
					FirstPage.this.finish();
				}
				else
				{
					Toast.makeText(FirstPage.this, "No Internet Connection", Toast.LENGTH_LONG).show();
				}

			}
		});
		currentCity.setOnClickListener(new View.OnClickListener() 
		{		
			@Override
			public void onClick(View v) 
			{

				int proceed = CheckInternetConection.isInternetConnection(FirstPage.this);
				if(proceed == 1)
				{
					loc = getLocation();

					if(loc==null)
					{
						Toast.makeText(FirstPage.this, "Location Not Availaible", Toast.LENGTH_LONG).show();
					}
					else
					{
						new Async().execute();
					}		
				}
				else
				{
					Toast.makeText(FirstPage.this, "No Internet Connection", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	class Async extends AsyncTask<String, Void, String>
	{
		ProgressDialog progress;
		protected String doInBackground(String... params) 
		{
			try 
			{							
				String initialURL1 = proURL.URL1 + "lat="+loc.getLatitude()+"lon="+loc.getLongitude()+"&APPID="+proURL.APPID;

				Log.i("MO",initialURL1);

				String getURL = new jSOn().execute(initialURL1);
				Log.i("MO",getURL);
				System.out.println("response ="+getURL);
				JSONObject json_obj = new JSONObject(getURL);
				JSONObject json_obj_1 = json_obj.getJSONObject("main");

				DateWiseWeather stat = new DateWiseWeather();
				stat.minTemp = json_obj_1.getString("temp_min");
				stat.maxTemp = json_obj_1.getString("temp_max");
				stat.humidity = json_obj_1.getString("humidity");

				JSONArray json_arr = json_obj.getJSONArray("weather");
				JSONObject json_obj_2 = json_arr.getJSONObject(0);
				stat.cloudDescription = json_obj_2.getString("description");

				JSONObject json_obj_3 = json_obj.getJSONObject("wind");
				stat.windSpeed = json_obj_3.getString("speed");

				JSONObject json_obj_4 = json_obj.getJSONObject("clouds");
				stat.cityName = json_obj_4.getString("name");

				cityName = json_obj_4.getString("name");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}		
			return null;
		}

		protected void onPreExecute() 
		{
			super.onPreExecute();			
			progress = ProgressDialog.show(FirstPage.this, null, "Processing");			
		}

		protected void onPostExecute(String result) 
		{	
			super.onPostExecute(result);
			progress.dismiss();			

			Intent i = new Intent(FirstPage.this,WeatherData.class);
			Bundle b = new Bundle();
			b.putString("CityName", cityName);
			i.putExtras(b);
			startActivity(i);
			FirstPage.this.finish();
		}
	}


	public Location getLocation() 
	{
		Location location = null;
		try 
		{
			//turnGPSOn();
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			// getting GPS status
			boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			//final LocationListener mlocList = new MyLocationList();

			if (!isGPSEnabled && !isNetworkEnabled) 
			{
				// no network provider is enabled
			}
			else 
			{
				//this.canGetLocation = true;
				if (isNetworkEnabled) 
				{

					/*locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, mlocList);*/
					//Log.d("Network", "Network Enabled");

					if (locationManager != null) 
					{
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) 
						{
							//latitude = location.getLatitude();
							//longitude = location.getLongitude();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) 
				{
					if (location == null) 
					{
						/*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, mlocList);*/
						//Log.d("GPS", "GPS Enabled");
						if (locationManager != null) 
						{
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) 
							{
								//latitude = location.getLatitude();
								//longitude = location.getLongitude();
							}
						}
					}
				}
			}

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		//turnGPSOff();
		return location;
	}

}
