package afterRefactor.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final RoomType type;
	private final List<Guest> guests = new ArrayList<>();
	private final List<Food> food = new ArrayList<>();
	public Room(RoomType type, List<Guest> guests) {
		// TODO Auto-generated constructor stub
		this.type = type;
		if(guests != null) this.guests.addAll(guests);
	}
	public RoomType type() { return type; }

    public List<Guest> guests() {
        return Collections.unmodifiableList(guests);
    }

    public Guest primaryGuest() {
        return guests.isEmpty() ? null : guests.get(0);
    }

    public String primaryGuestName() {
        Guest g = primaryGuest();
        return g == null ? "" : g.name();
    }

    public void addFoodOrder(Food order) {
        food.add(order);
    }

    public List<Food> foodOrders() {
        return Collections.unmodifiableList(food);
    }
}
