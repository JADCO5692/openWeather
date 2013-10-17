package checkNetConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternetConection 
{

	public static int isInternetConnection(Context mContext) 
	{
		
		/*Log.i("MO","Here1");
		
		final ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		Log.i("MO","Here2");
		
		final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		Log.i("MO","Here3");
		
		final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		Log.i("MO","Here4");
		
		if (wifi.isAvailable() && wifi.getState() == NetworkInfo.State.CONNECTED) 
		{
			Log.i("MO","Here5");
			URL url = new URL("http://www.amct2013.com/PhpProject13/index.php");
		    HttpURLConnection httpconn = (HttpURLConnection)url.openConnection();
		    httpconn.setConnectTimeout(5000);
		    if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
		    {
		    	return true;
		    }
		    else 
		    {
		    	return false;
			}
		}
		else if (mobile.isAvailable() && mobile.getState() == NetworkInfo.State.CONNECTED) 
		{
		    URL url = new URL("http://www.amct2013.com/PhpProject13/index.php");
		    HttpURLConnection httpconn = (HttpURLConnection)url.openConnection();
		    httpconn.setConnectTimeout(5000);
		    if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
		    {
		    	return true;
		    }
		    else 
		    {
		    	return false;
			}

		}
		else 
		{
			return false;
		}
		
		ConnectivityManager connManger = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connManger.getActiveNetworkInfo();
		if (netInfo == null) 
		{
	        return false;
		}
		else
		{
			URL url = new URL("http://www.google.com");
		    HttpURLConnection httpconn = (HttpURLConnection)url.openConnection();
		    httpconn.setConnectTimeout(5000);
		    if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
		    {
		    	return true;
		    }
		    else 
		    {
		    	return false;
			}
		}*/
		ConnectivityManager connManger = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connManger.getActiveNetworkInfo();
		if (netInfo == null) 
		{
			/*Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
	        finish();*/
			return 0;
		}
		else
		{
			return 1;
		}
	}
}
