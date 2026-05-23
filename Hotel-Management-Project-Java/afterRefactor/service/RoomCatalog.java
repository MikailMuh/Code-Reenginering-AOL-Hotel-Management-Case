package afterRefactor.service;

import afterRefactor.domain.RoomType;

public final class RoomCatalog {
	private RoomCatalog() {}

	public static void printFeatures(int code) {
        RoomType t = RoomType.fromCode(code);
        if (t == null) {
            System.out.println("Enter valid option");
            return;
        }
        System.out.println(t.featuresText());
    }
}
