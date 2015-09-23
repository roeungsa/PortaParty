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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class ViewStall extends FragmentActivity {
	private static String feed; 
	private WebView contentView; 
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.view_stalls);
		
		Intent intent = this.getIntent(); 
		
		String id = intent.getStringExtra("id"); 
		Log.d("id", id); 
		feed = "http://cscilab.bc.edu/~roeungsa/mobileapps/PortaParty/viewMessage.php?location_id=" + id;
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		getAndDisplay();
	}


	private void getAndDisplay() {
		// TODO Auto-generated method stub
		contentView = (WebView) findViewById(R.id.contentView);
		
		(new Thread(){
			@Override
			public void run(){
				String results = getFeedResults(feed);
				final String parsed = parseJson(results);
				contentView.post(new Runnable(){
					public void run(){
						contentView.loadData(parsed, "text/html", null);
					}
				});
			}
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
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content), 65536);
				String line;
				while ((line = reader.readLine()) != null) {
					result += line + "\n";
				}
			} else {
				Log.e("json", "failed to download");
			}
		} catch (IOException e) {
			Log.i("network", e.toString());
		}
		
		return result;
	}
	
	private String parseJson(String jsonString) {
		String result = "";
		try {
			JSONObject jObject   = new JSONObject(jsonString);
			JSONArray messages    = jObject.getJSONArray("messages");
			for (int i=0; i< messages.length(); i++) {
				JSONObject m   = messages.getJSONObject(i);
				String comment = m.getString("comment"); 
				
				result +=  "<div id=\"message\"><b>comment:</b> " + comment + "<hr></br>\n</div>";
			}
			return "<html>" +
					"<head>" +
					"  <style>body " +
					"{background-color: #ACD1E9;}" +
					"{text-indent: 10px;}" +
					"#message{ " +
					" background-color: #6d929b;" +
					" color: #FFFFFF; " +
					"</style>" +
					"</head><body>" + result + "</body></html>";
		} catch (JSONException e) {
			return "Unable to parse JSON";
		}
	}
}
