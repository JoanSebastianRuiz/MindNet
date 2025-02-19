package JoanRuiz.mindnet.exception;

public class CommentException extends RuntimeException {
    public CommentException(String message) {
        super("⚠️ Comment Error: "+ message);
    }
}
