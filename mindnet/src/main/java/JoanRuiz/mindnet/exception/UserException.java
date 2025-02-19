package JoanRuiz.mindnet.exception;

public class UserException extends RuntimeException{
    public UserException(String message) {
        super("⚠️ User Error: "+ message);
    }
}
