package com.spalla.rssreader.UtilityLib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;


//  This will read and write to a object file from apps data dir
// 	more efficient than shared prefs
public class WriteRSSObjectFile {

	private Context parent;
	private FileInputStream fileIn;
	private FileOutputStream fileOut;
	private ObjectInputStream objectIn;
	private ObjectOutputStream objectOut;
	private Object outputObject;

	public WriteRSSObjectFile(Context c)
	{
		parent = c;
	}

	public Object readObject(String fileName)
	{
		try
		{
			fileIn = parent.getApplicationContext().openFileInput(fileName);
			objectIn = new ObjectInputStream(fileIn);
			outputObject = objectIn.readObject();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (objectIn != null)
			{
				try
				{
					objectIn.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return outputObject;
	}

	public void writeObject(Object inputObject, String fileName)
	{
		try
		{
			fileOut = parent.openFileOutput(fileName, Context.MODE_PRIVATE);
			objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(inputObject);
			fileOut.getFD().sync();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (objectOut != null)
			{
				try
				{
					objectOut.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}