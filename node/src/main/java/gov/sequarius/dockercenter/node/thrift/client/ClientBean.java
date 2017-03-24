package gov.sequarius.dockercenter.node.thrift.client;

import gov.sequarius.dockercenter.common.rpc.CenterAsynRPCService;
import gov.sequarius.dockercenter.common.rpc.CenterSynRPCService;
import gov.sequarius.dockercenter.common.rpc.NodeRPCService;
import gov.sequarius.dockercenter.node.thrift.handler.NodeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

/**
 * Created by Sequarius on 2017/3/21.
 */
@Configuration
@Slf4j
public class ClientBean {
    @Value("${thrift.server.asyn.port}")
    private int ASYN_THRIFT_SERVER_PORT;
    @Value("${thrift.server.syn.port}")
    private int SYN_THRIFT_SERVER_PORT;
    @Bean
    public CenterAsynRPCService.Client centerAsynClient(NodeHandler nodeHandler){
        TSocket tSocket = new TSocket("127.0.0.1", ASYN_THRIFT_SERVER_PORT);
        NodeRPCService.Processor<NodeRPCService.Iface> np = new NodeRPCService.Processor<>(nodeHandler);
        try {
            tSocket.open();
        } catch (TTransportException e) {
            log.error(e.getMessage(),e);
        }
        CenterAsynRPCService.Client client = new CenterAsynRPCService.Client(new TBinaryProtocol(tSocket));
        log.info("asyn client init successfully");
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                while (np.process(new TBinaryProtocol(tSocket), new TBinaryProtocol(tSocket))) {
                }
            } catch (TException e) {
                log.warn(e.getMessage(),e);
                tSocket.close();
            }
        });
        return client;
    }

    @Bean
    public CenterSynRPCService.Client centerSynClient(){
        TSocket tSocket = new TSocket("127.0.0.1", SYN_THRIFT_SERVER_PORT);
        try {
            tSocket.open();
        } catch (TTransportException e) {
            log.error(e.getMessage(),e);
        }
        CenterSynRPCService.Client client = new CenterSynRPCService.Client(new TBinaryProtocol(tSocket));
        log.info("syn client init successfully");
        return client;
    }
}
