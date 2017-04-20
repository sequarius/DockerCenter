import sys
from idl.ttypes import CommandDTO
from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from idl import CenterSynRPCService
from Command import Command
from prettytable import PrettyTable

sys.path.append("./idl")

docker_center_command = ['node-list', "create-job", "job-list", "start-job", "end-job", 'help', 'version']
docker_center_param_name = ['--node-tag']

BILLION = 100000000


def execute_command(dc_command):
    try:
        transport = TSocket.TSocket('localhost', 9047)
        transport = TTransport.TBufferedTransport(transport)
        protocol = TBinaryProtocol.TBinaryProtocol(transport)
        client = CenterSynRPCService.Client(protocol)
        transport.open()
        dto = CommandDTO(command.command, dc_command.docker_params)
        dto.nodeTag = int(dc_command.center_params['--node-tag'])
        result = client.executeCommand(dto)
        print(result.returnMessage)
    except Thrift.TException as e:
        print(e)


def parse_param(args):
    center_param_map = {}
    docker_param_list = []
    dc_command = Command()
    if len(args) < 2:
        dc_command.command = 'help'
        return
    dc_command.command = args[1]
    skip_loop = False
    for x in args[2:]:
        if skip_loop:
            skip_loop = False
            continue
        if x in docker_center_param_name:
            center_param_map[x] = args[args.index(x) + 1]
            skip_loop = True
        else:
            docker_param_list.append(x)
    dc_command.center_params = center_param_map
    dc_command.docker_params = docker_param_list
    return dc_command


# def get_docker_engine_version(version_str):
#     lines = version_str.split('\n')
#     for i, line in enumerate(lines):
#         if "Server:" not in line:
#             continue
#         if "Version:" in lines[i + 1]:
#             return lines[i + 1].replace("Version:", "").strip()
#     return "UNKNOWN"


def get_node_info():
    try:
        client, transport = get_thrift_client()
        transport.open()
        result = client.getNodeMap()
        transport.close()
        x = PrettyTable(["Tag", "Name", "Node Ip", "version", "status", "Architecture", "Free Disk", "Free Memory",
                         "Response Time", "Container Running/Total"])
        for node in result.values():
            x.add_row([node.tag, node.name, node.ip, node.dockerVersion, node.dockerStatus,
                       node.architecture,
                       node.freeDiskSpace / BILLION, node.freeMemorySpace / BILLION, node.responseTime,
                       str(node.RunningContainerCount)
                       + '/' + str(node.containerCount)])
        print(x)
    except Thrift.TException as e:
        print(e)


def create_job(job_name):
    try:
        client, transport = get_thrift_client()
        transport.open()
        result = client.newJob(job_name)
        transport.close()
        print(result.message)
    except Thrift.TException as e:
        print(e)


def start_job(job_name):
    try:
        client, transport = get_thrift_client()
        transport.open()
        result = client.startJob(job_name)
        transport.close()
        print(result.message)
    except Thrift.TException as e:
        print(e)


def stop_job(job_name):
    try:
        print(job_name)
        client, transport = get_thrift_client()
        transport.open()
        result = client.stopJob(job_name)
        transport.close()
        print(result.message)
    except Thrift.TException as e:
        print(e)


def get_thrift_client():
    transport = TSocket.TSocket('localhost', 9047)
    transport = TTransport.TBufferedTransport(transport)
    protocol = TBinaryProtocol.TBinaryProtocol(transport)
    client = CenterSynRPCService.Client(protocol)
    return client, transport


def get_job_list():
    try:
        client, transport = get_thrift_client()
        transport.open()
        result = client.getJoblist()
        transport.close()
        print(result)

        x = PrettyTable(["ID", "Name", "Status", "Deploy Strategy", "SubName Strategy"])
        for job in result:
            x.add_row([job.jobId, job.jobname, job.status, job.deployStrategy, job.subNameStrategy])
        print(x)
    except Thrift.TException as e:
        print(e)


if __name__ == '__main__':
    command = parse_param(sys.argv)
    if command.command not in docker_center_command:
        execute_command(command)
    else:
        if command.command == docker_center_command[0]:
            get_node_info()
        if command.command == docker_center_command[1]:
            if len(command.docker_params) != 1:
                print("missing job name, try user dockerc help to get function use.")
            else:
                job_name = command.docker_params[0]
                create_job(job_name)
        if command.command == docker_center_command[2]:
            get_job_list()
        if command.command == docker_center_command[3]:
            if len(command.docker_params) != 1:
                print("missing job name, try user dockerc help to get function use.")
            else:
                job_name = command.docker_params[0]
                start_job(job_name)
        if command.command == docker_center_command[4]:
            if len(command.docker_params) != 1:
                print("missing job name, try user dockerc help to get function use.")
            else:
                job_name = command.docker_params[0]
                stop_job(job_name)
