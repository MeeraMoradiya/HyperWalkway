package com.example.hyperwalkway4;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class view extends Activity {
	ListView l1;
	 Button btn_back4;
	public static String SOAP_ACTION = "http://tempuri.org/product_details";
	String METHOD_NAME = "product_details";
	String ListviewItem="";
	SQLiteDatabase db;
	String name,price;
	ArrayList<product> p1 = new ArrayList<product>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view);
		l1=(ListView)findViewById(R.id.list1);
		btn_back4=(Button)findViewById(R.id.btn_back4);
		db=openOrCreateDatabase("Myshopping1.db",MODE_PRIVATE, null);
		btn_back4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();
			}
		});
				 Cursor c3=db.rawQuery("select * from Product as product, SubCategory,Brand,Category where product.brand_id=Brand.brand_id and SubCategory.sub_id=product.sub_id and Category.category_id=SubCategory.category_id and Category.category_name='" + makelist.cat + "' and SubCategory.sub_name='" + makelist.sub_cat + "'and Brand.brand_name='" + makelist.brand_name + "'",null);
				 if (c3.getCount()!=0)
				 {
					  Log.d("dis",c3.toString());
					 p1.clear();
					 while(c3.moveToNext())
					 {
						 product p2=new product();
						 
			    	     p2.pname=c3.getString(1);
			    	     p2.pimage=c3.getString(2);
			    	     p2.price=c3.getString(3);
			    	     Log.d("dis",p2.pname);
			    	     p1.add(p2);
			    	  
			       }
				        Customadapter c1 = new Customadapter(p1,view.this);
				        l1.setAdapter(c1);
				 }
	
		l1.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
		
				String prd_name=p1.get(arg2).pname;
					 Cursor c3=db.rawQuery("select * from Product as product, SubCategory,Brand,Category where product.brand_id=Brand.brand_id and SubCategory.sub_id=product.sub_id and Category.category_id=SubCategory.category_id and Category.category_name='" + makelist.cat + "' and SubCategory.sub_name='" + makelist.sub_cat + "'and Brand.brand_name='" + makelist.brand_name +"' and Product.product_name='"+prd_name+"' ",null);
					 if (c3.getCount()!=0)
					 {
						while(c3.moveToNext())
						{
							name=c3.getString(1);
						    price=c3.getString(3);
						}

				 	  //final ArrayAdapter<String> ar5=new ArrayAdapter<String>(view.this,android.R.layout.simple_expandable_list_item_1,a5);
				     // l1.setAdapter(ar5);	
					 }	

					 AlertDialog.Builder alert=new AlertDialog.Builder(view.this);
						alert.setTitle("Add To List??");
						
				  /*  	LinearLayout l2 = new LinearLayout(view.this);
				    	l2.setOrientation(1);
				    	
				    		final TextView input=new TextView(view.this);
				    		final TextView input1=new TextView(view.this);
				    		
				    		l2.addView(input)	;
				    		l2.addView(input1)	;
						input1.setTextColor(Color.WHITE);
						input.setTextColor(Color.WHITE);
						
						input.setText("Name:"+name);
	
						input1.setText("Price:"+price);
						alert.setView(l2);*/
						
						alert.setPositiveButton("Add",new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent i1=new Intent(view.this,display.class).putExtra(Intent.EXTRA_TEXT,name);
								startActivity(i1);
								
							//	Toast.makeText(selectlist.this, in, 3000).show();
								
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
		});
			}
	
}
class product 
{
String pname;
String pimage;
String price;
}

