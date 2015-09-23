package roeungsa.bc.cs.finalprojectpp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DrawableStall extends Activity{
	private String feed; 
	private Button button; 
	private String message; 
	private EditText messageView;
	private Intent intent; 
	private String id; 
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.drawing_activity);
		
		messageView = (EditText) findViewById(R.id.comment); 
		button = (Button) findViewById(R.id.post); 
		
		intent = getIntent(); 
		id = intent.getStringExtra("id"); 
		
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				message = messageView.getText().toString();
				Toast.makeText(getApplicationContext(), "Successful!", 10).show(); 
				Log.d("message", message); 
				
				try {
					feed = "http://cscilab.bc.edu/~roeungsa/mobileapps/PortaParty/postMessage.php?message="+
					URLEncoder.encode(message, "ISO-8859-1") + "&" + "location_id=" + id;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//feed.replace(" ", "%");
				getAndDisplay();
			}
		});
		
	}


	private void getAndDisplay() {
		final Handler handler = new Handler(Looper.getMainLooper());
		(new Thread(){
			@Override
			public void run(){
				getFeedResults(feed); 
				
				
				handler.post(new Runnable() {
					@Override
					public void run() {
						
						
					}
				});}
				
		}).start();
		
		
		
	}
	
	private String getFeedResults(String feed) {
		HttpClient client = new DefaultHttpClient(); 
		HttpGet httpget = new HttpGet(feed); 
		
		String result = ""; 
		try {
			HttpResponse response = client.execute(httpget);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				Log.i("success", "success"); 
			} else {
				Log.e("json", "failed to download");
			}
		} catch (IOException e) {
			Log.i("network", e.toString());
		}
		
		return result;
	}
	
	
	
	
	
}
