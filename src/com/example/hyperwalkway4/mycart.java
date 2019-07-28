package com.example.hyperwalkway4;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.hyperwalkway4.MainActivity;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class mycart extends Activity 
{
	ListView l1;
	SQLiteDatabase db;
	Button btn_done,btn_back7;
	String product_id,qty,uname,cust_id,send_str="",price=null;
    TextView t1,t2;
    int cart_total=0;
    public static String SOAP_ACTION = "http://tempuri.org/insert_Order_OrderDetails";
	String METHOD_NAME = "insert_Order_OrderDetails"; 
	ArrayList<CartProduct> cp1=new ArrayList<CartProduct>();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycart);
		l1=(ListView)findViewById(R.id.listView_cart);
		
		btn_back7=(Button)findViewById(R.id.btn_back7);
		t1=(TextView)findViewById(R.id.txt_total);
		t2=(TextView)findViewById(R.id.textview2);
		btn_done=(Button)findViewById(R.id.btn_done);
		
		db=openOrCreateDatabase("Myshopping1.db",MODE_PRIVATE,null);
		
		btn_back7.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		btn_done.setOnClickListener(new View.OnClickListener() 
		{
						@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
										
				String qr1="select * from cart";
				Cursor c3=db.rawQuery(qr1, null);
				
				 if (c3.getCount()!=0)
				 {
					 
					 while(c3.moveToNext())
			       {
						 product_id=c3.getString(1);
						 qty=c3.getString(2);
						 price=c3.getString(3);
						 uname=c3.getString(4);
						 
						 
						 send_str+=product_id+'`'+price+'`'+qty+',';
							
			       }
				 }
					 new DoOrder().execute();
					 db.execSQL("delete from cart");
					 db.execSQL("delete from Budget");
					 MainActivity.btn_set_budget.setText("Set Budget");
					 Cursor c1=db.rawQuery("select * from cart",null);
					 if (c1.getCount()==0)
					 {
						 cp1.clear();
						 CustomCartAdapter cpa1 = new CustomCartAdapter(cp1,mycart.this);
					     l1.setAdapter(cpa1);
					     search_product_view.ctotal=0;
					 }
					 t1.setText("0");
					
					 new Fill_Order_OrderDetails().execute();
					
				
			}
						 
		});
		
		
		Cursor c1=db.rawQuery("select cart.product_id,Product.product_name,Product.image,cart.qty,cart.price,cart.cart_id,Product.price from cart,Product where cart.product_id=Product.product_id",null);
		
		 if (c1.getCount()!=0)
		 {
			  Log.d("dis",c1.toString());
			 cp1.clear();
			 while(c1.moveToNext())
			 {
				 CartProduct cp2=new CartProduct();
				 cp2.prd_id=c1.getString(0);
	    	     cp2.prd_name=c1.getString(1);
	    	     cp2.prd_img=c1.getString(2);
	    	     cp2.prd_qty=c1.getString(3);
	    	     cp2.prd_price=c1.getString(4);
	    	     cp2.cart_id=c1.getString(5);
	    	     if(search_product_view.dis_st.equals("yes"))
	    	     {
	    	     Cursor c2=db.rawQuery("select dis_per from Scheme where product_id='"+cp2.prd_id+"' ",null);
	    	     if(c2.getCount()!=0)
	    	     {
	    	    	 c2.moveToNext();
	    	    	 cp2.prd_org_price=c1.getString(6);
	    	    	 cp2.prd_dis_per=c2.getString(0);
	    	     }
	    	     }
	    	     else
	    	     {
	    	    	 cp2.prd_org_price=null;
	    	    	 cp2.prd_dis_per=null;
	    	    	 
	    	     }
	    	     
	    	    // Log.d("dis",p2.pname);
	    	     cp1.add(cp2);
	    	 //    cart_total+=Integer.parseInt((cp2.prd_qty))*Integer.parseInt((cp2.prd_price));
	    	  //   search_product_view.total=search_product_view.total+(Integer.parseInt(cp2.prd_qty)*(Integer.parseInt(cp2.prd_price)));
	    	  //   t1.setText(search_product_view.ctotal.toString());
	    	     //  price=c3.getString(3);
	       }
			   t1.setText(String.valueOf(search_product_view.ctotal));
			 CustomCartAdapter cpa1 = new CustomCartAdapter(cp1,mycart.this);
		        l1.setAdapter(cpa1);
		  l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				String prd_name = cp1.get(arg2).prd_name;
				String cid=cp1.get(arg2).cart_id;
				String qty=cp1.get(arg2).prd_qty;
				String price=cp1.get(arg2).prd_price;
				//Log.d("prd_name",prd_name);
				//Log.d("c_id",cid);
				Bundle extras=new Bundle();
				extras.putString("cid",cid );
				extras.putString("qty",qty );
				extras.putString("price",price );
				Cursor c1=db.rawQuery("select product_id from Product where product_name='"+prd_name+"'",null);
				if(c1.getCount()!=0)
				{
					c1.moveToNext();
					String id=c1.getString(0);
					extras.putString("pid",id);
					extras.putString("url","mycart");
					//Log.d("pid",id);
					Intent i1=new Intent(mycart.this,search_product_view.class).putExtras(extras);
					startActivity(i1);
					
				}
			}
		});
		  
		l1.setLongClickable(true);
		l1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
			//	Toast.makeText(selectlist.this,arg2,5000).show();
				// TODO Auto-generated method stub
				Log.d("long clicked","pos: " + arg2);
				
				AlertDialog.Builder alert=new AlertDialog.Builder(mycart.this);
				alert.setTitle("Remove from cart ?");
				alert.setPositiveButton("Remove",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						Toast.makeText(mycart.this,cp1.get(arg2).cart_id, 3000).show();
						
						db.execSQL("DELETE FROM cart WHERE cart_id="+cp1.get(arg2).cart_id+"");
						String prd_price=cp1.get(arg2).prd_price;
						String prd_qty=cp1.get(arg2).prd_qty;
						int min=Integer.parseInt(prd_price)*Integer.parseInt(prd_qty);
						search_product_view.ctotal=search_product_view.ctotal-min;
						Intent i1=new Intent(mycart.this,mycart.class);
						startActivity(i1);
						
					}
				});
				alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				alert.show();
	            return true;
				
			}
		});


	}

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



	class DoOrder extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		
		
		protected String doInBackground(Void... params) 
		{
			try
			{			
			SoapObject request = new SoapObject("http://tempuri.org/",METHOD_NAME);
				request.addProperty("send_str",send_str);
				request.addProperty("user_name",uname);
				Log.d("send str",send_str);
				Log.d("user name",uname);
		        
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
			pdia = new ProgressDialog(mycart.this);
			pdia.setCanceledOnTouchOutside(false);
	        pdia.setMessage("inserting...");
	        pdia.show(); 
		}
		
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);				
			pdia.dismiss();	
			if(result.equals("true"))
			{
				Toast.makeText(mycart.this,"insert",5000).show();	
			}
			
			
			
		}
	}

}
class CartProduct
{
	String prd_id;
	String prd_name;
	String prd_qty;
	String prd_img;
	String prd_price;
	String prd_org_price;
	String prd_dis_per;
	String cart_id;
}
