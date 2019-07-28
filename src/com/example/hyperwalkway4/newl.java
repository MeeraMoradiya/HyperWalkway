package com.example.hyperwalkway4;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class newl  extends Activity{
	 EditText e1;
	 Button b1;
	 SQLiteDatabase db;
	 public static String tname="";
	

	 
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.newl);
	        db=openOrCreateDatabase("MyShopping1.db",MODE_PRIVATE, null);
	        db.execSQL("CREATE TABLE IF NOT EXISTS lists(item)");
	        
	        e1=(EditText)findViewById(R.id.namel);
	        b1=(Button)findViewById(R.id.save);
	        
	       
	        
	        b1.setOnClickListener(new View.OnClickListener() {
				
			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
			           if(e1.getText().toString().equals("") || e1.getText().toString().contains("\n"))
						{
							Toast.makeText(newl.this,"Invalid list name",3000).show();
						}
						else
						{
							tname=e1.getText().toString();
							db.execSQL("INSERT INTO lists VALUES('"+newl.tname+"')");
							//Toast.makeText(newl.this,e1.getText().toString(), 3000).show();
						 // db.execSQL("INSERT INTO  lists VALUES('"+e1.getText().toString()+"')");
						 // db.execSQL("CREATE TABLE IF NOT EXISTS "+e1.getText().toString()+"(item)");
				           // tname=e1.getText().toString();
						  e1.setText("");
						Intent i1=new Intent(newl.this,selectlist.class);//.putExtra(Intent.EXTRA_TEXT,tname);
						startActivity(i1);
					    //   final ArrayAdapter<String> ar1=new ArrayAdapter<String>(create.this,android.R.layout.simple_expandable_list_item_1,a1);
						  
					      
					       }
				}
				
			});
				
			
				
	                 
	 
	      
	 }    
}
