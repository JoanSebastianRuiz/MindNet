package JoanRuiz.mindnet.exception;

public class ImageException extends RuntimeException {
    public ImageException(String message) {
        super("⚠️ Image error "+ message);
    }
}
