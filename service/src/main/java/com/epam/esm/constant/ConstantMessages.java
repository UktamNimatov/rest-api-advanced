package com.epam.esm.constant;

public enum ConstantMessages {
    ;
    public static final Integer ERROR_CODE_404 = 40401;
    public static final Integer ERROR_CODE_400 = 40001;
    public static final Integer ERROR_CODE_409 = 40901;
    public static final Integer ERROR_CODE_405 = 40501;

    public static final String RESOURCE_NOT_FOUND = "requested.resource.not.found";

    public static final String DAO_ERROR_MESSAGE = " Error in DAO layer";
    public static final String SERVICE_ERROR_MESSAGE = " Error in SERVICE layer";

    public static final String SUCCESSFULLY_DELETED = "Entity deleted successfully id = ";
    public static final String DELETE_FAILED = "Could not find entity with id = ";

    public static final String INVALID_GIFT_CERTIFICATE = "invalid.gift.certificate";
    public static final String INVALID_TAG = "invalid.tag";
    public static final String INVALID_INPUT = " Invalid input ";
    public static final String INVALID_TAG_NAME = "invalid.tag.name";

    public static final String EXISTING_GIFT_CERTIFICATE_NAME = "already.existing.giftcertificate.name";
    public static final String EXISTING_TAG_NAME = "already.existing.tag.name";

    public static final String NO_HANDLER_FOUND = "No handler found for";
    public static final String NO_HANDLER_FOUND_MESSAGE = "no.handler.found";

    public static final String CONVERSION_NOT_SUPPORTED = "conversion.not.supported";
    public static final String CONVERSION_NOT_SUPPORTED_MESSAGE = "JSON parse error: Cannot deserialize value";

    public static final String METHOD_POST_NOT_SUPPORTED = "method.post.not.supported";
    public static final String METHOD_PUT_NOT_SUPPORTED = "method.put.not.supported";
    public static final String METHOD_POST_NOT_SUPPORTED_MESSAGE = "Request method 'POST' not supported";
    public static final String METHOD_PUT_NOT_SUPPORTED_MESSAGE = "Request method 'PUT' not supported";

    public static final String FAILED_TO_CONVERT = "failed.to.convert";
    public static final String FAILED_TO_CONVERT_MESSAGE = "Failed to convert value";

    public static final String INVALID_GIFT_CERTIFICATE_NAME = "invalid.name.gift.certificate";
    public static final String INVALID_GIFT_CERTIFICATE_PRICE = "invalid.price.gift.certificate";
    public static final String INVALID_GIFT_CERTIFICATE_DURATION = "invalid.duration.gift.certificate";
    public static final String INVALID_GIFT_CERTIFICATE_DESCRIPTION = "invalid.description.gift.certificate";
    public static final String INVALID_GIFT_CERTIFICATE_CREATE_DATE = "invalid.create.date.gift.certificate";
    public static final String INVALID_GIFT_CERTIFICATE_UPDATE_DATE = "invalid.update.date.gift.certificate";

    public static final String INVALID_PAGINATION = "pagination.invalid";

    public static final String INVALID_USERNAME = "invalid.username";
    public static final String EXISTING_USERNAME = "already.existing.username";

    public static final String OPERATION_NOT_SUPPORTED = "this.operation.not.supported";
}
