package afterRefactor.ui;

import java.util.Scanner;

import afterRefactor.service.BillingService;
import afterRefactor.service.BookingService;
import afterRefactor.service.CheckoutService;
import afterRefactor.service.OrderService;
import afterRefactor.service.RoomCatalog;

public class ConsoleUI {
	private final Scanner sc = new Scanner(System.in);
    private final BookingService bookingService;
    private final BillingService billingService;
    private final CheckoutService checkoutService;
    private final OrderService orderService;
    
    public ConsoleUI(BookingService bookingService, BillingService billingService, CheckoutService checkoutService, OrderService orderService) {
    	this.bookingService = bookingService;
        this.billingService = billingService;
        this.checkoutService = checkoutService;
        this.orderService = orderService;
    }
    
    public void run() {
        int ch, ch2;
        char wish;

        x:
        do {
            printMainMenu();
            ch = sc.nextInt();
            switch (ch) {
                case 1:
                    printRoomTypeMenu();
                    ch2 = sc.nextInt();
                    RoomCatalog.printFeatures(ch2);
                    break;
                case 2:
                    printRoomTypeMenu();
                    ch2 = sc.nextInt();
                    int free = bookingService.availability(ch2);
                    if (free >= 0) {
                        System.out.println("Number of rooms available : " + free);
                    } else {
                        System.out.println("Enter valid option");
                    }
                    break;
                case 3:
                    printRoomTypeMenu();
                    ch2 = sc.nextInt();
                    handleBooking(ch2);
                    break;
                case 4:
                    handleOrder();
                    break;
                case 5:
                    handleCheckout();
                    break;
                case 6:
                    break x;
            }

            System.out.println("\nContinue : (y/n)");
            wish = sc.next().charAt(0);
            if (!(wish == 'y' || wish == 'Y' || wish == 'n' || wish == 'N')) {
                System.out.println("Invalid Option");
                System.out.println("\nContinue : (y/n)");
                wish = sc.next().charAt(0);
            }
        } while (wish == 'y' || wish == 'Y');
    }
    
    private void printMainMenu() {
        System.out.println("\nEnter your choice :\n1.Display room details\n2.Display room availability " +
                "\n3.Book\n4.Order food\n5.Checkout\n6.Exit\n");
    }

    private void printRoomTypeMenu() {
        System.out.println("\nChoose room type :\n1.Luxury Double Room \n2.Deluxe Double Room " +
                "\n3.Luxury Single Room\n4.Deluxe Single Room\n");
    }

    private void printFoodMenu() {
        System.out.println("\n==========\n   Menu:  \n==========\n\n" +
                "1.Sandwich\tRs.50\n2.Pasta\t\tRs.60\n3.Noodles\tRs.70\n4.Coke\t\tRs.30\n");
    }
    
    private void handleBooking(int roomType) {
        // 1. Ask service which rooms are free
        int[] freeRoomNumbers = bookingService.listFreeRoomNumbers(roomType);
        if (freeRoomNumbers == null) {
            System.out.println("Enter valid option");
            return;
        }
        System.out.println("\nChoose room number from : ");
        for (int n : freeRoomNumbers) System.out.print(n + ",");

        // 2. Read room number
        System.out.print("\nEnter room number: ");
        int rn;
        try {
            rn = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid Option");
            return;
        }

        // 3. Collect customer info via UI
        String name, contact, gender;
        String name2 = null, contact2 = null, gender2 = "";
        System.out.print("\nEnter customer name: ");
        name = sc.next();
        System.out.print("Enter contact number: ");
        contact = sc.next();
        System.out.print("Enter gender: ");
        gender = sc.next();
        if (roomType < 3) {
            System.out.print("Enter second customer name: ");
            name2 = sc.next();
            System.out.print("Enter contact number: ");
            contact2 = sc.next();
            System.out.print("Enter gender: ");
            gender2 = sc.next();
        }

        // 4. Hand off to service
        boolean ok = bookingService.book(roomType, rn,
                name, contact, gender, name2, contact2, gender2);
        if (ok) {
            System.out.println("Room Booked");
        } else {
            System.out.println("Invalid Option");
        }
    }
    
    private void handleOrder() {
        System.out.print("Room Number -");
        int globalRoomNo = sc.nextInt();
        int[] parsed = parseGlobalRoomNumber(globalRoomNo);
        if (parsed == null) {
            System.out.println("Room doesn't exist");
            return;
        }
        int localRn = parsed[0], roomType = parsed[1];

        printFoodMenu();
        char wish;
        try {
            do {
                int itemno = sc.nextInt();
                System.out.print("Quantity- ");
                int qty = sc.nextInt();
                orderService.order(roomType, localRn, itemno, qty);

                System.out.println("Do you want to order anything else ? (y/n)");
                wish = sc.next().charAt(0);
            } while (wish == 'y' || wish == 'Y');
        } catch (NullPointerException e) {
            System.out.println("\nRoom not booked");
        } catch (Exception e) {
            System.out.println("Cannot be done");
        }
    }
    
    private void handleCheckout() {
        System.out.print("Room Number -");
        int globalRoomNo = sc.nextInt();
        int[] parsed = parseGlobalRoomNumber(globalRoomNo);
        if (parsed == null) {
            System.out.println("Room doesn't exist");
            return;
        }
        int localRn = parsed[0], roomType = parsed[1];

        String occupantName = checkoutService.occupantName(roomType, localRn);
        if (occupantName == null) {
            System.out.println("Empty Already");
            return;
        }
        System.out.println("Room used by " + occupantName);
        System.out.println("Do you want to checkout ?(y/n)");
        char w = sc.next().charAt(0);
        if (w == 'y' || w == 'Y') {
            String bill = billingService.buildBill(roomType, localRn);
            System.out.println(bill);
            checkoutService.deallocate(roomType, localRn);
            System.out.println("Deallocated succesfully");
        }
    }
    
    private int[] parseGlobalRoomNumber(int n) {
        if (n > 60 || n <= 0) return null;
        if (n > 40) return new int[]{n - 41, 4};
        if (n > 30) return new int[]{n - 31, 3};
        if (n > 10) return new int[]{n - 11, 2};
        return new int[]{n - 1, 1};
    }


}
