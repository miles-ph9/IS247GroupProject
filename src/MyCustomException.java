// exepction for errors such as withdrawing too much
public class MyCustomException extends Exception {
    public MyCustomException(String message) {
        super(message);
    }
}