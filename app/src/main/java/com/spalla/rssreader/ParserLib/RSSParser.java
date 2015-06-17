package com.spalla.rssreader.ParserLib;


import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

// Parses RSSFeed and sends to RSSFeed Object
// also added a progress bar to show status

public class RSSParser
{
	// for debugging
	private final String TAG = "RssParser";

	// Create a new RSS feed
	private RSSFeed feed = new RSSFeed();

	public RSSFeed parseXML(String feedURL)
	{

		// Start parsing the new url
		URL url = null;
		try
		{
			url = new URL(feedURL);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

		try {
			// Make a new instance
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			// Parse the XML
			Document doc = builder.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();

			NodeList list = doc.getElementsByTagName("item");
			int length = list.getLength();

			//Move through all items in the feed
			for (int i = 0; i < length; i++)
			{
				// start list
				Node currentItem = list.item(i);

				RSSItem item = new RSSItem();
				NodeList itemChild = currentItem.getChildNodes();
				int cLength = itemChild.getLength();

				// Move through items within each item
				for (int j = 1; j < cLength; j = j + 2)
				{
					String nodeName = itemChild.item(j).getNodeName(), innterItemString = null;
					if(itemChild.item(j).getFirstChild() != null)
					{
						innterItemString = itemChild.item(j).getFirstChild().getNodeValue();
					}
					if (innterItemString != null)
					{
						// scan for specific information and set accordingly
						if ("title".equals(nodeName))
						{
							item.setTitle(innterItemString);
						}
						else if ("content:encoded".equals(nodeName))
						{
							item.setDescription(innterItemString);
						}
						else if ("pubDate".equals(nodeName))
						{
							item.setDate(innterItemString.replace(" +0000", ""));
						}
						else if ("author".equals(nodeName) || "dc:creator".equals(nodeName))
						{
							item.setAuthor(innterItemString);
						}
						else if ("link".equals(nodeName))
						{
							item.setURL(innterItemString);
						}
						else if ("thumbnail".equals(nodeName))
						{
							item.setThumb(innterItemString);
						}
					}
				}
				feed.addItem(item);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return feed;
	}
}
