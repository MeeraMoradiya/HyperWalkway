package com.example.hyperwalkway4;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class my_expances extends Activity
{
	ListView l1;
	SQLiteDatabase db;
	Button btn_back8;
	
	 
	ArrayList<Bills> b1=new ArrayList<Bills>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_expances);
		l1=(ListView)findViewById(R.id.lst_bills);
		btn_back8=(Button)findViewById(R.id.btn_back8);
		 
		db=openOrCreateDatabase("Myshopping1.db",MODE_PRIVATE, null);
		
		new Fill_Order_OrderDetails().execute();
		
		 btn_back8.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				finish();	
				}
			});
		 
		 Cursor c2=db.rawQuery("select o.*,sum((cast(od.price as int))*(cast(od.qty as int)))as total from [Order] as o,OrderDetails as od where o.order_id=od.order_id and o.user_name='"+login.uname+"' group by o.order_id,o.user_name,o.date",null);
		 Toast.makeText(getBaseContext(), String.valueOf(c2.getCount()), 5000).show();
		 if (c2.getCount()!=0)
		 {
			 b1.clear();
			  while(c2.moveToNext())
			 {
				 
				  Bills ob2=new Bills();
				  ob2.order_id=c2.getString(0);
				  ob2.date=c2.getString(2);
				  ob2.rs=c2.getString(3);
				
				  b1.add(ob2);
				  
			 }
		 }
		 CustomBillAdapter cb1 = new CustomBillAdapter(b1,my_expances.this);
	        l1.setAdapter(cb1);
	       l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String order_id=b1.get(arg2).order_id;
				String order_date=b1.get(arg2).date;
				Bundle extras=new Bundle();
				extras.putString("order_id",order_id);
				extras.putString("order_date",order_date);
		//		Toast.makeText(my_expances.this, order_id, 5000).show();
		//		Toast.makeText(my_expances.this, order_date, 5000).show();
				Intent i1=new Intent(my_expances.this, view_order_details.class).putExtras(extras);
				startActivity(i1);
				
			}
		});
	       
	}
	public class Fill_Order_OrderDetails extends AsyncTask<Void, Void, String>

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
		        
//		        Toast.makeText(Reg.this, resultstr.toString(), Toast.LENGTH_SHORT).show();
			}
			catch(Exception e)
			{
				Log.e("error13", e.toString());
				return "fail,fail";
				
			}
				
			
			
		}
		
		
		/*@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdia = new ProgressDialog(.this);
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
			
			
			//pdia.dismiss();
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
					
					//
				}
			}
		
			}
		}


	}



	
}
class Bills
{
	String order_id;
	String date;
	String rs;
}
