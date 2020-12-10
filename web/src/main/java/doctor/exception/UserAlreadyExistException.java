package doctor.exception;

public class UserAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 665604690490855460L;

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
