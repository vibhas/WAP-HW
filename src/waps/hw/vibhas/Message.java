package waps.hw.vibhas;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Message 
{
	private int id;
	private String content;
	private Timestamp timeStamp;
	private String name;
	
	public Message(int id, String content, Timestamp timeStamp, String name) 
	{
		this.id = id;
		this.content = content;
		this.timeStamp = timeStamp;
		this.name = name;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public String getTimeStamp()
	{
		SimpleDateFormat formatter = new SimpleDateFormat(
	      "MMMMM dd, yyyy h:mm a");
		return formatter.format(timeStamp);
	}
	
	public String getName()
	{
		return name;
	}
}
