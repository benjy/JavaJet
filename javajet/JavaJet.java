package javajet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class JavaJet 
{
	private URL JET_LOGIN_URL;
	private CookieManager cookieManager;
	
	public JetLogin() throws MalformedURLException
	{

        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);	
        CookieHandler.setDefault(cookieManager); 
        HttpURLConnection.setFollowRedirects(false);
        JET_LOGIN_URL  = new URL("https://jet.curtin.edu.au/curtin/portal/login");

	}
	
	void login( String username, String password, int polltime ) throws Exception
	{
		int poll = polltime;
		String cookieContent = "";
        String targetUrl= "http://www.google.com";
        String charset = "UTF-8";
        String params = "username="+URLEncoder.encode(username, charset)+"&password="+URLEncoder.encode(password, charset)+"&targeturl="+
        URLEncoder.encode(targetUrl, charset)+"&submit="+URLEncoder.encode("Logon", charset);
        
        new PollTimer(JET_LOGIN_URL, params, poll);
	}
	
	void viewRequestProperties(HttpsURLConnection urlc)
	{
		System.out.println( "Request Properties" );
		Map m = urlc.getRequestProperties();
		for(int j = 1; j < m.size()-1; j++)
		{
			Set keys = m.keySet();
			Iterator i= keys.iterator();
			while(i.hasNext())
			{
				Object key = i.next();
				System.out.println(key.toString()+ ": " + m.get(key));
			}
		} 		
	}
	
	void viewCookies() throws IOException, URISyntaxException
	{
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Map<String, List<String>> map2;
		map2 = cookieManager.get(JET_LOGIN_URL.toURI(), map);
		
		System.out.println("Printing Cookies from map");
		Set<String> keys = map2.keySet();
		Iterator it = keys.iterator();
		while(it.hasNext())
		{
			List<String> l = map2.get(it.next());
			for(int i = 0; i<l.size(); i++)
			{
				System.out.println("Cookie");
				l.get(i);
			}
		}
		
		System.out.println("Printing Cookies");
		for(HttpCookie cookie : cookieManager.getCookieStore().getCookies())
		{
			System.out.println( cookie.getName() + ": " + cookie.getValue() );
			cookie.setMaxAge(0);
			System.out.println("Expring cookie: "+ cookie.getName());
		}
		
	}
	
	void clearCookies()
	{
		cookieManager.getCookieStore().removeAll();
	}
	
}
