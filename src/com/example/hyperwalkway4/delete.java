package com.example.hyperwalkway4;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class delete extends Activity {
	//public static String NAMESPACE = "http://tempuri.org/";
	//public static String URL = "http://192.168.43.166/Hyperwalkway_webservice/WebService.asmx"; 
	//public static String websiteURL = "http://172.16.1.101/hype/"; 
	public static String SOAP_ACTION = "http://tempuri.org/delete";

	Button deact;
	EditText username;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete);				
		deact = (Button)findViewById(R.id.b1);
		username = (EditText)findViewById(R.id.e1);
	//	login.edit.clear();
	//	login.edit.commit();
		deact.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub					
				DoReg dr = new DoReg();
				dr.execute();
			}
		});		
	
		
		
	}
	class DoReg extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		protected String doInBackground(Void... params) 
		{
			try
			{			
				SoapObject request = new SoapObject("http://tempuri.org/","delete");
				request.addProperty("username",username.getText().toString());
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet=true;
				envelope.setOutputSoapObject(request);				
				HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);				
				androidHttpTransport.call("http://tempuri.org/delete", envelope);				
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
			pdia = new ProgressDialog(delete.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("deactivating...");
			pdia.show(); 
		}		
		protected void onPostExecute(String result) {
			super.onPostExecute(result);				
			pdia.dismiss();		
			if(result.toString().equals("true"))
			{
				
				Toast.makeText(delete.this, "deactivated", 2).show();	
				Intent i1 = new Intent(delete.this,login.class); 
				startActivity(i1);
			}
			else
			{
				Toast.makeText(delete.this, "username wrong", 2).show();	
			}

			}
		}
}
