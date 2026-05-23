package afterRefactor.service;

import afterRefactor.domain.Holder;
import afterRefactor.domain.Singleroom;

public class CheckoutService {
	private final Holder hotel_ob;
	

	public CheckoutService(Holder hotel_ob) {
		// TODO Auto-generated constructor stub
		this.hotel_ob = hotel_ob;
	}
	
	public String occupantName(int roomType, int rn) {
        Singleroom room = roomAt(roomType, rn);
        return room == null ? null : room.name;
    }
	
	public void deallocate(int roomType, int rn) {
        switch (roomType) {
            case 1: hotel_ob.luxury_doublerrom[rn] = null; break;
            case 2: hotel_ob.deluxe_doublerrom[rn] = null; break;
            case 3: hotel_ob.luxury_singleerrom[rn] = null; break;
            case 4: hotel_ob.deluxe_singleerrom[rn] = null; break;
        }
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
