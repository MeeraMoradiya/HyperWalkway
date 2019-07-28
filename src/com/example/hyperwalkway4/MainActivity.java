package com.example.hyperwalkway4;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity 
{
	
	public static String NAMESPACE = "http://tempuri.org/";
	public static String  URL = "http://192.168.0.102/Hyperwalkway_webservice/WebService.asmx"; 
	public static String imagestr="http://192.168.0.102/Hyperwalkway_webservice/upload/";
	public static String SOAP_ACTION = "http://tempuri.org/gettables";
	public static String SOAP_ACTION1="http://tempuri.org/get_Rate";
	public static String SOAP_ACTION2="http://tempuri.org/get_scheme";
	public static String SOAP_ACTION3 = "http://tempuri.org/get_Order_OrderDetalis";
	public static String METHOD_NAME = "gettables"; 
	public static String METHOD_NAME1="get_Rate";
	public static String METHOD_NAME2="get_scheme";
	public static String METHOD_NAME3 = "get_Order_OrderDetalis";
	
	/*final String SENDER_ID = "660512296197";
	public static String regId;
	private NotificationManager mgr=null;*/
	
	String str=null;
	Button b1,b2,b3,b4,b5,b6,b7,bill,btn_rate_app;
	public static Button btn_set_budget;
	SQLiteDatabase db;
	public static int i=0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		// TODO Auto-generated method stub
	
		 setContentView(R.layout.activity_main);
		
		 db=openOrCreateDatabase("Myshopping1.db",MODE_PRIVATE, null);
	//	 db.execSQL("drop table [Order]");
		// db.execSQL("drop table OrderDetails");
		 db.execSQL("CREATE TABLE IF NOT EXISTS Category (category_id int  NOT NULL,category_name varchar(50) NOT NULL)");
		 db.execSQL("CREATE TABLE IF NOT EXISTS SubCategory(sub_id int NOT NULL,category_id varchar(50) NOT NULL,sub_name varchar(50) NOT NULL)");
		 db.execSQL("CREATE TABLE IF NOT EXISTS Brand(brand_id int NOT NULL,brand_name varchar(50) NOT NULL,sub_id int NOT NULL)");
		 db.execSQL("CREATE TABLE IF NOT EXISTS Product(product_id int NOT NULL,product_name varchar(50) NOT NULL,image varchar(50) NOT NULL,price varchar(50) NOT NULL,expiry_date varchar(50) NOT NULL,barcode_number varchar(50) NOT NULL,brand_id int NOT NULL,sub_id int NOT NULL,status varchar(50) NOT NULL)");
		 db.execSQL("CREATE TABLE IF NOT EXISTS cart(cart_id INTEGER,product_id int,qty int,price REAL,user_name TEXT)");
		 db.execSQL("CREATE TABLE IF NOT EXISTS [Order](order_id int NOT NULL,user_name varchar(50) NULL,date varchar(50) NULL)");
		 db.execSQL("CREATE TABLE IF NOT EXISTS OrderDetails(order_id int NOT NULL,product_id int NOT NULL,qty int NOT NULL,price varchar(50) NULL)");
		 db.execSQL("CREATE TABLE IF NOT EXISTS Budget(budget INTEGER)");
		 db.execSQL("CREATE TABLE IF NOT EXISTS Rate(product_id int,rate numeric(18, 0))");
		 db.execSQL("CREATE TABLE IF NOT EXISTS Scheme([product_id] [int] NOT NULL,[dis_per] [varchar](50) NOT NULL,[start_date] [varchar](50) NULL,[end_date] [varchar](50) NULL,[status] [varchar](50) NULL)");
		 db.execSQL("delete from cart");
		 db.execSQL("delete from budget");
		 
	        b1=(Button)findViewById(R.id.mlist);
	        b2=(Button)findViewById(R.id.sproduct);
	        b3=(Button)findViewById(R.id.mycart);
	        b4=(Button)findViewById(R.id.bscan);
	        b5=(Button)findViewById(R.id.bills);
	        b6=(Button)findViewById(R.id.delete);
	        b7=(Button)findViewById(R.id.review);
	        btn_set_budget=(Button)findViewById(R.id.btn_set_budget);
	        btn_rate_app=(Button)findViewById(R.id.btn_rate_app);
	
	      //  Toast.makeText(getBaseContext(), login.uname, 5000).show();
	    //    GCMRegistrar.checkDevice(MainActivity.this);
		//	GCMRegistrar.checkManifest(MainActivity.this);
		//	str=login.pref.getString("status",null);
			//if(str.equals("no")==true)
			///{
		/*		regId = GCMRegistrar.getRegistrationId(MainActivity.this);
			//	regId="0";
				if (regId.equals("")) 
				{
				  GCMRegistrar.register(this, SENDER_ID);
				 // Toast.makeText(getBaseContext(), regId, 5000).show();
				  Log.v("Push", regId);
				} 
				else
				{
				  Log.v("Push", regId);
				//  Log.v("uname", login.uname);
				  new Doregid().execute();
					mgr=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
					mgr.cancel(1);
				}
			*/
			
	        if(!isMyServiceRunning(backgroundservice.class))
			{
				startService(new Intent(MainActivity.this,backgroundservice.class));	
			}
			
			
	        bindcontrol();
	        new update_table().execute();
	        new fill_RateReview().execute();
	        new fill_scheme().execute();
	    	
	    	
	    	
	      //  new Fill_Order_OrderDetails().execute();
	        //Toast.makeText(MainActivity.this,String.valueOf(i),5000).show();
	       
	        
	    }
	public boolean isMyServiceRunning(Class<?> serviceClass) {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (serviceClass.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
/*	class Doregid extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		protected String doInBackground(Void... params) 
		{
		try
		{			
		SoapObject request = new SoapObject("http://tempuri.org/","updatedevice");
			request.addProperty("uname",login.uname);
			request.addProperty("deviceid",regId.toString());
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
	        androidHttpTransport.call("http://tempuri.org/updatedevice", envelope);
	        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
			return resultstr.toString();								
		}
		catch(Exception e)
		{
			Log.e("error", e.toString());
			return e.toString();
		}		
		}
	

	      //  new Fill_Order_OrderDetails().execute();
	        //Toast.makeText(MainActivity.this,String.valueOf(i),5000).show();
	       
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);				
			//pdia.dismiss();	
			
			//Toast.makeText(login.this, result,5000).show();
			if(result.equals("true"))
			{
				
			//	login.edit.putString("status","yes");
			//	login.edit.commit();
			
			}
			else
			{
				
			}
		}

	    }*/
	 class update_table extends AsyncTask<Void, Void, String>
	    {
	    	private ProgressDialog pdia;
	    	
	    	@Override
	    	protected String doInBackground(Void... params) {
	    		// TODO Auto-generated method stub
	    		
	    		try
	    		{
	    			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	    	      //  request.addProperty("data", "yes");
	    	      
	    	             
	    	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	    	        envelope.dotNet=true;
	    	        envelope.setOutputSoapObject(request);
	    	        
	    	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	    	        
	    	        androidHttpTransport.call(SOAP_ACTION, envelope);
	    	        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
	    	        
	    	        Log.d("message","MEssage : "+resultstr.toString());
	    	        return resultstr.toString();
	    	        
//	    	        Toast.makeText(Reg.this, resultstr.toString(), Toast.LENGTH_SHORT).show();
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
	    		pdia = new ProgressDialog(MainActivity.this);
	    		pdia.setCanceledOnTouchOutside(false);
	            pdia.setMessage("Loading...");
	            pdia.show(); 
	    	}
	    	
	    	
	    	@Override
	    	protected void onPostExecute(String result)
	    	{
	    		// TODO Auto-generated method stub
	    		//s1=(Spinner)findViewById(R.id.category);
	    		super.onPostExecute(result);    	    	
	    		
	    		
	    		pdia.dismiss();
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
	    			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);
	    	      //  request.addProperty("data", "yes");
	    	      
	    	             
	    	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	    	        envelope.dotNet=true;
	    	        envelope.setOutputSoapObject(request);
	    	        
	    	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	    	        
	    	        androidHttpTransport.call(SOAP_ACTION2, envelope);
	    	        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
	    	        
	    	        Log.d("message","MEssage : "+resultstr.toString());
	    	        return resultstr.toString();
	    	        
//	    	        Toast.makeText(Reg.this, resultstr.toString(), Toast.LENGTH_SHORT).show();
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
	    		pdia = new ProgressDialog(MainActivity.this);
	    		pdia.setCanceledOnTouchOutside(false);
	            pdia.setMessage("Loading...");
	            pdia.show(); 
	    	}
	    	
	    	
	    	@Override
	    	protected void onPostExecute(String result)
	    	{
	    		// TODO Auto-generated method stub
	    		//s1=(Spinner)findViewById(R.id.category);
	    		super.onPostExecute(result);    	    	
	    		
	    	
	    		pdia.dismiss();
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
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
	      //  request.addProperty("data", "yes");
	      
	             
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	        
	        androidHttpTransport.call(SOAP_ACTION1, envelope);
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
	
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pdia = new ProgressDialog(MainActivity.this);
		pdia.setCanceledOnTouchOutside(false);
        pdia.setMessage("Loading...");
        pdia.show(); 
	}
	
	
	@Override
	protected void onPostExecute(String result)
	{
		// TODO Auto-generated method stub
		//s1=(Spinner)findViewById(R.id.category);
		super.onPostExecute(result);    	    	
		
	
		pdia.dismiss();
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

	    void bindcontrol()
	    {
	    	b1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i1=new Intent(MainActivity.this,selectlist.class);
					startActivity(i1);
				}
			});
	    	btn_rate_app.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i1=new Intent(MainActivity.this,feedback.class);
					startActivity(i1);
				}
			});
	    	b2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i2=new Intent(MainActivity.this,searchproduct.class);
					startActivity(i2);
				}
			});
	    	b3.setOnClickListener(new View.OnClickListener() {
		
	    		@Override
	    		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i3=new Intent(MainActivity.this,mycart.class);
			startActivity(i3);
	    		}
	    	});
	    	
	    
	    	b4.setOnClickListener(new View.OnClickListener() {
		
	    		@Override
	    		public void onClick(View arg0) {
			// TODO Auto-generated method stub
	   		Intent i4=new Intent(MainActivity.this,barcode_scan.class);
	  		startActivity(i4);
	    		}
	    	});
	    	
	    	b6.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					

					Intent i1=new Intent(MainActivity.this,delete.class);
					startActivity(i1);
					
				}
			});
	    	b7.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					

					Intent i1=new Intent(MainActivity.this,my_expances.class);
					startActivity(i1);
					
				}
			});
	    	b5.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					

					Intent i1=new Intent(MainActivity.this,my_expances.class);
					startActivity(i1);
					
				}
			});
	    	
	    	btn_set_budget.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					

					/*Intent i1=new Intent(MainActivity.this,my_expances.class);
					startActivity(i1);*/
					if(btn_set_budget.getText().equals("Set Budget")==true)
					{
						
						AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
						alert.setTitle("Enter Budget");
						final EditText input=new EditText(MainActivity.this);
						alert.setView(input);
						alert.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								String in=input.getText().toString();
								if(in.equals(""))
								{
									Toast.makeText(getBaseContext(), "Enter Budget", 2000).show();
								}
								else
								{
									String qr1="select budget from Budget";
									Cursor c3=db.rawQuery(qr1, null);
									 if (c3.getCount()!=0)
									 {
									
										 db.execSQL("delete from Budget"); 
									 }
									 else
									 {
										 
									db.execSQL("INSERT INTO Budget VALUES('"+in+"')");
									btn_set_budget.setText("Unset Budget");
								//	db.execSQL("CREATE TABLE IF NOT EXISTS '"+in+"'(item)");
									 }
									
									
								}
								
								//budget=Integer.parseInt(in);
								}	
							
							
						});
						alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						});
						alert.show();
					}
					else
					{
						search_product_view.budget=0;
						 db.execSQL("delete from Budget");
						 btn_set_budget.setText("Set Budget");
					}
				}	
				
	    	});


	    }
	   
	    class Fill_Order_OrderDetails extends AsyncTask<Void, Void, String>
		{
			private ProgressDialog pdia;
			
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				try
				{
					SoapObject request = new SoapObject(MainActivity.NAMESPACE,METHOD_NAME3);
			      //  request.addProperty("data", "yes");
			      
			             
			        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			        envelope.dotNet=true;
			        envelope.setOutputSoapObject(request);
			        
			        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
			        
			        androidHttpTransport.call(SOAP_ACTION3, envelope);
			        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
			        
			        Log.d("message","MEssage : "+resultstr.toString());
			        return resultstr.toString();
			        
//			        Toast.makeText(Reg.this, resultstr.toString(), Toast.LENGTH_SHORT).show();
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

	    /**
	     * A placeholder fragment containing a simple view.
	     */
	    
	    
	}
