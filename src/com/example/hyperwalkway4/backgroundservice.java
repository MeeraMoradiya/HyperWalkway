package com.example.hyperwalkway4;

import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.hyperwalkway4.MainActivity.Fill_Order_OrderDetails;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
public class backgroundservice  extends Service 
{	
	public static boolean maxspeed=false; 
	public static double speed;
	public static double lntd;
	public static double altd;
	public static double temp;
	SQLiteDatabase db;
	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedpreferences;
	public String AppMode="";
	TimerTask  timerTask2;
	Timer timer=new Timer();
	Handler handler2=new Handler();
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		if (sharedpreferences.contains("AppStatus"))
        {
			AppMode=sharedpreferences.getString("AppStatus", "NO");
        }
		timerTask2=new TimerTask() {
			@Override
			public void run() {
				handler2.post(new Runnable() {
					@Override

					public void run() {
						db=openOrCreateDatabase("Myshopping1.db",MODE_PRIVATE, null);
						  new update_table().execute();
					        new fill_RateReview().execute();
					        new fill_scheme().execute();
						
					//call async task here				
						
					}
				});				
			}			
		};		
		timer.scheduleAtFixedRate(timerTask2, 1000, 10000);	
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	class update_table extends AsyncTask<Void, Void, String>
    {
    	private ProgressDialog pdia;
    	
    	@Override
    	protected String doInBackground(Void... params) {
    		// TODO Auto-generated method stub
    		
    		try
    		{
    			SoapObject request = new SoapObject(MainActivity.NAMESPACE,MainActivity.METHOD_NAME);
    	      //  request.addProperty("data", "yes");
    	      
    	             
    	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    	        envelope.dotNet=true;
    	        envelope.setOutputSoapObject(request);
    	        
    	        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
    	        
    	        androidHttpTransport.call(MainActivity.SOAP_ACTION, envelope);
    	        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
    	        
    	        Log.d("message","MEssage : "+resultstr.toString());
    	        return resultstr.toString();
    	        
//    	        Toast.makeText(Reg.this, resultstr.toString(), Toast.LENGTH_SHORT).show();
    		}
    		catch(Exception e)
    		{
    			Log.e("error13", e.toString());
    			return "fail,fail";
    			
    		}
    			
    		
    		
    	}
    	
    	
    	@Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
    		super.onPreExecute();
    	/*	pdia = new ProgressDialog(MainAc.this);
    		pdia.setCanceledOnTouchOutside(false);
            pdia.setMessage("Loading...");
            pdia.show(); */
    	}
    	
    	
    	@Override
    	protected void onPostExecute(String result)
    	{
    		// TODO Auto-generated method stub
    		//s1=(Spinner)findViewById(R.id.category);
    		super.onPostExecute(result);    	    	
    		
    		
    	//	pdia.dismiss();
    		// Log.d("str in update ",result);
    		String tbl[]=result.split("-");
    		Log.d("tablelenght",String.valueOf(tbl.length));
    	    db.execSQL("delete from Category");
    	    db.execSQL("delete from SubCategory");
    	    db.execSQL("delete from Brand");
        	db.execSQL("delete from Product");
    		for(int i =0;i<tbl.length;i++)
    		{
    			String row[]=tbl[i].split(",");
    			for(int j=0;j<row.length;j++)
    			{
    				String clm[]=row[j].split("`");
    				if(i==0)
    				{
    				String qr="insert into Category values('"+clm[0]+"','"+clm[1]+"')";	
    				db.execSQL(qr);
    				}
    				else if (i==1)
    				{
    					String qr="insert into SubCategory values('"+clm[0]+"','"+clm[1]+"','"+clm[2]+"')";	
	    				db.execSQL(qr);
    				}
    				else if (i==2)
    				{
    					String qr="insert into Brand values('"+clm[0]+"','"+clm[1]+"','"+clm[2]+"')";	
	    				db.execSQL(qr);
    				}
    				else if (i==3)
    				{
    					String qr="insert into Product values('"+clm[0]+"','"+clm[1]+"','"+clm[2]+"','"+clm[3]+"','"+clm[4]+"','"+clm[5]+"','"+clm[6]+"','"+clm[7]+"','"+clm[8]+"')";	
	    				db.execSQL(qr);
    				}
    				}
    				//
    			}
    	new Fill_Order_OrderDetails().execute();
    		}
    	
    	
    	}

	class fill_scheme extends AsyncTask<Void, Void, String>
    {
    	private ProgressDialog pdia;
    	
    	@Override
    	protected String doInBackground(Void... params) {
    		// TODO Auto-generated method stub
    		
    		try
    		{
    			SoapObject request = new SoapObject(MainActivity.NAMESPACE, MainActivity.METHOD_NAME2);
    	      //  request.addProperty("data", "yes");
    	      
    	             
    	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    	        envelope.dotNet=true;
    	        envelope.setOutputSoapObject(request);
    	        
    	        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
    	        
    	        androidHttpTransport.call(MainActivity.SOAP_ACTION2, envelope);
    	        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
    	        
    	        Log.d("message","MEssage : "+resultstr.toString());
    	        return resultstr.toString();
    	        
//    	        Toast.makeText(Reg.this, resultstr.toString(), Toast.LENGTH_SHORT).show();
    		}
    		catch(Exception e)
    		{
    			Log.e("error13", e.toString());
    			return "fail,fail";
    			
    		}
    	}
    	
    	
    	@Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
    		super.onPreExecute();
    	/*	pdia = new ProgressDialog(MainActivity.this);
    		pdia.setCanceledOnTouchOutside(false);
            pdia.setMessage("Loading...");
            pdia.show();*/ 
    	}
    	
    	
    	@Override
    	protected void onPostExecute(String result)
    	{
    		// TODO Auto-generated method stub
    		//s1=(Spinner)findViewById(R.id.category);
    		super.onPostExecute(result);    	    	
    		
    	
    		//pdia.dismiss();
    		//Toast.makeText(getBaseContext(), result.toString(),5000).show();
    		if(!(result.equals("false")))
    		{
    			db.execSQL("delete from Scheme");
    			String row[]=result.split(",");
    			for(int i=0;i<row.length;i++)
    			{
    				String col[]=row[i].split("`");
    				db.execSQL("insert into Scheme values("+col[0]+",'"+col[1]+"','"+col[2]+"','"+col[3]+"','"+col[4]+"')");
    				
    			}
    	    }
    	}
    }
class fill_RateReview extends AsyncTask<Void, Void, String>
{
private ProgressDialog pdia;

@Override
protected String doInBackground(Void... params) {
	// TODO Auto-generated method stub
	
	try
	{
		SoapObject request = new SoapObject(MainActivity.NAMESPACE, MainActivity.METHOD_NAME1);
      //  request.addProperty("data", "yes");
      
             
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        
        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
        
        androidHttpTransport.call(MainActivity.SOAP_ACTION1, envelope);
        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
        
        Log.d("message","MEssage : "+resultstr.toString());
        return resultstr.toString();
        
//        Toast.makeText(Reg.this, resultstr.toString(), Toast.LENGTH_SHORT).show();
	}
	catch(Exception e)
	{
		Log.e("error13", e.toString());
		return "fail,fail";
		
	}
		
	
	
}


@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
/*	pdia = new ProgressDialog(MainActivity.this);
	pdia.setCanceledOnTouchOutside(false);
    pdia.setMessage("Loading...");
    pdia.show(); */
}


@Override
protected void onPostExecute(String result)
{
	// TODO Auto-generated method stub
	//s1=(Spinner)findViewById(R.id.category);
	super.onPostExecute(result);    	    	
	

	//pdia.dismiss();
	//Toast.makeText(getBaseContext(), result.toString(),5000).show();
	if(!(result.equals("false")))
	{
		db.execSQL("delete from Rate");
		String row[]=result.split(",");
		for(int i=0;i<row.length;i++)
		{
			String col[]=row[i].split("`");
			db.execSQL("insert into Rate values("+col[0]+","+col[1]+")");
			
		}
    }
}
}
class Fill_Order_OrderDetails extends AsyncTask<Void, Void, String>
{
	private ProgressDialog pdia;
	
	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		try
		{
			SoapObject request = new SoapObject(MainActivity.NAMESPACE,MainActivity.METHOD_NAME3);
	      //  request.addProperty("data", "yes");
	      
	             
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
	        
	        androidHttpTransport.call(MainActivity.SOAP_ACTION3, envelope);
	        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
	        
	        Log.d("message","MEssage : "+resultstr.toString());
	        return resultstr.toString();
	        
//	        Toast.makeText(Reg.this, resultstr.toString(), Toast.LENGTH_SHORT).show();
		}
		catch(Exception e)
		{
			Log.e("error13", e.toString());
			return "fail,fail";
			
		}
			
		
		
	}
	
	
/*	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		//Log.d("context",getBaseContext().toString());
		pdia = new ProgressDialog();
		pdia.setCanceledOnTouchOutside(false);
        pdia.setMessage("Loading...");
        pdia.show(); 
	}*/
	
	
	@Override
	protected void onPostExecute(String result)
	{
		// TODO Auto-generated method stub
		//s1=(Spinner)findViewById(R.id.category);
		super.onPostExecute(result);    	    	
		
		
	//	pdia.dismiss();
		//Toast.makeText(getBaseContext(), result.toString(),5000).show();
		if(!(result.equals("false")))
		{
			
		
		String tbl[]=result.split("-");
		Log.d("Ordertablelenght",String.valueOf(tbl.length));
	    db.execSQL("delete from [Order]");
	    db.execSQL("delete from OrderDetails");
	    
		for(int i =0;i<tbl.length;i++)
		{
			String row[]=tbl[i].split(",");
			for(int j=0;j<row.length;j++)
			{
				String clm[]=row[j].split("`");
				if(i==0)
				{
				String qr="insert into [Order] values('"+clm[0]+"','"+clm[1]+"','"+clm[2]+"')";	
				db.execSQL(qr);
				}
				if(i==1)
				{
					String qr="insert into OrderDetails values('"+clm[0]+"','"+clm[1]+"','"+clm[2]+"','"+clm[3]+"')";	
    				db.execSQL(qr);
				}
				
				
			}
		}
	
		}
	}
}


}