package afterRefactor.service;

public final class RoomCatalog {
	private RoomCatalog() {}

	public static void printFeatures(int roomType) {
        switch (roomType) {
            case 1:
                System.out.println("Number of double beds : 1\nAC : Yes\nFree breakfast : Yes\nCharge per day:4000 ");
                break;
            case 2:
                System.out.println("Number of double beds : 1\nAC : No\nFree breakfast : Yes\nCharge per day:3000  ");
                break;
            case 3:
                System.out.println("Number of single beds : 1\nAC : Yes\nFree breakfast : Yes\nCharge per day:2200  ");
                break;
            case 4:
                System.out.println("Number of single beds : 1\nAC : No\nFree breakfast : Yes\nCharge per day:1200 ");
                break;
            default:
                System.out.println("Enter valid option");
        }
    }
	
	public static int dailyRate(int roomType) {
        switch (roomType) {
            case 1: return 4000;
            case 2: return 3000;
            case 3: return 2200;
            case 4: return 1200;
            default: return 0;
        }
    }

}
