package gov.sequarius.dockercenter.center.thrift.sever;

import gov.sequarius.dockercenter.center.thrift.handler.CenterHandler;
import gov.sequarius.dockercenter.common.rpc.CenterService;
import gov.sequarius.dockercenter.common.server.IThriftServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.Executors;

/**
 * Created by Sequarius on 2017/3/14.
 */
@Slf4j
@Component
public class CenterThriftServer implements IThriftServer {
    private TServer server;
    @Value("${thrift.server.name}")
    private String thriftServerName;
    @Value("${thrift.server.port}")
    private int thriftServerPort;

    @Resource
    private CenterHandler centerHandler;
    @Override
    @PostConstruct
    public void start() {
        if (server == null) {
            if (thriftServerPort < 1024 || thriftServerPort > 65535) {
                throw new RuntimeException(
                        "Thrift server initialize failed ! Port range must is 1025 ~ 65535 .");
            }
            initServer();
        }
        if (server != null) {
            if (server.isServing()) {
                log.info("Thrift server already run .");
                return;
            }
            Executors.newSingleThreadExecutor().execute(() -> server.serve());
            log.info("thrift server[" + thriftServerName + "] start complete! port==" + thriftServerPort);
        } else {
            log.warn("Starting thrift server failed !");
        }
    }

    private void initServer() {
        try {
            log.info("start thrift server " + thriftServerName + " on port " + thriftServerPort);
            TServer.Args args = new TServer.Args(new TServerSocket(thriftServerPort));
            TMultiplexedProcessor processor = new TMultiplexedProcessor();
            processor.registerProcessor("CenterService", new CenterService.Processor(centerHandler));
            args.transportFactory(new TTransportFactory());
            args.processor(processor);
            args.processorFactory(new TProcessorFactory(processor));
            server = new TSimpleServer(args);
        } catch (TTransportException e) {
            log.error("thrift server start error" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("thrift server start error");
        }
    }

    @Override
    public void stop() {
        if (server != null && server.isServing()) {
            server.stop();
            log.info("Stop thrift server ...... (name: " + thriftServerName + ")");
        } else {
            log.info("Thrift server not run. (name: " + thriftServerName + ")");
        }
    }

    @Override
    public boolean isServing() {
        if (server == null) {
            return false;
        }
        return server.isServing();
    }

    @Override
    public String getServerName() {
        return thriftServerName;
    }

    @Override
    public int getServerPort() {
        return thriftServerPort;
    }
}
