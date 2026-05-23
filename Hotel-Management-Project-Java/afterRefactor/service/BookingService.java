package afterRefactor.service;

import java.util.ArrayList;
import java.util.List;

import afterRefactor.domain.Guest;
import afterRefactor.domain.Holder;
import afterRefactor.domain.Room;
import afterRefactor.domain.RoomType;

public class BookingService {
	private final Holder hotel_ob;

	public BookingService(Holder hotel_ob) {
		// TODO Auto-generated constructor stub
		this.hotel_ob = hotel_ob;
	}
	
	public int[] listFreeRoomNumbers(RoomType type) {
		return hotel_ob.freeRoomNumbers(type);
    }
	
	public int availability(RoomType type) {
        return hotel_ob.countFree(type);
    }
	
	public boolean book(RoomType type, int globalRoomNumber, List<Guest> guests) {
		if (type == null) return false;
		if (guests == null || guests.isEmpty()) return false;
		if (type.isDouble() && guests.size() < 2) return false;
		
		int rn = globalRoomNumber - type.globalOffset();
        Room room = new Room(type, new ArrayList<>(guests));
        return hotel_ob.placeRoom(type, rn, room);
	}
}
