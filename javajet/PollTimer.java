package javajet;

import java.io.*;
import java.net.URL;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;

class PollTimer extends Timer 
{
	private String postParams;
	URL endpoint;
	Timer timer;
	PollTimer( URL url, String postargs, int minutes )
	{
		endpoint = url;
		postParams = postargs;
		
		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask()
		{
			public void run()
			{
				try 
				{
					connect();
					System.out.println("Connected");
				} 
				catch (Exception e) 
				{
					System.out.println(e.getMessage());
				}
			}
		}, 0, 1000*60*minutes);

	}
	
	public void connect() throws Exception
	{

		HttpsURLConnection urlc = null;
		urlc = (HttpsURLConnection) endpoint.openConnection();
		urlc.setRequestMethod("POST");
		
		urlc.setDoOutput(true);
		urlc.setDoInput(true);
		urlc.setUseCaches(false);
		urlc.setAllowUserInteraction(false);
		urlc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		urlc.setRequestProperty("Content-Length", String.valueOf(postParams.length()));
		//needed to pretend we are a browser
		urlc.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.13) Gecko/2010206 Red Hat/3.6.13-2.el6_0 Firefox/3.6.13");

		try
		{
			OutputStream out = urlc.getOutputStream();
		    try
			{
		        out.write(postParams.getBytes()); 
			} 
			catch (IOException e)
			{
				throw new Exception("IOException while posting data", e);
			} 
			finally
			{
				if (out != null)
					out.close();
			}
						
			InputStream in = urlc.getInputStream();
			try
			{
				BufferedWriter rw = new BufferedWriter( new FileWriter("output.txt"));
				Reader reader = new InputStreamReader(in);
				int c = reader.read();
				while(c != -1)
				{
					rw.write((char)c);
					c = reader.read();
				}
				rw.close();
				reader.close();
						
			} 
			catch (IOException e)
			{
				throw new Exception("IOException while reading response", e);
			} 
			finally
			{
				if (in != null)
					in.close();
			}
		
		} 
		catch (IOException e)
		{
			throw new Exception("Connection error (is server running at " + endpoint + " ?): " + e);
		} 
		finally
		{
			if (urlc != null)
				urlc.disconnect();
		}	
	}

}
