package gov.sequarius.dockercenter.center;

import gov.sequarius.dockercenter.common.rpc.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.junit.Test;

/**
 * Created by Sequarius on 2017/3/20.
 */
@Slf4j
public class CenterRPCTest {
    @Test
    public void test() throws TException, InterruptedException {
        TSocket tSocket = new TSocket("127.0.0.1", 9147);
        CenterAsynRPCService.Client client = new CenterAsynRPCService.Client(new TBinaryProtocol(tSocket));
        tSocket.open();
        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeRPCService.Processor<NodeRPCService.Iface> np = new NodeRPCService.Processor<>(new NodeRPCService
                        .Iface() {

                    @Override
                    public void exctueCommand(CommandDTO commandDTO) throws TException {
                        log.debug("command=={}",commandDTO);
                        ExecuteResultDTO resultDTO=new ExecuteResultDTO("bbb",0,2333,6666);
                        client.onCommandExcuteFinish(resultDTO);
                    }
                });
                try {
                    while (np.process(new TBinaryProtocol(tSocket), new TBinaryProtocol(tSocket))) {
                    }
                } catch (TException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        NodeInfoDTO nodeInfoDTO=new NodeInfoDTO();
        nodeInfoDTO.setIp("192.168.1.1");
        client.connet();
        while (true){
            TSocket tSocket1 = new TSocket("127.0.0.1", 9047);
            tSocket1.open();
            CenterSynRPCService.Client client2 = new CenterSynRPCService.Client(new TBinaryProtocol(tSocket1));
            CommonResultDTO commonResultDTO = client2.removeNode("99999999");
            log.debug("result=={}",commonResultDTO);
            Thread.sleep(5000);
        }
    }
}
