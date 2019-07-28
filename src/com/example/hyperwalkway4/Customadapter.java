package com.example.hyperwalkway4;

import java.io.InputStream;
import java.net.URL;
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

public class Customadapter extends BaseAdapter
{
		ArrayList<product> a2;
		Context c2;
		public Customadapter() {
			// TODO Auto-generated constructor stub
		}
		
		
		public Customadapter(ArrayList<product> a1,Context c1) {
			// TODO Auto-generated constructor stub
			a2=a1;
			c2=c1;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return a2.size();
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
		private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
			  ImageView bmImage;

			  public DownloadImageTask(ImageView bmImage) {
			      this.bmImage = bmImage;
			  }

			  protected Bitmap doInBackground(String... urls) {
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
		public View getView(int position, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub // TODO Auto-generated method stub
			 LayoutInflater inflater = (LayoutInflater) c2
					 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					 View single_row = inflater.inflate(R.layout.listitem, null,
					 true);
					 ImageView i1 = (ImageView) single_row.findViewById(R.id.i1);

					 TextView textView = (TextView) single_row.findViewById(R.id.lbl_list_prd_name);
					TextView textView1 = (TextView) single_row.findViewById(R.id.lbl_rs_list);
					 new DownloadImageTask(i1)
				        .execute(MainActivity.imagestr+a2.get(position).pimage);
				
						
					 
				//	 TextView textView1 = (TextView) single_row.findViewById(R.id.textView2);
				//	 TextView textView2 = (TextView) single_row.findViewById(R.id.textView3);
						textView.setText(a2.get(position).pname);
						String str=a2.get(position).price+" "+"Rs.";
						textView1.setText(str);
					//	textView2.setText(a2.get(position).price);
					 
					 return single_row; 
		}

	}

