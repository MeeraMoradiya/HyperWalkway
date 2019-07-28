package com.example.hyperwalkway4;

import java.io.InputStream;
import java.util.ArrayList;




import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomCartAdapter extends BaseAdapter
{
	ArrayList<CartProduct> a2;
	ArrayList<OrderProduct> a1;
	Context c2;
	public CustomCartAdapter() 
	{
		// TODO Auto-generated constructor stub
	}
	public CustomCartAdapter(ArrayList<CartProduct> a1,Context c1)
	{
		// TODO Auto-generated constructor stub
		a2=a1;
		c2=c1;
	}
	
	
	public CustomCartAdapter(ArrayList<OrderProduct> cp1,
			view_order_details view_order_details) {
		// TODO Auto-generated constructor stub
		a1=cp1;
		c2=view_order_details;
	}
	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		if(a1!=null)
		{
			return a1.size();
		}
		else
		{
		return a2.size();
		}
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private class DownloadImageTask1 extends AsyncTask<String, Void, Bitmap> {
		  ImageView bmImage;

		  public DownloadImageTask1(ImageView bmImage) {
		      this.bmImage = bmImage;
		  }
		  
		  protected Bitmap doInBackground(String... urls)
		  {
		      String urldisplay = urls[0];
		      Bitmap mIcon11 = null;
		      try {
		        InputStream in = new java.net.URL(urldisplay).openStream();
		        mIcon11 = BitmapFactory.decodeStream(in);
		      } catch (Exception e) {
		          Log.e("Error", e.getMessage());
		          e.printStackTrace();
		      }
		      return mIcon11;
		  }
		  
		  protected void onPostExecute(Bitmap result) {
		      bmImage.setImageBitmap(result);
		  }
	}
	
	@Override
	public View getView(int position, View arg1, ViewGroup arg2)
	{
		// TODO Auto-generated method stub // TODO Auto-generated method stub
		 LayoutInflater inflater = (LayoutInflater) c2
				 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				 View single_row = inflater.inflate(R.layout.cart_listview_element, null,
				 true);
				 ImageView i1 = (ImageView) single_row.findViewById(R.id.image_cart);

				 TextView text_prd_name = (TextView) single_row.findViewById(R.id.text_prd_name);
				 TextView text_prd_qty = (TextView) single_row.findViewById(R.id.text_prd_qty);
				 TextView text_prd_price = (TextView) single_row.findViewById(R.id.text_prd_price);
				 TextView txt_org_price = (TextView) single_row.findViewById(R.id.txt_org_price);
				 TextView txt_dis_per = (TextView) single_row.findViewById(R.id.txt_dis_per);
				 txt_org_price.setVisibility(View.INVISIBLE);
				 txt_dis_per.setVisibility(View.INVISIBLE);
				 if(a1!=null)
					{
					 new DownloadImageTask1(i1)
				        .execute(MainActivity.imagestr+a1.get(position).prd_img);
					 text_prd_name.setText(a1.get(position).prd_name);
						text_prd_qty.setText(a1.get(position).prd_qty);
						String str=a1.get(position).prd_price+" "+"Rs.";
						text_prd_price.setText(str);
						//textView1.setText(a2.get(position).Category);
					}
					else
					{
						if((a2.get(position).prd_dis_per!=null) &&(a2.get(position).prd_org_price!=null))
						{
							txt_org_price.setVisibility(View.VISIBLE);
							 txt_dis_per.setVisibility(View.VISIBLE);
							 String str="Original Price:"+ a2.get(position).prd_org_price+"   ";
							 txt_org_price.setText(str);
							 str=a2.get(position).prd_dis_per+"% Off";
							 txt_dis_per.setText(str);
						}
						 
						 new DownloadImageTask1(i1)
					        .execute(MainActivity.imagestr+a2.get(position).prd_img);
						 text_prd_name.setText(a2.get(position).prd_name);
							text_prd_qty.setText(a2.get(position).prd_qty);
							String str=a2.get(position).prd_price+" "+"Rs.";
							text_prd_price.setText(str);
							//textView1.setText(a2.get(position).Category);
					}
				 
				
			
					
				 
			//	 TextView textView1 = (TextView) single_row.findViewById(R.id.textView2);
			//	 TextView textView2 = (TextView) single_row.findViewById(R.id.textView3);
					
				//	textView2.setText(a2.get(position).price);
				 
				 return single_row; 
	}


}
