package codenet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

public class HttpUtils {
	
	public static String post(String urlstr, HashMap<String, String> map) throws IOException{
		StringBuilder arg =  new StringBuilder();
		for(Map.Entry<String, String> en : map.entrySet()){
			arg.append(en.getKey()+"="+en.getValue()).append('&');
		}
		arg.deleteCharAt(arg.length()-1);
		URL url = new URL(urlstr);
		HttpURLConnection connection= (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");

		out.write(arg.toString());       
		out.flush();
		out.close();

		String strLine="";
		String strResponse ="";
		InputStream in =connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while((strLine =reader.readLine()) != null)       {
			strResponse +=strLine +"\n";
		}

		in.close();out.close();
		return strResponse;
	}
	
	public static String get(String urlstr, HashMap<String, String> map) throws IOException{
		StringBuilder arg =  new StringBuilder(); arg.append('?');
		for(Map.Entry<String, String> en : map.entrySet()){
			arg.append(en.getKey()+"="+en.getValue()).append('&');
		}
		arg.deleteCharAt(arg.length()-1);
		URL url = new URL(urlstr+arg.toString());
		HttpURLConnection connection= (HttpURLConnection) url.openConnection();

		connection.connect();
		String strLine="";
		String strResponse ="";
		InputStream in =connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while((strLine =reader.readLine()) != null)       {
			strResponse +=strLine +"\n";
		}
		in.close();
		return strResponse;
	}

}
