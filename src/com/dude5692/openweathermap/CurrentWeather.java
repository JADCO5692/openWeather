package com.dude5692.openweathermap;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


import StaticData.DateWiseWeather;
import StaticData.ProjectURL;
import StaticData.jSOn;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CurrentWeather extends Activity 
{
	String s;
	ProjectURL proURL;
	ArrayList<DateWiseWeather> al_data;
	ListView dayWise;
	ShowList adapter;	
	TextView cityWeather;
	int i=1;
	Location loc;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_list);
		
		findViews();
		
		loc = getLocation();

		if(loc==null)
		{
			Toast.makeText(CurrentWeather.this, "Location Not Availaible", Toast.LENGTH_LONG).show();
		}
		else
		{
			new Async().execute();
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
	
	void findViews()
	{
		dayWise = (ListView) findViewById(R.id.dayWiseList1);
		proURL = new ProjectURL();
		al_data = new ArrayList<DateWiseWeather>();
		cityWeather = (TextView) findViewById(R.id.cityWeather);
	}

	int dataLength;

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
				
				al_data.add(stat);
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
			al_data = new ArrayList<DateWiseWeather>();
			progress = ProgressDialog.show(CurrentWeather.this, null, "Processing");			
		}

		protected void onPostExecute(String result) 
		{	
			super.onPostExecute(result);
			
			cityWeather.setText(al_data.get(0).cityName);
			
			System.out.println("here-4");
			progress.dismiss();			

			System.out.println("here-3");
			adapter = new ShowList(CurrentWeather.this);
			System.out.println("here-2");
			dayWise.setAdapter(adapter);
			System.out.println("here-1");
		}
	}

	class ShowList extends BaseAdapter
	{
		Context con;
		LayoutInflater inflater;		

		public ShowList(Context con) 
		{
			this.con = con;
			inflater = (LayoutInflater) con.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		}
		public int getCount() 
		{
			return dataLength;
		}
		public Object getItem(int position) 
		{
			return null;
		}
		public long getItemId(int position) 
		{
			return 0;
		}
		public View getView(final int position, View convertView, ViewGroup parent) 
		{
			System.out.println("here");

			convertView = inflater.inflate(R.layout.list_weather, null);
			TextView cloudDes,maxTemp,minTemp,humidity,windSpeed,dayNo;

			dayNo = (TextView) convertView.findViewById(R.id.dayCount);			
			maxTemp = (TextView) convertView.findViewById(R.id.maxTemp);
			minTemp = (TextView) convertView.findViewById(R.id.minTemp);
			humidity = (TextView) convertView.findViewById(R.id.humidity);
			windSpeed = (TextView) convertView.findViewById(R.id.windSpeed);
			cloudDes = (TextView) convertView.findViewById(R.id.cloudDescription);
			System.out.println("here1");

			dayNo.setText("Day "+(position+1));
			System.out.println("here2");
			maxTemp.setText(al_data.get(position).maxTemp);
			System.out.println("here3");
			minTemp.setText(al_data.get(position).minTemp);
			System.out.println("here4");
			cloudDes.setText(al_data.get(position).cloudDescription);
			System.out.println("here5");
			humidity.setText(al_data.get(position).humidity);
			System.out.println("here6");
			windSpeed.setText(al_data.get(position).windSpeed);
			System.out.println("here7");

			
			return convertView;

		}
	}
	
	
	
	@Override
	public void onBackPressed() 
	{
		Intent i = new Intent(CurrentWeather.this,FirstPage.class);
		startActivity(i);
		CurrentWeather.this.finish();
		super.onBackPressed();
	}
}
