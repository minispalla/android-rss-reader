package com.spalla.rssreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

// my package
import com.spalla.rssreader.ParserLib.RSSUtilities;

// Start screen, after default feed has been loaded

public class MainActivity extends Activity
{
	private final String TAG = "MainActivity";

	// Keep track of when feed exists
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Remove the icon from the ActionBar as it looks bad IMO
		getActionBar().setDisplayShowHomeEnabled(false);

		// Get our shared prefs
		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		// See if the feed has been used
		if(!prefs.getBoolean("isSetup", false))
		{
			// Set the content view
			setContentView(R.layout.loading);

			// see if you have a internet connection
			if(checkInternetConnection(getApplicationContext()) == false)
			{
				// if no connection found throw error msg
				internetErrorDialog();
			}
			else
			{
				RSSUtilities rssutil = new RSSUtilities();
				rssutil.changeRSSFeed(false, this);
			}
		}
		// Start new activity and stop this one
		else
		{
			startActivity(new Intent(this, ListViewActivity.class));
			finish();
		}
	}

	private boolean checkInternetConnection(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
		// test for connection
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected())
		{
			return true;
		}
		else
		{
			Log.v(TAG, "Internet Connection Not Present");
			return false;
		}
	}

	// Dialogs
	public void internetErrorDialog()
	{
		new AlertDialog.Builder(this)
				.setTitle( "No Internet Connection" )
				.setMessage( "No internet connection found, you might want to try again later." )
				.setCancelable(false)
				/*
				.setPositiveButton( "OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						Log.d( "AlertDialog", "OK" );
						// here is where you would throw to somewhere else
					}
				})
				*/
				.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Log.d("AlertDialog", "Exit");
						// here is where you would throw to somewhere else
						finish();
					}
				})
				.show();
	}

}
