class DVD extends NotBook
{

	public DVD(InventoryItem x)
	{
		super(x.getID(), x.getName(), x.getType());
	}
	
	public String toString()
	{
		return getName();
	}
}