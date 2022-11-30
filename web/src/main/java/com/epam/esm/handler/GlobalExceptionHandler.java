package com.epam.esm.handler;

import com.epam.esm.config.localization.Translator;
import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final HttpStatus internalServerErrorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private final HttpStatus badRequestStatus = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(DaoException.class)
    public ResponseEntity<ExceptionResponse> handleDaoException(DaoException daoException) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(daoException.getLocalizedMessage(), ConstantMessages.DAO_ERROR_MESSAGE);
        exceptionResponse.setErrorCode(internalServerErrorStatus + ConstantMessages.DAO_ERROR_MESSAGE);
        return new ResponseEntity<>(exceptionResponse, internalServerErrorStatus);
    }

    @ExceptionHandler({DuplicateResourceException.class})
    public ResponseEntity<ExceptionResponse> handle(DuplicateResourceException exception) {
        String errorMessage = exception.getErrorMessage();
        if (errorMessage.contains(ConstantMessages.EXISTING_GIFT_CERTIFICATE_NAME) ||
                errorMessage.contains(ConstantMessages.EXISTING_TAG_NAME)) {
            errorMessage = Translator.toLocale(exception.getErrorMessage());
        }
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(errorMessage, exception.getErrorCode());
        return new ResponseEntity<>(exceptionResponse, badRequestStatus);
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ExceptionResponse> handleException(InvalidFieldException invalidFieldException) {
        String errorMessage = invalidFieldException.getErrorMessage();
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_TAG)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_TAG,
                    Translator.toLocale(ConstantMessages.INVALID_TAG));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE_NAME)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE_NAME,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE_NAME));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE_DESCRIPTION)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE_DESCRIPTION,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE_DESCRIPTION));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE_DURATION)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE_DURATION,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE_DURATION));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE_PRICE)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE_PRICE,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE_PRICE));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE_CREATE_DATE)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE_CREATE_DATE,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE_CREATE_DATE));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE_UPDATE_DATE)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE_UPDATE_DATE,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE_UPDATE_DATE));
        }
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(errorMessage, invalidFieldException.getErrorCode());
        return new ResponseEntity<>(exceptionResponse, badRequestStatus);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionResponse> handleServiceException(ServiceException serviceException) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(serviceException.getLocalizedMessage(), ConstantMessages.SERVICE_ERROR_MESSAGE);
        exceptionResponse.setErrorCode(internalServerErrorStatus + ConstantMessages.SERVICE_ERROR_MESSAGE);
        return new ResponseEntity<>(exceptionResponse, internalServerErrorStatus);
    }
}
