package roeungsa.bc.cs.finalprojectpp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class EventInfo {

	private String id; 
	private String title; 
	private String address; 
	private String latitude; 
	private String longitude; 
	private String description; 
	
	public EventInfo(String id, String title, String latitude, String longitude, String address, String description){
			this.id = id;
			this.title = title; 
			this.latitude = latitude; 
			this.longitude = longitude; 
			this.address = address; 
			this.description = description; 
	}
	
	public String getId(){
		return id; 
	}
	
	public String getTitle(){
		return title; 
	}
	
	public String getAddress(){
		return address; 
	}
	
	public String getLatitude(){
		return latitude;
	}
	
	public String getLongitude(){
		return longitude; 
	}
	
	public String getDescription(){
		return description; 
	}

}
