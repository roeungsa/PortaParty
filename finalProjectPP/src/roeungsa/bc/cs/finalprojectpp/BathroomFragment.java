package roeungsa.bc.cs.finalprojectpp;



import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BathroomFragment extends Activity {
	Intent intent; 
	String id; 
	public BathroomFragment(){
		
	}

	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.bathroom_fragment);
		
		
		TextView titleView = (TextView) findViewById(R.id.title); 
		TextView addressView = (TextView) findViewById(R.id.address); 
		
		intent = getIntent(); 
		
		String title = intent.getStringExtra("name"); 
		String address = intent.getStringExtra("address"); 
		id = intent.getStringExtra("id"); 
		
		titleView.setText(title);
		addressView.setText(address); 
	
	
	}
	
	public void postStall(View view){
		Intent graffitiStall = new Intent(getApplicationContext(), DrawableStall.class); 
		graffitiStall.putExtra("id", id); 
		startActivity(graffitiStall); 
	}
	
	public void viewStall(View view){
		Intent contentViewer = new Intent(getApplicationContext(), ViewStall.class);
		contentViewer.putExtra("id", id); 
		startActivity(contentViewer);
	}

	

}
