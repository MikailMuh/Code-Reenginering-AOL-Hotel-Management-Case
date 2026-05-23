package afterRefactor;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import afterRefactor.domain.Holder;
import afterRefactor.infra.Write;
import afterRefactor.service.BillingService;
import afterRefactor.service.BookingService;
import afterRefactor.service.CheckoutService;
import afterRefactor.service.OrderService;
import afterRefactor.ui.ConsoleUI;

public class Main{
	public static void main(String[] args) {
        try {
            Holder hotel_ob = loadOrCreate();

            BookingService booking = new BookingService(hotel_ob);
            BillingService billing = new BillingService(hotel_ob);
            CheckoutService checkout = new CheckoutService(hotel_ob);
            OrderService order = new OrderService(hotel_ob);

            ConsoleUI ui = new ConsoleUI(booking, billing, checkout, order);
            ui.run();

            new Thread(new Write(hotel_ob)).start();
        } catch (Exception e) {
            System.out.println("Not a valid input");
        }
    }
	
	private static Holder loadOrCreate() {
        File f = new File("backup");
        if (f.exists()) {
            try (FileInputStream fin = new FileInputStream(f);
                 ObjectInputStream ois = new ObjectInputStream(fin)) {
                return (Holder) ois.readObject();
            } catch (Exception e) {
            }
        }
        return new Holder();
    }
}