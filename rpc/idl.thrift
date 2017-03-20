namespace java gov.sequarius.dockercenter.common.rpc

exception CommonException {
    1: required i32 code,
    2: string message
}

struct ExecuteResultDTO{
    1: string returnMessage,
    2: required i32 resultCode,
}

struct CommonResultDTO{
    1: required i32 resultCode,
    2: bool result,
    3: string message,
}

/**
* 节点信息DTO
**/
struct NodeInfoDTO {
    /** 名称 */
    1:string name,
    /** ip */
    2:required string ip,
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
    /** 节点tag*/
    11:i32 tag
}
/**
* 执行命令dto
**/
struct CommandDTO{
    /** 命令 */
    1:required string command ,
    /** 参数 */
    2:list<string> params,
    /** 执行节点tag*/
    3:i32 nodeTag;
}

/**
* 基础服务
**/
service BaseService{

}
/**
* 中心节点服务
**/
service CenterRPCService extends BaseService{
    /** 注册节点*/
    CommonResultDTO registerNode(1:NodeInfoDTO nodeInfo,2:string authCode);
    /** 注销节点*/
    CommonResultDTO removeNode(1:string ip);
    /** 获取注册节点列表 */
    map<string,NodeInfoDTO> getNodeMap();
    /** 执行docker指令*/
    ExecuteResultDTO excuteCommand(1:CommandDTO dto);
}
/**
* 子节点服务
**/
service NodeRPCService extends BaseService{
    /** 在子节点执行命令*/
    ExecuteResultDTO exctueCommand(1:CommandDTO dto);
    /** 获取子节点信息*/
    NodeInfoDTO getNodeInfo();
}