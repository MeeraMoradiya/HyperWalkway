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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class selectlist extends Activity {
	Button b1,btn_back1;
	ListView l1;
	SQLiteDatabase db;
	public static String tablename="";
	 ArrayList<String> a1=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.selectlist);
		 db=openOrCreateDatabase("Myshopping1.db",MODE_PRIVATE, null);
		 db.execSQL("CREATE TABLE IF NOT EXISTS lists(item)");	
		 b1=(Button)findViewById(R.id.newl);
		 btn_back1=(Button)findViewById(R.id.btn_back1);
		 l1=(ListView)findViewById(R.id.lst_bills);
		 btn_back1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();
			}
		});
		 b1.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
				AlertDialog.Builder alert=new AlertDialog.Builder(selectlist.this);
				alert.setTitle("Give Name");
				final EditText input=new EditText(selectlist.this);
				alert.setView(input);
				alert.setPositiveButton("Save",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String in=input.getText().toString();
						a1.add(in);
						db.execSQL("INSERT INTO lists VALUES('"+in+"')");
					//	db.execSQL("CREATE TABLE IF NOT EXISTS '"+in+"'(item)");
				    	final ArrayAdapter<String> ar1=new ArrayAdapter<String>(selectlist.this,android.R.layout.simple_expandable_list_item_1,a1);
						 l1.setAdapter(ar1);
				     	 ar1.notifyDataSetChanged();
						 l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					    	
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
								tablename=ar1.getItem(arg2).toString();
								db.execSQL("CREATE TABLE IF NOT EXISTS '"+tablename+"'(item)");
								String abc="";
								Intent i1=new Intent(selectlist.this,display.class).putExtra(Intent.EXTRA_TEXT,abc);
								startActivity(i1);
							}	
						});
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
		Cursor c1=db.rawQuery("select * from lists",null);
		if (c1.getCount()!=0)
		{
			a1.clear();
			 while(c1.moveToNext())
			 {
	    	   a1.add(c1.getString(0));
			 }
			 final ArrayAdapter<String> ar1=new ArrayAdapter<String>(selectlist.this,android.R.layout.simple_expandable_list_item_1,a1);
			 l1.setAdapter(ar1);
			 registerForContextMenu(l1);
			 ar1.notifyDataSetChanged();
			 l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    	
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					tablename=ar1.getItem(arg2).toString();
					db.execSQL("CREATE TABLE IF NOT EXISTS '"+tablename+"'(item)");
					String abc="";
					Intent i1=new Intent(selectlist.this,display.class).putExtra(Intent.EXTRA_TEXT,abc);
					startActivity(i1);
					
				}	
			});
			 l1.setLongClickable(true);
		}
	l1.setOnItemLongClickListener(new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				final int arg2, long arg3) {
		//	Toast.makeText(selectlist.this,arg2,5000).show();
			// TODO Auto-generated method stub
			Log.d("long clicked","pos: " + arg2);
			
			AlertDialog.Builder alert=new AlertDialog.Builder(selectlist.this);
			alert.setTitle("Delete?");
			alert.setPositiveButton("Delete",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					//Toast.makeText(display.this, x, 3000).show();
					db.execSQL("DELETE FROM lists WHERE item='"+a1.get(arg2).toString()+"'");
					Intent i1=new Intent(selectlist.this,selectlist.class);
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
	  /*  l1.setOnItemLongClickListener(new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                int pos, long id) {
            // TODO Auto-generated method stub
/*

here pos is position on which item is clicked so that you can get arraylist item from position 

*//*
Log.d("long clicked","pos: " + pos);

            return true;
        }
    });*/ 
/*

add listview property android:longClickable="true" in xml file.

*/
	
	}
}

	


