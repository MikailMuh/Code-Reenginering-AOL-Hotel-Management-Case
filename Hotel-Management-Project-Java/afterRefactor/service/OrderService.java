package afterRefactor.service;

import afterRefactor.domain.Food;
import afterRefactor.domain.Holder;
import afterRefactor.domain.Room;
import afterRefactor.domain.RoomType;

/**
 * Food ordering. Step 4: uses Room.addFoodOrder().
 */
public class OrderService {
    private final Holder hotel_ob;

    public OrderService(Holder hotel_ob) {
        this.hotel_ob = hotel_ob;
    }

    public void order(RoomType type, int rn, int itemno, int quantity) {
        Room room = hotel_ob.findRoom(type, rn);
        if (room == null) {
            throw new NullPointerException("Room not booked");
        }
        room.addFoodOrder(new Food(itemno, quantity));
    }
}
