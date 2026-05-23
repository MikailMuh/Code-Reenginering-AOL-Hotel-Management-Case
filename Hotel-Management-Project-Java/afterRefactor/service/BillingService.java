package afterRefactor.service;

import afterRefactor.domain.Food;
import afterRefactor.domain.Holder;
import afterRefactor.domain.RoomType;
import afterRefactor.domain.Singleroom;

public class BillingService {
	private final Holder hotel_ob;
	private static final String[] MENU_NAMES = {"Sandwich", "Pasta", "Noodles", "Coke"};

	public BillingService(Holder hotel_ob) {
		// TODO Auto-generated constructor stub
		this.hotel_ob = hotel_ob;
	}
	public String buildBill(RoomType type, int localRoomIndex) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n*******\n");
        sb.append(" Bill:-\n");
        sb.append("*******\n");

        
        if (type == null) {
            sb.append("Not valid\n");
            return sb.toString();
        }
        Singleroom[]arr = type.arrayIn(hotel_ob);
        if(localRoomIndex < 0 || localRoomIndex >= arr.length || arr[localRoomIndex] == null) {
        	sb.append("Not valid\n");
        	return sb.toString();
        }
        Singleroom room = arr[localRoomIndex];

        int rate = type.dailyRate();
        double amount = rate;
        sb.append("\nRoom Charge - ").append(rate).append("\n");
        sb.append("\n===============\n");
        sb.append("Food Charges:- \n");
        sb.append("===============\n");
        sb.append("Item   Quantity    Price\n");
        sb.append("-------------------------\n");

        String format = "%-10s%-10s%-10s%n";
        for (Food f : room.food) {
            amount += f.price;
            sb.append(String.format(format, MENU_NAMES[f.itemno - 1], f.quantity, f.price));
        }

        sb.append("\nTotal Amount- ").append(amount);
        return sb.toString();
    }
	
	

}
