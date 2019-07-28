package com.example.hyperwalkway4;

import java.util.EnumSet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.abhi.barcode.frag.libv2.BarcodeFragment;
import com.abhi.barcode.frag.libv2.IScanResultHandler;
import com.abhi.barcode.frag.libv2.ScanResult;
import com.google.zxing.BarcodeFormat;

public class barcode_scan extends FragmentActivity implements IScanResultHandler {
	/** Called when the activity is first created. */

	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	BarcodeFragment fragment;
	Button btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.barcode_scan);
		fragment = (BarcodeFragment)getSupportFragmentManager().findFragmentById(R.id.sample);
		fragment.setScanResultHandler(this);
		btn = ((Button)findViewById(R.id.scan));
		btn.setEnabled(false);

		// Support for adding decoding type
		fragment.setDecodeFor(EnumSet.of(BarcodeFormat.QR_CODE));

	}

	@Override
	public void scanResult(ScanResult result) {
		btn.setEnabled(true);
		Toast.makeText(this, result.getRawResult().getText(), Toast.LENGTH_LONG).show();
		if(result.getRawResult().getText().equals(" ")==false)
		{
			String prd_info[]=result.getRawResult().getText().split("-");
			String prd_id=prd_info[0];
			String prd_name=prd_info[1];
			String prd_cat=prd_info[2];
			String prd_brand=prd_info[3];
			String prd_price=prd_info[4];
			String prd_exp=prd_info[5];
			Bundle extras=new Bundle();
			extras.putString("prd_id",prd_id );
			extras.putString("prd_name",prd_name);
			extras.putString("prd_cat", prd_cat);
			extras.putString("prd_brand", prd_brand);
			extras.putString("prd_price",prd_price);
			extras.putString("prd_exp",prd_exp);
			extras.putString("url","barcode_scan");
			
			//Toast.makeText(getBaseContext(), str, 2000).show();
			Intent i1=new Intent(this,search_product_view.class).putExtras(extras);
			startActivity(i1);
		}
	}

	public void scanAgain(View v){
		fragment.restart();
	}
} 	