package com.example.hyperwalkway4;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class CustomAdapter_SearchPro extends BaseAdapter
{
	ArrayList<SearchPrd> a2;
	Context c2;
	
	public CustomAdapter_SearchPro()
	{
		
	}
	public CustomAdapter_SearchPro(ArrayList<SearchPrd> a1,Context c1)
	{
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

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		 LayoutInflater inflater = (LayoutInflater) c2
				 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				 View single_row = inflater.inflate(R.layout.search_product_listview_element, null,
				 true);
				 
		 TextView textView = (TextView) single_row.findViewById(R.id.txt_prd_name);
		 RatingBar r=(RatingBar)single_row.findViewById(R.id.rate_sprd);
		 r.setEnabled(false);
		 r.setFocusable(false);
		 r.setFocusableInTouchMode(false);
		
		 textView.setText(a2.get(position).prd_name);
		 if(a2.get(position).rate==0.0)
		 {
			r.setVisibility(View.INVISIBLE);
		 }
		 else
		 {
		 r.setRating(a2.get(position).rate);
		 }
		return single_row;
	}

}
