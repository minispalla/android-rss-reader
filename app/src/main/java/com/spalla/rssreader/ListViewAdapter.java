package com.spalla.rssreader;

import com.spalla.rssreader.ParserLib.RSSFeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


// This is the listView adapter and sets up the view
// to be put in the listView

public class ListViewAdapter extends BaseAdapter
{

	// Create a new LayoutInflater and Rss Feed
	private LayoutInflater layoutInflater;
	private RSSFeed feed;

	public ListViewAdapter(Context c, RSSFeed rssFeed)
	{
		// Setting up the layout inflater
		layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		feed = rssFeed;
	}

	@Override
	public int getCount()
	{
		return feed.getItemCount();
	}

	@Override
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View listItem, ViewGroup parent)
	{
		if (listItem == null)
		{
			// Inflate a list feed item for listView
			listItem = layoutInflater.inflate(R.layout.feed_list_item, null);
		}
		// Setting the view in the layout for ListView
		((TextView)listItem.findViewById(R.id.title)).setText(feed.getItem(position).getTitle());

		// Formatting for adding the author on the view
		((TextView)listItem.findViewById(R.id.date)).setText(feed.getItem(position).getDate() + " - " + feed.getItem(position).getAuthor());
		return listItem;
	}
}