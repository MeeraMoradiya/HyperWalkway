package com.example.hyperwalkway4;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class show_notification extends Activity{
	TextView l1;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.show_notification);
	l1=(TextView)findViewById(R.id.lbl_notify);
	Intent i1=getIntent();
	if(i1!=null && (i1.hasExtra(Intent.EXTRA_TEXT)==true))
	{
		String str=i1.getStringExtra(Intent.EXTRA_TEXT);
		l1.setText(str);
	}
}
}
