package gov.sequarius.dockercenter.center.thrift.sever;

/**
 * Created by Sequarius on 2017/3/14.
 */
/**
 * Thrift Server
 *
 */
public interface IThriftServer {

    /**
     * start thrift server
     */
     void start();

    /**
     * stop thrift server
     */
    void stop();

    /**
     * check thrift
     */
    boolean isServing();

    /**
     * 获取Thrift服务器名称
     */
    public String getServerName();

    /**
     * 获取Thrift服务器端口
     */
    public int getServerPort();


}
