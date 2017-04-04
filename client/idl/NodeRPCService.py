#
# Autogenerated by Thrift Compiler (0.10.0)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TFrozenDict, TException, TApplicationException
from thrift.protocol.TProtocol import TProtocolException
import sys
import idl.BaseService
import logging
from .ttypes import *
from thrift.Thrift import TProcessor
from thrift.transport import TTransport


class Iface(idl.BaseService.Iface):
    def exctueCommand(self, dto):
        """
        run command on node

        Parameters:
         - dto
        """
        pass


class Client(idl.BaseService.Client, Iface):
    def __init__(self, iprot, oprot=None):
        idl.BaseService.Client.__init__(self, iprot, oprot)

    def exctueCommand(self, dto):
        """
        run command on node

        Parameters:
         - dto
        """
        self.send_exctueCommand(dto)

    def send_exctueCommand(self, dto):
        self._oprot.writeMessageBegin('exctueCommand', TMessageType.ONEWAY, self._seqid)
        args = exctueCommand_args()
        args.dto = dto
        args.write(self._oprot)
        self._oprot.writeMessageEnd()
        self._oprot.trans.flush()


class Processor(idl.BaseService.Processor, Iface, TProcessor):
    def __init__(self, handler):
        idl.BaseService.Processor.__init__(self, handler)
        self._processMap["exctueCommand"] = Processor.process_exctueCommand

    def process(self, iprot, oprot):
        (name, type, seqid) = iprot.readMessageBegin()
        if name not in self._processMap:
            iprot.skip(TType.STRUCT)
            iprot.readMessageEnd()
            x = TApplicationException(TApplicationException.UNKNOWN_METHOD, 'Unknown function %s' % (name))
            oprot.writeMessageBegin(name, TMessageType.EXCEPTION, seqid)
            x.write(oprot)
            oprot.writeMessageEnd()
            oprot.trans.flush()
            return
        else:
            self._processMap[name](self, seqid, iprot, oprot)
        return True

    def process_exctueCommand(self, seqid, iprot, oprot):
        args = exctueCommand_args()
        args.read(iprot)
        iprot.readMessageEnd()
        try:
            self._handler.exctueCommand(args.dto)
        except (TTransport.TTransportException, KeyboardInterrupt, SystemExit):
            raise
        except:
            pass

# HELPER FUNCTIONS AND STRUCTURES


class exctueCommand_args(object):
    """
    Attributes:
     - dto
    """

    thrift_spec = (
        None,  # 0
        (1, TType.STRUCT, 'dto', (CommandDTO, CommandDTO.thrift_spec), None, ),  # 1
    )

    def __init__(self, dto=None,):
        self.dto = dto

    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, (self.__class__, self.thrift_spec))
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            if fid == 1:
                if ftype == TType.STRUCT:
                    self.dto = CommandDTO()
                    self.dto.read(iprot)
                else:
                    iprot.skip(ftype)
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, (self.__class__, self.thrift_spec)))
            return
        oprot.writeStructBegin('exctueCommand_args')
        if self.dto is not None:
            oprot.writeFieldBegin('dto', TType.STRUCT, 1)
            self.dto.write(oprot)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)
