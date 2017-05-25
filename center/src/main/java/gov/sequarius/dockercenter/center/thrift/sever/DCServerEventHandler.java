package gov.sequarius.dockercenter.center.thrift.sever;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sequarius on 2017/3/27.
 */
@Component
@Slf4j
public class DCServerEventHandler implements TServerEventHandler {

    private Map<Long,Socket> socketMap;


    @PostConstruct
    private void init(){
        socketMap=new HashMap<>();
    }

    public Socket getSocketByThradId(Long threadId){
        return socketMap.get(threadId);
    }

    @Override
    public void preServe() {

    }

    @Override
    public ServerContext createContext(TProtocol tProtocol, TProtocol tProtocol1) {
        if (tProtocol != null && tProtocol.getTransport() != null) {
            Socket socket = ((TSocket) tProtocol.getTransport()).getSocket();
//            log.debug("[Monitor] ThriftServer Socket Info : server address: " + socket.getLocalAddress() + " , " +
//                    "port: " + socket.getLocalPort() + " , client address: " + socket.getInetAddress() + " , " +
//                    "port: " + socket.getPort());
            socketMap.put(Thread.currentThread().getId(),socket);
        }
        return null;
    }

    @Override
    public void deleteContext(ServerContext serverContext, TProtocol tProtocol, TProtocol tProtocol1) {

    }

    @Override
    public void processContext(ServerContext serverContext, TTransport tTransport, TTransport tTransport1) {

    }
}
