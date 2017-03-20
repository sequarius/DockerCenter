/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package gov.sequarius.dockercenter.common.rpc;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * 执行命令dto
 * 
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-03-20")
public class CommandDTO implements org.apache.thrift.TBase<CommandDTO, CommandDTO._Fields>, java.io.Serializable, Cloneable, Comparable<CommandDTO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CommandDTO");

  private static final org.apache.thrift.protocol.TField COMMAND_FIELD_DESC = new org.apache.thrift.protocol.TField("command", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PARAMS_FIELD_DESC = new org.apache.thrift.protocol.TField("params", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField NODE_TAG_FIELD_DESC = new org.apache.thrift.protocol.TField("nodeTag", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CommandDTOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CommandDTOTupleSchemeFactory();

  /**
   * 命令
   */
  public String command; // required
  /**
   * 参数
   */
  public java.util.List<String> params; // required
  /**
   * 执行节点tag
   */
  public int nodeTag; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 命令
     */
    COMMAND((short)1, "command"),
    /**
     * 参数
     */
    PARAMS((short)2, "params"),
    /**
     * 执行节点tag
     */
    NODE_TAG((short)3, "nodeTag");

    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // COMMAND
          return COMMAND;
        case 2: // PARAMS
          return PARAMS;
        case 3: // NODE_TAG
          return NODE_TAG;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __NODETAG_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.COMMAND, new org.apache.thrift.meta_data.FieldMetaData("command", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARAMS, new org.apache.thrift.meta_data.FieldMetaData("params", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.NODE_TAG, new org.apache.thrift.meta_data.FieldMetaData("nodeTag", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CommandDTO.class, metaDataMap);
  }

  public CommandDTO() {
  }

  public CommandDTO(
    String command,
    java.util.List<String> params,
    int nodeTag)
  {
    this();
    this.command = command;
    this.params = params;
    this.nodeTag = nodeTag;
    setNodeTagIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CommandDTO(CommandDTO other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetCommand()) {
      this.command = other.command;
    }
    if (other.isSetParams()) {
      java.util.List<String> __this__params = new java.util.ArrayList<String>(other.params);
      this.params = __this__params;
    }
    this.nodeTag = other.nodeTag;
  }

  public CommandDTO deepCopy() {
    return new CommandDTO(this);
  }

  @Override
  public void clear() {
    this.command = null;
    this.params = null;
    setNodeTagIsSet(false);
    this.nodeTag = 0;
  }

  /**
   * 命令
   */
  public String getCommand() {
    return this.command;
  }

  /**
   * 命令
   */
  public CommandDTO setCommand(String command) {
    this.command = command;
    return this;
  }

  public void unsetCommand() {
    this.command = null;
  }

  /** Returns true if field command is set (has been assigned a value) and false otherwise */
  public boolean isSetCommand() {
    return this.command != null;
  }

  public void setCommandIsSet(boolean value) {
    if (!value) {
      this.command = null;
    }
  }

  public int getParamsSize() {
    return (this.params == null) ? 0 : this.params.size();
  }

  public java.util.Iterator<String> getParamsIterator() {
    return (this.params == null) ? null : this.params.iterator();
  }

  public void addToParams(String elem) {
    if (this.params == null) {
      this.params = new java.util.ArrayList<String>();
    }
    this.params.add(elem);
  }

  /**
   * 参数
   */
  public java.util.List<String> getParams() {
    return this.params;
  }

  /**
   * 参数
   */
  public CommandDTO setParams(java.util.List<String> params) {
    this.params = params;
    return this;
  }

  public void unsetParams() {
    this.params = null;
  }

  /** Returns true if field params is set (has been assigned a value) and false otherwise */
  public boolean isSetParams() {
    return this.params != null;
  }

  public void setParamsIsSet(boolean value) {
    if (!value) {
      this.params = null;
    }
  }

  /**
   * 执行节点tag
   */
  public int getNodeTag() {
    return this.nodeTag;
  }

  /**
   * 执行节点tag
   */
  public CommandDTO setNodeTag(int nodeTag) {
    this.nodeTag = nodeTag;
    setNodeTagIsSet(true);
    return this;
  }

  public void unsetNodeTag() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __NODETAG_ISSET_ID);
  }

  /** Returns true if field nodeTag is set (has been assigned a value) and false otherwise */
  public boolean isSetNodeTag() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __NODETAG_ISSET_ID);
  }

  public void setNodeTagIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __NODETAG_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case COMMAND:
      if (value == null) {
        unsetCommand();
      } else {
        setCommand((String)value);
      }
      break;

    case PARAMS:
      if (value == null) {
        unsetParams();
      } else {
        setParams((java.util.List<String>)value);
      }
      break;

    case NODE_TAG:
      if (value == null) {
        unsetNodeTag();
      } else {
        setNodeTag((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case COMMAND:
      return getCommand();

    case PARAMS:
      return getParams();

    case NODE_TAG:
      return getNodeTag();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case COMMAND:
      return isSetCommand();
    case PARAMS:
      return isSetParams();
    case NODE_TAG:
      return isSetNodeTag();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CommandDTO)
      return this.equals((CommandDTO)that);
    return false;
  }

  public boolean equals(CommandDTO that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_command = true && this.isSetCommand();
    boolean that_present_command = true && that.isSetCommand();
    if (this_present_command || that_present_command) {
      if (!(this_present_command && that_present_command))
        return false;
      if (!this.command.equals(that.command))
        return false;
    }

    boolean this_present_params = true && this.isSetParams();
    boolean that_present_params = true && that.isSetParams();
    if (this_present_params || that_present_params) {
      if (!(this_present_params && that_present_params))
        return false;
      if (!this.params.equals(that.params))
        return false;
    }

    boolean this_present_nodeTag = true;
    boolean that_present_nodeTag = true;
    if (this_present_nodeTag || that_present_nodeTag) {
      if (!(this_present_nodeTag && that_present_nodeTag))
        return false;
      if (this.nodeTag != that.nodeTag)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetCommand()) ? 131071 : 524287);
    if (isSetCommand())
      hashCode = hashCode * 8191 + command.hashCode();

    hashCode = hashCode * 8191 + ((isSetParams()) ? 131071 : 524287);
    if (isSetParams())
      hashCode = hashCode * 8191 + params.hashCode();

    hashCode = hashCode * 8191 + nodeTag;

    return hashCode;
  }

  @Override
  public int compareTo(CommandDTO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCommand()).compareTo(other.isSetCommand());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCommand()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.command, other.command);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetParams()).compareTo(other.isSetParams());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParams()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.params, other.params);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNodeTag()).compareTo(other.isSetNodeTag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNodeTag()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nodeTag, other.nodeTag);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("CommandDTO(");
    boolean first = true;

    sb.append("command:");
    if (this.command == null) {
      sb.append("null");
    } else {
      sb.append(this.command);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("params:");
    if (this.params == null) {
      sb.append("null");
    } else {
      sb.append(this.params);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("nodeTag:");
    sb.append(this.nodeTag);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (command == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'command' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CommandDTOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CommandDTOStandardScheme getScheme() {
      return new CommandDTOStandardScheme();
    }
  }

  private static class CommandDTOStandardScheme extends org.apache.thrift.scheme.StandardScheme<CommandDTO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CommandDTO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // COMMAND
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.command = iprot.readString();
              struct.setCommandIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PARAMS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.params = new java.util.ArrayList<String>(_list0.size);
                String _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = iprot.readString();
                  struct.params.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setParamsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // NODE_TAG
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.nodeTag = iprot.readI32();
              struct.setNodeTagIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, CommandDTO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.command != null) {
        oprot.writeFieldBegin(COMMAND_FIELD_DESC);
        oprot.writeString(struct.command);
        oprot.writeFieldEnd();
      }
      if (struct.params != null) {
        oprot.writeFieldBegin(PARAMS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.params.size()));
          for (String _iter3 : struct.params)
          {
            oprot.writeString(_iter3);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(NODE_TAG_FIELD_DESC);
      oprot.writeI32(struct.nodeTag);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CommandDTOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CommandDTOTupleScheme getScheme() {
      return new CommandDTOTupleScheme();
    }
  }

  private static class CommandDTOTupleScheme extends org.apache.thrift.scheme.TupleScheme<CommandDTO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CommandDTO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.command);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetParams()) {
        optionals.set(0);
      }
      if (struct.isSetNodeTag()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetParams()) {
        {
          oprot.writeI32(struct.params.size());
          for (String _iter4 : struct.params)
          {
            oprot.writeString(_iter4);
          }
        }
      }
      if (struct.isSetNodeTag()) {
        oprot.writeI32(struct.nodeTag);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CommandDTO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.command = iprot.readString();
      struct.setCommandIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.params = new java.util.ArrayList<String>(_list5.size);
          String _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = iprot.readString();
            struct.params.add(_elem6);
          }
        }
        struct.setParamsIsSet(true);
      }
      if (incoming.get(1)) {
        struct.nodeTag = iprot.readI32();
        struct.setNodeTagIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

