package com.example.hyperwalkway4;

import java.util.ArrayList;
import java.util.Locale;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class searchproduct extends Activity {
	Button b1,btn_back5;
	ImageButton img_btn_search;
	EditText e1;
	ListView l1;
	boolean flag=false;
	SQLiteDatabase db;
	String pname,pid;
	 private final int REQ_CODE_SPEECH_INPUT = 100;
	ArrayList<SearchPrd> a1=new ArrayList<SearchPrd>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.searchproduct);
		b1=(Button)findViewById(R.id.btn_search);
		
		btn_back5=(Button)findViewById(R.id.btn_back5);
		img_btn_search=(ImageButton)findViewById(R.id.img_btn_search);
		e1=(EditText)findViewById(R.id.search);
		l1=(ListView)findViewById(R.id.lst_bills);
		db =openOrCreateDatabase("Myshopping1.db",MODE_PRIVATE, null);

		
		img_btn_search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				promptSpeechInput();
			}
		});
		 
		btn_back5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		
		//String str="s";
		Intent i1=getIntent();
		if(i1!=null && i1.hasExtra(Intent.EXTRA_TEXT))
			e1.setText(i1.getStringExtra(Intent.EXTRA_TEXT));
		
		Cursor c1=db.rawQuery("select * from Product order by product_name asc",null);
		 if (c1.getCount()!=0)
		 {
			 a1.clear();
			 while(c1.moveToNext())
	       {
				SearchPrd s1=new SearchPrd();
				s1.prd_name=c1.getString(1);
	    	   
	    	   Cursor c2=db.rawQuery("select * from Rate",null);
				 if(c2.getCount()!=0)
				 {
					 while(c2.moveToNext())
					 {
						 if(c1.getString(0).equals(c2.getString(0))==true)
						 {
							s1.rate= Float.parseFloat(c2.getString(1));
							flag=true;
							break;
						 }
					 }
					
				 }
				 if(flag==true)
				 {
	    	   a1.add(s1);
	    	   	flag=false;
				 }
				 else
				 {
					 s1.rate=Float.parseFloat("0.0");
					 a1.add(s1);
				 }
	       }
		 }
		 CustomAdapter_SearchPro cad1=new CustomAdapter_SearchPro(a1,searchproduct.this);
		 l1.setAdapter(cad1);
		/*  ArrayAdapter<String> ar1=new ArrayAdapter<String>(searchproduct.this,android.R.layout.simple_expandable_list_item_1,a1);
	      l1.setAdapter(ar1);*/
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str=e1.getText().toString();
				// Toast.makeText(searchproduct.this,"select * from Product where product_name like '%"+str+"%'",1).show();
				 Cursor c1=db.rawQuery("select * from Product where product_name like '%"+str+"%' order by product_name asc",null);
				 if (c1.getCount()!=0)
				 {
					 a1.clear();
					 while(c1.moveToNext())
			       {
						SearchPrd s1=new SearchPrd();
						s1.prd_name=c1.getString(1);
			    	   
			    	   Cursor c2=db.rawQuery("select * from Rate",null);
						 if(c2.getCount()!=0)
						 {
							 while(c2.moveToNext())
							 {
								 if(c1.getString(0).equals(c2.getString(0))==true)
								 {
									s1.rate= Float.parseFloat(c2.getString(1));
									flag=true;
									break;
								 }
							 }
							
						 }
						 if(flag==true)
						 {
			    	   a1.add(s1);
			    	   	flag=false;
						 }
						 else
						 {
							 s1.rate=Float.parseFloat("0.0");
							 a1.add(s1);
						 }
			      
				 }
				 }
				 else
				 {
					 a1.clear();
					 SearchPrd s1=new SearchPrd();
					 s1.prd_name="No data found";
					 s1.rate=Float.parseFloat("0.0");
					 a1.add(s1);
					 
				 }
				 CustomAdapter_SearchPro cad1=new CustomAdapter_SearchPro(a1,searchproduct.this);
				 l1.setAdapter(cad1);
				
			}
		});
		e1.addTextChangedListener(new TextWatcher()
		{
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String str=e1.getText().toString();
			//	 Toast.makeText(searchproduct.this,"select * from Product where product_name like '%"+str+"%'",1).show();
				 Cursor c1=db.rawQuery("select * from Product where product_name like '%"+str+"%'order by product_name asc",null);
				 if (c1.getCount()!=0)
				 {
					 a1.clear();
					 while(c1.moveToNext())
			       {
						SearchPrd s1=new SearchPrd();
						s1.prd_name=c1.getString(1);
			    	   
			    	   Cursor c2=db.rawQuery("select * from Rate",null);
						 if(c2.getCount()!=0)
						 {
							 while(c2.moveToNext())
							 {
								 if(c1.getString(0).equals(c2.getString(0))==true)
								 {
									s1.rate= Float.parseFloat(c2.getString(1));
									flag=true;
									break;
								 }
							 }
							
						 }
						 if(flag==true)
						 {
			    	   a1.add(s1);
			    	   	flag=false;
						 }
						 else
						 {
							 s1.rate=Float.parseFloat("0.0");
							 a1.add(s1);
						 }
			      
				 }
				 }
				 else
				 {
					 a1.clear();
					 SearchPrd s1=new SearchPrd();
					 s1.prd_name="No data found";
					 s1.rate=Float.parseFloat("0.0");
					 a1.add(s1);
					 
				 } 
				 CustomAdapter_SearchPro cad1=new CustomAdapter_SearchPro(a1,searchproduct.this);
				 l1.setAdapter(cad1);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		}); 
	
		l1.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
				// TODO Auto-generated method stub
			//	Toast.makeText(searchproduct.this,"hello",5000).show();
				pname=a1.get(arg2).prd_name;
			//	Toast.makeText(searchproduct.this,pname,5000).show();				 
				
				Cursor c1=db.rawQuery("select product_id from Product where product_name ='"+pname+"'",null);
				 if (c1.getCount()!=0)
				 {
					
					 while(c1.moveToNext())
			       {
			    	   pid=c1.getString(0);
			    	 //  Toast.makeText(searchproduct.this,pid,5000).show();
			    	   Intent i1=new Intent(searchproduct.this,cmbine.class).putExtra(Intent.EXTRA_TEXT,pid);
						startActivity(i1);
			       }
				 }
				 else
				 {
					  Toast.makeText(searchproduct.this,"No details found",5000).show();
					 
				 }
				
			}
		});
	
	}	
	
	public void promptSpeechInput() 
	{
		// TODO Auto-generated method stub
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak..");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a)
        {
            Toast.makeText(getApplicationContext(),
            		"Speak..",
                    Toast.LENGTH_SHORT).show();
        }
	}
	  @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) 
     {
          super.onActivityResult(requestCode, resultCode, data);
   
          switch (requestCode) {
          case REQ_CODE_SPEECH_INPUT: {
              if (resultCode == RESULT_OK && null != data) {
   
                  ArrayList<String> result = data
                          .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                  e1.setText(result.get(0));
              }
              break;
          }
   
          }
      }
   


}
class SearchPrd
{
	String prd_name;
	Float rate;
}