// exepction for errors such as withdrawing too much or any other banking problems.
public class MyCustomException extends Exception {
    // my constructor
    public MyCustomException(String message) {
        // the message will be passed to the base execption class
        super(message);
    }
}