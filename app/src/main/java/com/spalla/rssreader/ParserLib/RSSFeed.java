package com.spalla.rssreader.ParserLib;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

 // RSSParser will write and add to this list
 // also added  getItemCount()

public class RSSFeed implements Serializable
{
	private int itemCount = 0;
	private List<RSSItem> itemList;

	// Serializable IDENTIFICATION
	private static final long serialVersionUID = 1L;

	public RSSFeed()
	{
		// Initialize the item list
		itemList = new Vector<RSSItem>(0);
	}

	void addItem(RSSItem item)
	{
		// Adding item to the itemlist
		itemList.add(item);
		itemCount++;
	}

	// Return the item from the position parameter
	public RSSItem getItem(int position)
	{
		return itemList.get(position);
	}

	// Return title count from feed
	public int getItemCount()
	{
		return itemCount;
	}

}
