package com.example.hyperwalkway4;

import java.util.ArrayList;





import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class display extends Activity {
	Button b1,b2,btn_back2;
	ListView l1;
	SQLiteDatabase db;
	String fstr="",x;
	ArrayList<String> a1=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		l1=(ListView)findViewById(R.id.productlist);
		b1=(Button)findViewById(R.id.addnew);
		btn_back2=(Button)findViewById(R.id.btn_back2);
		
		db=openOrCreateDatabase("Myshopping1.db",MODE_PRIVATE, null);
		 Intent i2=getIntent();
		 if(i2!=null && i2.hasExtra(Intent.EXTRA_TEXT))
			 fstr=i2.getStringExtra(Intent.EXTRA_TEXT);
		
	//	Toast.makeText(display.this,selectlist.tablename, 5000).show();
	//	Log.d("selectlist.Tablename",selectlist.tablename);
		 btn_back2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				finish();
				}
			});
		 b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent i3=new Intent(display.this,makelist.class).putExtra(Intent.EXTRA_TEXT,fstr);
				startActivity(i3);
				// TODO Auto-generated method stub
				
			}
		});
		
		 
		if(fstr.equals(""))
		{
			
		}
		else{
	
		//	 Toast.makeText(display.this,selectlist.tablename, 5000).show();
	    	 db.execSQL("INSERT INTO '"+selectlist.tablename+"' VALUES('"+fstr+"')");
		
		 }
		//db.execSQL("CREATE TABLE IF NOT EXISTS '"+selectlist.tablename+"'(item)");
		 Cursor c1=db.rawQuery("select * from '"+selectlist.tablename+"'",null);
		 if (c1.getCount()!=0)
		 {
         c1.moveToLast();
         a1.add(c1.getString(0));
	       while(c1.moveToPrevious())
	       {
	    	   a1.add(c1.getString(0));
	       }
		   final ArrayAdapter<String> ar1=new ArrayAdapter<String>(display.this,android.R.layout.simple_expandable_list_item_1,a1);
	       l1.setAdapter(ar1);
	       l1.setOnItemClickListener(new AdapterView.OnItemClickListener() 
			{
		    	
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					 
					//Toast.makeText(display.this,ar1.getItem(arg2).toString(),3000).show();
					AlertDialog.Builder alert=new AlertDialog.Builder(display.this);
					alert.setTitle("What?");
				//	alert.setView(l1);
				    x=ar1.getItem(arg2).toString();
					alert.setPositiveButton("Delete",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							//Toast.makeText(display.this, x, 3000).show();
							db.execSQL("DELETE FROM '"+selectlist.tablename+"' WHERE item='"+x+"'");
							Intent i1=new Intent(display.this,display.class).putExtra(Intent.EXTRA_TEXT,"");
							startActivity(i1);
						
							
						}
					});
					alert.setNegativeButton("Search",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent i1=new Intent(display.this,searchproduct.class).putExtra(Intent.EXTRA_TEXT, x);
							startActivity(i1);
						}
					});
					alert.show();
		    		
					
				}
			});
		 }
		 else
		 {
			// Toast.makeText(display.this,"no table", 5000).show();
		 }
		 }
	
	}
	


