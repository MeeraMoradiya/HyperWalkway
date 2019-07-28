package com.example.hyperwalkway4;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class my_expances_product_view extends Activity 
{
	TextView t1,t2,t3,t4,t5,t6;
	ImageView i2;
	Button btn_back10;
	SQLiteDatabase db;
	String prd_id,str;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_expances_product_view);
		t1=(TextView)findViewById(R.id.t1);
		t2=(TextView)findViewById(R.id.t2);
		t3=(TextView)findViewById(R.id.t3);
		t4=(TextView)findViewById(R.id.t4);
		t5=(TextView)findViewById(R.id.t5);
		t6=(TextView)findViewById(R.id.t6);
		i2=(ImageView)findViewById(R.id.i1);
		btn_back10=(Button)findViewById(R.id.btn_back10);
		db=openOrCreateDatabase("Myshopping1.db", MODE_PRIVATE,null);
		btn_back10.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		Intent i1=getIntent();
		if(i1!=null && i1.hasExtra(Intent.EXTRA_TEXT))
			prd_id=i1.getStringExtra(Intent.EXTRA_TEXT);
		Cursor c1=db.rawQuery("select product_name,image,od.price,expiry_date,brand_name,sub_name from Product as p,OrderDetails as od,Brand as b,SubCategory as s where p.brand_id=b.brand_id and p.sub_id=s.sub_id and p.product_id=od.product_id and p.product_id='"+prd_id+"'", null);
		if(c1.getCount()!=0)
		{
			 while(c1.moveToNext())
		       {
					 str="Name:"+c1.getString(0);
					 t2.setText(str);
					 str="Category:"+c1.getString(5);
					 t3.setText(str);
					 str="Brand:"+c1.getString(4);
					 t4.setText(str);
					 str="Price:"+c1.getString(2);
					 t5.setText(str);
					 str="Expiry Date:"+c1.getString(3);
					 t6.setText(str);
					 Picasso.with(my_expances_product_view.this).load(MainActivity.imagestr + c1.getString(1)).placeholder(R.drawable.ic_launcher).into(i2);
		    	  
		       }		
		}
	}
}
