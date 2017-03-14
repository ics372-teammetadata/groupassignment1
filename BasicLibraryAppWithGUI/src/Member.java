public class Member
{
	protected String id, name, cardNumber;

	public Member(String idNumber, String itemName, String card){
		id = idNumber;
		name = itemName;
		cardNumber = card;
	}
	public String getID(){
		return id;
	}
	public String getCardNumber(){
		return cardNumber;
	}
	public String getName(){
		return name;
	}
}
