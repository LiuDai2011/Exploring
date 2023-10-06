package Exploring.util;

public class VelocityException extends RuntimeException {
    public VelocityException() {
        super();
    }

    public VelocityException(String message) {
        super(message);
    }

    public VelocityException(String message, Throwable cause) {
        super(message, cause);
    }
    public VelocityException(Throwable cause) {
        super(cause);
    }
    public VelocityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
