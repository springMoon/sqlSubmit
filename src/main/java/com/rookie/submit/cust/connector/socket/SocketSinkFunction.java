package com.rookie.submit.cust.connector.socket;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * socket table source
 */
public class SocketSinkFunction<RowData> extends RichSinkFunction<RowData> {

    private final Logger LOG = LoggerFactory.getLogger(SocketSinkFunction.class);
    private final String hostname;
    private final int port;
    private final SerializationSchema<RowData> serializer;
    private final int maxRetry;
    private final long retryInterval;

    private volatile boolean isRunning = true;
    private Socket socket;
    private OutputStream os;

    public SocketSinkFunction(String hostname, int port, SerializationSchema<RowData> serializer, int maxRetry, long retryInterval) {
        this.hostname = hostname;
        this.port = port;
        this.serializer = serializer;
        this.maxRetry = maxRetry;
        this.retryInterval = retryInterval;
    }

    @Override
    public void open(Configuration parameters) throws Exception {

        reconnect();
    }

    /**
     * reconnecto to socket port
     *
     * @throws IOException
     */
    private void reconnect() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            LOG.info("close socket error");
        }
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(hostname, port), 0);
            os = socket.getOutputStream();
        } catch (IOException e) {
            LOG.info("connect host: {} port: {} error.", hostname, port);

        }
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    @Override
    public void invoke(RowData element, Context context) throws Exception {

        int retryTime = 0;
        byte[] message = serializer.serialize(element);
        while (retryTime <= maxRetry) {
            try {
                os.write(message);
                os.flush();
                return;
            } catch (Exception e) {
                Thread.sleep(retryInterval);
                ++retryTime;
                reconnect();
//                LOG.warn("send data error retry: {}", retryTime);
            }
        }
        LOG.warn("send error after retry {} times, ignore it: {}", maxRetry, new String(message));
    }
}