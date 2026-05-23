package afterRefactor.domain;

import java.io.Serializable;
import java.util.EnumMap;

public class Holder implements Serializable{
    private static final long serialVersionUID = 2L;
    
    private final EnumMap<RoomType, Room[]> rooms = new EnumMap<>(RoomType.class);

    public Holder() {
        for (RoomType t : RoomType.values()) {
            rooms.put(t, new Room[t.capacity()]);
        }
    }
    
    public Room findRoom(RoomType type, int localIndex) {
        if (type == null) return null;
        Room[] arr = rooms.get(type);
        if (localIndex < 0 || localIndex >= arr.length) return null;
        return arr[localIndex];
    }
    
    public int countFree(RoomType type) {
        if (type == null) return -1;
        int c = 0;
        for (Room r : rooms.get(type)) if (r == null) c++;
        return c;
    }
    
    public int[] freeRoomNumbers(RoomType type) {
        if (type == null) return null;
        Room[] arr = rooms.get(type);
        int count = 0;
        for (Room r : arr) if (r == null) count++;
        int[] out = new int[count];
        int k = 0;
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] == null) out[k++] = j + type.globalOffset();
        }
        return out;
    }
    
    public boolean placeRoom(RoomType type, int localIndex, Room room) {
        if (type == null || room == null) return false;
        Room[] arr = rooms.get(type);
        if (localIndex < 0 || localIndex >= arr.length) return false;
        if (arr[localIndex] != null) return false;
        arr[localIndex] = room;
        return true;
    }
    
    public void releaseRoom(RoomType type, int localIndex) {
        if (type == null) return;
        Room[] arr = rooms.get(type);
        if (localIndex < 0 || localIndex >= arr.length) return;
        arr[localIndex] = null;
    }
    
    
}
