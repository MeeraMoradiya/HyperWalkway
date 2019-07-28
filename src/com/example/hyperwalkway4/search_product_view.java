package com.example.hyperwalkway4;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas.VertexMode;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class search_product_view extends Activity
{
	TextView t2,t3,t4,t5,t6;
	ImageView i1;
	EditText e1;
	RatingBar r1;
	Button b1,btn_plus,btn_minus,btn_back6;
	String pid=null,expiry_date,cid=null,prd_name,prd_cat,prd_brand,dis_price;
	SQLiteDatabase db;
	public static int budget=0;
	int per;
	Integer p=0,qty=0;
	String price,start_date,end_date;
	public static String dis_st="no";
	 public static Integer total=0,try_total=0;
	 public static Integer ctotal=0;
	 ArrayList<String> arr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_product_view);
		
		t2=(TextView)findViewById(R.id.t2);
		t3=(TextView)findViewById(R.id.t3);
		t4=(TextView)findViewById(R.id.t4);
		t5=(TextView)findViewById(R.id.t5);
		t6=(TextView)findViewById(R.id.t6);
		i1=(ImageView)findViewById(R.id.i1);
		b1=(Button)findViewById(R.id.b1);
		btn_plus=(Button)findViewById(R.id.btn_plus);
		btn_minus=(Button)findViewById(R.id.btn_minus);
		btn_back6=(Button)findViewById(R.id.btn_back6);
		e1=(EditText)findViewById(R.id.e1);
		r1=(RatingBar)findViewById(R.id.rbar_product);
		
		db=openOrCreateDatabase("Myshopping1.db",MODE_PRIVATE, null);
		
		i1.setVisibility(View.INVISIBLE);
		e1.setText("1");
		r1.setEnabled(false);
		
		Intent i2=getIntent();
		Bundle extras=i2.getExtras();
		if(i2!=null &&(extras.getString("url").equals("barcode_scan")==true))
		{
			Toast.makeText(getBaseContext(), "true", 2000).show();
		
			pid=extras.getString("prd_id");
			prd_name=extras.getString("prd_name");
			prd_cat=extras.getString("prd_cat");
			prd_brand=extras.getString("prd_brand");
			price=extras.getString("prd_price");
			expiry_date=extras.getString("prd_exp");
		}
		else
		{
			Toast.makeText(getBaseContext(), "false", 2000).show();
		
			i1.setVisibility(View.VISIBLE);
		
			  pid=extras.getString("pid");
			  cid=extras.getString("cid");
			 qty=Integer.parseInt(extras.getString("qty"));
			 price=extras.getString("price");
			 e1.setText(qty.toString());
			  Log.d("pid",pid);
			  Log.d("qty",qty.toString());
			  Log.d("cid",cid);
		//	 Toast.makeText(getBaseContext(), pid, 2000).show();
		//	 Toast.makeText(getBaseContext(), cid, 2000).show();
		}
		// Toast.makeText(getBaseContext(),ctotal, 2000).show();
		
	/*	Intent i2=getIntent();
		
		 if(i2!=null && i2.hasExtra(Intent.EXTRA_TEXT))
		 pid=i2.getStringExtra(Intent.EXTRA_TEXT);
		 else
		 {
			 Bundle extras=i2.getExtras();
			  pid=extras.getString("pid");
			  cid=extras.getString("cid");
			 qty=Integer.parseInt(extras.getString("qty"));
			 e1.setText(qty.toString());
			  Log.d("pid",pid);
			  Log.d("qty",qty.toString());
			  Log.d("cid",cid);
			 Toast.makeText(getBaseContext(), pid, 2000).show();
			 Toast.makeText(getBaseContext(), cid, 2000).show();
		 }
			*/
		
		Cursor c2=db.rawQuery("select rate from Rate where product_id="+pid+"",null);
			 if(c2.getCount()!=0)
			 {
				 c2.moveToNext();
				 r1.setRating(Float.parseFloat(c2.getString(0)));
			 }
			 else
			 {
				 r1.setVisibility(View.INVISIBLE);
			 }
			 
			 
			
			 
			 if(cid==null)
			 {
				 String str="";
				 str="Name:"+prd_name;
				 t2.setText(str);
				 str="Category:"+prd_cat;
				 t3.setText(str);
				 str="Brand:"+prd_brand;
				 t4.setText(str);
				 str="Price:"+price;
				 t5.setText(str);
				 str="Expiry Date:"+expiry_date;
				 t6.setText(str);
			 }
			 else
			 {
				 Cursor c1=db.rawQuery("select product_name,image,price,expiry_date,brand_name,sub_name from Product as p,Brand as b,SubCategory as s where p.brand_id=b.brand_id and p.sub_id=s.sub_id and product_id='"+pid+"'",null);
				 String str="";
				 
				 if (c1.getCount()!=0)
				 {
					// Toast.makeText(getBaseContext(),c1.getCount(), 2000).show();
					 while(c1.moveToNext())
			       {
						 
						 str="Name:"+c1.getString(0);
						 t2.setText(str);
						 str="Category:"+c1.getString(5);
						 t3.setText(str);
						 str="Brand:"+c1.getString(4);
						 t4.setText(str);
						 str="Price:"+price;
						 t5.setText(str);
						 str="Expiry Date:"+c1.getString(3);
						 t6.setText(str);
						 Picasso.with(search_product_view.this).load(MainActivity.imagestr + c1.getString(1)).placeholder(R.drawable.ic_launcher).into(i1);
			    	  
			       }
				 }
			 }
			 	 
		 //Toast.makeText(getBaseContext(), pid, 2000).show();
		if(cid!=null)
		{
			String qr1="select price,expiry_date from Product where product_id='"+pid+"'";
			Cursor c3=db.rawQuery(qr1, null);
			 if (c3.getCount()!=0)
			 {
				
				 while(c3.moveToNext())
		       {
				//	 price=c3.getString(0);
					 expiry_date=c3.getString(1);
		       }
			 }
			 
		}
		
		if(cid==null)
		{
			 Calendar cal=Calendar.getInstance();
			 Calendar cal1=Calendar.getInstance();
			 Calendar cal2=Calendar.getInstance();
			 int dd=cal.get(Calendar.DATE);
			 int mm=cal.get(Calendar.MONTH);
			 int yy=cal.get(Calendar.YEAR);
			java.util.Date current_date;
			 SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
			 String status="";
			 
			 
			Cursor c6=db.rawQuery("select dis_per,start_date,end_date,status from Scheme where product_id="+pid+"",null);
			 if(c6.getCount()!=0)
			 {
				c6.moveToNext();
					per=Integer.parseInt(c6.getString(0));
					 start_date=c6.getString(1);
					 end_date=c6.getString(2);	
					 Log.d("start",start_date);
					 Log.d("end",end_date);
						//Log.d("days",per);
					 status=c6.getString(3);
				 if(status.equals("on"))
				 {
					 Toast.makeText(getBaseContext(), status, 5000).show();
					
				 try 
				 {
					java.util.Date f_st_dt=df.parse(start_date);
					java.util.Date f_end_dt=df.parse(end_date);
					current_date=df.parse(dd+"/"+(mm+1)+"/"+yy);
					
					Log.d("current_date",String.valueOf(current_date));
					cal.setTime(f_st_dt);
					cal1.setTime(current_date);
					cal2.setTime(f_end_dt);
					long diff=cal1.getTimeInMillis()-cal.getTimeInMillis();
					long days=diff/(24*60*60*1000);
					Log.d("days",String.valueOf(days));
					if(days>=0)
					{
						// Toast.makeText(getBaseContext(), String.valueOf(diff), 2000).show();
						diff=cal2.getTimeInMillis()-cal1.getTimeInMillis();
						days=diff/(24*60*60*1000);
						Log.d("days_2",String.valueOf(days));
						if(days>=0)
						{
							 //Toast.makeText(getBaseContext(), String.valueOf(diff), 2000).show();
							
							  dis_st="yes";
							  price=String.valueOf((Integer.parseInt(price))-((per*Integer.parseInt(price))/100));
							 AlertDialog.Builder alert=new AlertDialog.Builder(search_product_view.this);
							 String str1="Getting "+per+"% discount..."+" Discounted Price"+price;
								alert.setTitle(str1);
								/*LinearLayout l2=new LinearLayout(search_product_view.this);
								l2.setOrientation(1);
								TextView at1=new TextView(search_product_view.this);
							 	l2.addView(at1);
								
								
								
								at1.setTextColor(Color.WHITE);
								at1.setText("Discounted Price"+price);
								*/

							
								alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
									
									}
								});
								alert.show();

						}
					}
					//Toast.makeText(getBaseContext(),String.valueOf(current_date), 10000).show();
					
				 }
				 catch(ParseException e)
				 {
					 Log.d("date",String.valueOf(e.getMessage()));
				 }
				 } 			 
			}
			 

		}
		 Calendar cal=Calendar.getInstance();
		 Calendar cal1=Calendar.getInstance();
		 int dd=cal.get(Calendar.DATE);
		 int mm=cal.get(Calendar.MONTH);
		 int yy=cal.get(Calendar.YEAR);
		 
		 java.util.Date current_date;
		 SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		 try 
		 {
			java.util.Date f_ex_dt=df.parse(expiry_date);
			current_date=df.parse(dd+"/"+(mm+1)+"/"+yy);
			cal.setTime(f_ex_dt);
			cal1.setTime(current_date);
			//Toast.makeText(getBaseContext(),String.valueOf(current_date), 10000).show();
			long diff=cal.getTimeInMillis()-cal1.getTimeInMillis();
			long days=diff/(24*60*60*1000);
			//Toast.makeText(getBaseContext(),String.valueOf(days), 10000).show();
			
		 if(days>0 && 7>=days)
			{
			 AlertDialog.Builder alert=new AlertDialog.Builder(search_product_view.this);
			 	String str1="Product will expire in "+days+" days";
				alert.setTitle(str1);
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				alert.show();
			}
		 if(days<=0)
		 {
			 AlertDialog.Builder alert=new AlertDialog.Builder(search_product_view.this);
				alert.setTitle("Product expired");
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				alert.show();
		 }
		 }
		 
		 catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		btn_back6.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
	 btn_plus.setOnClickListener(new View.OnClickListener()
		 {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int qty=Integer.parseInt(e1.getText().toString())+1;
				e1.setText(String.valueOf(qty));
			}
		});
		 btn_minus.setOnClickListener(new View.OnClickListener()
		 {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int qty=Integer.parseInt(e1.getText().toString())-1;
				if(qty!=-1)
				e1.setText(String.valueOf(qty));
			}
		});
		b1.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				String qr1;
				Cursor c3;
				if(cid!=null)
				{
					qr1="select price,expiry_date from Product where product_id='"+pid+"'";
					 c3=db.rawQuery(qr1, null);
					 if (c3.getCount()!=0)
					 {
					
						 while(c3.moveToNext())
				       {
							// price=c3.getString(0);
							 expiry_date=c3.getString(1);
				       }
					 }
				}
				
				 
				// Toast.makeText(getBaseContext(), expiry_date, 5000).show();
				 
				 
					// Toast.makeText(getBaseContext(), String.valueOf(dd), 5000).show();
				 
				  qr1="select budget from Budget";
				 c3=db.rawQuery(qr1, null);
					 if (c3.getCount()!=0)
					 {
					
						 while(c3.moveToNext())
				       {
							 budget=Integer.parseInt(c3.getString(0));
							 
				       }
					 }
				 p=Integer.parseInt(price);
				 ctotal=ctotal+(p*Integer.parseInt(e1.getText().toString()));
				 Log.d("ctotal",ctotal.toString());
				//Toast.makeText(search_product_view.this,String.valueOf((MainActivity.budget)),5000).show();
				// TODO Auto-generated method stub
				 if(budget!=0)
				 {
					 if(ctotal.intValue()>budget)
						{
							//Toast.makeText(search_product_view.this,ctotal,5000).show();
						 	try_total=ctotal-(p*Integer.parseInt(e1.getText().toString()));
						 	try_total=budget-try_total;
							AlertDialog.Builder alert=new AlertDialog.Builder(search_product_view.this);
							alert.setTitle("Out Of Budget...");
							LinearLayout l2=new LinearLayout(search_product_view.this);
							l2.setOrientation(1);
							TextView at1=new TextView(search_product_view.this);
							TextView at2=new TextView(search_product_view.this);
						//	TextView at3=new TextView(search_product_view.this);
							TextView at4=new TextView(search_product_view.this);
							
							
							ListView lv1=new ListView(getBaseContext());
							arr=new ArrayList<String>();
							
							Cursor c4=db.rawQuery("select sub_id from Product where product_id='"+pid+"'", null);
							if(c4.getCount()!=0)
							{
							
								while(c4.moveToNext())
								{
									String sub_id=c4.getString(0);
									Log.d("sub_id",sub_id);
									Log.d("try_total",try_total.toString());
									int i=0;
									Cursor c5=db.rawQuery("select product_name,price from Product where sub_id='"+sub_id+"' and (cast(price as int))<='"+try_total+"' order by (cast(price as int)) desc", null);
									if(c5.getCount()!=0)
									{
										arr.add("Try This");
										while(c5.moveToNext())
										{
											i++;
											if(i<=5)
											{
												String str=c5.getString(0)+" "+c5.getString(1)+"Rs.";
												arr.add(str);
											//	arr.add(c5.getString(0));
												Log.d("product_name",c5.getString(0));
											}
											
										}
									}
								}
							}
							
							ArrayAdapter<String> adp=new ArrayAdapter<String>(getBaseContext(),R.layout.custom_layout,R.id.lbl_rs, arr);
							
							lv1.setAdapter(adp);
							
							l2.addView(at1);
							l2.addView(at2);
							//l2.addView(at3);
							l2.addView(at4);
							l2.addView(lv1);
							
							at1.setTextColor(Color.WHITE);
							at2.setTextColor(Color.WHITE);
						//	at3.setTextColor(Color.WHITE);
							at4.setTextColor(Color.WHITE);
							
							at1.setText("Budget:"+budget);
							if(cid!=null)
							{
								total=ctotal-(p*qty);
								
								at2.setText("Total:"+total);
							}
							else
							{
								at2.setText("Total:"+ctotal);
							}
							
							//at3.setText("Try This");
							at4.setText("try total:"+try_total);
							
					/*		lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) 
								{
									// TODO Auto-generated method stub
									ctotal=ctotal-(p*Integer.parseInt(e1.getText().toString()));
									String prd_name=arr.get(arg2).toString();
									String qr1="select product_id from Product where product_name='"+prd_name+"' ";
									Cursor c3=db.rawQuery(qr1, null);
									 if (c3.getCount()!=0)
									 {
									
										 while(c3.moveToNext())
								       {
											 pid=c3.getString(0);
											 Intent i1=new Intent(getBaseContext(), search_product_view.class).putExtra(Intent.EXTRA_TEXT, pid);
											 startActivity(i1);
								       }
									 }
									
								}
							});
					*/		
							alert.setView(l2);
							alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									
									MainActivity.i++;
									//Toast.makeText(search_product_view.this,String.valueOf(MainActivity.i),5000).show();
									
									if(cid!=null)
									{
										ctotal=ctotal-(p*qty);
										ctotal=ctotal+(p*Integer.parseInt(e1.getText().toString()));
										 Log.d("ctotal",ctotal.toString());
										String qr="update cart set qty='"+Integer.parseInt(e1.getText().toString())+"' where cart_id='"+cid+"'";	
										db.execSQL(qr);
										Intent i1=new Intent(search_product_view.this,mycart.class);
										startActivity(i1);
									}
									else
									{
										String qr="insert into cart(cart_id,product_id,qty,price,user_name) values("+MainActivity.i+",'"+pid+"','"+Integer.parseInt(e1.getText().toString())+"','"+price+"','"+login.uname+"')";	
										db.execSQL(qr);
										Toast.makeText(getBaseContext(), "Added to the cart", 5000).show();
									}
									
									
									
								}
							});
							alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									ctotal=ctotal-(p*Integer.parseInt(e1.getText().toString()));
									//Toast.makeText(search_product_view.this,ctotal.toString(),5000).show();
								}
							});
							alert.show();
						}
					 else
					 {
							MainActivity.i++;
							//Toast.makeText(search_product_view.this,String.valueOf(MainActivity.i),5000).show();
							if(cid!=null)
							{
								ctotal=ctotal-(p*qty);
								String qr="update cart set qty='"+Integer.parseInt(e1.getText().toString())+"' where cart_id='"+cid+"'";	
								db.execSQL(qr);
								Intent i1=new Intent(search_product_view.this,mycart.class);
								startActivity(i1);
							}
							else
							{
								String qr="insert into cart(cart_id,product_id,qty,price,user_name) values("+MainActivity.i+",'"+pid+"','"+Integer.parseInt(e1.getText().toString())+"','"+price+"','"+login.uname+"')";	
								db.execSQL(qr);
								Toast.makeText(getBaseContext(), "Added to the cart", 5000).show();
							}
							
							
					 }
				 }
				 else
				 {
						MainActivity.i++;
						//Toast.makeText(search_product_view.this,String.valueOf(MainActivity.i),5000).show();
						if(cid!=null)
						{
							ctotal=ctotal-(p*qty);
							String qr="update cart set qty='"+Integer.parseInt(e1.getText().toString())+"' where cart_id='"+cid+"'";	
							db.execSQL(qr);
							Intent i1=new Intent(search_product_view.this,mycart.class);
							startActivity(i1);
						}
						else
						{
							String qr="insert into cart(cart_id,product_id,qty,price,user_name) values("+MainActivity.i+",'"+pid+"','"+Integer.parseInt(e1.getText().toString())+"','"+price+"','"+login.uname+"')";	
							db.execSQL(qr);
							Toast.makeText(getBaseContext(), "Added to the cart", 5000).show();
						}
						
						
				 }
				 
					
			
			}
			
		});
	}
}
