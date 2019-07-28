package com.example.hyperwalkway4;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.hyperwalkway4.rate.DoRate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class feedback extends Activity
{
	public static String SOAP_ACTION = "http://tempuri.org/rate_app_insert";
	String METHOD_NAME = "rate_app_insert"; 
	RatingBar r;
	Button b1;
	EditText e1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		r=(RatingBar)findViewById(R.id.rate_app);
		b1=(Button)findViewById(R.id.b1_app);
		e1=(EditText)findViewById(R.id.e1_app);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DoRate d1=new DoRate();
				d1.execute();	
			}
		});
	}
	class DoRate extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		
		
		protected String doInBackground(Void... params) 
		{
			try
			{			
			SoapObject request = new SoapObject(MainActivity.NAMESPACE,METHOD_NAME);
				
		        request.addProperty("cust_name",login.uname);
		        request.addProperty("rate",String.valueOf(r.getRating()).toString());
		        request.addProperty("review",e1.getText().toString());
		        
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
		        
		        androidHttpTransport.call(SOAP_ACTION, envelope);
		
		        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
		        
		       
				return resultstr.toString();								
			}
			catch(Exception e)
			{
				Log.e("error", e.toString());
				return e.toString();
			}	
				
		}
		
		
		protected void onPreExecute() 
		{
			
			super.onPreExecute();
			pdia = new ProgressDialog(feedback.this);
			pdia.setCanceledOnTouchOutside(false);
	        pdia.setMessage("Verifying...");
	        pdia.show(); 
		}
		
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);				
			pdia.dismiss();	
			//Toast.makeText(rate.this,result,5000).show();
			if(result.toString().equals("true"))
			{
				Toast.makeText(feedback.this,"Rate Inserted",5000).show();
			}
			else
			{
				Toast.makeText(feedback.this,"Not Inserted",5000).show();
			}
		}

	}

}
