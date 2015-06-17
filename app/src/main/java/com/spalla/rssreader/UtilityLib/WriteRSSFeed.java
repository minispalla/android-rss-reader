package com.spalla.rssreader.UtilityLib;

// my packages
import com.spalla.rssreader.ListViewActivity;
import com.spalla.rssreader.R;
import com.spalla.rssreader.ParserLib.RSSParser;
import com.spalla.rssreader.ParserLib.RSSFeed;
import static com.spalla.rssreader.ParserLib.RSSUtilities.*;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.ContextThemeWrapper;


// Write RSS feed from the RSS URL and
// writes to a object file in the data dir

public class WriteRSSFeed extends AsyncTask<Void, Void, Void>
{
	private Context parent;
	private ProgressDialog FeedLoadingDialog;
	private RSSFeed feed;

	// The RSS URL we are parsing from
	private String RSSFEEDURL;

	public WriteRSSFeed(Context c, String url)
	{
		// Set the parent
		parent = c;
		// Set the feed URL
		RSSFEEDURL = url;
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		// Parse the RSSFeed and save the object
		feed = new RSSParser().parseXML(RSSFEEDURL);
		return null;
	}

	@Override
	protected void onPreExecute()
	{
		FeedLoadingDialog = new ProgressDialog(new ContextThemeWrapper(parent, R.style.AlertBox));
		FeedLoadingDialog.setMessage("Loading feed...");
		FeedLoadingDialog.setIndeterminate(false);
		FeedLoadingDialog.setCanceledOnTouchOutside(false);
		FeedLoadingDialog.setCancelable(true);
		FeedLoadingDialog.show();
	}

	@Override
	protected void onPostExecute(Void result)
	{
		super.onPostExecute(result);
		new WriteRSSObjectFile(parent).writeObject(feed, getFeedName());
		PreferenceManager.getDefaultSharedPreferences(parent).edit().putBoolean("isSetup", true).commit();
		FeedLoadingDialog.dismiss();
		parent.startActivity(new Intent(parent, ListViewActivity.class));
	}
}