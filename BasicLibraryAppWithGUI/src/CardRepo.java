public class CardRepo
{
	private MemberData[] mData = new MemberData[100];
	static int index;
	
	public void addCard(String newCard)
	{
		mData[index] = new MemberData(newCard);
		index++;
	}
	
	public MemberData getMemberData(String ID)
	{
		for (int i = 0; i < index; i++)
		{
			if (mData[i].getCardID().equals(ID))
				return mData[i];
		}
		return null;
	}

}
