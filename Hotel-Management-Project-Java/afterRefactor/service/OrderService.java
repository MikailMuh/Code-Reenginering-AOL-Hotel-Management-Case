package afterRefactor.service;

import afterRefactor.domain.Food;
import afterRefactor.domain.Holder;
import afterRefactor.domain.RoomType;
import afterRefactor.domain.Singleroom;

public class OrderService {
	private final Holder hotel_ob;
	

	public OrderService(Holder hotel_ob) {
		// TODO Auto-generated constructor stub
		this.hotel_ob = hotel_ob;
	}
	
	public void order(RoomType type, int rn, int itemno, int quantity) {
		if (type == null) {
            throw new NullPointerException("Invalid room type");
        }
        Singleroom[] arr = type.arrayIn(hotel_ob);
        if (rn < 0 || rn >= arr.length || arr[rn] == null) {
            throw new NullPointerException("Room not booked");
        }
        arr[rn].food.add(new Food(itemno, quantity));
	}
}
