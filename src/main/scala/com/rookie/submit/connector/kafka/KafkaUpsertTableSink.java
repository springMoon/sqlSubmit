package com.rookie.submit.connector.kafka;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.internals.KeyedSerializationSchemaWrapper;
import org.apache.flink.streaming.connectors.kafka.partitioner.FlinkKafkaPartitioner;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.api.ValidationException;
import org.apache.flink.table.sinks.UpsertStreamTableSink;
import org.apache.flink.table.utils.TableConnectorUtils;
import org.apache.flink.table.utils.TableSchemaUtils;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;
import org.apache.flink.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

/**
 * kafka upsert sink
 */
public class KafkaUpsertTableSink implements UpsertStreamTableSink<Row> {

    Logger logger = LoggerFactory.getLogger(KafkaUpsertTableSink.class);

    /** The schema of the table. */
    private TableSchema schema = null;

    /** The Kafka topic to write to. */
    protected String topic = null;

    /** Properties for the Kafka producer. */
    protected Properties properties = null;

    /** Serialization schema for encoding records to Kafka. */
    protected SerializationSchema<Row> serializationSchema = null;

    /** Partitioner to select Kafka partition for each item. */
    protected Optional<FlinkKafkaPartitioner<Row>> partitioner = null;

    KafkaUpsertTableSink(
            TableSchema schema,
            String topic,
            Properties properties,
            Optional<FlinkKafkaPartitioner<Row>> partitioner,
            SerializationSchema<Row> serializationSchema) {
        this.schema = TableSchemaUtils.checkNoGeneratedColumns(schema);
        this.topic = Preconditions.checkNotNull(topic, "Topic must not be null.");
        this.properties = Preconditions.checkNotNull(properties, "Properties must not be null.");
        this.partitioner = Preconditions.checkNotNull(partitioner, "Partitioner must not be null.");
        this.serializationSchema = Preconditions.checkNotNull(serializationSchema, "Serialization schema must not be null.");
    }

    /**
     * Returns the version-specific Kafka producer.
     *
     * @param topic               Kafka topic to produce to.
     * @param properties          Properties for the Kafka producer.
     * @param serializationSchema Serialization schema to use to create Kafka records.
     * @param partitioner         Partitioner to select Kafka partition.
     * @return The version-specific Kafka producer
     */
    protected SinkFunction<Row> createKafkaProducer(
            String topic,
            Properties properties,
            SerializationSchema<Row> serializationSchema,
            Optional<FlinkKafkaPartitioner<Row>> partitioner){

        return new FlinkKafkaProducer<>(
                topic,
                new KeyedSerializationSchemaWrapper<>(serializationSchema),
                properties,
                partitioner);
    }

    @Override
    public DataStreamSink<?> consumeDataStream(DataStream<Tuple2<Boolean, Row>> dataStream) {

        final SinkFunction<Row> kafkaProducer = createKafkaProducer(
                topic,
                properties,
                serializationSchema,
                partitioner);

        // todo cast DataStream<Tuple2<Boolean, Row>> to DataStream<Row>
        return dataStream
                .flatMap(new FlatMapFunction<Tuple2<Boolean, Row>, Row>() {
                    @Override
                    public void flatMap(Tuple2<Boolean, Row> element, Collector<Row> out) throws Exception {
                        // upsertStream include insert/update/delete change, true is upsert, false is delete
                        // create new row include upsert message
                        if (element.f0) {
                            out.collect(element.f1);
                        } else {
                            System.out.println("KafkaUpsertTableSinkBase : retract stream f0 will be false");
                        }
                    }
                })
                .addSink(kafkaProducer)
                .setParallelism(dataStream.getParallelism())
                .name(TableConnectorUtils.generateRuntimeName(this.getClass(), getFieldNames()));
    }


    public void setKeyFields(String[] strings) {

    }

    @Override
    public String[] getFieldNames() {
        return schema.getFieldNames();
    }

    public void setIsAppendOnly(Boolean aBoolean) {

    }

    public TypeInformation<Row> getRecordType() {
        return schema.toRowType();
    }

    public void emitDataStream(DataStream<Tuple2<Boolean, Row>> dataStream) {
        consumeDataStream(dataStream);
    }

    @Override
    public TypeInformation<?>[] getFieldTypes() {
        return schema.getFieldTypes();
    }

    @Override
    public KafkaUpsertTableSink configure(String[] fieldNames, TypeInformation<?>[] fieldTypes) {
        if (!Arrays.equals(getFieldNames(), fieldNames) || !Arrays.equals(getFieldTypes(), fieldTypes)) {
            throw new ValidationException("Reconfiguration with different fields is not allowed. " +
                    "Expected: " + Arrays.toString(getFieldNames()) + " / " + Arrays.toString(getFieldTypes()) + ". " +
                    "But was: " + Arrays.toString(fieldNames) + " / " + Arrays.toString(fieldTypes));
        }
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            KafkaUpsertTableSink that = (KafkaUpsertTableSink)o;
            return Objects.equals(this.schema, that.schema) && Objects.equals(this.topic, that.topic) && Objects.equals(this.properties, that.properties) && Objects.equals(this.serializationSchema, that.serializationSchema) && Objects.equals(this.partitioner, that.partitioner);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.schema, this.topic, this.properties, this.serializationSchema, this.partitioner});
    }
}
