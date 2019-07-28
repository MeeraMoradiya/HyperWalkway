package com.example.hyperwalkway4;

import java.util.ArrayList;

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

import android.text.StaticLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class makelist extends Activity {
	
	int flag=0;
	ListView l1;
	Button b1,b2,btn_back3;
	Spinner s1,s2,s3;
	SQLiteDatabase db;
	public static String cat="";
	public static String sub_cat="";
	public static String brand_name="";
	ArrayList<String> a1=new ArrayList<String>();
	ArrayList<String> a2=new ArrayList<String>();
	ArrayList<String> a3=new ArrayList<String>();
	public static ArrayList<String> a4=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.makelist);
		db=openOrCreateDatabase("Myshopping1.db",MODE_PRIVATE, null);
		b1=(Button)findViewById(R.id.add1);
		//b2=(Button)findViewById(R.id.sync);
		btn_back3=(Button)findViewById(R.id.btn_back3);
		s1=(Spinner)findViewById(R.id.category);
		s2=(Spinner)findViewById(R.id.brand);
		s3=(Spinner)findViewById(R.id.product);
		
		 btn_back3.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				finish();
				}
			});
		 Cursor c1=db.rawQuery("select * from Category",null);
		 if (c1.getCount()!=0)
		 {
			 a1.clear();
			 while(c1.moveToNext())
	       {
	    	   a1.add(c1.getString(1));
	       }
	 	  final ArrayAdapter<String> ar1=new ArrayAdapter<String>(makelist.this,android.R.layout.simple_expandable_list_item_1,a1);
	       s1.setAdapter(ar1);
        
        b1.setOnClickListener(new View.OnClickListener() {

			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cat=s1.getSelectedItem().toString();
				sub_cat=s2.getSelectedItem().toString();
				brand_name=s3.getSelectedItem().toString();
				//cat=s1.getSelectedItem().toString();
				Intent i1=new Intent(makelist.this,view.class);
				startActivity(i1);
			}
		});
       

        s1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String cat=s1.getItemAtPosition(position).toString();
				Cursor c2=db.rawQuery("Select * from Category , SubCategory where SubCategory.category_id=Category.category_id and Category.category_name='" + cat + "'",null);
				 if (c2.getCount()!=0)
				 {
					 a2.clear();
					 while(c2.moveToNext())
			       {
			    	   a2.add(c2.getString(4));
			       }
			 	  final ArrayAdapter<String> ar2=new ArrayAdapter<String>(makelist.this,android.R.layout.simple_expandable_list_item_1,a2);
			       s2.setAdapter(ar2);
				 }	
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			});
           s2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String sub_cat=s2.getItemAtPosition(position).toString();
					 Cursor c3=db.rawQuery("Select * from SubCategory,Brand where SubCategory.sub_id=Brand.sub_id and SubCategory.sub_name='" + sub_cat + "'",null);
					 if (c3.getCount()!=0)
					 {
						 a3.clear();
						 while(c3.moveToNext())
				       {
				    	   a3.add(c3.getString(4));
				       }
				 	  final ArrayAdapter<String> ar3=new ArrayAdapter<String>(makelist.this,android.R.layout.simple_expandable_list_item_1,a3);
				       s3.setAdapter(ar3);	
					 }	
				 				
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			});
        s3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			//
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			});
       


			
			
		 }
	}
}
   





		 



