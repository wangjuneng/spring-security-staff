package org.springframework.security.acls.jdbc;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.acls.model.ObjectIdentity;

/**
 * Utility class for helping convert database representations of {@link ObjectIdentity#getIdentifier()} into the correct
 * Java type as specified by <code>acl_class.class_id_type</code>.
 * 
 * @author paulwheeler
 */
public class AclClassIdUtils {
    private static final String DEFAULT_CLASS_ID_TYPE_COLUMN_NAME = "class_id_type";

    private static final Log log = LogFactory.getLog(AclClassIdUtils.class);

    private ConversionService conversionService;

    public AclClassIdUtils() {
    }

    Serializable identifierFrom(Serializable identifier, ResultSet resultSet) throws SQLException {
        if (isString(identifier) && hasValidClassIdType(resultSet)
            && canConvertFromStringTo(classIdTypeFrom(resultSet))) {

            identifier = convertFromStringTo((String) identifier, classIdTypeFrom(resultSet));
        }
        else {
            // Assume it should be a Long type
            identifier = convertToLong(identifier);
        }

        return identifier;
    }

    private boolean hasValidClassIdType(ResultSet resultSet) throws SQLException {
        boolean hasClassIdType = false;
        try {
            hasClassIdType = classIdTypeFrom(resultSet) != null;
        }
        catch (SQLException e) {
            log.debug("Unable to obtain the class id type", e);
        }
        return hasClassIdType;
    }

    private <T extends Serializable> Class<T> classIdTypeFrom(ResultSet resultSet) throws SQLException {
        return classIdTypeFrom(resultSet.getString(DEFAULT_CLASS_ID_TYPE_COLUMN_NAME));
    }

    private <T extends Serializable> Class<T> classIdTypeFrom(String className) {
        Class targetType = null;
        if (className != null) {
            try {
                targetType = Class.forName(className);
            }
            catch (ClassNotFoundException e) {
                log.debug("Unable to find class id type on classpath", e);
            }
        }
        return targetType;
    }

    private <T> boolean canConvertFromStringTo(Class<T> targetType) {
        return hasConversionService() && conversionService.canConvert(String.class, targetType);
    }

    private <T extends Serializable> T convertFromStringTo(String identifier, Class<T> targetType) {
        return conversionService.convert(identifier, targetType);
    }

    /**
     * Converts to a {@link Long}, attempting to use the {@link ConversionService} if available.
     * 
     * @param identifier The identifier
     * @return Long version of the identifier
     * @throws NumberFormatException if the string cannot be parsed to a long.
     * @throws org.springframework.core.convert.ConversionException if a conversion exception occurred
     * @throws IllegalArgumentException if targetType is null
     */
    private Long convertToLong(Serializable identifier) {
        Long idAsLong;
        if (hasConversionService()) {
            idAsLong = conversionService.convert(identifier, Long.class);
        }
        else {
            idAsLong = Long.valueOf(identifier.toString());
        }
        return idAsLong;
    }

    private boolean hasConversionService() {
        return conversionService != null;
    }

    private boolean isString(Serializable object) {
        return object.getClass().isAssignableFrom(String.class);
    }

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }
}
