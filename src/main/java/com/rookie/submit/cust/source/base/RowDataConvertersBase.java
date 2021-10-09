package com.rookie.submit.cust.source.base;

import org.apache.flink.formats.common.TimeFormats;
import org.apache.flink.formats.csv.CsvToRowDataConverters;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.flink.table.data.*;
import org.apache.flink.table.types.logical.*;
import org.apache.flink.table.types.logical.utils.LogicalTypeUtils;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RowDataConvertersBase {

    private final boolean ignoreParseErrors;

    public RowDataConvertersBase(boolean ignoreParseErrors) {
        this.ignoreParseErrors = ignoreParseErrors;
    }

    public RowDataConvertersBase.RowDataConverterBase createRowConverter(RowType rowType, boolean isTopLevel) {
        RowDataConvertersBase.RowDataConverterBase[] fieldConverters = (RowDataConvertersBase.RowDataConverterBase[]) rowType.getFields().stream().map(RowType.RowField::getType).map(this::createNullableConverter).toArray((x$0) -> {
            return new RowDataConvertersBase.RowDataConverterBase[x$0];
        });
        String[] fieldNames = (String[]) rowType.getFieldNames().toArray(new String[0]);
        int arity = fieldNames.length;
        return (jsonNode) -> {
            int nodeSize = jsonNode.size();
            if (nodeSize != 0) {
                validateArity(arity, nodeSize, this.ignoreParseErrors);
                GenericRowData row = new GenericRowData(arity);

                for (int i = 0; i < arity; ++i) {
                    JsonNode field;
                    if (isTopLevel) {
                        field = jsonNode.get(fieldNames[i]);
                    } else {
                        field = jsonNode.get(i);
                    }

                    try {
                        if (field == null) {
                            row.setField(i, (Object) null);
                        } else {
                            row.setField(i, fieldConverters[i].convert(field));
                        }
                    } catch (Throwable var11) {
                        throw new RuntimeException(String.format("Fail to deserialize at field: %s.", fieldNames[i]), var11);
                    }
                }

                return row;
            } else {
                return null;
            }
        };
    }

    private RowDataConvertersBase.RowDataConverterBase createNullableConverter(LogicalType type) {
        RowDataConvertersBase.RowDataConverterBase converter = this.createConverter(type);
        return (jsonNode) -> {
            if (jsonNode != null && !jsonNode.isNull()) {
                try {
                    return converter.convert(jsonNode);
                } catch (Throwable var4) {
                    if (!this.ignoreParseErrors) {
                        throw var4;
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
        };
    }

    private RowDataConvertersBase.RowDataConverterBase createConverter(LogicalType type) {
        switch (type.getTypeRoot()) {
            case NULL:
                return (jsonNode) -> {
                    return null;
                };
            case BOOLEAN:
                return this::convertToBoolean;
            case TINYINT:
                return (jsonNode) -> {
                    return Byte.parseByte(jsonNode.asText().trim());
                };
            case SMALLINT:
                return (jsonNode) -> {
                    return Short.parseShort(jsonNode.asText().trim());
                };
            case INTEGER:
            case INTERVAL_YEAR_MONTH:
                return this::convertToInt;
            case BIGINT:
            case INTERVAL_DAY_TIME:
                return this::convertToLong;
            case DATE:
                return this::convertToDate;
            case TIME_WITHOUT_TIME_ZONE:
                return this.convertToTime((TimeType) type);
            case TIMESTAMP_WITHOUT_TIME_ZONE:
                return (jsonNode) -> {
                    return this.convertToTimestamp(jsonNode, TimeFormats.SQL_TIMESTAMP_FORMAT);
                };
            case TIMESTAMP_WITH_LOCAL_TIME_ZONE:
                return (jsonNode) -> {
                    return this.convertToTimestamp(jsonNode, TimeFormats.SQL_TIMESTAMP_WITH_LOCAL_TIMEZONE_FORMAT);
                };
            case FLOAT:
                return this::convertToFloat;
            case DOUBLE:
                return this::convertToDouble;
            case CHAR:
            case VARCHAR:
                return this::convertToString;
            case BINARY:
            case VARBINARY:
                return this::convertToBytes;
            case DECIMAL:
                return this.createDecimalConverter((DecimalType) type);
            case ARRAY:
                return this.createArrayConverter((ArrayType) type);
            case ROW:
                return this.createRowConverter((RowType) type, false);
            case MAP:
            case MULTISET:
            case RAW:
            default:
                throw new UnsupportedOperationException("Unsupported type: " + type);
        }
    }

    private boolean convertToBoolean(JsonNode jsonNode) {
        return jsonNode.isBoolean() ? jsonNode.asBoolean() : Boolean.parseBoolean(jsonNode.asText().trim());
    }

    private int convertToInt(JsonNode jsonNode) {
        return jsonNode.canConvertToInt() ? jsonNode.asInt() : Integer.parseInt(jsonNode.asText().trim());
    }

    private long convertToLong(JsonNode jsonNode) {
        return jsonNode.canConvertToLong() ? jsonNode.asLong() : Long.parseLong(jsonNode.asText().trim());
    }

    private double convertToDouble(JsonNode jsonNode) {
        return jsonNode.isDouble() ? jsonNode.asDouble() : Double.parseDouble(jsonNode.asText().trim());
    }

    private float convertToFloat(JsonNode jsonNode) {
        return jsonNode.isDouble() ? (float) jsonNode.asDouble() : Float.parseFloat(jsonNode.asText().trim());
    }

    private int convertToDate(JsonNode jsonNode) {
        return (int) Date.valueOf(jsonNode.asText()).toLocalDate().toEpochDay();
    }

    private RowDataConvertersBase.RowDataConverterBase convertToTime(TimeType timeType) {
        int precision = timeType.getPrecision();
        if (precision > 3) {
            throw new IllegalArgumentException("Csv does not support TIME type with precision: " + precision + ", it only supports precision 0 ~ 3.");
        } else {
            return (jsonNode) -> {
                LocalTime localTime = LocalTime.parse(jsonNode.asText());
                int mills = (int) (localTime.toNanoOfDay() / 1000000L);
                if (precision == 2) {
                    mills = mills / 10 * 10;
                } else if (precision == 1) {
                    mills = mills / 100 * 100;
                } else if (precision == 0) {
                    mills = mills / 1000 * 1000;
                }

                return mills;
            };
        }
    }

    private TimestampData convertToTimestamp(JsonNode jsonNode, DateTimeFormatter dateTimeFormatter) {
        return TimestampData.fromLocalDateTime(LocalDateTime.parse(jsonNode.asText().trim(), dateTimeFormatter));
    }

    private StringData convertToString(JsonNode jsonNode) {
        return StringData.fromString(jsonNode.asText());
    }

    private byte[] convertToBytes(JsonNode jsonNode) {
        try {
            return jsonNode.binaryValue();
        } catch (IOException var3) {
            throw new RowDataConvertersBaseParseException("Unable to deserialize byte array.", var3);
        }
    }

    private RowDataConvertersBase.RowDataConverterBase createDecimalConverter(DecimalType decimalType) {
        int precision = decimalType.getPrecision();
        int scale = decimalType.getScale();
        return (jsonNode) -> {
            BigDecimal bigDecimal;
            if (jsonNode.isBigDecimal()) {
                bigDecimal = jsonNode.decimalValue();
            } else {
                bigDecimal = new BigDecimal(jsonNode.asText());
            }

            return DecimalData.fromBigDecimal(bigDecimal, precision, scale);
        };
    }

    private RowDataConvertersBase.RowDataConverterBase createArrayConverter(ArrayType arrayType) {
        RowDataConvertersBase.RowDataConverterBase elementConverter = this.createNullableConverter(arrayType.getElementType());
        Class<?> elementClass = LogicalTypeUtils.toInternalConversionClass(arrayType.getElementType());
        return (jsonNode) -> {
            ArrayNode node = (ArrayNode) jsonNode;
            Object[] array = (Object[]) ((Object[]) Array.newInstance(elementClass, node.size()));

            for (int i = 0; i < node.size(); ++i) {
                JsonNode innerNode = node.get(i);
                array[i] = elementConverter.convert(innerNode);
            }

            return new GenericArrayData(array);
        };
    }

    private static void validateArity(int expected, int actual, boolean ignoreParseErrors) {
        if (expected != actual && !ignoreParseErrors) {
            throw new RuntimeException("Row length mismatch. " + expected + " fields expected but was " + actual + ".");
        }
    }

    private static final class RowDataConvertersBaseParseException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public RowDataConvertersBaseParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @FunctionalInterface
    public interface RowDataConverterBase extends Serializable {
        Object convert(JsonNode var1);
    }

}
