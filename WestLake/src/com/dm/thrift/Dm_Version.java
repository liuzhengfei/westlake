/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.dm.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dm_Version implements org.apache.thrift.TBase<Dm_Version, Dm_Version._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Dm_Version");

  private static final org.apache.thrift.protocol.TField IS_SUCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("isSucess", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField MESSAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("message", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("version", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField DOWN_URL_FIELD_DESC = new org.apache.thrift.protocol.TField("downUrl", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new Dm_VersionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new Dm_VersionTupleSchemeFactory());
  }

  public boolean isSucess; // required
  public String message; // required
  public String version; // required
  public String downUrl; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    IS_SUCESS((short)1, "isSucess"),
    MESSAGE((short)2, "message"),
    VERSION((short)3, "version"),
    DOWN_URL((short)4, "downUrl");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // IS_SUCESS
          return IS_SUCESS;
        case 2: // MESSAGE
          return MESSAGE;
        case 3: // VERSION
          return VERSION;
        case 4: // DOWN_URL
          return DOWN_URL;
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
  private static final int __ISSUCESS_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.IS_SUCESS, new org.apache.thrift.meta_data.FieldMetaData("isSucess", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.MESSAGE, new org.apache.thrift.meta_data.FieldMetaData("message", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VERSION, new org.apache.thrift.meta_data.FieldMetaData("version", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DOWN_URL, new org.apache.thrift.meta_data.FieldMetaData("downUrl", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Dm_Version.class, metaDataMap);
  }

  public Dm_Version() {
  }

  public Dm_Version(
    boolean isSucess,
    String message,
    String version,
    String downUrl)
  {
    this();
    this.isSucess = isSucess;
    setIsSucessIsSet(true);
    this.message = message;
    this.version = version;
    this.downUrl = downUrl;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Dm_Version(Dm_Version other) {
    __isset_bitfield = other.__isset_bitfield;
    this.isSucess = other.isSucess;
    if (other.isSetMessage()) {
      this.message = other.message;
    }
    if (other.isSetVersion()) {
      this.version = other.version;
    }
    if (other.isSetDownUrl()) {
      this.downUrl = other.downUrl;
    }
  }

  public Dm_Version deepCopy() {
    return new Dm_Version(this);
  }

  @Override
  public void clear() {
    setIsSucessIsSet(false);
    this.isSucess = false;
    this.message = null;
    this.version = null;
    this.downUrl = null;
  }

  public boolean isIsSucess() {
    return this.isSucess;
  }

  public Dm_Version setIsSucess(boolean isSucess) {
    this.isSucess = isSucess;
    setIsSucessIsSet(true);
    return this;
  }

  public void unsetIsSucess() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ISSUCESS_ISSET_ID);
  }

  /** Returns true if field isSucess is set (has been assigned a value) and false otherwise */
  public boolean isSetIsSucess() {
    return EncodingUtils.testBit(__isset_bitfield, __ISSUCESS_ISSET_ID);
  }

  public void setIsSucessIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ISSUCESS_ISSET_ID, value);
  }

  public String getMessage() {
    return this.message;
  }

  public Dm_Version setMessage(String message) {
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

  public String getVersion() {
    return this.version;
  }

  public Dm_Version setVersion(String version) {
    this.version = version;
    return this;
  }

  public void unsetVersion() {
    this.version = null;
  }

  /** Returns true if field version is set (has been assigned a value) and false otherwise */
  public boolean isSetVersion() {
    return this.version != null;
  }

  public void setVersionIsSet(boolean value) {
    if (!value) {
      this.version = null;
    }
  }

  public String getDownUrl() {
    return this.downUrl;
  }

  public Dm_Version setDownUrl(String downUrl) {
    this.downUrl = downUrl;
    return this;
  }

  public void unsetDownUrl() {
    this.downUrl = null;
  }

  /** Returns true if field downUrl is set (has been assigned a value) and false otherwise */
  public boolean isSetDownUrl() {
    return this.downUrl != null;
  }

  public void setDownUrlIsSet(boolean value) {
    if (!value) {
      this.downUrl = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case IS_SUCESS:
      if (value == null) {
        unsetIsSucess();
      } else {
        setIsSucess((Boolean)value);
      }
      break;

    case MESSAGE:
      if (value == null) {
        unsetMessage();
      } else {
        setMessage((String)value);
      }
      break;

    case VERSION:
      if (value == null) {
        unsetVersion();
      } else {
        setVersion((String)value);
      }
      break;

    case DOWN_URL:
      if (value == null) {
        unsetDownUrl();
      } else {
        setDownUrl((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case IS_SUCESS:
      return Boolean.valueOf(isIsSucess());

    case MESSAGE:
      return getMessage();

    case VERSION:
      return getVersion();

    case DOWN_URL:
      return getDownUrl();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case IS_SUCESS:
      return isSetIsSucess();
    case MESSAGE:
      return isSetMessage();
    case VERSION:
      return isSetVersion();
    case DOWN_URL:
      return isSetDownUrl();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Dm_Version)
      return this.equals((Dm_Version)that);
    return false;
  }

  public boolean equals(Dm_Version that) {
    if (that == null)
      return false;

    boolean this_present_isSucess = true;
    boolean that_present_isSucess = true;
    if (this_present_isSucess || that_present_isSucess) {
      if (!(this_present_isSucess && that_present_isSucess))
        return false;
      if (this.isSucess != that.isSucess)
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

    boolean this_present_version = true && this.isSetVersion();
    boolean that_present_version = true && that.isSetVersion();
    if (this_present_version || that_present_version) {
      if (!(this_present_version && that_present_version))
        return false;
      if (!this.version.equals(that.version))
        return false;
    }

    boolean this_present_downUrl = true && this.isSetDownUrl();
    boolean that_present_downUrl = true && that.isSetDownUrl();
    if (this_present_downUrl || that_present_downUrl) {
      if (!(this_present_downUrl && that_present_downUrl))
        return false;
      if (!this.downUrl.equals(that.downUrl))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Dm_Version other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Dm_Version typedOther = (Dm_Version)other;

    lastComparison = Boolean.valueOf(isSetIsSucess()).compareTo(typedOther.isSetIsSucess());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsSucess()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isSucess, typedOther.isSucess);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMessage()).compareTo(typedOther.isSetMessage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMessage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.message, typedOther.message);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVersion()).compareTo(typedOther.isSetVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVersion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.version, typedOther.version);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDownUrl()).compareTo(typedOther.isSetDownUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDownUrl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.downUrl, typedOther.downUrl);
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
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Dm_Version(");
    boolean first = true;

    sb.append("isSucess:");
    sb.append(this.isSucess);
    first = false;
    if (!first) sb.append(", ");
    sb.append("message:");
    if (this.message == null) {
      sb.append("null");
    } else {
      sb.append(this.message);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("version:");
    if (this.version == null) {
      sb.append("null");
    } else {
      sb.append(this.version);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("downUrl:");
    if (this.downUrl == null) {
      sb.append("null");
    } else {
      sb.append(this.downUrl);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
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

  private static class Dm_VersionStandardSchemeFactory implements SchemeFactory {
    public Dm_VersionStandardScheme getScheme() {
      return new Dm_VersionStandardScheme();
    }
  }

  private static class Dm_VersionStandardScheme extends StandardScheme<Dm_Version> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Dm_Version struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // IS_SUCESS
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.isSucess = iprot.readBool();
              struct.setIsSucessIsSet(true);
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
          case 3: // VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.version = iprot.readString();
              struct.setVersionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DOWN_URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.downUrl = iprot.readString();
              struct.setDownUrlIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Dm_Version struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(IS_SUCESS_FIELD_DESC);
      oprot.writeBool(struct.isSucess);
      oprot.writeFieldEnd();
      if (struct.message != null) {
        oprot.writeFieldBegin(MESSAGE_FIELD_DESC);
        oprot.writeString(struct.message);
        oprot.writeFieldEnd();
      }
      if (struct.version != null) {
        oprot.writeFieldBegin(VERSION_FIELD_DESC);
        oprot.writeString(struct.version);
        oprot.writeFieldEnd();
      }
      if (struct.downUrl != null) {
        oprot.writeFieldBegin(DOWN_URL_FIELD_DESC);
        oprot.writeString(struct.downUrl);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class Dm_VersionTupleSchemeFactory implements SchemeFactory {
    public Dm_VersionTupleScheme getScheme() {
      return new Dm_VersionTupleScheme();
    }
  }

  private static class Dm_VersionTupleScheme extends TupleScheme<Dm_Version> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Dm_Version struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetIsSucess()) {
        optionals.set(0);
      }
      if (struct.isSetMessage()) {
        optionals.set(1);
      }
      if (struct.isSetVersion()) {
        optionals.set(2);
      }
      if (struct.isSetDownUrl()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetIsSucess()) {
        oprot.writeBool(struct.isSucess);
      }
      if (struct.isSetMessage()) {
        oprot.writeString(struct.message);
      }
      if (struct.isSetVersion()) {
        oprot.writeString(struct.version);
      }
      if (struct.isSetDownUrl()) {
        oprot.writeString(struct.downUrl);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Dm_Version struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.isSucess = iprot.readBool();
        struct.setIsSucessIsSet(true);
      }
      if (incoming.get(1)) {
        struct.message = iprot.readString();
        struct.setMessageIsSet(true);
      }
      if (incoming.get(2)) {
        struct.version = iprot.readString();
        struct.setVersionIsSet(true);
      }
      if (incoming.get(3)) {
        struct.downUrl = iprot.readString();
        struct.setDownUrlIsSet(true);
      }
    }
  }

}

