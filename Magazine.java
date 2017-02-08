class Magazine extends NotBook
{

	public Magazine(InventoryItem x)
	{
		super(x.getID(), x.getName(), x.getType());
	}
}