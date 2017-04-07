package gov.sequarius.dockercenter.center.thrift.sever;

import gov.sequarius.dockercenter.center.thrift.handler.CenterAsynHandler;
import gov.sequarius.dockercenter.common.rpc.CenterAsynRPCService;
import gov.sequarius.dockercenter.common.rpc.NodeRPCService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
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
public class CenterAsynThriftServer implements IThriftServer {
    private TServer server;
    @Value("${thrift.server.asyn.name}")
    private String thriftServerName;
    @Value("${thrift.server.asyn.port}")
    private int thriftServerPort;

    @Resource
    private CenterAsynHandler centerHandler;
    private BoostTProcessorFactory boostTProcessorFactory;

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
            log.info("thrift Asyn server[" + thriftServerName + "] start complete! port==" + thriftServerPort);
        } else {
            log.warn("Starting thrift server failed !");
        }
    }

    private void initServer() {
        try {
            log.info("start thrift server " + thriftServerName + " on port " + thriftServerPort);
            TServer.Args args = new TServer.Args(new TServerSocket(thriftServerPort));
//            TMultiplexedProcessor processor = new TMultiplexedProcessor();
            TBinaryProtocol.Factory protocolFactory = new TBinaryProtocol.Factory();
            boostTProcessorFactory = new BoostTProcessorFactory(new CenterAsynRPCService.Processor
                    (centerHandler));
//            processor.registerProcessor("CenterRPCService", new CenterRPCService.Processor(centerHandler));
            args.protocolFactory(protocolFactory);
            args.processorFactory(boostTProcessorFactory);
//            args.processorFactory(new TProcessorFactory(processor));
            server = new TSimpleServer(args);
        } catch (TTransportException e) {
            log.error("thrift server start error" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("thrift server start error");
        }
    }

    public NodeRPCService.Client selectClientByIp(String ip){
        return boostTProcessorFactory.selectClientByIp(ip);
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
