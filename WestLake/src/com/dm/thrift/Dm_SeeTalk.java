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

public class Dm_SeeTalk implements org.apache.thrift.TBase<Dm_SeeTalk, Dm_SeeTalk._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Dm_SeeTalk");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("content", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CREATE_USER_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("createUserName", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField CREATE_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("createDate", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField FILE_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("fileList", org.apache.thrift.protocol.TType.LIST, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new Dm_SeeTalkStandardSchemeFactory());
    schemes.put(TupleScheme.class, new Dm_SeeTalkTupleSchemeFactory());
  }

  public String id; // required
  public String content; // required
  public String createUserName; // required
  public String createDate; // required
  public List<String> fileList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    CONTENT((short)2, "content"),
    CREATE_USER_NAME((short)3, "createUserName"),
    CREATE_DATE((short)4, "createDate"),
    FILE_LIST((short)5, "fileList");

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
        case 1: // ID
          return ID;
        case 2: // CONTENT
          return CONTENT;
        case 3: // CREATE_USER_NAME
          return CREATE_USER_NAME;
        case 4: // CREATE_DATE
          return CREATE_DATE;
        case 5: // FILE_LIST
          return FILE_LIST;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CONTENT, new org.apache.thrift.meta_data.FieldMetaData("content", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_USER_NAME, new org.apache.thrift.meta_data.FieldMetaData("createUserName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_DATE, new org.apache.thrift.meta_data.FieldMetaData("createDate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FILE_LIST, new org.apache.thrift.meta_data.FieldMetaData("fileList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Dm_SeeTalk.class, metaDataMap);
  }

  public Dm_SeeTalk() {
  }

  public Dm_SeeTalk(
    String id,
    String content,
    String createUserName,
    String createDate,
    List<String> fileList)
  {
    this();
    this.id = id;
    this.content = content;
    this.createUserName = createUserName;
    this.createDate = createDate;
    this.fileList = fileList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Dm_SeeTalk(Dm_SeeTalk other) {
    if (other.isSetId()) {
      this.id = other.id;
    }
    if (other.isSetContent()) {
      this.content = other.content;
    }
    if (other.isSetCreateUserName()) {
      this.createUserName = other.createUserName;
    }
    if (other.isSetCreateDate()) {
      this.createDate = other.createDate;
    }
    if (other.isSetFileList()) {
      List<String> __this__fileList = new ArrayList<String>();
      for (String other_element : other.fileList) {
        __this__fileList.add(other_element);
      }
      this.fileList = __this__fileList;
    }
  }

  public Dm_SeeTalk deepCopy() {
    return new Dm_SeeTalk(this);
  }

  @Override
  public void clear() {
    this.id = null;
    this.content = null;
    this.createUserName = null;
    this.createDate = null;
    this.fileList = null;
  }

  public String getId() {
    return this.id;
  }

  public Dm_SeeTalk setId(String id) {
    this.id = id;
    return this;
  }

  public void unsetId() {
    this.id = null;
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return this.id != null;
  }

  public void setIdIsSet(boolean value) {
    if (!value) {
      this.id = null;
    }
  }

  public String getContent() {
    return this.content;
  }

  public Dm_SeeTalk setContent(String content) {
    this.content = content;
    return this;
  }

  public void unsetContent() {
    this.content = null;
  }

  /** Returns true if field content is set (has been assigned a value) and false otherwise */
  public boolean isSetContent() {
    return this.content != null;
  }

  public void setContentIsSet(boolean value) {
    if (!value) {
      this.content = null;
    }
  }

  public String getCreateUserName() {
    return this.createUserName;
  }

  public Dm_SeeTalk setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
    return this;
  }

  public void unsetCreateUserName() {
    this.createUserName = null;
  }

  /** Returns true if field createUserName is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateUserName() {
    return this.createUserName != null;
  }

  public void setCreateUserNameIsSet(boolean value) {
    if (!value) {
      this.createUserName = null;
    }
  }

  public String getCreateDate() {
    return this.createDate;
  }

  public Dm_SeeTalk setCreateDate(String createDate) {
    this.createDate = createDate;
    return this;
  }

  public void unsetCreateDate() {
    this.createDate = null;
  }

  /** Returns true if field createDate is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateDate() {
    return this.createDate != null;
  }

  public void setCreateDateIsSet(boolean value) {
    if (!value) {
      this.createDate = null;
    }
  }

  public int getFileListSize() {
    return (this.fileList == null) ? 0 : this.fileList.size();
  }

  public java.util.Iterator<String> getFileListIterator() {
    return (this.fileList == null) ? null : this.fileList.iterator();
  }

  public void addToFileList(String elem) {
    if (this.fileList == null) {
      this.fileList = new ArrayList<String>();
    }
    this.fileList.add(elem);
  }

  public List<String> getFileList() {
    return this.fileList;
  }

  public Dm_SeeTalk setFileList(List<String> fileList) {
    this.fileList = fileList;
    return this;
  }

  public void unsetFileList() {
    this.fileList = null;
  }

  /** Returns true if field fileList is set (has been assigned a value) and false otherwise */
  public boolean isSetFileList() {
    return this.fileList != null;
  }

  public void setFileListIsSet(boolean value) {
    if (!value) {
      this.fileList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((String)value);
      }
      break;

    case CONTENT:
      if (value == null) {
        unsetContent();
      } else {
        setContent((String)value);
      }
      break;

    case CREATE_USER_NAME:
      if (value == null) {
        unsetCreateUserName();
      } else {
        setCreateUserName((String)value);
      }
      break;

    case CREATE_DATE:
      if (value == null) {
        unsetCreateDate();
      } else {
        setCreateDate((String)value);
      }
      break;

    case FILE_LIST:
      if (value == null) {
        unsetFileList();
      } else {
        setFileList((List<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case CONTENT:
      return getContent();

    case CREATE_USER_NAME:
      return getCreateUserName();

    case CREATE_DATE:
      return getCreateDate();

    case FILE_LIST:
      return getFileList();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case CONTENT:
      return isSetContent();
    case CREATE_USER_NAME:
      return isSetCreateUserName();
    case CREATE_DATE:
      return isSetCreateDate();
    case FILE_LIST:
      return isSetFileList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Dm_SeeTalk)
      return this.equals((Dm_SeeTalk)that);
    return false;
  }

  public boolean equals(Dm_SeeTalk that) {
    if (that == null)
      return false;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (!this.id.equals(that.id))
        return false;
    }

    boolean this_present_content = true && this.isSetContent();
    boolean that_present_content = true && that.isSetContent();
    if (this_present_content || that_present_content) {
      if (!(this_present_content && that_present_content))
        return false;
      if (!this.content.equals(that.content))
        return false;
    }

    boolean this_present_createUserName = true && this.isSetCreateUserName();
    boolean that_present_createUserName = true && that.isSetCreateUserName();
    if (this_present_createUserName || that_present_createUserName) {
      if (!(this_present_createUserName && that_present_createUserName))
        return false;
      if (!this.createUserName.equals(that.createUserName))
        return false;
    }

    boolean this_present_createDate = true && this.isSetCreateDate();
    boolean that_present_createDate = true && that.isSetCreateDate();
    if (this_present_createDate || that_present_createDate) {
      if (!(this_present_createDate && that_present_createDate))
        return false;
      if (!this.createDate.equals(that.createDate))
        return false;
    }

    boolean this_present_fileList = true && this.isSetFileList();
    boolean that_present_fileList = true && that.isSetFileList();
    if (this_present_fileList || that_present_fileList) {
      if (!(this_present_fileList && that_present_fileList))
        return false;
      if (!this.fileList.equals(that.fileList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Dm_SeeTalk other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Dm_SeeTalk typedOther = (Dm_SeeTalk)other;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(typedOther.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, typedOther.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetContent()).compareTo(typedOther.isSetContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.content, typedOther.content);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreateUserName()).compareTo(typedOther.isSetCreateUserName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateUserName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createUserName, typedOther.createUserName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreateDate()).compareTo(typedOther.isSetCreateDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createDate, typedOther.createDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFileList()).compareTo(typedOther.isSetFileList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileList, typedOther.fileList);
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
    StringBuilder sb = new StringBuilder("Dm_SeeTalk(");
    boolean first = true;

    sb.append("id:");
    if (this.id == null) {
      sb.append("null");
    } else {
      sb.append(this.id);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("content:");
    if (this.content == null) {
      sb.append("null");
    } else {
      sb.append(this.content);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("createUserName:");
    if (this.createUserName == null) {
      sb.append("null");
    } else {
      sb.append(this.createUserName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("createDate:");
    if (this.createDate == null) {
      sb.append("null");
    } else {
      sb.append(this.createDate);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("fileList:");
    if (this.fileList == null) {
      sb.append("null");
    } else {
      sb.append(this.fileList);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class Dm_SeeTalkStandardSchemeFactory implements SchemeFactory {
    public Dm_SeeTalkStandardScheme getScheme() {
      return new Dm_SeeTalkStandardScheme();
    }
  }

  private static class Dm_SeeTalkStandardScheme extends StandardScheme<Dm_SeeTalk> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Dm_SeeTalk struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.id = iprot.readString();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.content = iprot.readString();
              struct.setContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CREATE_USER_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.createUserName = iprot.readString();
              struct.setCreateUserNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CREATE_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.createDate = iprot.readString();
              struct.setCreateDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // FILE_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list112 = iprot.readListBegin();
                struct.fileList = new ArrayList<String>(_list112.size);
                for (int _i113 = 0; _i113 < _list112.size; ++_i113)
                {
                  String _elem114; // required
                  _elem114 = iprot.readString();
                  struct.fileList.add(_elem114);
                }
                iprot.readListEnd();
              }
              struct.setFileListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Dm_SeeTalk struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.id != null) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeString(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.content != null) {
        oprot.writeFieldBegin(CONTENT_FIELD_DESC);
        oprot.writeString(struct.content);
        oprot.writeFieldEnd();
      }
      if (struct.createUserName != null) {
        oprot.writeFieldBegin(CREATE_USER_NAME_FIELD_DESC);
        oprot.writeString(struct.createUserName);
        oprot.writeFieldEnd();
      }
      if (struct.createDate != null) {
        oprot.writeFieldBegin(CREATE_DATE_FIELD_DESC);
        oprot.writeString(struct.createDate);
        oprot.writeFieldEnd();
      }
      if (struct.fileList != null) {
        oprot.writeFieldBegin(FILE_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.fileList.size()));
          for (String _iter115 : struct.fileList)
          {
            oprot.writeString(_iter115);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class Dm_SeeTalkTupleSchemeFactory implements SchemeFactory {
    public Dm_SeeTalkTupleScheme getScheme() {
      return new Dm_SeeTalkTupleScheme();
    }
  }

  private static class Dm_SeeTalkTupleScheme extends TupleScheme<Dm_SeeTalk> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Dm_SeeTalk struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetContent()) {
        optionals.set(1);
      }
      if (struct.isSetCreateUserName()) {
        optionals.set(2);
      }
      if (struct.isSetCreateDate()) {
        optionals.set(3);
      }
      if (struct.isSetFileList()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetId()) {
        oprot.writeString(struct.id);
      }
      if (struct.isSetContent()) {
        oprot.writeString(struct.content);
      }
      if (struct.isSetCreateUserName()) {
        oprot.writeString(struct.createUserName);
      }
      if (struct.isSetCreateDate()) {
        oprot.writeString(struct.createDate);
      }
      if (struct.isSetFileList()) {
        {
          oprot.writeI32(struct.fileList.size());
          for (String _iter116 : struct.fileList)
          {
            oprot.writeString(_iter116);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Dm_SeeTalk struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.id = iprot.readString();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.content = iprot.readString();
        struct.setContentIsSet(true);
      }
      if (incoming.get(2)) {
        struct.createUserName = iprot.readString();
        struct.setCreateUserNameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.createDate = iprot.readString();
        struct.setCreateDateIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TList _list117 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.fileList = new ArrayList<String>(_list117.size);
          for (int _i118 = 0; _i118 < _list117.size; ++_i118)
          {
            String _elem119; // required
            _elem119 = iprot.readString();
            struct.fileList.add(_elem119);
          }
        }
        struct.setFileListIsSet(true);
      }
    }
  }

}

