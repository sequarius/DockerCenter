namespace java gov.sequarius.dockercenter.common.rpc
namespace cpp dockercenter


const map<i32,string> RESPONSE_CODE_MAP={
    0:"SUCCESS",
    -0xFF00FFF:"UNDEFINED CODE",
    401:"UNAUTHORIZED NODE"
}

exception CommonException {
    1: required i32 code,
    2: optional string message
}

struct ExecuteResultDTO{
    1: string returnMessage,
    2: required i32 resultCode,
    3: optional i32 nodeTag,
    4: optional i32 commandTag
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
    3: optional i32 nodeTag,
    4: optional i32 commandTag
}

struct CommonResultDTO{
    1: required i32 resultCode,
    2: bool result,
    3: optional string message
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
    11:optional i32 tag
}


/**
* 基础服务
**/
service BaseService{

}
/**
* 中心节点服务
**/
service CenterSynRPCService extends BaseService{
    /** 注册节点*/
    CommonResultDTO registerNode(1:NodeInfoDTO nodeInfo,2:string authCode);
    /** 更新节点状态*/
    CommonResultDTO updateNodeInfo(1:NodeInfoDTO nodeInfo);
    /** 注销节点*/
    CommonResultDTO removeNode(1:string ip);
    /** 获取注册节点列表 */
    map<string,NodeInfoDTO> getNodeMap();
    /** 执行docker指令*/
    ExecuteResultDTO executeCommand(1:CommandDTO dto);
}

service CenterAsynRPCService{
    /**连接主节点 */
    oneway void connet();
    /**异步通知命令执行完成*/
    oneway void onCommandExcuteFinish(1:ExecuteResultDTO executeResultDTO);
}
/**
* 子节点服务
**/
service NodeRPCService extends BaseService{
    /** 在子节点执行命令*/
    oneway void exctueCommand(1:CommandDTO dto);
}