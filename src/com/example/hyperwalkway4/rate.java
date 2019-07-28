package com.example.hyperwalkway4;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
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


public class rate extends Activity{

	public static String SOAP_ACTION = "http://tempuri.org/rate_insert";
	String METHOD_NAME = "rate_insert"; 
	RatingBar r;
	Button b1;
	TextView txt_prd_name,txt_brand,txt_price,txt_date,txt_qty;
	String tmp,pid,cust_id;
	EditText e1;
	SQLiteDatabase db;
@Override
protected void onCreate(Bundle savedInstanceState) 
{
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.rate);
	r=(RatingBar)findViewById(R.id.rate_bar);
	b1=(Button)findViewById(R.id.b1);
	e1=(EditText)findViewById(R.id.e1);
	txt_brand=(TextView)findViewById(R.id.txt_brand);
	txt_date=(TextView)findViewById(R.id.txt_date);
	txt_prd_name=(TextView)findViewById(R.id.txt_prd_name);
	txt_price=(TextView)findViewById(R.id.txt_price);
	txt_qty=(TextView)findViewById(R.id.txt_qty);
	
	db=openOrCreateDatabase("Myshopping1.db",MODE_PRIVATE, null);
	
	Intent i2=getIntent();
	 if(i2!=null)
	 {
		 Bundle extras=i2.getExtras();


		pid=extras.getString("prd_id");

		 
		 tmp="Rate: ";
		 tmp+=extras.getString("prd_name");
		 txt_prd_name.setText(tmp);
		 
		 tmp="Brand: ";
		 tmp+=extras.getString("brand_name");
		 txt_brand.setText(tmp);
		 
		 tmp="Price: ";
		 tmp+=extras.getString("prd_price");
		 txt_price.setText(tmp);
		 
		 tmp="Shopping Date: ";
		 tmp+=extras.getString("date");
		 txt_date.setText(tmp);
		 
		 tmp="Quantity: ";
		 tmp+=extras.getString("prd_qty");
		 txt_qty.setText(tmp);
			
		  
	//	 Toast.makeText(getBaseContext(), pid, 2000).show();
	//	 Toast.makeText(getBaseContext(), cid, 2000).show();
	 }
b1.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		
		
	//	Toast.makeText(rate.this,pid,5000).show();
		//Toast.makeText(rate.this,login.uname,5000).show();
		//Toast.makeText(rate.this,String.valueOf(r.getRating()).toString(),5000).show();
		//Toast.makeText(rate.this,e1.getText().toString(),5000).show();
		DoRate d1=new DoRate();
		d1.execute();
		String rate=String.valueOf( r.getRating());
		//Toast.makeText(rate.this,rate,5000).show();
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
			request.addProperty("product_id",pid);
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
		pdia = new ProgressDialog(rate.this);
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
			Toast.makeText(rate.this,"Rate Inserted",5000).show();
		}
		else
		{
			Toast.makeText(rate.this,"Not Inserted",5000).show();
		}
	}

}
}
