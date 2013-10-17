package StaticData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class jSOn 
{
StringBuilder response= new StringBuilder();
	public String execute(String URL)
	{
		try
		{
			java.net.URL url = new java.net.URL(URL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();//new java.net.URL(URL).openConnection();
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStream is = conn.getInputStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(is));
				String strLine = "";
				while ((strLine = read.readLine()) != null)
				{
					response.append(strLine);
				}
				is.close();				
			}
		}
		catch(Exception e)
		{
			
		}
		return response.toString();	
	}	
}
