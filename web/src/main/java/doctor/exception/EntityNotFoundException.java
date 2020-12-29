package doctor.exception;

public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -7529749469389666991L;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
