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

public class WeatherData extends Activity 
{
	String s;
	ProjectURL proURL;
	ArrayList<DateWiseWeather> al_data;
	ListView dayWise;
	ShowList adapter;	
	TextView cityWeather;
	int i=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_list);
		
		findViews();
		new Async().execute();
	}

	void findViews()
	{
		dayWise = (ListView) findViewById(R.id.dayWiseList1);
		s = getIntent().getStringExtra("CityName");
		proURL = new ProjectURL();
		al_data = new ArrayList<DateWiseWeather>();
		cityWeather = (TextView) findViewById(R.id.cityWeather);
	}

	@Override
	public void onBackPressed() 
	{
		Intent i = new Intent(WeatherData.this,FirstPage.class);
		startActivity(i);
		WeatherData.this.finish();

		super.onBackPressed();
	}

	int dataLength;

	class Async extends AsyncTask<String, Void, String>
	{
		ProgressDialog progress;
		protected String doInBackground(String... params) 
		{
			try 
			{							
				String initialURL1 = proURL.URL + "q="+s+"&APPID="+proURL.APPID;

				Log.i("MO",initialURL1);

				String getURL = new jSOn().execute(initialURL1);
				Log.i("MO",getURL);
				System.out.println("response ="+getURL);
				JSONObject json_obj = new JSONObject(getURL);
				JSONArray js_arr = json_obj.getJSONArray("list");												

				dataLength = js_arr.length();

				for (int i = 0; i < js_arr.length(); i++) 
				{	


					JSONObject json_obj_inner = js_arr.getJSONObject(i);
					DateWiseWeather stat = new DateWiseWeather();


					JSONArray js_arr_inner = json_obj_inner.getJSONArray("weather");					
					JSONObject json_obj_inner_2 = js_arr_inner.getJSONObject(0);					
					stat.cloudDescription = json_obj_inner_2.getString("description");					


					JSONObject js_arr_inner_3 = json_obj_inner.getJSONObject("main");					
					stat.minTemp = js_arr_inner_3.getString("temp_min");
					stat.maxTemp = js_arr_inner_3.getString("temp_max");
					stat.humidity = js_arr_inner_3.getString("humidity");


					JSONObject js_arr_inner_4 = json_obj_inner.getJSONObject("wind");
					stat.windSpeed = js_arr_inner_4.getString("speed");					


					JSONObject js_obj_inner_3 = json_obj_inner.getJSONObject("city");
					JSONArray js_arr_inner_5 = js_obj_inner_3.getJSONArray("find");
					stat.cityName = js_arr_inner_5.getString(0);					

					al_data.add(stat);
				}												
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
			progress = ProgressDialog.show(WeatherData.this, null, "Processing");			
		}

		protected void onPostExecute(String result) 
		{	
			super.onPostExecute(result);
			
			cityWeather.setText(al_data.get(0).cityName);
			
			System.out.println("here-4");
			progress.dismiss();			

			System.out.println("here-3");
			adapter = new ShowList(WeatherData.this);
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

			i++;
			
			return convertView;

		}
	}
}
