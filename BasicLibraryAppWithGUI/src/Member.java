public class Member
{
	protected String id, name, cardNumber;

	public Member(String idNumber, String memberName, String cardNumber){
		id = idNumber;
		name = memberName;
		this.cardNumber = cardNumber;
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
