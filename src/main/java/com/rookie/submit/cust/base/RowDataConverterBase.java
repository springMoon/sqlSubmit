package com.rookie.submit.cust.source.base;

import org.apache.flink.formats.common.TimeFormats;
import org.apache.flink.table.data.DecimalData;
import org.apache.flink.table.data.StringData;
import org.apache.flink.table.data.TimestampData;
import org.apache.flink.table.types.logical.ArrayType;
import org.apache.flink.table.types.logical.DecimalType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.TimeType;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * customize data convert
 * <p>
 * parse string to special type
 */
public class RowDataConverterBase {

    public static Object createConverter(LogicalType type, String value) {
        switch (type.getTypeRoot()) {
            case NULL:
                return null;
            case BOOLEAN:
                return Boolean.parseBoolean(value);
            case TINYINT:
                return Byte.parseByte(value);
            case SMALLINT:
                return Short.parseShort(value);
            case INTEGER:
            case INTERVAL_YEAR_MONTH:
                return Integer.parseInt(value);
            case BIGINT:
            case INTERVAL_DAY_TIME:
                return Long.parseLong(value);
            case DATE:
                return (int) Date.valueOf(value).toLocalDate().toEpochDay();
            case TIME_WITHOUT_TIME_ZONE:
                return convertToTime((TimeType) type, value);
            case TIMESTAMP_WITHOUT_TIME_ZONE:

                return TimestampData.fromLocalDateTime(LocalDateTime.parse(value, TimeFormats.SQL_TIMESTAMP_FORMAT));
            case TIMESTAMP_WITH_LOCAL_TIME_ZONE:
                return TimestampData.fromLocalDateTime(LocalDateTime.parse(value, TimeFormats.SQL_TIMESTAMP_WITH_LOCAL_TIMEZONE_FORMAT));
            case FLOAT:
                return Float.parseFloat(value);
            case DOUBLE:
                return Double.parseDouble(value);
            case CHAR:
            case VARCHAR:
                return StringData.fromString(value);
            case BINARY:
            case VARBINARY:
                return value.getBytes();
            case DECIMAL:
                return createDecimalConverter((DecimalType) type, value);
            case ARRAY:
                return createArrayConverter((ArrayType) type, value);
            case ROW:
            case MAP:
            case MULTISET:
            case RAW:
            default:
                throw new UnsupportedOperationException("Unsupported type: " + type);
        }
    }


    private static int convertToTime(TimeType timeType, String value) {
        int precision = timeType.getPrecision();
        if (precision > 3) {
            throw new IllegalArgumentException("Csv does not support TIME type with precision: " + precision + ", it only supports precision 0 ~ 3.");
        } else {
            LocalTime localTime = LocalTime.parse(value);
            int mills = (int) (localTime.toNanoOfDay() / 1000000L);
            if (precision == 2) {
                mills = mills / 10 * 10;
            } else if (precision == 1) {
                mills = mills / 100 * 100;
            } else if (precision == 0) {
                mills = mills / 1000 * 1000;
            }
            return mills;
        }
    }


    private static DecimalData createDecimalConverter(DecimalType decimalType, String value) {
        int precision = decimalType.getPrecision();
        int scale = decimalType.getScale();

        BigDecimal bigDecimal = new BigDecimal(value);

        return DecimalData.fromBigDecimal(bigDecimal, precision, scale);
    }

    // todo
    private static Object[] createArrayConverter(ArrayType arrayType, String value) {
        Object[] arr = value.split(",");
        return arr;
    }

}
