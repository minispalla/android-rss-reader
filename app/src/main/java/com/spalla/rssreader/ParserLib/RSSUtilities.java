package com.spalla.rssreader.ParserLib;


// My packages
import com.spalla.rssreader.UtilityLib.WriteRSSFeed;

// imported lib
import org.apache.commons.validator.routines.UrlValidator;

import java.net.URI;
import java.net.URISyntaxException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.widget.EditText;

// Stores RSSFeed + Strips the domain name out for simple storage

public class RSSUtilities
{

	// HardCode your default rss feed
	// One of the RSS feeds I found that wasn't a complete mess
	public static String DEFAULTRSSFEED = "https://www.androidpit.com/feed/main.xml";

	// Change to a given feed
	public static void changeRSSFeed(boolean refresh, Context context)
	{
		changeURLDialog(context);
	}

	// return the full feed address
	public static String getFullAddr()
	{
		return DEFAULTRSSFEED;
	}

	// Returns the cleaned name of the stored feed
	public static String getFeedName()
	{
		return getDomainName(DEFAULTRSSFEED);
	}

	// Converts URL to just domain name
	// Ex. http://lifehacker.com/rss to lifehacker
	private static String getDomainName(String url)
	{
		String domain = "";

		try
		{
			domain = new URI(url).getHost();
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return domain.startsWith("www.") ? domain.substring(4) : domain;
	}


	// DIALOGS

	// Takes a string URL as a new feed
	private static void changeURLDialog(final Context context)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		final EditText input = new EditText(context);
		builder.setTitle("New RSS Feed URL:")
		.setMessage("Please enter a valid RSS URL:")
				//+ "Press OK for the default RSS URL")
		.setView(input)
		.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// see if it is a valid URL
				if (new UrlValidator().isValid(input.getText().toString())) {
					DEFAULTRSSFEED = input.getText().toString();
				}
				// Parse the new feed/URL
				new WriteRSSFeed(context, DEFAULTRSSFEED).execute();
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isSetup", false)) {
					((Activity) context).finish();
				} else {
					dialog.dismiss();
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.setCanceledOnTouchOutside(false);
		alert.show();

	}
}
