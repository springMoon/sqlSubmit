////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package org.apache.flink.table.api.config;
//
//import java.time.Duration;
//import org.apache.flink.annotation.PublicEvolving;
//import org.apache.flink.annotation.docs.Documentation.ExcludeFromDocumentation;
//import org.apache.flink.annotation.docs.Documentation.ExecMode;
//import org.apache.flink.annotation.docs.Documentation.TableOption;
//import org.apache.flink.configuration.ConfigOption;
//import org.apache.flink.configuration.ConfigOptions;
//import org.apache.flink.configuration.MemorySize;
//import org.apache.flink.configuration.description.Description;
//import org.apache.flink.configuration.description.InlineElement;
//import org.apache.flink.configuration.description.TextElement;
//
//@PublicEvolving
//public class ExecutionConfigOptions {
//    @TableOption(
//        execMode = ExecMode.STREAMING
//    )
//    public static final ConfigOption<Duration> IDLE_STATE_RETENTION = ConfigOptions.key("table.exec.state.ttl").durationType().defaultValue(Duration.ofMillis(0L)).withDescription("Specifies a minimum time interval for how long idle state (i.e. state which was not updated), will be retained. State will never be cleared until it was idle for less than the minimum time, and will be cleared at some time after it was idle. Default is never clean-up the state. NOTE: Cleaning up state requires additional overhead for bookkeeping. Default value is 0, which means that it will never clean up state.");
//    @TableOption(
//        execMode = ExecMode.STREAMING
//    )
//    public static final ConfigOption<Duration> TABLE_EXEC_SOURCE_IDLE_TIMEOUT = ConfigOptions.key("table.exec.source.idle-timeout").durationType().defaultValue(Duration.ofMillis(0L)).withDescription("When a source do not receive any elements for the timeout time, it will be marked as temporarily idle. This allows downstream tasks to advance their watermarks without the need to wait for watermarks from this source while it is idle. Default value is 0, which means detecting source idleness is not enabled.");
//    @TableOption(
//        execMode = ExecMode.STREAMING
//    )
//    public static final ConfigOption<Boolean> TABLE_EXEC_SOURCE_CDC_EVENTS_DUPLICATE = ConfigOptions.key("table.exec.source.cdc-events-duplicate").booleanType().defaultValue(false).withDescription(Description.builder().text("Indicates whether the CDC (Change Data Capture) sources in the job will produce duplicate change events that requires the framework to deduplicate and get consistent result. CDC source refers to the source that produces full change events, including INSERT/UPDATE_BEFORE/UPDATE_AFTER/DELETE, for example Kafka source with Debezium format. The value of this configuration is false by default.").linebreak().linebreak().text("However, it's a common case that there are duplicate change events. Because usually the CDC tools (e.g. Debezium) work in at-least-once delivery when failover happens. Thus, in the abnormal situations Debezium may deliver duplicate change events to Kafka and Flink will get the duplicate events. This may cause Flink query to get wrong results or unexpected exceptions.").linebreak().linebreak().text("Therefore, it is recommended to turn on this configuration if your CDC tool is at-least-once delivery. Enabling this configuration requires to define PRIMARY KEY on the CDC sources. The primary key will be used to deduplicate change events and generate normalized changelog stream at the cost of an additional stateful operator.").build());
//    @TableOption(
//        execMode = ExecMode.BATCH_STREAMING
//    )
//    public static final ConfigOption<ExecutionConfigOptions.NotNullEnforcer> TABLE_EXEC_SINK_NOT_NULL_ENFORCER;
//    @TableOption(
//        execMode = ExecMode.STREAMING
//    )
//    public static final ConfigOption<ExecutionConfigOptions.UpsertMaterialize> TABLE_EXEC_SINK_UPSERT_MATERIALIZE;
//    @TableOption(
//        execMode = ExecMode.BATCH
//    )
//    public static final ConfigOption<Integer> TABLE_EXEC_SORT_DEFAULT_LIMIT;
//    @TableOption(
//        execMode = ExecMode.BATCH
//    )
//    public static final ConfigOption<Integer> TABLE_EXEC_SORT_MAX_NUM_FILE_HANDLES;
//    @TableOption(
//        execMode = ExecMode.BATCH
//    )
//    public static final ConfigOption<Boolean> TABLE_EXEC_SORT_ASYNC_MERGE_ENABLED;
//    @TableOption(
//        execMode = ExecMode.BATCH
//    )
//    public static final ConfigOption<Boolean> TABLE_EXEC_SPILL_COMPRESSION_ENABLED;
//    @TableOption(
//        execMode = ExecMode.BATCH
//    )
//    public static final ConfigOption<MemorySize> TABLE_EXEC_SPILL_COMPRESSION_BLOCK_SIZE;
//    @TableOption(
//        execMode = ExecMode.BATCH_STREAMING
//    )
//    public static final ConfigOption<Integer> TABLE_EXEC_RESOURCE_DEFAULT_PARALLELISM;
//    @ExcludeFromDocumentation("Beginning from Flink 1.10, this is interpreted as a weight hint instead of an absolute memory requirement. Users should not need to change these carefully tuned weight hints.")
//    public static final ConfigOption<MemorySize> TABLE_EXEC_RESOURCE_EXTERNAL_BUFFER_MEMORY;
//    @ExcludeFromDocumentation("Beginning from Flink 1.10, this is interpreted as a weight hint instead of an absolute memory requirement. Users should not need to change these carefully tuned weight hints.")
//    public static final ConfigOption<MemorySize> TABLE_EXEC_RESOURCE_HASH_AGG_MEMORY;
//    @ExcludeFromDocumentation("Beginning from Flink 1.10, this is interpreted as a weight hint instead of an absolute memory requirement. Users should not need to change these carefully tuned weight hints.")
//    public static final ConfigOption<MemorySize> TABLE_EXEC_RESOURCE_HASH_JOIN_MEMORY;
//    @ExcludeFromDocumentation("Beginning from Flink 1.10, this is interpreted as a weight hint instead of an absolute memory requirement. Users should not need to change these carefully tuned weight hints.")
//    public static final ConfigOption<MemorySize> TABLE_EXEC_RESOURCE_SORT_MEMORY;
//    @TableOption(
//        execMode = ExecMode.BATCH
//    )
//    public static final ConfigOption<Integer> TABLE_EXEC_WINDOW_AGG_BUFFER_SIZE_LIMIT;
//    @TableOption(
//        execMode = ExecMode.BATCH_STREAMING
//    )
//    public static final ConfigOption<Integer> TABLE_EXEC_ASYNC_LOOKUP_BUFFER_CAPACITY;
//    @TableOption(
//        execMode = ExecMode.BATCH_STREAMING
//    )
//    public static final ConfigOption<Duration> TABLE_EXEC_ASYNC_LOOKUP_TIMEOUT;
//    @TableOption(
//        execMode = ExecMode.STREAMING
//    )
//    public static final ConfigOption<Boolean> TABLE_EXEC_MINIBATCH_ENABLED;
//    @TableOption(
//        execMode = ExecMode.STREAMING
//    )
//    public static final ConfigOption<Duration> TABLE_EXEC_MINIBATCH_ALLOW_LATENCY;
//    @TableOption(
//        execMode = ExecMode.STREAMING
//    )
//    public static final ConfigOption<Long> TABLE_EXEC_MINIBATCH_SIZE;
//    @TableOption(
//        execMode = ExecMode.BATCH
//    )
//    public static final ConfigOption<String> TABLE_EXEC_DISABLED_OPERATORS;
//    /** @deprecated */
//    @Deprecated
//    @TableOption(
//        execMode = ExecMode.BATCH
//    )
//    public static final ConfigOption<String> TABLE_EXEC_SHUFFLE_MODE;
//
//    @TableOption(
//            execMode = ExecMode.STREAMING
//    )
//    public static final ConfigOption<Boolean> TABLE_EXEC_SOURCE_FORCE_BREAK_CHAIN =
//            ConfigOptions.key("table.exec.source.force-break-chain")
//                    .booleanType()
//                    .defaultValue(false)
//                    .withDescription(
//                            "Indicates whether to forcefully break the operator chain after the source.");
//
//    public ExecutionConfigOptions() {
//    }
//
//    static {
//        TABLE_EXEC_SINK_NOT_NULL_ENFORCER = ConfigOptions.key("table.exec.sink.not-null-enforcer").enumType(ExecutionConfigOptions.NotNullEnforcer.class).defaultValue(ExecutionConfigOptions.NotNullEnforcer.ERROR).withDescription("The NOT NULL column constraint on a table enforces that null values can't be inserted into the table. Flink supports 'error' (default) and 'drop' enforcement behavior. By default, Flink will check values and throw runtime exception when null values writing into NOT NULL columns. Users can change the behavior to 'drop' to silently drop such records without throwing exception.");
//        TABLE_EXEC_SINK_UPSERT_MATERIALIZE = ConfigOptions.key("table.exec.sink.upsert-materialize").enumType(ExecutionConfigOptions.UpsertMaterialize.class).defaultValue(ExecutionConfigOptions.UpsertMaterialize.AUTO).withDescription(Description.builder().text("Because of the disorder of ChangeLog data caused by Shuffle in distributed system, the data received by Sink may not be the order of global upsert. So add upsert materialize operator before upsert sink. It receives the upstream changelog records and generate an upsert view for the downstream.").linebreak().text("By default, the materialize operator will be added when a distributed disorder occurs on unique keys. You can also choose no materialization(NONE) or force materialization(FORCE).").build());
//        TABLE_EXEC_SORT_DEFAULT_LIMIT = ConfigOptions.key("table.exec.sort.default-limit").defaultValue(-1).withDescription("Default limit when user don't set a limit after order by. -1 indicates that this configuration is ignored.");
//        TABLE_EXEC_SORT_MAX_NUM_FILE_HANDLES = ConfigOptions.key("table.exec.sort.max-num-file-handles").defaultValue(128).withDescription("The maximal fan-in for external merge sort. It limits the number of file handles per operator. If it is too small, may cause intermediate merging. But if it is too large, it will cause too many files opened at the same time, consume memory and lead to random reading.");
//        TABLE_EXEC_SORT_ASYNC_MERGE_ENABLED = ConfigOptions.key("table.exec.sort.async-merge-enabled").defaultValue(true).withDescription("Whether to asynchronously merge sorted spill files.");
//        TABLE_EXEC_SPILL_COMPRESSION_ENABLED = ConfigOptions.key("table.exec.spill-compression.enabled").defaultValue(true).withDescription("Whether to compress spilled data. Currently we only support compress spilled data for sort and hash-agg and hash-join operators.");
//        TABLE_EXEC_SPILL_COMPRESSION_BLOCK_SIZE = ConfigOptions.key("table.exec.spill-compression.block-size").memoryType().defaultValue(MemorySize.parse("64 kb")).withDescription("The memory size used to do compress when spilling data. The larger the memory, the higher the compression ratio, but more memory resource will be consumed by the job.");
//        TABLE_EXEC_RESOURCE_DEFAULT_PARALLELISM = ConfigOptions.key("table.exec.resource.default-parallelism").defaultValue(-1).withDescription("Sets default parallelism for all operators (such as aggregate, join, filter) to run with parallel instances. This config has a higher priority than parallelism of StreamExecutionEnvironment (actually, this config overrides the parallelism of StreamExecutionEnvironment). A value of -1 indicates that no default parallelism is set, then it will fallback to use the parallelism of StreamExecutionEnvironment.");
//        TABLE_EXEC_RESOURCE_EXTERNAL_BUFFER_MEMORY = ConfigOptions.key("table.exec.resource.external-buffer-memory").memoryType().defaultValue(MemorySize.parse("10 mb")).withDescription("Sets the external buffer memory size that is used in sort merge join and nested join and over window. Note: memory size is only a weight hint, it will affect the weight of memory that can be applied by a single operator in the task, the actual memory used depends on the running environment.");
//        TABLE_EXEC_RESOURCE_HASH_AGG_MEMORY = ConfigOptions.key("table.exec.resource.hash-agg.memory").memoryType().defaultValue(MemorySize.parse("128 mb")).withDescription("Sets the managed memory size of hash aggregate operator. Note: memory size is only a weight hint, it will affect the weight of memory that can be applied by a single operator in the task, the actual memory used depends on the running environment.");
//        TABLE_EXEC_RESOURCE_HASH_JOIN_MEMORY = ConfigOptions.key("table.exec.resource.hash-join.memory").memoryType().defaultValue(MemorySize.ofMebiBytes(128L)).withDescription("Sets the managed memory for hash join operator. It defines the lower limit. Note: memory size is only a weight hint, it will affect the weight of memory that can be applied by a single operator in the task, the actual memory used depends on the running environment.");
//        TABLE_EXEC_RESOURCE_SORT_MEMORY = ConfigOptions.key("table.exec.resource.sort.memory").memoryType().defaultValue(MemorySize.ofMebiBytes(128L)).withDescription("Sets the managed buffer memory size for sort operator. Note: memory size is only a weight hint, it will affect the weight of memory that can be applied by a single operator in the task, the actual memory used depends on the running environment.");
//        TABLE_EXEC_WINDOW_AGG_BUFFER_SIZE_LIMIT = ConfigOptions.key("table.exec.window-agg.buffer-size-limit").defaultValue(100000).withDescription("Sets the window elements buffer size limit used in group window agg operator.");
//        TABLE_EXEC_ASYNC_LOOKUP_BUFFER_CAPACITY = ConfigOptions.key("table.exec.async-lookup.buffer-capacity").defaultValue(100).withDescription("The max number of async i/o operation that the async lookup join can trigger.");
//        TABLE_EXEC_ASYNC_LOOKUP_TIMEOUT = ConfigOptions.key("table.exec.async-lookup.timeout").durationType().defaultValue(Duration.ofMinutes(3L)).withDescription("The async timeout for the asynchronous operation to complete.");
//        TABLE_EXEC_MINIBATCH_ENABLED = ConfigOptions.key("table.exec.mini-batch.enabled").defaultValue(false).withDescription("Specifies whether to enable MiniBatch optimization. MiniBatch is an optimization to buffer input records to reduce state access. This is disabled by default. To enable this, users should set this config to true. NOTE: If mini-batch is enabled, 'table.exec.mini-batch.allow-latency' and 'table.exec.mini-batch.size' must be set.");
//        TABLE_EXEC_MINIBATCH_ALLOW_LATENCY = ConfigOptions.key("table.exec.mini-batch.allow-latency").durationType().defaultValue(Duration.ofMillis(0L)).withDescription("The maximum latency can be used for MiniBatch to buffer input records. MiniBatch is an optimization to buffer input records to reduce state access. MiniBatch is triggered with the allowed latency interval and when the maximum number of buffered records reached. NOTE: If " + TABLE_EXEC_MINIBATCH_ENABLED.key() + " is set true, its value must be greater than zero.");
//        TABLE_EXEC_MINIBATCH_SIZE = ConfigOptions.key("table.exec.mini-batch.size").defaultValue(-1L).withDescription("The maximum number of input records can be buffered for MiniBatch. MiniBatch is an optimization to buffer input records to reduce state access. MiniBatch is triggered with the allowed latency interval and when the maximum number of buffered records reached. NOTE: MiniBatch only works for non-windowed aggregations currently. If " + TABLE_EXEC_MINIBATCH_ENABLED.key() + " is set true, its value must be positive.");
//        TABLE_EXEC_DISABLED_OPERATORS = ConfigOptions.key("table.exec.disabled-operators").noDefaultValue().withDescription("Mainly for testing. A comma-separated list of operator names, each name represents a kind of disabled operator.\nOperators that can be disabled include \"NestedLoopJoin\", \"ShuffleHashJoin\", \"BroadcastHashJoin\", \"SortMergeJoin\", \"HashAgg\", \"SortAgg\".\nBy default no operator is disabled.");
//        TABLE_EXEC_SHUFFLE_MODE = ConfigOptions.key("table.exec.shuffle-mode").stringType().noDefaultValue().withDescription(Description.builder().text("Sets exec shuffle mode.").linebreak().text("Accepted values are:").list(new InlineElement[]{TextElement.text("%s: All edges will use blocking shuffle.", new InlineElement[]{TextElement.code("ALL_EDGES_BLOCKING")}), TextElement.text("%s: Forward edges will use pipelined shuffle, others blocking.", new InlineElement[]{TextElement.code("FORWARD_EDGES_PIPELINED")}), TextElement.text("%s: Pointwise edges will use pipelined shuffle, others blocking. Pointwise edges include forward and rescale edges.", new InlineElement[]{TextElement.code("POINTWISE_EDGES_PIPELINED")}), TextElement.text("%s: All edges will use pipelined shuffle.", new InlineElement[]{TextElement.code("ALL_EDGES_PIPELINED")}), TextElement.text("%s: the same as %s. Deprecated.", new InlineElement[]{TextElement.code("batch"), TextElement.code("ALL_EDGES_BLOCKING")}), TextElement.text("%s: the same as %s. Deprecated.", new InlineElement[]{TextElement.code("pipelined"), TextElement.code("ALL_EDGES_PIPELINED")})}).text("Note: Blocking shuffle means data will be fully produced before sent to consumer tasks. Pipelined shuffle means data will be sent to consumer tasks once produced.").build());
//    }
//
//    public static enum UpsertMaterialize {
//        NONE,
//        AUTO,
//        FORCE;
//
//        private UpsertMaterialize() {
//        }
//    }
//
//    public static enum NotNullEnforcer {
//        ERROR,
//        DROP;
//
//        private NotNullEnforcer() {
//        }
//    }
//}
