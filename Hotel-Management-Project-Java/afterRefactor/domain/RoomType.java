package afterRefactor.domain;

public enum RoomType {
	
	LUXURY_DOUBLE(1, 1,  10, 4000, true,  true,  "double") {
        @Override public Singleroom[] arrayIn(Holder h) { return h.luxury_doublerrom; }
    },
    DELUXE_DOUBLE(2, 11, 20, 3000, false, true,  "double") {
        @Override public Singleroom[] arrayIn(Holder h) { return h.deluxe_doublerrom; }
    },
    LUXURY_SINGLE(3, 31, 10, 2200, true,  true,  "single") {
        @Override public Singleroom[] arrayIn(Holder h) { return h.luxury_singleerrom; }
    },
    DELUXE_SINGLE(4, 41, 20, 1200, false, true,  "single") {
        @Override public Singleroom[] arrayIn(Holder h) { return h.deluxe_singleerrom; }
    };
	
	private final int code;
    private final int globalOffset;
    private final int capacity;
    private final int dailyRate;
    private final boolean hasAc;
    private final boolean freeBreakfast;
    private final String bedKind;
    
    RoomType(int code, int globalOffset, int capacity, int dailyRate,boolean hasAc, boolean freeBreakfast, String bedKind){
    	this.code = code;
        this.globalOffset = globalOffset;
        this.capacity = capacity;
        this.dailyRate = dailyRate;
        this.hasAc = hasAc;
        this.freeBreakfast = freeBreakfast;
        this.bedKind = bedKind;
    }
    public abstract Singleroom[] arrayIn(Holder h);

    public int code()         { return code; }
    public int globalOffset() { return globalOffset; }
    public int capacity()     { return capacity; }
    public int dailyRate()    { return dailyRate; }
    public boolean isDouble() { return "double".equals(bedKind); }
    
    
    public String featuresText() {
        return "Number of " + bedKind + " beds : 1\n" +
                "AC : " + (hasAc ? "Yes" : "No") + "\n" +
                "Free breakfast : " + (freeBreakfast ? "Yes" : "No") + "\n" +
                "Charge per day:" + dailyRate;
    }
    
    public static RoomType fromCode(int code) {
        for (RoomType t : values()) if (t.code == code) return t;
        return null;
    }
    
    public static Object[] resolveGlobal(int globalRoomNumber) {
        if (globalRoomNumber <= 0) return null;
        // Pick the type whose range contains this number.
        RoomType chosen = null;
        for (RoomType t : values()) {
            if (globalRoomNumber >= t.globalOffset
                    && globalRoomNumber < t.globalOffset + t.capacity) {
                chosen = t;
                break;
            }
        }
        if (chosen == null) return null;
        int local = globalRoomNumber - chosen.globalOffset;
        return new Object[]{chosen, local};
    }
}
