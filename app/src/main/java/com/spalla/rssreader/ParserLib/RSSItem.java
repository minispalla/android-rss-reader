package com.spalla.rssreader.ParserLib;

import android.util.Log;

import java.io.Serializable;

// Stores values from RSS feed prior to being sent to RSSDFeed object list

public class RSSItem implements Serializable
{
	private final String TAG = "RSSitem";

	// Create the strings we need to store
	private String	title;
	private String description;
	private String date;
	private String thumb;
	private String author;
	private String URL;

	// Serializable IDENTIFICATION
	private static final long serialVersionUID = 1L;

	// SETTERS
	void setTitle(String Title)
	{
		title = Title;
	}

	void setAuthor(String Author)
	{
		author = Author;
	}

	void setURL(String url)
	{
		URL = url;
	}

	void setDescription(String Description)
	{
		description = Description;
	}

	void setDate(String Date)
	{
		date = Date;
	}

	void setThumb(String Thumb)
	{
		thumb = Thumb;
	}

	// GETTERS
	public String getTitle()
	{
		return title;
	}

	public String getDescription()
	{
		return description;
	}

	public String getDate()
	{
		return date;
	}

	public String getThumb()
	{
		return thumb;
	}

	public String getAuthor()
	{
		return author;
	}

	public String getURL()
	{
		return URL;
	}
}
