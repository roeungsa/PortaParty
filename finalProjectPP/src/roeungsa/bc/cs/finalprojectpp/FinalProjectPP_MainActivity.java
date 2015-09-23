package roeungsa.bc.cs.finalprojectpp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.DebugUtils;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class FinalProjectPP_MainActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {
	private GoogleMap map;

	private static final LatLng BC_IGGY = new LatLng(42.3376521, -71.1697487);
	private static final LatLng FULTON = new LatLng(42.334483, -71.170084);
	private static final LatLng BAPST = new LatLng(42.3349535, -71.1696023);
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private LatLng curPosition;
	private DecimalFormat format = new DecimalFormat("####.##");
	private OnMyLocationChangeListener listener;
	private LocationClient myLocationClient; 
	private Location myCurrentLocation;
	private HashMap<Marker, EventInfo> eventMarkerMap = new HashMap<Marker, EventInfo>(); 
	
	private static final String feed="http://cscilab.bc.edu/~roeungsa/mobileapps/PortaParty/createBathrooms.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_final_project_pp__main);
		
		SupportMapFragment manager = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		// grabs the GOOGLE map fragment
		map = manager.getMap();

		map.setMyLocationEnabled(true);
		
		
		//click on a marker and bathroom info activity pops up 
		map.setOnInfoWindowClickListener( new OnInfoWindowClickListener(){

			@Override
			public void onInfoWindowClick(Marker marker) {
			
				
				Intent intent = new Intent(getApplicationContext(), BathroomFragment.class);
				
				EventInfo eventInfo = eventMarkerMap.get(marker);

				intent.putExtra("name", eventInfo.getTitle()); 
				intent.putExtra("address", eventInfo.getAddress()); 
				
				//Intent intent2 = new Intent(getApplicationContext(), ViewStall.class);
				intent.putExtra("id", eventInfo.getId());
				
				startActivity(intent);
				
			}
			
		}); 
		 /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
        //myLocationClient = new LocationClient(this, this, this);
        
		
		setUpGPSWatcher();

	}
	
	/*
     * Called when the Activity becomes visible.
     */
//    @Override
//    protected void onStart() {
//        super.onStart();
//        // Connect the client.
//        myLocationClient.connect();
//        Log.d("onstart", "CALLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLED"); 
//    }
//    
//    /*
//     * Called when the Activity is no longer visible.
//     */
//    @Override
//    protected void onStop() {
//        // Disconnecting the client invalidates it.
//        myLocationClient.disconnect();
//        super.onStop();
//    }
   

	/** set the map to current location and zoom to current location by default **/
	public void setUpGPSWatcher() {
		// enables GPS

		// listener = new OnMyLocationChangeListener() {
		//
		//
		//
		// @Override
		// public void onMyLocationChange(Location location) {
		//
		//
		
//		myCurrentLocation = myLocationClient.getLastLocation();
//		if (myCurrentLocation != null) {
			SupportMapFragment mapView = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
//			
//			LatLng loc = new LatLng(myCurrentLocation.getLatitude(), myCurrentLocation.getLongitude()); 
			// Zoom parameter is set to 15
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
					BC_IGGY, 15);

			// Use map.animateCamera(update) if you want moving effect
			map.moveCamera(update);
			mapView.onResume();

			
//			String fultonMiles = format
//					.format(findDistance(BC_IGGY, FULTON));
//			
//			String bapstMiles = format
//					.format(findDistance(BC_IGGY, BAPST));
//
//			showMarkerLocation("Ignacio Hall", BC_IGGY, "0");
//			showMarkerLocation("Fulton", FULTON, fultonMiles);
//			showMarkerLocation("Bapst", BAPST, bapstMiles);
			
//		} else
//			Log.e("location null", "location null");
	}

	// };

	// map.setOnMyLocationChangeListener(listener);

	/** uses haversin formula to find distance between 2 locations **/
	public double findDistance(LatLng loc1, LatLng loc2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(loc2.latitude - loc1.latitude); // currentLocation
																		// returns
																		// null

		double dLng = Math.toRadians(loc2.longitude - loc2.longitude);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
				* Math.cos(Math.toRadians(loc1.latitude))
				* Math.cos(Math.toRadians(loc2.latitude));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		return dist;
	}

	public Marker showMarkerLocation(EventInfo info) {
		
		
		LatLng loc = new LatLng(Double.valueOf(info.getLatitude()),
				                Double.valueOf(info.getLongitude()));
		
		String distance = format.format(findDistance(loc, BC_IGGY));
		
		Marker marker = map.addMarker(new MarkerOptions()
				.position(loc)
				.title(info.getTitle())
				.snippet(distance + " miles"));
		
		return marker; 
				
	}

	/* uses reverse geocoding to find address */
	public String getAddress(LatLng loc) {
		
		// Get address from location
		Geocoder geoCoder = new Geocoder(FinalProjectPP_MainActivity.this,
				Locale.getDefault());
		List<Address> addresses;
		try {
			addresses = geoCoder
					.getFromLocation(loc.latitude, loc.longitude, 1);
			if (addresses.size() > 0) {

				// Get the first address from the list and get its address lines
				Address address = addresses.get(0);
				String addressString = "";
				for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
					addressString += address.getAddressLine(i) + " ";
				}
				return addressString;
			}
		} catch (IOException e) {
			Log.d("GEOCODER", e.getMessage(), e);
		}
		return "Address not found: Geocoder not supported on this phone";

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.final_project_pp__main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);

	}


	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			/*
			 * If no resolution is available, display a dialog to the user with
			 * the error.
			 */
			// showErrorDialog(connectionResult.getErrorCode());
			Log.d("connectResult", "no resolution available");

		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		// Display the connection status
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

	}

	/*
	 * Called by Location Services if the connection to the location client
	 * drops because of an error.
	 */
	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(this, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	// Define a DialogFragment that displays the error dialog
	public static class ErrorDialogFragment extends DialogFragment {
		// Global field to contain the error dialog
		private Dialog mDialog;

		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		// Set the dialog to display
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}

	/*
	 * Handle results returned to the FragmentActivity by Google Play services
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Decide what to do based on the original request code
		switch (requestCode) {

		case CONNECTION_FAILURE_RESOLUTION_REQUEST:
			/*
			 * If the result code is Activity.RESULT_OK, try to connect again
			 */
			switch (resultCode) {
			case Activity.RESULT_OK:
				/*
				 * Try the request again
				 */

				break;
			}

		}
	}

	private boolean servicesConnected() {
		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d("Location Updates", "Google Play services is available.");
			// Continue
			return true;
			// Google Play services was not available for some reason
		} else {
			 // Get the error code
//			 int errorCode = connectionResult.getErrorCode();
//			 // Get the error dialog from Google Play services
//			 Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
//			 errorCode,
//			 this,
//			 CONNECTION_FAILURE_RESOLUTION_REQUEST);
//			
//			 // If Google Play services can provide an error dialog
//			 if (errorDialog != null) {
//			 // Create a new DialogFragment for the error dialog
//			 ErrorDialogFragment errorFragment =
//			 new ErrorDialogFragment();
//			 // Set the dialog in the DialogFragment
//			 errorFragment.setDialog(errorDialog);
//			 // Show the error dialog in the DialogFragment
//			 errorFragment.show(getSupportFragmentManager(),
//			 "Location Updates");
//			 }
			return false;
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		getAndDisplay();
	}


	private void getAndDisplay() {
		final Handler handler = new Handler(Looper.getMainLooper());
		(new Thread(){
			@Override
			public void run(){
				final String results = getFeedResults(feed);
				
				handler.post(new Runnable() {
					@Override
					public void run() {
						
						final String parsed = parseJson(results);
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
	
	// For JSON functions available, a good summary is available at:
	// http://stackoverflow.com/questions/9605913/how-to-parse-json-in-android
	private String parseJson(String jsonString) {
		String result = "";
		try {
			JSONObject jObject   = new JSONObject(jsonString);
			JSONArray r    = jObject.getJSONArray("results");
			
			for (int i=0; i<r.length(); i++) {
				JSONObject j      = r.getJSONObject(i);
				String id            = j.getString("id");
				String title          = j.getString("title");
				String address =        j.getString("address");
				String latitude           = j.getString("latitude");
			    String longitude 	  = j.getString("longitude");
			    String description = j.getString("description"); 
			    
			    
			    EventInfo info = new EventInfo(id, title, latitude, longitude, address, description);
			    Marker m = showMarkerLocation(info); 
			    eventMarkerMap.put(m, info); 
			    
			   
				result +=  "parse successful";
				
			
			}
			return "<html><body>" + result + "</body></html>";
		} catch (JSONException e) {
			return "Unable to parse JSON";
		}
	}
}
