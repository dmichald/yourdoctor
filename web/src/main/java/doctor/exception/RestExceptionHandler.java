package doctor.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder().message("ACCESS DENIED")
                .error(ex.getMessage())
                .build();
        String logInfo = String.format(" %s. %s ", ex.getLocalizedMessage(), ex.getClass().getSimpleName());
        log.error(logInfo, ex);

        return new ResponseEntity<>(errorResponse, FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder().message("ENTITY NOT FOUND")
                .error(ex.getMessage())
                .build();
        String logInfo = String.format(" %s. %s ", ex.getLocalizedMessage(), ex.getClass().getSimpleName());
        log.error(logInfo, ex);

        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<ErrorResponse> handleUnexpectedError(Throwable ex) {

        ErrorResponse apiError = new ErrorResponse("OPS, SOMETHING WENT WRONG", Collections.emptySet());
        String logInfo = String.format("%s: %s. %s ", "Unexpected error", ex.getLocalizedMessage(), ex.getClass().getSimpleName());
        log.error(logInfo, ex);

        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {

        String error = String.format("%s - required type (%s)", ex.getName(), ex.getRequiredType());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("WRONG ARGUMENT TYPE")
                .error(error)
                .build();
        log.error("WRONG ARGUMENT TYPE: ", ex);

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "METHOD NOT ALLOWED";
        String errorDesc = ex.getMethod() + " method is not supported for this request. Supported methods are " + Arrays.toString(ex.getSupportedMethods());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .error(errorDesc)
                .build();
        log.error("METHOD NOT ALLOWED: ", ex);

        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = "NO HANDLER FOUND";
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(errorMessage)
                .error(ex.getHttpMethod() + " : " + ex.getLocalizedMessage())
                .build();
        log.error("NO HANDLER FOUND: ", ex);

        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

/*    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        ex.getBindingResult().getFieldErrors().forEach(er -> {
            String error = String.format("%s : %s ", er.getField(), er.getRejectedValue());
            errorResponse.getErrors().add(error);
        });

        errorResponse.setMessage(ex.getMessage());
        log.error("NO HANDLER FOUND", ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }*/

}
