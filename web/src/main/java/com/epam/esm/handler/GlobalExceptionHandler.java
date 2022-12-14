package com.epam.esm.handler;

import com.epam.esm.config.localization.Translator;
import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.exception.*;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final HttpStatus internalServerErrorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private final HttpStatus badRequestStatus = HttpStatus.BAD_REQUEST;
    private final HttpStatus notFoundStatus = HttpStatus.NOT_FOUND;

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


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(ResourceNotFoundException exception) {
        String localizedMessage = Translator.toLocale(exception.getErrorMessage());
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(localizedMessage, exception.getErrorCode());
        return new ResponseEntity<>(exceptionResponse, notFoundStatus);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getMessage();
        if (errorMessage.contains(ConstantMessages.NO_HANDLER_FOUND)) {
            errorMessage = errorMessage.replace(ConstantMessages.NO_HANDLER_FOUND,
                    Translator.toLocale(ConstantMessages.NO_HANDLER_FOUND_MESSAGE));
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage,
                String.valueOf(ConstantMessages.ERROR_CODE_404));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getMessage();
        logger.info("()()()()()"+errorMessage);
        logger.info("<><><><><><>"+"handleHttpMessageNotReadable");
        if (errorMessage.contains(ConstantMessages.CONVERSION_NOT_SUPPORTED_MESSAGE)) {
            errorMessage = errorMessage.replace(ConstantMessages.CONVERSION_NOT_SUPPORTED_MESSAGE,
                    Translator.toLocale(ConstantMessages.CONVERSION_NOT_SUPPORTED));
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage,
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getMessage();
        logger.info("<><><><><><>"+"handleConversionNotSupported");
        if (errorMessage.contains(ConstantMessages.CONVERSION_NOT_SUPPORTED_MESSAGE)) {
            errorMessage = errorMessage.replace(ConstantMessages.CONVERSION_NOT_SUPPORTED_MESSAGE,
                    Translator.toLocale(ConstantMessages.CONVERSION_NOT_SUPPORTED));
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage,
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getMessage();
        logger.info("<><><><><><>"+"handleHttpRequestMethodNotSupported");
        if (errorMessage.contains(ConstantMessages.METHOD_POST_NOT_SUPPORTED_MESSAGE)) {
            errorMessage = errorMessage.replace(ConstantMessages.METHOD_POST_NOT_SUPPORTED_MESSAGE,
                    Translator.toLocale(ConstantMessages.METHOD_POST_NOT_SUPPORTED));
        }
        if (errorMessage.contains(ConstantMessages.METHOD_PUT_NOT_SUPPORTED_MESSAGE)) {
            errorMessage = errorMessage.replace(ConstantMessages.METHOD_PUT_NOT_SUPPORTED_MESSAGE,
                    Translator.toLocale(ConstantMessages.METHOD_PUT_NOT_SUPPORTED));
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage,
                String.valueOf(ConstantMessages.ERROR_CODE_405));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ExceptionResponse> handleUnsupportedOperationException(UnsupportedOperationException exception) {
        String errorMessage = exception.getMessage();
        if (errorMessage.contains(ConstantMessages.OPERATION_NOT_SUPPORTED)) {
            errorMessage = errorMessage.replace(ConstantMessages.OPERATION_NOT_SUPPORTED,
                    Translator.toLocale(ConstantMessages.OPERATION_NOT_SUPPORTED));
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage,
                String.valueOf(ConstantMessages.ERROR_CODE_405));
        return new ResponseEntity<>(exceptionResponse, notFoundStatus);
    }


    /*methods below are of secondary importance*/

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getMessage();
        logger.info("<><><><><><>"+"handleTypeMismatch");
        if (errorMessage.contains(ConstantMessages.FAILED_TO_CONVERT_MESSAGE)) {
            errorMessage = errorMessage.replace(ConstantMessages.FAILED_TO_CONVERT_MESSAGE,
                    Translator.toLocale(ConstantMessages.FAILED_TO_CONVERT));
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage,
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info("<><><><><><>"+"handleHttpMediaTypeNotSupported");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info("<><><><><><>"+"handleHttpMediaTypeNotAcceptable");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info("<><><><><><>"+"handleMissingPathVariable");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info("<><><><><><>"+"handleMissingServletRequestParameter");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info("<><><><><><>"+"handleServletRequestBindingException");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info("<><><><><><>"+"handleHttpMessageNotWritable");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info("<><><><><><>"+"handleMethodArgumentNotValid");
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }
}
