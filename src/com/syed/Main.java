import com.syed.booking.CarBooking;
import com.syed.booking.CarBookingService;
import com.syed.car.Car;
import com.syed.car.CarService;
import com.syed.user.User;
import com.syed.user.UserService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    System.out.println("Car Booking CLI");

    Scanner scanner = new Scanner(System.in);
    int userInput = 0;
    boolean inLoop = true;

    CarBookingService carBookingService = new CarBookingService();
    do{
        System.out.println("""
            ===============Car Booking System Menu=================
            1 - Book Car
            2 - Delete Booking
            3 - View All User Booked Cars
            4 - View All Bookings
            5 - View Available Cars
            6 - View Available Electric Cars
            7 - View All Users
            8 - Exit
            """);

        try{
            userInput = scanner.nextInt();
            scanner.nextLine();
        } catch(InputMismatchException e){
            System.out.println("Please select a number from 1-8");
            scanner.nextLine();
        }


        switch (userInput){
            case 1:
                try{
                    System.out.println("New Booking");
                    System.out.println("Enter a User ID: ");
                    UUID userIDInput = UUID.fromString(scanner.nextLine());

                    System.out.println("Enter a Car ID: ");
                    UUID carIDInput = UUID.fromString(scanner.nextLine());


                    System.out.println("Enter start date for booking(dd/MM/yyyy):");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate sDate = LocalDate.parse(scanner.nextLine(), formatter);

                    System.out.println("Enter end date for booking(dd/MM/yyyy):");
                    LocalDate eDate = LocalDate.parse(scanner.nextLine(), formatter);

                    CarBooking newBooking = carBookingService.bookCar(userIDInput,carIDInput,sDate,eDate);
                    System.out.println("Booking Made");
                    System.out.println(newBooking);
                }
                catch (IllegalArgumentException | DateTimeParseException | IllegalStateException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                System.out.println("Delete booking by ID");
                System.out.println("Enter booking id:");
                UUID bookingIDInput = UUID.fromString(scanner.nextLine());
                carBookingService.deleteBooking(bookingIDInput);
                System.out.println("Booking cancelled");
                break;
            case 3:
                System.out.println("Get bookings for a User");
                System.out.println("Enter user id:");
                UUID userIDInput = UUID.fromString(scanner.nextLine());
                CarBooking[] bookings = carBookingService.getBookingsByUser(userIDInput);
                System.out.println("Bookings for User :");
                for(CarBooking carBooking : bookings){
                    System.out.println(carBooking);
                }
                break;
            case 4:
                System.out.println("Showing all bookings");
                CarBooking[] carBookings = carBookingService.getAllCarBookings();
                for(CarBooking carBooking : carBookings){
                    System.out.println(carBooking);
                }
                break;
            case 5:
                System.out.println("Available cars");
                Car[] availableCars = carBookingService.getAvailableCars();
                for(Car car : availableCars){
                    System.out.println(car);
                }
                break;
            case 6:
                System.out.println("Available Electric cars");
                Car[] availableElectricCars = carBookingService.getAvailableElectricCars();
                for(Car car : availableElectricCars){
                    System.out.println(car);
                }
                break;
            case 7:
                UserService userService = new UserService();
                for(User user : userService.getAllUsers()){
                    System.out.println(user);
                }
                break;
            case 8:
                System.out.println("Exiting");
                inLoop = false;
                break;
            default:
                System.out.println("Please select options 1-8");
                break;
        }
    }while(inLoop);
}
