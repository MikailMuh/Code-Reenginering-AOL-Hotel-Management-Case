package afterRefactor.service;

import afterRefactor.domain.Food;
import afterRefactor.domain.Holder;
import afterRefactor.domain.Singleroom;

public class OrderService {
	private final Holder hotel_ob;
	

	public OrderService(Holder hotel_ob) {
		// TODO Auto-generated constructor stub
		this.hotel_ob = hotel_ob;
	}
	
	public void order(int roomType, int rn, int itemno, int quantity) {
		Singleroom room = roomAt(roomType, rn);
		if(room == null) {
			throw new NullPointerException("Room not booked");
		}
		room.food.add(new Food(itemno, quantity));
	}
	private Singleroom roomAt(int roomType, int rn) {
	   switch (roomType) {
	       case 1: return hotel_ob.luxury_doublerrom[rn];
	       case 2: return hotel_ob.deluxe_doublerrom[rn];
	       case 3: return hotel_ob.luxury_singleerrom[rn];
	       case 4: return hotel_ob.deluxe_singleerrom[rn];
	       default: return null;
	   }
	}
	

}
