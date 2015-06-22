package com.spalla.rssreader;

// my packages
import com.spalla.rssreader.ParserLib.RSSFeed;
import com.spalla.rssreader.UtilityLib.WriteRSSFeed;
import com.spalla.rssreader.UtilityLib.WriteRSSObjectFile;
import static com.spalla.rssreader.ParserLib.RSSUtilities.*;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


// The RSS Feed of the site and takes the user
// to the inline browser
// also added manual reload (even though it is rudimentary

public class ListViewActivity extends Activity
{

	// For debugging
	private final String TAG = "ListViewActivity";

	// set to false
	private boolean isRefresh = false;
	private ListViewAdapter adapter;
	private ListView list;
	// this will be our current feed
	private RSSFeed feed;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		// Make a new ViewGroup for the layout fragment
		setContentView(R.layout.feed_list);

		// Remove the icon from the ActionBar
		getActionBar().setDisplayShowHomeEnabled(false);


		// Get feed from the passed bundle
		feed = (RSSFeed)new WriteRSSObjectFile(this).readObject(getFeedName());
		String feedName = getFeedName();
		Log.d(TAG,"feedName="+feedName);
		if(feed == null)
		{
			Log.d(TAG, "feed=null");
		}

		list = (ListView)findViewById(R.id.listView);
		list.setVerticalFadingEdgeEnabled(true);

		// Setup new adapter
		adapter = new ListViewAdapter(this, feed);
		list.setAdapter(adapter);


		// Set on item click listener to the ListView
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{

				// grab url from rss element
				String itemUrl = feed.getItem(arg2).getURL();

				// intent to throw to browser on phone
				Uri uriUrl = Uri.parse(itemUrl);
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
				startActivity(launchBrowser);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem change = menu.add(Menu.NONE, 0, Menu.NONE, "New Feed");
		MenuItem reload = menu.add(Menu.NONE, 1, Menu.NONE, "Refresh");

		// Always show buttons
		change.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		reload.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// We're refreshing
		isRefresh = true;
		// Depending on what's pressed
		switch (item.getItemId()) {
		case 0:
			// Change the URL
			changeRSSFeed(isRefresh, this);;
			return true;
		case 1:
			// Start parsing the feed again
			new WriteRSSFeed(this, DEFAULTRSSFEED).execute();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Exit instead of going to loading screen
		adapter.notifyDataSetChanged();
	}

	@Override 
	public void onResume(){
		super.onResume();
		// dirty way for refreshing a class online
		if(isRefresh)
		{
			feed = (RSSFeed)new WriteRSSObjectFile(this).readObject(getFeedName());
			adapter = new ListViewAdapter(ListViewActivity.this, feed);
			list.setAdapter(adapter); 
			isRefresh = false;
		}
	}
}