package com.example.hyperwalkway4;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBillAdapter extends BaseAdapter
{
	ArrayList<Bills> b1;
	Context c1;
	public CustomBillAdapter() 
	{
		// TODO Auto-generated constructor stub
	}
	public CustomBillAdapter(ArrayList<Bills> bb1,Context cc1) 
	{
		// TODO Auto-generated constructor stub
		b1=bb1;
		c1=cc1;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return b1.size();
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
	public View getView(int position, View arg1, ViewGroup arg2) 
	{
		// TODO Auto-generated method stub
		 LayoutInflater inflater = (LayoutInflater) c1
				 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 View single_row = inflater.inflate(R.layout.bills_listview_element, null,
				 true);
		 TextView textView = (TextView) single_row.findViewById(R.id.txt_date);
		 TextView textView1 = (TextView) single_row.findViewById(R.id.txt_rs);
		 TextView textView2 = (TextView) single_row.findViewById(R.id.txt_lbl_rs);
		 textView.setText(b1.get(position).date);
		 textView1.setText(b1.get(position).rs);
		 
		 return single_row;
	}

}
