package roomescape.exception;

import static roomescape.exception.ExceptionType.INVALID_DATE_TIME_FORMAT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.domain.payment.ApiCallException;
import roomescape.dto.ErrorResponse;

@ControllerAdvice
public class RoomescapeExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(INVALID_DATE_TIME_FORMAT.getStatus())
                .body(new ErrorResponse(INVALID_DATE_TIME_FORMAT.getMessage()));
    }

    @ExceptionHandler(RoomescapeException.class)
    public ResponseEntity<ErrorResponse> handle(RoomescapeException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(ApiCallException.class)
    public ResponseEntity<ErrorResponse> handle(ApiCallException e) {
        logger.error(e.getMessage(), e);
        String errorMessage = getApiErrorMessage(e);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(errorMessage));
    }

    private String getApiErrorMessage(ApiCallException e) {
        if (!TossPaymentApiUserExceptionCode.hasErrorCode(e.getCode())) {
            return "서버에 오류가 발생했습니다.";
        }
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity
                .internalServerError()
                .body(new ErrorResponse("서버에 오류가 발생했습니다."));
    }
}
