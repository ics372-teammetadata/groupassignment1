public class MemberData
{
	String[] data = new String[]{"a","b","c"};
	String libCard;
	
	public MemberData(String libCard)
	{
		this.libCard = libCard;
	}
	
	public String getCardID()
	{
		return libCard;
	}
	
	public String[] getData()
	{
		return data;
	}
}
