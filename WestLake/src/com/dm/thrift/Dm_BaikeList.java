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

public class Dm_BaikeList implements org.apache.thrift.TBase<Dm_BaikeList, Dm_BaikeList._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Dm_BaikeList");

  private static final org.apache.thrift.protocol.TField IS_SUCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("isSucess", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField MESSAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("message", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField BAIKE_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("baikeList", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new Dm_BaikeListStandardSchemeFactory());
    schemes.put(TupleScheme.class, new Dm_BaikeListTupleSchemeFactory());
  }

  public boolean isSucess; // required
  public String message; // required
  public List<Dm_Baike> baikeList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    IS_SUCESS((short)1, "isSucess"),
    MESSAGE((short)2, "message"),
    BAIKE_LIST((short)3, "baikeList");

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
        case 3: // BAIKE_LIST
          return BAIKE_LIST;
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
    tmpMap.put(_Fields.BAIKE_LIST, new org.apache.thrift.meta_data.FieldMetaData("baikeList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Dm_Baike.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Dm_BaikeList.class, metaDataMap);
  }

  public Dm_BaikeList() {
  }

  public Dm_BaikeList(
    boolean isSucess,
    String message,
    List<Dm_Baike> baikeList)
  {
    this();
    this.isSucess = isSucess;
    setIsSucessIsSet(true);
    this.message = message;
    this.baikeList = baikeList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Dm_BaikeList(Dm_BaikeList other) {
    __isset_bitfield = other.__isset_bitfield;
    this.isSucess = other.isSucess;
    if (other.isSetMessage()) {
      this.message = other.message;
    }
    if (other.isSetBaikeList()) {
      List<Dm_Baike> __this__baikeList = new ArrayList<Dm_Baike>();
      for (Dm_Baike other_element : other.baikeList) {
        __this__baikeList.add(new Dm_Baike(other_element));
      }
      this.baikeList = __this__baikeList;
    }
  }

  public Dm_BaikeList deepCopy() {
    return new Dm_BaikeList(this);
  }

  @Override
  public void clear() {
    setIsSucessIsSet(false);
    this.isSucess = false;
    this.message = null;
    this.baikeList = null;
  }

  public boolean isIsSucess() {
    return this.isSucess;
  }

  public Dm_BaikeList setIsSucess(boolean isSucess) {
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

  public Dm_BaikeList setMessage(String message) {
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

  public int getBaikeListSize() {
    return (this.baikeList == null) ? 0 : this.baikeList.size();
  }

  public java.util.Iterator<Dm_Baike> getBaikeListIterator() {
    return (this.baikeList == null) ? null : this.baikeList.iterator();
  }

  public void addToBaikeList(Dm_Baike elem) {
    if (this.baikeList == null) {
      this.baikeList = new ArrayList<Dm_Baike>();
    }
    this.baikeList.add(elem);
  }

  public List<Dm_Baike> getBaikeList() {
    return this.baikeList;
  }

  public Dm_BaikeList setBaikeList(List<Dm_Baike> baikeList) {
    this.baikeList = baikeList;
    return this;
  }

  public void unsetBaikeList() {
    this.baikeList = null;
  }

  /** Returns true if field baikeList is set (has been assigned a value) and false otherwise */
  public boolean isSetBaikeList() {
    return this.baikeList != null;
  }

  public void setBaikeListIsSet(boolean value) {
    if (!value) {
      this.baikeList = null;
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

    case BAIKE_LIST:
      if (value == null) {
        unsetBaikeList();
      } else {
        setBaikeList((List<Dm_Baike>)value);
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

    case BAIKE_LIST:
      return getBaikeList();

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
    case BAIKE_LIST:
      return isSetBaikeList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Dm_BaikeList)
      return this.equals((Dm_BaikeList)that);
    return false;
  }

  public boolean equals(Dm_BaikeList that) {
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

    boolean this_present_baikeList = true && this.isSetBaikeList();
    boolean that_present_baikeList = true && that.isSetBaikeList();
    if (this_present_baikeList || that_present_baikeList) {
      if (!(this_present_baikeList && that_present_baikeList))
        return false;
      if (!this.baikeList.equals(that.baikeList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Dm_BaikeList other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Dm_BaikeList typedOther = (Dm_BaikeList)other;

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
    lastComparison = Boolean.valueOf(isSetBaikeList()).compareTo(typedOther.isSetBaikeList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBaikeList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.baikeList, typedOther.baikeList);
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
    StringBuilder sb = new StringBuilder("Dm_BaikeList(");
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
    sb.append("baikeList:");
    if (this.baikeList == null) {
      sb.append("null");
    } else {
      sb.append(this.baikeList);
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

  private static class Dm_BaikeListStandardSchemeFactory implements SchemeFactory {
    public Dm_BaikeListStandardScheme getScheme() {
      return new Dm_BaikeListStandardScheme();
    }
  }

  private static class Dm_BaikeListStandardScheme extends StandardScheme<Dm_BaikeList> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Dm_BaikeList struct) throws org.apache.thrift.TException {
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
          case 3: // BAIKE_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list72 = iprot.readListBegin();
                struct.baikeList = new ArrayList<Dm_Baike>(_list72.size);
                for (int _i73 = 0; _i73 < _list72.size; ++_i73)
                {
                  Dm_Baike _elem74; // required
                  _elem74 = new Dm_Baike();
                  _elem74.read(iprot);
                  struct.baikeList.add(_elem74);
                }
                iprot.readListEnd();
              }
              struct.setBaikeListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Dm_BaikeList struct) throws org.apache.thrift.TException {
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
      if (struct.baikeList != null) {
        oprot.writeFieldBegin(BAIKE_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.baikeList.size()));
          for (Dm_Baike _iter75 : struct.baikeList)
          {
            _iter75.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class Dm_BaikeListTupleSchemeFactory implements SchemeFactory {
    public Dm_BaikeListTupleScheme getScheme() {
      return new Dm_BaikeListTupleScheme();
    }
  }

  private static class Dm_BaikeListTupleScheme extends TupleScheme<Dm_BaikeList> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Dm_BaikeList struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetIsSucess()) {
        optionals.set(0);
      }
      if (struct.isSetMessage()) {
        optionals.set(1);
      }
      if (struct.isSetBaikeList()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetIsSucess()) {
        oprot.writeBool(struct.isSucess);
      }
      if (struct.isSetMessage()) {
        oprot.writeString(struct.message);
      }
      if (struct.isSetBaikeList()) {
        {
          oprot.writeI32(struct.baikeList.size());
          for (Dm_Baike _iter76 : struct.baikeList)
          {
            _iter76.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Dm_BaikeList struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.isSucess = iprot.readBool();
        struct.setIsSucessIsSet(true);
      }
      if (incoming.get(1)) {
        struct.message = iprot.readString();
        struct.setMessageIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list77 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.baikeList = new ArrayList<Dm_Baike>(_list77.size);
          for (int _i78 = 0; _i78 < _list77.size; ++_i78)
          {
            Dm_Baike _elem79; // required
            _elem79 = new Dm_Baike();
            _elem79.read(iprot);
            struct.baikeList.add(_elem79);
          }
        }
        struct.setBaikeListIsSet(true);
      }
    }
  }

}

