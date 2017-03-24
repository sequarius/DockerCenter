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

docker_center_command = ['node-list', 'help', 'version']
docker_center_param_name = ['--node-tag']


def execute_command(dc_command):
    try:
        transport = TSocket.TSocket('localhost', 9047)
        transport = TTransport.TBufferedTransport(transport)
        protocol = TBinaryProtocol.TBinaryProtocol(transport)
        client = CenterSynRPCService.Client(protocol)
        transport.open()
        dto = CommandDTO(command.command, dc_command.docker_params)
        result = client.executeCommand(dto)
        print(result)
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


def get_node_info():
    try:
        transport = TSocket.TSocket('localhost', 9047)
        transport = TTransport.TBufferedTransport(transport)
        protocol = TBinaryProtocol.TBinaryProtocol(transport)
        client = CenterSynRPCService.Client(protocol)
        transport.open()
        result = client.getNodeMap()
        x = PrettyTable(["Tag", "Name", "Node Ip", "version", "status", "Architecture", "Free Disk", "Free Memory",
                         "Response Time", "Container Running/Total"])
        for node in result.values():
            print(node)
            x.add_row([node.tag, node.name, node.ip, node.dockerVersion, node.dockerStatus, node.architecture,
                       node.freeDiskSpace, node.freeMemorySpace, node.responseTime, str(node.RunningContainerCount)
                       + '/' + str(node.containerCount)])
        print(x)
    except Thrift.TException as e:
        print(e)


if __name__ == '__main__':
    command = parse_param(sys.argv)
    if command.command not in docker_center_command:
        print(command.docker_params)
        execute_command(command)
    else:
        if command.command == docker_center_command[0]:
            get_node_info()
