package JoanRuiz.mindnet.exception;

public class PostException extends RuntimeException {
    public PostException(String message) {
        super("⚠️ Post Error: "+ message);
    }
}
