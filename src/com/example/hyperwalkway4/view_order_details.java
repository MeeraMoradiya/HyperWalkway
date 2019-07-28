package com.example.hyperwalkway4;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

public class view_order_details extends Activity 
{
	ListView l1;
	Button btn_back9;
	SQLiteDatabase db;
	String order_id,order_date;
	ArrayList<OrderProduct> cp1=new ArrayList<OrderProduct>();
	Bundle extras;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_order_details);
		l1=(ListView)findViewById(R.id.lst_order_details);
		btn_back9=(Button)findViewById(R.id.btn_back9);
		Intent i1=getIntent();
		if(i1!=null)
		{
			Bundle extras=i1.getExtras();
			  order_id=extras.getString("order_id");
			  order_date=extras.getString("order_date");
			//  Log.d("order_id",order_id);
			 // Log.d("order_date",order_date);
		//	 Toast.makeText(getBaseContext(), order_id, 5000).show();
			 // Toast.makeText(getBaseContext(), order_date, 5000).show();
		}
			
		db=openOrCreateDatabase("Myshopping1.db", MODE_PRIVATE,null);
		Cursor c1=db.rawQuery("select od.*,p.product_name,p.image,od.price from OrderDetails as od,Product as p where od.product_id=p.product_id and od.order_id='"+order_id+"'",null);
		Log.d("getcount",String.valueOf(c1.getCount()));
		btn_back9.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		if(c1.getCount()!=0)
		{
			cp1.clear();
			 while(c1.moveToNext())
			 {
				 OrderProduct cp2=new OrderProduct();
				 cp2.prd_id=c1.getString(1);
	    	     cp2.prd_name=c1.getString(4);
	    	     cp2.prd_img=c1.getString(5);
	    	     cp2.prd_qty=c1.getString(2);
	    	     cp2.prd_price=c1.getString(3);
	    	    
	    	    // Log.d("dis",p2.pname);
	    	     cp1.add(cp2);
	    	  //   search_product_view.total=search_product_view.total+(Integer.parseInt(cp2.prd_qty)*(Integer.parseInt(cp2.prd_price)));
	    	    
	    	     //  price=c3.getString(3);
	       }
		}
		CustomCartAdapter cpa1 = new CustomCartAdapter(cp1,view_order_details.this);
        l1.setAdapter(cpa1);
		l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String prd_id=cp1.get(arg2).prd_id.toString();
				
				Intent i1=new Intent(view_order_details.this,my_expances_product_view.class).putExtra(Intent.EXTRA_TEXT, prd_id);
				startActivity(i1);
			}
		});
		l1.setLongClickable(true);
	       l1.setOnItemLongClickListener(new OnItemLongClickListener() {

	   		@Override
	   		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
	   				final int arg2, long arg3) {
	   		//	Toast.makeText(selectlist.this,arg2,5000).show();
	   			// TODO Auto-generated method stub
	   			String brand_name=null;
	   			String prd_id=cp1.get(arg2).prd_id.toString();
	   			Cursor c1=db.rawQuery("select b.brand_name from Product as p,Brand as b where b.brand_id=p.brand_id and p.product_id='"+prd_id+"'",null);
	   			if(c1.getCount()!=0)
				{
					c1.moveToNext();
					brand_name=c1.getString(0);
				}
	   			String prd_name=cp1.get(arg2).prd_name.toString();
	   			
	   			String prd_price=cp1.get(arg2).prd_price.toString();
	   			String prd_qty=cp1.get(arg2).prd_qty.toString();
	   			extras=new Bundle();
	   			extras.putString("prd_id",prd_id);
				extras.putString("prd_name",prd_name);
				extras.putString("brand_name",brand_name);
				extras.putString("prd_price",prd_price);
				extras.putString("date",order_date);
				extras.putString("prd_qty",prd_qty);
	  	   		AlertDialog.Builder alert=new AlertDialog.Builder(view_order_details.this);
	   			alert.setTitle("Rate the Product?");
	   			alert.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
	   				
	   				@Override
	   				public void onClick(DialogInterface dialog, int which) {
	   					// TODO Auto-generated method stub
	   					
	   					//Toast.makeText(display.this, x, 3000).show();
	   					Intent i1=new Intent(view_order_details.this,rate.class).putExtras(extras);
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
class OrderProduct
{
	String prd_id;
	String prd_name;
	String prd_qty;
	String prd_img;
	String prd_price;
	
}

