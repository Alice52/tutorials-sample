package cn.edu.ntu.javaee.springboot.validation.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author zack <br>
 * @create 2020-08-01 19:29 <br>
 * @project validation <br>
 */
public enum ErrorMessageEnum {
    // common
    SYSTEM_ERROR(999999, "Internal Server Error"),
    BEAN_VALIDATION_ERROR(400400, "Validate bean property error");

    private static final Logger LOG = LoggerFactory.getLogger(ErrorMessageEnum.class);
    private Integer errorCode;
    private String errorMsg;

    ErrorMessageEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * get ErrorMessage by error code.
     *
     * @param errorCode
     * @return ErrorMessage
     */
    public static ErrorMessageEnum getByErrorCode(final Integer errorCode) {
        return Arrays.stream(values())
                .filter(enumErrorCode -> enumErrorCode.getErrorCode().equals(errorCode))
                .findFirst()
                .orElse(null);
    }

    /**
     * get ErrorMessage by enum name, such as UNKNOWN_EXCEPTION.
     *
     * @param enumName
     * @return ErrorMessage
     */
    public static ErrorMessageEnum getByEnumName(final String enumName) {
        Optional<ErrorMessageEnum> errorCode = getValueOf(ErrorMessageEnum.class, enumName);
        return errorCode.isPresent() ? errorCode.get() : null;
    }

    public static String getMessageByCode(final String errorCode) {
        Optional<ErrorMessageEnum> errorMessage =
                Arrays.stream(values())
                        .filter(enumErrorCode -> enumErrorCode.getErrorCode().equals(errorCode))
                        .findFirst();
        return errorMessage.isPresent() ? errorMessage.get().getErrorMsg() : null;
    }

    public static Integer getCodeByMessage(final String errorMessage) {
        Optional<ErrorMessageEnum> errorCode =
                Arrays.stream(ErrorMessageEnum.class.getEnumConstants())
                        .filter(enumErrorCode -> enumErrorCode.getErrorMsg().equals(errorMessage))
                        .findFirst();
        return errorCode.isPresent() ? errorCode.get().getErrorCode() : null;
    }

    /**
     * Convert enum to Optional, and handle IllegalArgumentException.
     *
     * @param enumType
     * @param name
     * @return Optional<Enum> or Optional.empty()
     */
    private static Optional<ErrorMessageEnum> getValueOf(
            Class<ErrorMessageEnum> enumType, String name) {
        ErrorMessageEnum enumValue;
        try {
            enumValue = Enum.valueOf(enumType, name);
        } catch (IllegalArgumentException ex) {
            LOG.info(
                    "occurs IllegalArgumentException when get enum[{}] by enum name: {}, so return {}",
                    enumType,
                    name,
                    Optional.empty());
            return Optional.empty();
        }
        return Optional.ofNullable(enumValue);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String toString() {
        return "ErrorMessages{" + "code=" + errorCode + ", errorMsg='" + errorMsg + '\'' + '}';
    }
}
