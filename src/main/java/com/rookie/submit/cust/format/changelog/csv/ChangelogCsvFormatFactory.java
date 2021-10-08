package com.rookie.submit.cust.format.changelog.csv;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.table.connector.format.DecodingFormat;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.factories.FactoryUtil;
import org.apache.flink.table.factories.DeserializationFormatFactory;
import org.apache.flink.table.factories.DynamicTableFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * FormatFaction, flink use spi register format factory
 * define format option and create Format object
 * flink format demo
 */
public class ChangelogCsvFormatFactory implements DeserializationFormatFactory {

    // define all options statically
    public static final ConfigOption<String> COLUMN_DELIMITER = ConfigOptions.key("column-delimiter")
            .stringType()
            .defaultValue("|");

    @Override
    public String factoryIdentifier() {
        return "changelog-csv"; // used for matching to `format = 'changelog-csv'`
    }

    /**
     * special require properties
     *
     * @return require properties
     */
    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        return Collections.emptySet();
    }

    /**
     * special option properties
     *
     * @return option properties
     */
    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        options.add(COLUMN_DELIMITER);
        return options;
    }

    /**
     * create decode format class, for decode recode
     *
     * @param context       runtime context
     * @param formatOptions format option
     * @return format class
     */
    @Override
    public DecodingFormat<DeserializationSchema<RowData>> createDecodingFormat(
            DynamicTableFactory.Context context,
            ReadableConfig formatOptions) {
        // either implement your custom validation logic here ...
        // or use the provided helper method
        FactoryUtil.validateFactoryOptions(this, formatOptions);

        // get the validated options
        final String columnDelimiter = formatOptions.get(COLUMN_DELIMITER);

        // create and return the format
        return new ChangelogCsvFormat(columnDelimiter);
    }
}