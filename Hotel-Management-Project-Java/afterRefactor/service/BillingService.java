package afterRefactor.service;

import afterRefactor.domain.Food;
import afterRefactor.domain.Holder;
import afterRefactor.domain.Singleroom;

public class BillingService {
	private final Holder hotel_ob;
	private static final String[] MENU_NAMES = {"Sandwich", "Pasta", "Noodles", "Coke"};

	public BillingService(Holder hotel_ob) {
		// TODO Auto-generated constructor stub
		this.hotel_ob = hotel_ob;
	}
	public String buildBill(int roomType, int localRoomIndex) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n*******\n");
        sb.append(" Bill:-\n");
        sb.append("*******\n");

        Singleroom room = findRoom(roomType, localRoomIndex);
        if (room == null) {
            sb.append("Not valid\n");
            return sb.toString();
        }

        int rate = RoomCatalog.dailyRate(roomType);
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
	
	private Singleroom findRoom(int roomType, int rn) {
        switch (roomType) {
            case 1: return hotel_ob.luxury_doublerrom[rn];
            case 2: return hotel_ob.deluxe_doublerrom[rn];
            case 3: return hotel_ob.luxury_singleerrom[rn];
            case 4: return hotel_ob.deluxe_singleerrom[rn];
            default: return null;
        }
    }

}
