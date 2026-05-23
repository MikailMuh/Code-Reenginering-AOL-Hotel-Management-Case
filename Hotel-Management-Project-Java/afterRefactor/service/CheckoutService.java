package afterRefactor.service;

import afterRefactor.domain.Holder;
import afterRefactor.domain.RoomType;
import afterRefactor.domain.Singleroom;

public class CheckoutService {
	private final Holder hotel_ob;
	

	public CheckoutService(Holder hotel_ob) {
		// TODO Auto-generated constructor stub
		this.hotel_ob = hotel_ob;
	}
	
	public String occupantName(RoomType type, int rn) {
        Singleroom room = roomAt(type, rn);
        return room == null ? null : room.name;
    }
	
	public void deallocate(RoomType type, int rn) {
		if (type == null) return;
        Singleroom[] arr = type.arrayIn(hotel_ob);
        if (rn < 0 || rn >= arr.length) return;
        arr[rn] = null;
    }
	
	private Singleroom roomAt(RoomType type, int rn) {
		if (type == null) return null;
        Singleroom[] arr = type.arrayIn(hotel_ob);
        if (rn < 0 || rn >= arr.length) return null;
        return arr[rn];
    }
}
