package com.example.hyperwalkway4;

import java.security.PublicKey;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMConstants;


public class GCMIntentService extends GCMBaseIntentService {
	
	protected GCMIntentService(String senderId) {
		super(senderId);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onError(Context arg0, String errorId) {
		// TODO Auto-generated method stub
		if(GCMConstants.ERROR_ACCOUNT_MISSING.equalsIgnoreCase(errorId)) {
            Log.v(TAG, "Error Account Missing");
        } else if(GCMConstants.ERROR_AUTHENTICATION_FAILED.equalsIgnoreCase(errorId)) {
            Log.v(TAG, "Error Authentication Failed");
        } else if(GCMConstants.ERROR_INVALID_PARAMETERS.equalsIgnoreCase(errorId)) {
            Log.v(TAG, "Error Invalid Parameters");
        } else if(GCMConstants.ERROR_INVALID_SENDER.equalsIgnoreCase(errorId)) {
            Log.v(TAG, "Error Invalid Sender");
        } else if(GCMConstants.ERROR_PHONE_REGISTRATION_ERROR.equalsIgnoreCase(errorId)) {
            Log.v(TAG, "Error Phone Registration Error");
        } else if(GCMConstants.ERROR_SERVICE_NOT_AVAILABLE.equalsIgnoreCase(errorId)) {
            Log.v(TAG, "Error Service Not Available");
        } 
	}
	@Override
	protected void onMessage(Context arg0, Intent intent) {
		 String action  = intent.getStringExtra("action");
	        int extra = 0;
	        try {
                extra = Integer.parseInt(intent.getStringExtra("action_id"));
        } catch (Exception e){
                /* ignore */
        }
	        String message = intent.getStringExtra("message");
	        String ticker = intent.getStringExtra("tickerText");
	        String content = intent.getStringExtra("contentTitle");
	        Log.d("msg",message);
	        Log.d("tickerText",ticker);
	        Log.d("contentTitle",content);
	        String ns = Context.NOTIFICATION_SERVICE;
	        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
	        int icon = R.drawable.ic_launcher;        
	        CharSequence tickerText = "Hello"; // ticker-text
	        long when = System.currentTimeMillis();         
	        Context context = getApplicationContext();     
	        CharSequence contentTitle = content;  
	        CharSequence contentText = message;      
	        Intent notificationIntent = new Intent(arg0,show_notification.class).putExtra(Intent.EXTRA_TEXT, contentText);
	        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
	        Notification notification = new Notification(icon, tickerText, when);
	        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	        
	        int HELLO_ID = 1;
	        mNotificationManager.notify(HELLO_ID, notification);

	        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	        WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
	        wl.acquire(15000);
	        
	        notification.vibrate=new long[] {500L, 200L, 200L, 500L};
	        
	        
	        Uri not = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), not);
	        r.play();
	    	}
	@Override
	protected void onRegistered(Context arg0, String regId) {
		// TODO Auto-generated method stub
		Log.v(TAG, "Successfull Registration : "+regId);
	}
	@Override
	protected void onUnregistered(Context arg0, String regId) {
		// TODO Auto-generated method stub
		Log.v(TAG, "Successfully Unregistred : "+regId);
	}
	
	@Override
    protected void onDeletedMessages(Context context, int total) {
        super.onDeletedMessages(context, total);
    }
	@Override
    protected boolean onRecoverableError(Context context, String errorId) {
        return super.onRecoverableError(context, errorId);
    }
    
}
