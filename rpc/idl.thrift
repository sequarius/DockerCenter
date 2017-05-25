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
    3: optional i32 nodeTag
    4: optional i32 commandTag
}

struct ExecuteResultDTO{
    1: string returnMessage,
    2: required i32 resultCode,
    3: optional i32 nodeTag,
    4: optional i32 commandTag
}

/**
* command dto
**/
struct CommandDTO{
    /** command */
    1:required string command ,
    /** params */
    2:list<string> params,
    /** execute tag*/
    3: optional i32 nodeTag,
    /** command tag*/
    4: optional i32 commandTag
}

/**
* command Result dto
**/
struct CommonResultDTO{
    1: required i32 resultCode,
    2: bool result,
    3: optional string message
    4: optional i32 nodeTag,
    5: optional i32 commandTag
}

struct JobDTO{
    1: required string jobname,
    2: optional string jobId,
    3: optional string status,
    4: optional string deployStrategy,
    5: optional string subNameStrategy,
    6: optional string config,
}

/**
* node info dto
**/
struct NodeInfoDTO {
    1:string name,
    2:optional string ip,
    3:string architecture,
	4:i64 freeDiskSpace,
	5:i64 freeMemorySpace,
	6:i64 responseTime,
	7:i64 containerCount,
	8:i64 RunningContainerCount,
    9:string dockerVersion,
    10:string dockerStatus
    11:optional i32 tag
    12:i64 callTime
}



service BaseService{

}


service CenterSynRPCService extends BaseService{
    /** registerNode*/
    CommonResultDTO registerNode(1:NodeInfoDTO nodeInfo,2:string authCode);
    /** updateNodeInfo*/
    CommonResultDTO updateNodeInfo(1:NodeInfoDTO nodeInfo);
    /** removeNode*/
    CommonResultDTO removeNode();
    /** getNodeMap */
    map<string,NodeInfoDTO> getNodeMap();
    /** executeCommand*/
    ExecuteResultDTO executeCommand(1:CommandDTO dto);
    CommonResultDTO newJob(1:string jobName);
    JobDTO getJobStatus(1:string jobName);
    CommonResultDTO startJob(1:string jobName);
    CommonResultDTO stopJob(1:string jobName);
    list<JobDTO> getJoblist();
    CommonResultDTO uploadLog(1:binary log,2:i32 tagId);
}

service CenterAsynRPCService{
    /**connet */
    oneway void connet();
    /**call back when excute command finish*/
    oneway void onCommandExcuteFinish(1:ExecuteResultDTO executeResultDTO);
}

service NodeRPCService extends BaseService{
    /** run command on node*/
    oneway void exctueCommand(1:CommandDTO dto);
}