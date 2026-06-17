///*
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.rookie.submit.connector.kafka;
//
//import org.apache.flink.api.common.serialization.DeserializationSchema;
//import org.apache.flink.api.common.serialization.SerializationSchema;
//import org.apache.flink.streaming.connectors.kafka.config.StartupMode;
//import org.apache.flink.streaming.connectors.kafka.internals.KafkaTopicPartition;
//import org.apache.flink.streaming.connectors.kafka.partitioner.FlinkFixedPartitioner;
//import org.apache.flink.streaming.connectors.kafka.partitioner.FlinkKafkaPartitioner;
//import org.apache.flink.table.api.TableException;
//import org.apache.flink.table.api.TableSchema;
//import org.apache.flink.table.descriptors.DescriptorProperties;
//import org.apache.flink.table.descriptors.SchemaValidator;
//import org.apache.flink.table.factories.DeserializationSchemaFactory;
//import org.apache.flink.table.factories.SerializationSchemaFactory;
//import org.apache.flink.table.factories.StreamTableSinkFactory;
//import org.apache.flink.table.factories.TableFactoryService;
//import org.apache.flink.table.sinks.StreamTableSink;
//import org.apache.flink.table.sources.RowtimeAttributeDescriptor;
//import org.apache.flink.table.utils.TableSchemaUtils;
//import org.apache.flink.types.Row;
//import org.apache.flink.util.InstantiationUtil;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Properties;
//
//import static org.apache.flink.table.descriptors.ConnectorDescriptorValidator.CONNECTOR_PROPERTY_VERSION;
//import static org.apache.flink.table.descriptors.ConnectorDescriptorValidator.CONNECTOR_TYPE;
//import static org.apache.flink.table.descriptors.ConnectorDescriptorValidator.CONNECTOR_VERSION;
//import static org.apache.flink.table.descriptors.DescriptorProperties.EXPR;
//import static org.apache.flink.table.descriptors.DescriptorProperties.WATERMARK;
//import static org.apache.flink.table.descriptors.DescriptorProperties.WATERMARK_ROWTIME;
//import static org.apache.flink.table.descriptors.DescriptorProperties.WATERMARK_STRATEGY_DATA_TYPE;
//import static org.apache.flink.table.descriptors.DescriptorProperties.WATERMARK_STRATEGY_EXPR;
//import static org.apache.flink.table.descriptors.FormatDescriptorValidator.FORMAT;
//import static org.apache.flink.table.descriptors.Rowtime.ROWTIME_TIMESTAMPS_CLASS;
//import static org.apache.flink.table.descriptors.Rowtime.ROWTIME_TIMESTAMPS_FROM;
//import static org.apache.flink.table.descriptors.Rowtime.ROWTIME_TIMESTAMPS_SERIALIZED;
//import static org.apache.flink.table.descriptors.Rowtime.ROWTIME_TIMESTAMPS_TYPE;
//import static org.apache.flink.table.descriptors.Rowtime.ROWTIME_WATERMARKS_CLASS;
//import static org.apache.flink.table.descriptors.Rowtime.ROWTIME_WATERMARKS_DELAY;
//import static org.apache.flink.table.descriptors.Rowtime.ROWTIME_WATERMARKS_SERIALIZED;
//import static org.apache.flink.table.descriptors.Rowtime.ROWTIME_WATERMARKS_TYPE;
//import static org.apache.flink.table.descriptors.Schema.SCHEMA;
//import static org.apache.flink.table.descriptors.Schema.SCHEMA_DATA_TYPE;
//import static org.apache.flink.table.descriptors.Schema.SCHEMA_FROM;
//import static org.apache.flink.table.descriptors.Schema.SCHEMA_NAME;
//import static org.apache.flink.table.descriptors.Schema.SCHEMA_PROCTIME;
//import static org.apache.flink.table.descriptors.Schema.SCHEMA_TYPE;
//import static org.apache.flink.table.descriptors.StreamTableDescriptorValidator.UPDATE_MODE;
//import static com.rookie.submit.connector.kafka.UpsertKafkaValidator.*;
//
///**
// * Factory for creating configured instances of {@link KafkaUpsertTableSink}.
// */
//public abstract class KafkaUpsertTableSinkFactoryBase implements
//        StreamTableSinkFactory<Row> {
//
//    @Override
//    public Map<String, String> requiredContext() {
//        Map<String, String> context = new HashMap<>();
//        context.put(CONNECTOR_TYPE, CONNECTOR_TYPE_VALUE_KAFKA); // kafka
//        context.put(CONNECTOR_VERSION, kafkaVersion()); // version
//        context.put(CONNECTOR_PROPERTY_VERSION, "1"); // backwards compatibility
//        return context;
//    }
//
//    @Override
//    public List<String> supportedProperties() {
//        List<String> properties = new ArrayList<>();
//        // update mode
//        properties.add(UPDATE_MODE);
//
//        // kafka
//        properties.add(CONNECTOR_TOPIC);
//        properties.add(CONNECTOR_PROPERTIES);
//        properties.add(CONNECTOR_PROPERTIES + ".#." + CONNECTOR_PROPERTIES_KEY);
//        properties.add(CONNECTOR_PROPERTIES + ".#." + CONNECTOR_PROPERTIES_VALUE);
//        properties.add(CONNECTOR_PROPERTIES + ".*");
//        properties.add(CONNECTOR_STARTUP_MODE);
//        properties.add(CONNECTOR_SPECIFIC_OFFSETS);
//        properties.add(CONNECTOR_SPECIFIC_OFFSETS + ".#." + CONNECTOR_SPECIFIC_OFFSETS_PARTITION);
//        properties.add(CONNECTOR_SPECIFIC_OFFSETS + ".#." + CONNECTOR_SPECIFIC_OFFSETS_OFFSET);
//        properties.add(CONNECTOR_SINK_PARTITIONER);
//        properties.add(CONNECTOR_SINK_PARTITIONER_CLASS);
//
//        // schema
//        properties.add(SCHEMA + ".#." + SCHEMA_DATA_TYPE);
//        properties.add(SCHEMA + ".#." + SCHEMA_TYPE);
//        properties.add(SCHEMA + ".#." + SCHEMA_NAME);
//        properties.add(SCHEMA + ".#." + SCHEMA_FROM);
//        // computed column
//        properties.add(SCHEMA + ".#." + EXPR);
//
//        // time attributes
//        properties.add(SCHEMA + ".#." + SCHEMA_PROCTIME);
//        properties.add(SCHEMA + ".#." + ROWTIME_TIMESTAMPS_TYPE);
//        properties.add(SCHEMA + ".#." + ROWTIME_TIMESTAMPS_FROM);
//        properties.add(SCHEMA + ".#." + ROWTIME_TIMESTAMPS_CLASS);
//        properties.add(SCHEMA + ".#." + ROWTIME_TIMESTAMPS_SERIALIZED);
//        properties.add(SCHEMA + ".#." + ROWTIME_WATERMARKS_TYPE);
//        properties.add(SCHEMA + ".#." + ROWTIME_WATERMARKS_CLASS);
//        properties.add(SCHEMA + ".#." + ROWTIME_WATERMARKS_SERIALIZED);
//        properties.add(SCHEMA + ".#." + ROWTIME_WATERMARKS_DELAY);
//
//        // watermark
//        properties.add(SCHEMA + "." + WATERMARK + ".#." + WATERMARK_ROWTIME);
//        properties.add(SCHEMA + "." + WATERMARK + ".#." + WATERMARK_STRATEGY_EXPR);
//        properties.add(SCHEMA + "." + WATERMARK + ".#." + WATERMARK_STRATEGY_DATA_TYPE);
//
//        // format wildcard
//        properties.add(FORMAT + ".*");
//
//        return properties;
//    }
//
//
//    @Override
//    public StreamTableSink<Row> createStreamTableSink(Map<String, String> properties) {
//        final DescriptorProperties descriptorProperties = getValidatedProperties(properties);
//
//        final TableSchema schema = TableSchemaUtils.getPhysicalSchema(
//                descriptorProperties.getTableSchema(SCHEMA));
//        final String topic = descriptorProperties.getString(CONNECTOR_TOPIC);
//        final Optional<String> proctime = SchemaValidator.deriveProctimeAttribute(descriptorProperties);
//        final List<RowtimeAttributeDescriptor> rowtimeAttributeDescriptors =
//                SchemaValidator.deriveRowtimeAttributes(descriptorProperties);
//
//        // see also FLINK-9870
//        if (proctime.isPresent() || !rowtimeAttributeDescriptors.isEmpty() ||
//                checkForCustomFieldMapping(descriptorProperties, schema)) {
//            throw new TableException("Time attributes and custom field mappings are not supported yet.");
//        }
//        // todo error
//        return createKafkaTableSink(
//                schema,
//                topic,
//                getKafkaProperties(descriptorProperties),
//                getFlinkKafkaPartitioner(descriptorProperties),
//                getSerializationSchema(properties));
//    }
//
//    // --------------------------------------------------------------------------------------------
//    // For version-specific factories
//    // --------------------------------------------------------------------------------------------
//
//    /**
//     * Returns the Kafka version.
//     */
//    protected abstract String kafkaVersion();
//
//    /**
//     * True if the Kafka source supports Kafka timestamps, false otherwise.
//     *
//     * @return True if the Kafka source supports Kafka timestamps, false otherwise.
//     */
//    protected abstract boolean supportsKafkaTimestamps();
//
//
//    /**
//     * Constructs the version-specific Kafka table sink.
//     *
//     * @param schema      Schema of the produced table.
//     * @param topic       Kafka topic to consume.
//     * @param properties  Properties for the Kafka consumer.
//     * @param partitioner Partitioner to select Kafka partition for each item.
//     */
//    protected abstract StreamTableSink createKafkaTableSink(
//            TableSchema schema,
//            String topic,
//            Properties properties,
//            Optional<FlinkKafkaPartitioner<Row>> partitioner,
//            SerializationSchema<Row> serializationSchema);
//
//    // --------------------------------------------------------------------------------------------
//    // Helper methods
//    // --------------------------------------------------------------------------------------------
//
//    private DescriptorProperties getValidatedProperties(Map<String, String> properties) {
//        final DescriptorProperties descriptorProperties = new DescriptorProperties(true);
//        descriptorProperties.putProperties(properties);
//
//        // allow Kafka timestamps to be used, watermarks can not be received from source
//        new SchemaValidator(true, supportsKafkaTimestamps(), false).validate(descriptorProperties);
//        new UpsertKafkaValidator().validate(descriptorProperties);
//
//        return descriptorProperties;
//    }
//
//    private DeserializationSchema<Row> getDeserializationSchema(Map<String, String> properties) {
//        @SuppressWarnings("unchecked") final DeserializationSchemaFactory<Row> formatFactory = TableFactoryService.find(
//                DeserializationSchemaFactory.class,
//                properties,
//                this.getClass().getClassLoader());
//        return formatFactory.createDeserializationSchema(properties);
//    }
//
//    private SerializationSchema<Row> getSerializationSchema(Map<String, String> properties) {
//        @SuppressWarnings("unchecked") final SerializationSchemaFactory<Row> formatFactory = TableFactoryService.find(
//                SerializationSchemaFactory.class,
//                properties,
//                this.getClass().getClassLoader());
//        return formatFactory.createSerializationSchema(properties);
//    }
//
//    private Properties getKafkaProperties(DescriptorProperties descriptorProperties) {
//        final Properties kafkaProperties = new Properties();
//
//        if (UpsertKafkaValidator.hasConciseKafkaProperties(descriptorProperties)) {
//            descriptorProperties.asMap().keySet()
//                    .stream()
//                    .filter(key -> key.startsWith(CONNECTOR_PROPERTIES))
//                    .forEach(key -> {
//                        final String value = descriptorProperties.getString(key);
//                        final String subKey = key.substring((CONNECTOR_PROPERTIES + '.').length());
//                        kafkaProperties.put(subKey, value);
//                    });
//        } else {
//            final List<Map<String, String>> propsList = descriptorProperties.getFixedIndexedProperties(
//                    CONNECTOR_PROPERTIES,
//                    Arrays.asList(CONNECTOR_PROPERTIES_KEY, CONNECTOR_PROPERTIES_VALUE));
//            propsList.forEach(kv -> kafkaProperties.put(
//                    descriptorProperties.getString(kv.get(CONNECTOR_PROPERTIES_KEY)),
//                    descriptorProperties.getString(kv.get(CONNECTOR_PROPERTIES_VALUE))
//            ));
//        }
//        return kafkaProperties;
//    }
//
//    private StartupOptions getStartupOptions(
//            DescriptorProperties descriptorProperties,
//            String topic) {
//        final Map<KafkaTopicPartition, Long> specificOffsets = new HashMap<>();
//        final StartupMode startupMode = descriptorProperties
//                .getOptionalString(CONNECTOR_STARTUP_MODE)
//                .map(modeString -> {
//                    switch (modeString) {
//                        case UpsertKafkaValidator.CONNECTOR_STARTUP_MODE_VALUE_EARLIEST:
//                            return StartupMode.EARLIEST;
//
//                        case UpsertKafkaValidator.CONNECTOR_STARTUP_MODE_VALUE_LATEST:
//                            return StartupMode.LATEST;
//
//                        case UpsertKafkaValidator.CONNECTOR_STARTUP_MODE_VALUE_GROUP_OFFSETS:
//                            return StartupMode.GROUP_OFFSETS;
//
//                        case UpsertKafkaValidator.CONNECTOR_STARTUP_MODE_VALUE_SPECIFIC_OFFSETS:
//                            buildSpecificOffsets(descriptorProperties, topic, specificOffsets);
//                            return StartupMode.SPECIFIC_OFFSETS;
//                        default:
//                            throw new TableException("Unsupported startup mode. Validator should have checked that.");
//                    }
//                }).orElse(StartupMode.GROUP_OFFSETS);
//        final StartupOptions options = new StartupOptions();
//        options.startupMode = startupMode;
//        options.specificOffsets = specificOffsets;
//        return options;
//    }
//
//    private void buildSpecificOffsets(DescriptorProperties descriptorProperties, String topic, Map<KafkaTopicPartition, Long> specificOffsets) {
//        if (descriptorProperties.containsKey(CONNECTOR_SPECIFIC_OFFSETS)) {
//            final Map<Integer, Long> offsetMap = UpsertKafkaValidator.validateAndParseSpecificOffsetsString(descriptorProperties);
//            offsetMap.forEach((partition, offset) -> {
//                final KafkaTopicPartition topicPartition = new KafkaTopicPartition(topic, partition);
//                specificOffsets.put(topicPartition, offset);
//            });
//        } else {
//            final List<Map<String, String>> offsetList = descriptorProperties.getFixedIndexedProperties(
//                    CONNECTOR_SPECIFIC_OFFSETS,
//                    Arrays.asList(CONNECTOR_SPECIFIC_OFFSETS_PARTITION, CONNECTOR_SPECIFIC_OFFSETS_OFFSET));
//            offsetList.forEach(kv -> {
//                final int partition = descriptorProperties.getInt(kv.get(CONNECTOR_SPECIFIC_OFFSETS_PARTITION));
//                final long offset = descriptorProperties.getLong(kv.get(CONNECTOR_SPECIFIC_OFFSETS_OFFSET));
//                final KafkaTopicPartition topicPartition = new KafkaTopicPartition(topic, partition);
//                specificOffsets.put(topicPartition, offset);
//            });
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    private Optional<FlinkKafkaPartitioner<Row>> getFlinkKafkaPartitioner(DescriptorProperties descriptorProperties) {
//        return descriptorProperties
//                .getOptionalString(CONNECTOR_SINK_PARTITIONER)
//                .flatMap((String partitionerString) -> {
//                    switch (partitionerString) {
//                        case CONNECTOR_SINK_PARTITIONER_VALUE_FIXED:
//                            return Optional.of(new FlinkFixedPartitioner<>());
//                        case CONNECTOR_SINK_PARTITIONER_VALUE_ROUND_ROBIN:
//                            return Optional.empty();
//                        case CONNECTOR_SINK_PARTITIONER_VALUE_CUSTOM:
//                            final Class<? extends FlinkKafkaPartitioner> partitionerClass =
//                                    descriptorProperties.getClass(CONNECTOR_SINK_PARTITIONER_CLASS, FlinkKafkaPartitioner.class);
//                            return Optional.of((FlinkKafkaPartitioner<Row>) InstantiationUtil.instantiate(partitionerClass));
//                        default:
//                            throw new TableException("Unsupported sink partitioner. Validator should have checked that.");
//                    }
//                });
//    }
//
//    private boolean checkForCustomFieldMapping(DescriptorProperties descriptorProperties, TableSchema schema) {
//        final Map<String, String> fieldMapping = SchemaValidator.deriveFieldMapping(
//                descriptorProperties,
//                Optional.of(schema.toRowType())); // until FLINK-9870 is fixed we assume that the table schema is the output type
//        return fieldMapping.size() != schema.getFieldNames().length ||
//                !fieldMapping.entrySet().stream().allMatch(mapping -> mapping.getKey().equals(mapping.getValue()));
//    }
//
//    private static class StartupOptions {
//        private StartupMode startupMode;
//        private Map<KafkaTopicPartition, Long> specificOffsets;
//    }
//}
