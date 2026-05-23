package afterRefactor.service;

import afterRefactor.domain.Doubleroom;
import afterRefactor.domain.Holder;
import afterRefactor.domain.Singleroom;

public class BookingService {
	private final Holder hotel_ob;

	public BookingService(Holder hotel_ob) {
		// TODO Auto-generated constructor stub
		this.hotel_ob = hotel_ob;
	}
	
	public int[] listFreeRoomNumbers(int roomType) {
        switch (roomType) {
            case 1: return freeIndices(hotel_ob.luxury_doublerrom, 1);
            case 2: return freeIndices(hotel_ob.deluxe_doublerrom, 11);
            case 3: return freeIndices(hotel_ob.luxury_singleerrom, 31);
            case 4: return freeIndices(hotel_ob.deluxe_singleerrom, 41);
            default: return null;
        }
    }
	
	private int[] freeIndices(Object[] arr, int offset) {
        int count = 0;
        for (Object o : arr) if (o == null) count++;
        int[] out = new int[count];
        int k = 0;
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] == null) out[k++] = j + offset;
        }
        return out;
    }
	
	public int availability(int roomType) {
        switch (roomType) {
            case 1: return countNulls(hotel_ob.luxury_doublerrom);
            case 2: return countNulls(hotel_ob.deluxe_doublerrom);
            case 3: return countNulls(hotel_ob.luxury_singleerrom);
            case 4: return countNulls(hotel_ob.deluxe_singleerrom);
            default: return -1;
        }
    }
	
	private int countNulls(Object[] arr) {
        int c = 0;
        for (Object o : arr) if (o == null) c++;
        return c;
    }
	
	public boolean book(int roomType, int globalRoomNumber, String name, String contact, String gender, String name2, String contact2, String gender2) {
		try {
			int rn;
			switch(roomType) {
			case 1:
				rn = globalRoomNumber -1;
				if(rn < 0 || rn>= hotel_ob.luxury_doublerrom.length)return false;
				if (hotel_ob.luxury_doublerrom[rn] != null) return false;
                hotel_ob.luxury_doublerrom[rn] =
                        new Doubleroom(name, contact, gender, name2, contact2, gender2);
                return true;
			case 2:
				rn = globalRoomNumber - 11;
                if (rn < 0 || rn >= hotel_ob.deluxe_doublerrom.length) return false;
                if (hotel_ob.deluxe_doublerrom[rn] != null) return false;
                hotel_ob.deluxe_doublerrom[rn] =
                        new Doubleroom(name, contact, gender, name2, contact2, gender2);
                return true;
			case 3:
				rn = globalRoomNumber - 31;
                if (rn < 0 || rn >= hotel_ob.luxury_singleerrom.length) return false;
                if (hotel_ob.luxury_singleerrom[rn] != null) return false;
                hotel_ob.luxury_singleerrom[rn] = new Singleroom(name, contact, gender);
                return true;
			case 4:
				rn = globalRoomNumber - 41;
                if (rn < 0 || rn >= hotel_ob.deluxe_singleerrom.length) return false;
                if (hotel_ob.deluxe_singleerrom[rn] != null) return false;
                hotel_ob.deluxe_singleerrom[rn] = new Singleroom(name, contact, gender);
                return true;
            default:
            	return false;
			}
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	
	
	

}
