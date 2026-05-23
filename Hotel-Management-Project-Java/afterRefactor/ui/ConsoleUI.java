package afterRefactor.ui;

import afterRefactor.domain.Guest;
import afterRefactor.domain.RoomType;
import afterRefactor.service.BookingService;
import afterRefactor.service.BillingService;
import afterRefactor.service.CheckoutService;
import afterRefactor.service.OrderService;
import afterRefactor.service.RoomCatalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based UI. All System.out / Scanner interaction lives here.
 *
 * Step 4: the booking flow now collects a List<Guest> and passes it
 * as a single argument, instead of 6 loose strings.
 */
public class ConsoleUI {
    private final Scanner sc = new Scanner(System.in);
    private final BookingService bookingService;
    private final BillingService billingService;
    private final CheckoutService checkoutService;
    private final OrderService orderService;

    public ConsoleUI(BookingService bookingService,
                     BillingService billingService,
                     CheckoutService checkoutService,
                     OrderService orderService) {
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
                case 2: {
                    printRoomTypeMenu();
                    ch2 = sc.nextInt();
                    RoomType type = RoomType.fromCode(ch2);
                    int free = bookingService.availability(type);
                    if (free >= 0) {
                        System.out.println("Number of rooms available : " + free);
                    } else {
                        System.out.println("Enter valid option");
                    }
                    break;
                }
                case 3: {
                    printRoomTypeMenu();
                    ch2 = sc.nextInt();
                    RoomType type = RoomType.fromCode(ch2);
                    handleBooking(type);
                    break;
                }
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

    private void handleBooking(RoomType type) {
        int[] freeRoomNumbers = bookingService.listFreeRoomNumbers(type);
        if (freeRoomNumbers == null) {
            System.out.println("Enter valid option");
            return;
        }
        System.out.println("\nChoose room number from : ");
        for (int n : freeRoomNumbers) System.out.print(n + ",");

        System.out.print("\nEnter room number: ");
        int rn;
        try {
            rn = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid Option");
            return;
        }

        List<Guest> guests = new ArrayList<>();
        guests.add(promptGuest("first"));
        if (type != null && type.isDouble()) {
            guests.add(promptGuest("second"));
        }

        boolean ok = bookingService.book(type, rn, guests);
        if (ok) {
            System.out.println("Room Booked");
        } else {
            System.out.println("Invalid Option");
        }
    }

    /** Extracted from handleBooking — the duplicated 6 lines for guest #1 and #2. */
    private Guest promptGuest(String position) {
        System.out.print("\nEnter " + position + " customer name: ");
        String name = sc.next();
        System.out.print("Enter contact number: ");
        String contact = sc.next();
        System.out.print("Enter gender: ");
        String gender = sc.next();
        return new Guest(name, contact, gender);
    }

    private void handleOrder() {
        System.out.print("Room Number -");
        int globalRoomNo = sc.nextInt();
        Object[] parsed = RoomType.resolveGlobal(globalRoomNo);
        if (parsed == null) {
            System.out.println("Room doesn't exist");
            return;
        }
        RoomType type = (RoomType) parsed[0];
        int localRn = (int) parsed[1];

        printFoodMenu();
        char wish;
        try {
            do {
                int itemno = sc.nextInt();
                System.out.print("Quantity- ");
                int qty = sc.nextInt();
                orderService.order(type, localRn, itemno, qty);

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
        Object[] parsed = RoomType.resolveGlobal(globalRoomNo);
        if (parsed == null) {
            System.out.println("Room doesn't exist");
            return;
        }
        RoomType type = (RoomType) parsed[0];
        int localRn = (int) parsed[1];

        String occupantName = checkoutService.occupantName(type, localRn);
        if (occupantName == null || occupantName.isEmpty()) {
            System.out.println("Empty Already");
            return;
        }
        System.out.println("Room used by " + occupantName);
        System.out.println("Do you want to checkout ?(y/n)");
        char w = sc.next().charAt(0);
        if (w == 'y' || w == 'Y') {
            String bill = billingService.buildBill(type, localRn);
            System.out.println(bill);
            checkoutService.deallocate(type, localRn);
            System.out.println("Deallocated succesfully");
        }
    }
}
