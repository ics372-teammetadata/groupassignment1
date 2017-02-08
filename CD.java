class CD extends NotBook
{
	private String artist;

	public CD(InventoryItem x, String artist)
	{
		super(x.getID(), x.getName(), x.getType());
		this.artist = artist;
	}
	
	public String getArtistName()
	{
		return artist;
	}
}