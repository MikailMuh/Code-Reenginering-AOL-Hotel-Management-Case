package afterRefactor.service;

import afterRefactor.domain.Doubleroom;
import afterRefactor.domain.Holder;
import afterRefactor.domain.RoomType;
import afterRefactor.domain.Singleroom;

public class BookingService {
	private final Holder hotel_ob;

	public BookingService(Holder hotel_ob) {
		// TODO Auto-generated constructor stub
		this.hotel_ob = hotel_ob;
	}
	
	public int[] listFreeRoomNumbers(RoomType type) {
		if (type == null) return null;
        Singleroom[] arr = type.arrayIn(hotel_ob);
        int count = 0;
        for (Singleroom r : arr) if (r == null) count++;
        int[] out = new int[count];
        int k = 0;
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] == null) out[k++] = j + type.globalOffset();
        }
        return out;
    }
	
	public int availability(RoomType type) {
        if (type == null) return -1;
        int c = 0;
        for (Singleroom r : type.arrayIn(hotel_ob)) if (r == null) c++;
        return c;
    }
	
	public boolean book(RoomType type, int globalRoomNumber,
            String name, String contact, String gender,
            String name2, String contact2, String gender2) {
		if (type == null) return false;
		int rn = globalRoomNumber - type.globalOffset();
		Singleroom[] arr = type.arrayIn(hotel_ob);
		if (rn < 0 || rn >= arr.length) return false;
		if (arr[rn] != null) return false;

		arr[rn] = type.isDouble()
				? new Doubleroom(name, contact, gender, name2, contact2, gender2)
				: new Singleroom(name, contact, gender);
		return true;
}
	
	
	
	

}
