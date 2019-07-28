package com.example.hyperwalkway4;

import java.security.PublicKey;
import java.util.Set;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.string;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity {
	public static String NAMESPACE = "http://tempuri.org/";
//	public static String  URL = "http://192.168.43.166/Hyperwalkway_webservice/WebService.asmx"; 
 
	//public static String SOAP_ACTION = "http://tempuri.org/get_cust_reg";
	//String METHOD_NAME = "get_cust_reg"; 
	SQLiteDatabase db;
	EditText e1,e2;
	Button b1,b2,b3;
	public static String uname;
//	public static String mypref="mypref";
//	public static SharedPreferences pref;
//	public static Editor edit;	
	String str=null;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        e1=(EditText)findViewById(R.id.uname);
        e2=(EditText)findViewById(R.id.pass);
        
        b1=(Button)findViewById(R.id.login);
        b2=(Button)findViewById(R.id.reg);
        
        db=openOrCreateDatabase("Myshopping1.db",MODE_PRIVATE, null);
        
      // db.execSQL("CREATE TABLE IF NOT EXISTS CustomerReg ([cust_id] [int] IDENTITY(1,1) NOT NULL,[user_name] [varchar](50) NOT NULL,[password] [varchar](50) NOT NULL,[mobile_number] [varchar](50) NOT NULL,[device_id] [varchar](500) NOT NULL)");
     /*  pref=getSharedPreferences(mypref, MODE_PRIVATE);
         edit=pref.edit();	
        String str=pref.getString("uname",uname);
        if(str!=null)
        {
        	Intent i1=new Intent(login.this,MainActivity.class);
			startActivity(i1);	
        }*/
        //b3=(Button)findViewById(R.id.forget);
       //new fill_cust_reg().execute();
		
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
		/*		Cursor c1=db.rawQuery("select * from CustomerReg where user_name='"+e1.getText()+"' ",null);
				if(c1.getCount()!=0)
				{
					Intent i1=new Intent(login.this,MainActivity.class);
					startActivity(i1);
				}
				else
				{
					Toast.makeText(login.this,"Username or password is incorrect",5000).show();
				}
				// TODO Auto-generated method stub
			//Intent i1=new Intent(MainActivity.this,home.class);
			//startActivity(i1);
		/*		
					String id=c1.getString(0);
					extras.putString("pid",id);
					extras.putString("url","mycart");
					//Log.d("pid",id);
					Intent i1=new Intent(mycart.this,search_product_view.class).putExtras(extras);
					startActivity(i1);
					
				}*/
				DoReg obj=new DoReg();
				obj.execute();
				
			}
		});
        b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Intent i1=new Intent(login.this,regi.class);
			startActivity(i1);
			}
		});
    }
   /*class fill_cust_reg extends AsyncTask<Void, Void, String>
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
    		pdia = new ProgressDialog(login.this);
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
    			db.execSQL("delete from CustomerReg");
    			String row[]=result.split(",");
    			//Log.d("cust_reg_lenght",row.length);
    			for(int i=0;i<row.length;i++)
    			{
    				String col[]=row[i].split("`");
    				db.execSQL("insert into CustomerReg values("+col[0]+",'"+col[1]+"','"+col[2]+"',"+col[3]+"','"+col[4]+"')");
    				
    			}
    	    }
    	}
    }*/
    class DoReg extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		
		
		protected String doInBackground(Void... params) 
		{
			try
			{			
			SoapObject request = new SoapObject("http://tempuri.org/","select1");
				request.addProperty("uname",e1.getText().toString());
		        request.addProperty("pass",e2.getText().toString());
		        
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
		        
		        androidHttpTransport.call("http://tempuri.org/select1", envelope);
		
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
			pdia = new ProgressDialog(login.this);
			pdia.setCanceledOnTouchOutside(false);
	        pdia.setMessage("Verifying...");
	        pdia.show(); 
		}
		
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);				
			pdia.dismiss();	
			
			//Toast.makeText(login.this, result,5000).show();
			if(result.equals("true"))
			{
				uname=e1.getText().toString();
			/*	edit.putString("uname", uname);
				edit.putString("status","no");
				edit.commit();*/
			Intent i1=new Intent(login.this,MainActivity.class);
			startActivity(i1);	
			}
			else
			{
				Toast.makeText(login.this,"Username or password is incorrect",5000).show();
			}
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

   
}

