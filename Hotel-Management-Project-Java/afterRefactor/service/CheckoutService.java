package afterRefactor.service;

import afterRefactor.domain.Holder;
import afterRefactor.domain.Room;
import afterRefactor.domain.RoomType;


public class CheckoutService {
	private final Holder hotel_ob;
	
	public CheckoutService(Holder hotel_ob) {
		// TODO Auto-generated constructor stub
		this.hotel_ob = hotel_ob;
	}
	
	public String occupantName(RoomType type, int rn) {
        Room room = hotel_ob.findRoom(type, rn);
        return room == null ? null : room.primaryGuestName();
    }
	
	public void deallocate(RoomType type, int rn) {
		hotel_ob.releaseRoom(type, rn);
    }
}
