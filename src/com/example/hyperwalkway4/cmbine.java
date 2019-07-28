package com.example.hyperwalkway4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.color;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class cmbine extends Activity 
{
	Button A[]= new Button[8];
TextView lbl_floor,msg;
	String src_bt_fid,dest_bt_fid,src_bt_bid,src_bt_rid,dest_bt_rid;
	int dest_bt_bid;
	String pid;
	SQLiteDatabase db;
	public static String NAMESPACE = "http://tempuri.org/";
	public static String  URL = "http://192.168.0.102/Hyperwalkway_webservice/WebService.asmx";
	public static String SOAP_ACTION3 = "http://tempuri.org/get_structure";
	public static String METHOD_NAME3 = "get_structure";
	public static ArrayList<BT> bt=new ArrayList<BT>();
    private BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.bluetooth_view);
    	 registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
    	A[0]=(Button)findViewById(R.id.btn_bt1);
    	A[1]=(Button)findViewById(R.id.btn_bt2);
    	A[2]=(Button)findViewById(R.id.btn_bt3);
    	A[3]=(Button)findViewById(R.id.btn_bt4);
    	A[4]=(Button)findViewById(R.id.btn_bt5);
    	A[5]=(Button)findViewById(R.id.btn_bt6);
    	A[6]=(Button)findViewById(R.id.btn_bt7);
    	A[7]=(Button)findViewById(R.id.btn_bt8);
		lbl_floor=(TextView)findViewById(R.id.lbl_floor);
		//msg=(TextView)findViewById(R.id.message1);
		
		db=openOrCreateDatabase("bluetooth.db",MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS Structure([product_id] [int] NULL,[floor_id] int NULL,[rack_id] int NULL,[block_id] int NULL)");
		 db.execSQL("CREATE TABLE IF NOT EXISTS Bluetooth([bt_id] [int] NULL,bt_name [varchar](50) NULL,[floor_id] int NULL,[block_id] int NULL)");
		 
		 Intent i1=getIntent();
			if(i1!=null && i1.hasExtra(Intent.EXTRA_TEXT))
				pid=i1.getStringExtra(Intent.EXTRA_TEXT);
		 new Fill_Structure().execute();
		 			
    }
    public void bt_search()
    {
    	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();    
    	if (!mBluetoothAdapter.isEnabled()) {
    
    		mBluetoothAdapter.enable();   
    	}
    	 BTAdapter.startDiscovery();
    			
		
		
    }
    class Fill_Structure extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try
			{
				SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME3);
		      //  request.addProperty("data", "yes");
		      
		             
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		        
		        androidHttpTransport.call(SOAP_ACTION3, envelope);
		        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
		        
		        Log.d("message","MEssage : "+resultstr.toString());
		        return resultstr.toString();
		   }
			catch(Exception e)
			{
				Log.e("error13", e.toString());
				return "fail,fail";
				
			}
				
			
			
		}
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdia = new ProgressDialog(cmbine.this);
			pdia.setCanceledOnTouchOutside(false);
	        pdia.setMessage("Loading...");
	        pdia.show(); 
		}
		
		
		@Override
		protected void onPostExecute(String result)
		{
			super.onPostExecute(result);    	    	
			
			
			pdia.dismiss();
			if(!(result.equals("false")))
			{
				
			
			String tbl[]=result.split(":");
			Log.d("structuretable",String.valueOf(tbl.length));
		    db.execSQL("delete from Structure");
		    db.execSQL("delete from Bluetooth");
		    
			for(int i =0;i<tbl.length;i++)
			{
				String row[]=tbl[i].split(",");
				for(int j=0;j<row.length;j++)
				{
					String clm[]=row[j].split("`");
					if(i==0)
					{
					String qr="insert into Structure values('"+clm[0]+"','"+clm[1]+"','"+clm[2]+"','"+clm[3]+"')";	
					db.execSQL(qr);
					}
					if(i==1)
					{
						String qr="insert into Bluetooth values('"+clm[0]+"','"+clm[1]+"','"+clm[2]+"','"+clm[3]+"')";	
	    				db.execSQL(qr);
					}
					
					
				}
			}
			Cursor c1=db.rawQuery("select floor_id,block_id,rack_id from Structure where product_id='"+pid+"'",null);
			if(c1.getCount()!=0)
			{
				c1.moveToNext();
				dest_bt_fid=c1.getString(0);
				
				dest_bt_bid=Integer.parseInt(c1.getString(1));
				for(int i=1;i<=A.length;i++)
				{
					if(dest_bt_bid==i)
					{
						A[i-1].setBackgroundColor(Color.GREEN);
	      				A[i-1].setText("Block"+i+" Product");
					}
				}
				/*if(dest_bt_bid.equals("1"))
	
				{
      				A[0].setBackgroundColor(Color.GREEN);
      				A[0].setText("Product");
 
				}
				else if(dest_bt_bid.equals("2"))
				{
   					A[1].setBackgroundColor(Color.GREEN);
   					A[0].setText("Product");
				}
				else if(dest_bt_bid.equals("3"))
				{
   					A[2].setBackgroundColor(Color.GREEN);
   					A[0].setText("Product");
				}
				else if(dest_bt_bid.equals("4"))
				{
   					A[3].setBackgroundColor(Color.GREEN);
   					A[0].setText("Product");
 
				}
				else if(dest_bt_bid.equals("5"))
				{
   					A[4].setBackgroundColor(Color.GREEN);
   					A[0].setText("Product");
 
				}
				else if(dest_bt_bid.equals("6"))
				{
   					A[5].setBackgroundColor(Color.GREEN);
   					A[0].setText("Product");
 
				}
				else if(dest_bt_bid.equals("7"))
				{
   					A[6].setBackgroundColor(Color.GREEN);
   					A[0].setText("Product");
 
				}
				else if(dest_bt_bid.equals("8"))
				{
   					A[7].setBackgroundColor(Color.GREEN);
   					A[0].setText("Product");
 
				}
			*/



				
				}

			bt_search();
		
			}
		}
	}


    private final BroadcastReceiver receiver = new BroadcastReceiver(){


        @Override
        public void onReceive(Context context, Intent intent) 
        {

            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
            	int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                BT b1=new BT();
                b1.name=name;
                b1.rssi=rssi;
                bt.add(b1);
                Collections.sort(bt,new MyBT());

           	 
                if(bt.size()>0)
                {
                	Cursor c2=db.rawQuery("select bt_name,floor_id from Bluetooth",null);
                	int i =0;
                	Log.d("bt.get(0).name", bt.get(0).name);
                	while(c2.moveToNext())
                	{
                		//Log.d("c2.getString(0)", c2.getString(0));
                			i++;
                			if(c2.getString(0).equals(bt.get(0).name))
                			{
                				if(c2.getString(1).equals(dest_bt_fid))
                				{
                				
                
                				Log.d("bt.get(0).name", bt.get(0).name+i);
                        		Log.d("c2.getString(0)", c2.getString(0));
                        		Cursor c3=db.rawQuery("select block_id from Bluetooth where bt_name='"+c2.getString(0)+"'",null);
                        		if(c3.getCount()!=0)
                        		{
                        			c3.moveToNext();
                        			showbutton(Integer.parseInt(c3.getString(0))-1);
                        			
                        		}
                        		//showbutton(i-1);

            					
                				}
                				else
                				{
                					clearallbutton();
                            		
                					lbl_floor.setText("Please Go to Floor no : "+dest_bt_fid);
                				}
                			}
                			else
                			{

            				
                			}
                	}
                }       	}

            }

		private void clearallbutton() {
			// TODO Auto-generated method stub

			for(int j =0;j<8;j++)
			{
					ColorDrawable buttonColor = (ColorDrawable) A[j].getBackground();
					
					if(buttonColor.getColor()!=Color.GREEN)
					{
							A[j].setBackgroundColor(Color.WHITE);
							A[j].setText("block"+(j+1));
					}
			}
			
		}

		private void showbutton(int i) {
			// TODO Auto-generated method stub
			
			for(int j =0;j<8;j++)
			{
				if(j==i)
				{
					ColorDrawable buttonColor = (ColorDrawable) A[j].getBackground();
				
		
					if(buttonColor.getColor()!=Color.GREEN)
					{
					A[j].setBackgroundColor(Color.RED);
					A[j].setText("Block"+j+"You");
					Canvas c1 = new Canvas();
					
					 Paint paint = new Paint();
					paint.setColor(Color.RED);
					View v1 = null;
					if(j==0)
					{
						v1 = (View) findViewById(R.id.view1);
					}
					else if(j==1)
					{
						v1 = (View) findViewById(R.id.view1);
					}
					else if(j==2)
					{
						v1 = (View) findViewById(R.id.view2);
					}
					else if(j==3)
					{
						v1 = (View) findViewById(R.id.view2);
					}
					else if(j==4)
					{
						v1 = (View) findViewById(R.id.view3);
					}
					else if(j==5)
					{
						v1 = (View) findViewById(R.id.view3);
					}
					else if(j==6)
					{
						v1 = (View) findViewById(R.id.view4);
					}
					else if(j==7)
					{
						v1 = (View) findViewById(R.id.view4);
					}
			
					c1.drawLine(A[j].getX(), A[j].getY(),v1.getX() , v1.getY(), paint);
					}
				}
				else
				{
					ColorDrawable buttonColor = (ColorDrawable) A[j].getBackground();
					
					if(buttonColor.getColor()!=Color.GREEN)
					{
							A[j].setBackgroundColor(Color.WHITE);
							A[j].setText("Block"+(j+1));
					}
				}
			}
		
			
		};
            
        
		

};
class BT
{
	String name;
	int rssi;
	
}
class MyBT implements Comparator<BT>
{

	@Override
	public int compare(BT arg0, BT arg1) {
		// TODO Auto-generated method stub
		if(arg0.rssi>arg1.rssi)
		{
			return -1;
		}
		else
		{
			return 1;	
		}
		
		
	}
	
}
}
    