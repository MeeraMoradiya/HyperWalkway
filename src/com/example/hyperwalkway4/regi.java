package com.example.hyperwalkway4;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class regi extends Activity{
//	public static String NAMESPACE = "http://tempuri.org/";
//	public static String  URL ="http://192.168.1.107/Hyperwalkway_webservice/WebService.asmx"; 
	//public static String  websiteURL = "http://192.168.1.136/WebSite3/WebService.asmx"; 
	public static String SOAP_ACTION = "http://tempuri.org/insert";
//	String METHOD_NAME = "HelloWorld"; 
//	public static String IP = "172.16.1.101";
	EditText e1,e2,e3,e4,e5;
	Button b1,b2;
	
	final String SENDER_ID = "698134854177";
	public static String regId;
	private NotificationManager mgr=null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regi);
        e4=(EditText)findViewById(R.id.uname);
        e5=(EditText)findViewById(R.id.pass);
        e1=(EditText)findViewById(R.id.cpass);
        
        e3=(EditText)findViewById(R.id.phone);
       
        b1=(Button)findViewById(R.id.reg);
        b2=(Button)findViewById(R.id.btn_back11);
        
        GCMRegistrar.checkDevice(regi.this);
		GCMRegistrar.checkManifest(regi.this);
		
		regId = GCMRegistrar.getRegistrationId(regi.this);
		 Log.d("Push", regId);
		 Toast.makeText(getBaseContext(), regId, 5000).show();
		if (regId.equals("")) 
		{
		  GCMRegistrar.register(this, SENDER_ID);
		 
		  regId = GCMRegistrar.getRegistrationId(regi.this);
		  Toast.makeText(getBaseContext(), "second"+regId, 5000).show();
		  Log.v("Push blank", regId);
		 
		} 
		else
		{
		  Log.v("Push", regId);
		//  Log.v("uname", login.uname);
		  Toast.makeText(getBaseContext(), regId, 5000).show();
		 
			
		}
		
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			if(!(e5.getText().toString().equals(e1.getText().toString())))
			{
				Toast.makeText(regi.this,"Password and confirm passsword must be matchd", 5000).show();
			}
			else if(e3.getText().toString().length()!=10)
			{
				Toast.makeText(regi.this,"Moblie number must contain 10 digits", 5000).show();
			}
			else
			{
				DoReg obj=new DoReg();
				obj.execute();
				mgr=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
				mgr.cancel(1);
			}
			}
		});
        
        b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
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
   			SoapObject request = new SoapObject("http://tempuri.org/","insert");
   				
   				request.addProperty("mob",e3.getText().toString());
   		        request.addProperty("uname",e4.getText().toString());
   		        request.addProperty("pass",e5.getText().toString());
   		        request.addProperty("device_id",regId);
		        
   		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
   		        envelope.dotNet=true;
   		        envelope.setOutputSoapObject(request);
   		        
   		        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
   		        
   		        androidHttpTransport.call("http://tempuri.org/insert", envelope);
   		
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
   			pdia = new ProgressDialog(regi.this);
   			pdia.setCanceledOnTouchOutside(false);
   	        pdia.setMessage("Registering...");
   	        pdia.show(); 
   		}
   		
   		protected void onPostExecute(String result) {
   			
   			super.onPostExecute(result);				
   			pdia.dismiss();				
   			Intent i1=new Intent(regi.this,login.class);
   			startActivity(i1);	
   			
   		}
   	}

  
   

}

