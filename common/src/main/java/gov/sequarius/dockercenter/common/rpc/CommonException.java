/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package gov.sequarius.dockercenter.common.rpc;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-05-17")
public class CommonException extends org.apache.thrift.TException implements org.apache.thrift.TBase<CommonException, CommonException._Fields>, java.io.Serializable, Cloneable, Comparable<CommonException> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CommonException");

  private static final org.apache.thrift.protocol.TField CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("code", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField MESSAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("message", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField NODE_TAG_FIELD_DESC = new org.apache.thrift.protocol.TField("nodeTag", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField COMMAND_TAG_FIELD_DESC = new org.apache.thrift.protocol.TField("commandTag", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CommonExceptionStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CommonExceptionTupleSchemeFactory();

  public int code; // required
  public String message; // optional
  public int nodeTag; // optional
  public int commandTag; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CODE((short)1, "code"),
    MESSAGE((short)2, "message"),
    NODE_TAG((short)3, "nodeTag"),
    COMMAND_TAG((short)4, "commandTag");

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
        case 1: // CODE
          return CODE;
        case 2: // MESSAGE
          return MESSAGE;
        case 3: // NODE_TAG
          return NODE_TAG;
        case 4: // COMMAND_TAG
          return COMMAND_TAG;
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
  private static final int __CODE_ISSET_ID = 0;
  private static final int __NODETAG_ISSET_ID = 1;
  private static final int __COMMANDTAG_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.MESSAGE,_Fields.NODE_TAG,_Fields.COMMAND_TAG};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CODE, new org.apache.thrift.meta_data.FieldMetaData("code", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MESSAGE, new org.apache.thrift.meta_data.FieldMetaData("message", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.NODE_TAG, new org.apache.thrift.meta_data.FieldMetaData("nodeTag", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.COMMAND_TAG, new org.apache.thrift.meta_data.FieldMetaData("commandTag", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CommonException.class, metaDataMap);
  }

  public CommonException() {
  }

  public CommonException(
    int code)
  {
    this();
    this.code = code;
    setCodeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CommonException(CommonException other) {
    __isset_bitfield = other.__isset_bitfield;
    this.code = other.code;
    if (other.isSetMessage()) {
      this.message = other.message;
    }
    this.nodeTag = other.nodeTag;
    this.commandTag = other.commandTag;
  }

  public CommonException deepCopy() {
    return new CommonException(this);
  }

  @Override
  public void clear() {
    setCodeIsSet(false);
    this.code = 0;
    this.message = null;
    setNodeTagIsSet(false);
    this.nodeTag = 0;
    setCommandTagIsSet(false);
    this.commandTag = 0;
  }

  public int getCode() {
    return this.code;
  }

  public CommonException setCode(int code) {
    this.code = code;
    setCodeIsSet(true);
    return this;
  }

  public void unsetCode() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CODE_ISSET_ID);
  }

  /** Returns true if field code is set (has been assigned a value) and false otherwise */
  public boolean isSetCode() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CODE_ISSET_ID);
  }

  public void setCodeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CODE_ISSET_ID, value);
  }

  public String getMessage() {
    return this.message;
  }

  public CommonException setMessage(String message) {
    this.message = message;
    return this;
  }

  public void unsetMessage() {
    this.message = null;
  }

  /** Returns true if field message is set (has been assigned a value) and false otherwise */
  public boolean isSetMessage() {
    return this.message != null;
  }

  public void setMessageIsSet(boolean value) {
    if (!value) {
      this.message = null;
    }
  }

  public int getNodeTag() {
    return this.nodeTag;
  }

  public CommonException setNodeTag(int nodeTag) {
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

  public int getCommandTag() {
    return this.commandTag;
  }

  public CommonException setCommandTag(int commandTag) {
    this.commandTag = commandTag;
    setCommandTagIsSet(true);
    return this;
  }

  public void unsetCommandTag() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __COMMANDTAG_ISSET_ID);
  }

  /** Returns true if field commandTag is set (has been assigned a value) and false otherwise */
  public boolean isSetCommandTag() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __COMMANDTAG_ISSET_ID);
  }

  public void setCommandTagIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __COMMANDTAG_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CODE:
      if (value == null) {
        unsetCode();
      } else {
        setCode((Integer)value);
      }
      break;

    case MESSAGE:
      if (value == null) {
        unsetMessage();
      } else {
        setMessage((String)value);
      }
      break;

    case NODE_TAG:
      if (value == null) {
        unsetNodeTag();
      } else {
        setNodeTag((Integer)value);
      }
      break;

    case COMMAND_TAG:
      if (value == null) {
        unsetCommandTag();
      } else {
        setCommandTag((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CODE:
      return getCode();

    case MESSAGE:
      return getMessage();

    case NODE_TAG:
      return getNodeTag();

    case COMMAND_TAG:
      return getCommandTag();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CODE:
      return isSetCode();
    case MESSAGE:
      return isSetMessage();
    case NODE_TAG:
      return isSetNodeTag();
    case COMMAND_TAG:
      return isSetCommandTag();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CommonException)
      return this.equals((CommonException)that);
    return false;
  }

  public boolean equals(CommonException that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_code = true;
    boolean that_present_code = true;
    if (this_present_code || that_present_code) {
      if (!(this_present_code && that_present_code))
        return false;
      if (this.code != that.code)
        return false;
    }

    boolean this_present_message = true && this.isSetMessage();
    boolean that_present_message = true && that.isSetMessage();
    if (this_present_message || that_present_message) {
      if (!(this_present_message && that_present_message))
        return false;
      if (!this.message.equals(that.message))
        return false;
    }

    boolean this_present_nodeTag = true && this.isSetNodeTag();
    boolean that_present_nodeTag = true && that.isSetNodeTag();
    if (this_present_nodeTag || that_present_nodeTag) {
      if (!(this_present_nodeTag && that_present_nodeTag))
        return false;
      if (this.nodeTag != that.nodeTag)
        return false;
    }

    boolean this_present_commandTag = true && this.isSetCommandTag();
    boolean that_present_commandTag = true && that.isSetCommandTag();
    if (this_present_commandTag || that_present_commandTag) {
      if (!(this_present_commandTag && that_present_commandTag))
        return false;
      if (this.commandTag != that.commandTag)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + code;

    hashCode = hashCode * 8191 + ((isSetMessage()) ? 131071 : 524287);
    if (isSetMessage())
      hashCode = hashCode * 8191 + message.hashCode();

    hashCode = hashCode * 8191 + ((isSetNodeTag()) ? 131071 : 524287);
    if (isSetNodeTag())
      hashCode = hashCode * 8191 + nodeTag;

    hashCode = hashCode * 8191 + ((isSetCommandTag()) ? 131071 : 524287);
    if (isSetCommandTag())
      hashCode = hashCode * 8191 + commandTag;

    return hashCode;
  }

  @Override
  public int compareTo(CommonException other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCode()).compareTo(other.isSetCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.code, other.code);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMessage()).compareTo(other.isSetMessage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMessage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.message, other.message);
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
    lastComparison = Boolean.valueOf(isSetCommandTag()).compareTo(other.isSetCommandTag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCommandTag()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.commandTag, other.commandTag);
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
    StringBuilder sb = new StringBuilder("CommonException(");
    boolean first = true;

    sb.append("code:");
    sb.append(this.code);
    first = false;
    if (isSetMessage()) {
      if (!first) sb.append(", ");
      sb.append("message:");
      if (this.message == null) {
        sb.append("null");
      } else {
        sb.append(this.message);
      }
      first = false;
    }
    if (isSetNodeTag()) {
      if (!first) sb.append(", ");
      sb.append("nodeTag:");
      sb.append(this.nodeTag);
      first = false;
    }
    if (isSetCommandTag()) {
      if (!first) sb.append(", ");
      sb.append("commandTag:");
      sb.append(this.commandTag);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'code' because it's a primitive and you chose the non-beans generator.
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

  private static class CommonExceptionStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CommonExceptionStandardScheme getScheme() {
      return new CommonExceptionStandardScheme();
    }
  }

  private static class CommonExceptionStandardScheme extends org.apache.thrift.scheme.StandardScheme<CommonException> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CommonException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.code = iprot.readI32();
              struct.setCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MESSAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.message = iprot.readString();
              struct.setMessageIsSet(true);
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
          case 4: // COMMAND_TAG
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.commandTag = iprot.readI32();
              struct.setCommandTagIsSet(true);
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
      if (!struct.isSetCode()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'code' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, CommonException struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(CODE_FIELD_DESC);
      oprot.writeI32(struct.code);
      oprot.writeFieldEnd();
      if (struct.message != null) {
        if (struct.isSetMessage()) {
          oprot.writeFieldBegin(MESSAGE_FIELD_DESC);
          oprot.writeString(struct.message);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetNodeTag()) {
        oprot.writeFieldBegin(NODE_TAG_FIELD_DESC);
        oprot.writeI32(struct.nodeTag);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCommandTag()) {
        oprot.writeFieldBegin(COMMAND_TAG_FIELD_DESC);
        oprot.writeI32(struct.commandTag);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CommonExceptionTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CommonExceptionTupleScheme getScheme() {
      return new CommonExceptionTupleScheme();
    }
  }

  private static class CommonExceptionTupleScheme extends org.apache.thrift.scheme.TupleScheme<CommonException> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CommonException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI32(struct.code);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetMessage()) {
        optionals.set(0);
      }
      if (struct.isSetNodeTag()) {
        optionals.set(1);
      }
      if (struct.isSetCommandTag()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetMessage()) {
        oprot.writeString(struct.message);
      }
      if (struct.isSetNodeTag()) {
        oprot.writeI32(struct.nodeTag);
      }
      if (struct.isSetCommandTag()) {
        oprot.writeI32(struct.commandTag);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CommonException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.code = iprot.readI32();
      struct.setCodeIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.message = iprot.readString();
        struct.setMessageIsSet(true);
      }
      if (incoming.get(1)) {
        struct.nodeTag = iprot.readI32();
        struct.setNodeTagIsSet(true);
      }
      if (incoming.get(2)) {
        struct.commandTag = iprot.readI32();
        struct.setCommandTagIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

