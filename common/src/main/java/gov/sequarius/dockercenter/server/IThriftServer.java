package gov.sequarius.dockercenter.server;

/**
 * Created by Sequarius on 2017/3/14.
 */
/**
 * Thrift服务器接口
 *
 */
public interface IThriftServer {

    /**
     * 启动Thrift服务器
     */
    public void start();

    /**
     * 停止Thrift服务器
     */
    public void stop();

    /**
     * Thrift服务器是否运行
     */
    public boolean isServing();

    /**
     * 获取Thrift服务器名称
     */
    public String getServerName();

    /**
     * 获取Thrift服务器端口
     */
    public int getServerPort();


}
