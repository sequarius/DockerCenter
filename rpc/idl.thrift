namespace java gov.sequarius.dockercenter.rpc

exception CommonException {
    1: required i32 code,
    2: string message
}

struct ResultDTO{
    1: required bool result,
    2: string message,
    3: string code,
    4: string commandExcResult
}


/**
* 节点信息DTO
**/
struct NodeInfoDTO {
    /** 名称 */
    1:string name,
    /** ip */
    2:string ip,
    /** 操作系统类型 */
    3:string architecture,
    /** 剩余磁盘空间 (kb) */
	4:i64 freeDiskSpace,
	/** 剩余内存空间 (kb) */
	5:i64 freeMemorySpace,
	/** 响应时间(ms)*/
	6:i64 responseTime,
	/** 容器数量*/
	7:i64 containerCount,
	/** 正在运行容器数量*/
	8:i64 RunningContainerCount,
	/** docker 版本信息 */
    9:string dockerVersion,
    /** docker 状态 */
    10:string dockerStatus
}
/**
* 执行命令dto
**/
struct CommandDTO{
    /** 命令 */
    1:string command ,
    /** 参数 */
    2:list<string> params
}

/**
* 基础服务
**/
service BaseService{

}
/**
* 中心节点服务
**/
service CenterService extends BaseService{
    /** 注册节点*/
    ResultDTO registerNode(1:NodeInfoDTO nodeInfo,2:string authCode);
}
/**
* 子节点服务
**/
service NodeService extends BaseService{
    /** 在子节点执行命令*/
    ResultDTO exctueCommand(1:CommandDTO dto);
    /** 获取子节点信息*/
    NodeInfoDTO getNodeInfo();
}