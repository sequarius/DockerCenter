package gov.sequarius.dockercenter.center.thrift.sever;

import gov.sequarius.dockercenter.common.rpc.NodeRPCService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sequarius on 2017/3/20.
 */
@Slf4j
public class BoostTProcessorFactory extends TProcessorFactory {
    public BoostTProcessorFactory(TProcessor processor) {
        super(processor);
    }

    private Map<String, NodeRPCService.Client> clientMap = new HashMap<>();

    public NodeRPCService.Client selectClientByIp(String ip) {
        return clientMap.get(ip);
    }

    @Override
    public TProcessor getProcessor(TTransport trans) {
        TSocket socket = (TSocket) trans;
        InetAddress inetAddress = socket.getSocket().getInetAddress();
        String hostAddress = inetAddress.getHostAddress();

        NodeRPCService.Client client = new NodeRPCService.Client(new TBinaryProtocol(trans));
        log.debug("host address=={},port=={}", hostAddress, socket.getSocket().getPort());
        clientMap.put(hostAddress, client);

//        CommandDTO commandDTO = new CommandDTO("aaaa", null);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (true) {
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        client.exctueCommand(commandDTO);
//                    }
//                } catch (TException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        return super.getProcessor(trans);
    }
}
