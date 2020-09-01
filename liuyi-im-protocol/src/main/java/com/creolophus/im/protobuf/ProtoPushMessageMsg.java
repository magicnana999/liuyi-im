// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PushMessageMsg.proto

package com.creolophus.im.protobuf;

public final class ProtoPushMessageMsg {
  private ProtoPushMessageMsg() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PushMessageMsgOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.creolophus.im.protobuf.PushMessageMsg)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 messageId = 1;</code>
     */
    long getMessageId();

    /**
     * <code>int32 messageType = 2;</code>
     */
    int getMessageType();

    /**
     * <code>int64 groupId = 3;</code>
     */
    long getGroupId();

    /**
     * <code>string messageBody = 4;</code>
     */
    java.lang.String getMessageBody();
    /**
     * <code>string messageBody = 4;</code>
     */
    com.google.protobuf.ByteString
        getMessageBodyBytes();

    /**
     * <code>int64 receiverId = 5;</code>
     */
    long getReceiverId();

    /**
     * <code>int64 senderId = 6;</code>
     */
    long getSenderId();
  }
  /**
   * <pre>
   **
   *private Long messageId;
   *private Integer messageType;
   *private Long groupId;
   *private String messageBody;
   *private Long receiverId;
   *private Long senderId;
   * </pre>
   *
   * Protobuf type {@code com.creolophus.im.protobuf.PushMessageMsg}
   */
  public  static final class PushMessageMsg extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.creolophus.im.protobuf.PushMessageMsg)
      PushMessageMsgOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use PushMessageMsg.newBuilder() to construct.
    private PushMessageMsg(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private PushMessageMsg() {
      messageBody_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private PushMessageMsg(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {

              messageId_ = input.readInt64();
              break;
            }
            case 16: {

              messageType_ = input.readInt32();
              break;
            }
            case 24: {

              groupId_ = input.readInt64();
              break;
            }
            case 34: {
              java.lang.String s = input.readStringRequireUtf8();

              messageBody_ = s;
              break;
            }
            case 40: {

              receiverId_ = input.readInt64();
              break;
            }
            case 48: {

              senderId_ = input.readInt64();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.creolophus.im.protobuf.ProtoPushMessageMsg.internal_static_com_creolophus_im_protobuf_PushMessageMsg_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.creolophus.im.protobuf.ProtoPushMessageMsg.internal_static_com_creolophus_im_protobuf_PushMessageMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg.class, com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg.Builder.class);
    }

    public static final int MESSAGEID_FIELD_NUMBER = 1;
    private long messageId_;
    /**
     * <code>int64 messageId = 1;</code>
     */
    public long getMessageId() {
      return messageId_;
    }

    public static final int MESSAGETYPE_FIELD_NUMBER = 2;
    private int messageType_;
    /**
     * <code>int32 messageType = 2;</code>
     */
    public int getMessageType() {
      return messageType_;
    }

    public static final int GROUPID_FIELD_NUMBER = 3;
    private long groupId_;
    /**
     * <code>int64 groupId = 3;</code>
     */
    public long getGroupId() {
      return groupId_;
    }

    public static final int MESSAGEBODY_FIELD_NUMBER = 4;
    private volatile java.lang.Object messageBody_;
    /**
     * <code>string messageBody = 4;</code>
     */
    public java.lang.String getMessageBody() {
      java.lang.Object ref = messageBody_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        messageBody_ = s;
        return s;
      }
    }
    /**
     * <code>string messageBody = 4;</code>
     */
    public com.google.protobuf.ByteString
        getMessageBodyBytes() {
      java.lang.Object ref = messageBody_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        messageBody_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int RECEIVERID_FIELD_NUMBER = 5;
    private long receiverId_;
    /**
     * <code>int64 receiverId = 5;</code>
     */
    public long getReceiverId() {
      return receiverId_;
    }

    public static final int SENDERID_FIELD_NUMBER = 6;
    private long senderId_;
    /**
     * <code>int64 senderId = 6;</code>
     */
    public long getSenderId() {
      return senderId_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (messageId_ != 0L) {
        output.writeInt64(1, messageId_);
      }
      if (messageType_ != 0) {
        output.writeInt32(2, messageType_);
      }
      if (groupId_ != 0L) {
        output.writeInt64(3, groupId_);
      }
      if (!getMessageBodyBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, messageBody_);
      }
      if (receiverId_ != 0L) {
        output.writeInt64(5, receiverId_);
      }
      if (senderId_ != 0L) {
        output.writeInt64(6, senderId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (messageId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, messageId_);
      }
      if (messageType_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, messageType_);
      }
      if (groupId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(3, groupId_);
      }
      if (!getMessageBodyBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, messageBody_);
      }
      if (receiverId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(5, receiverId_);
      }
      if (senderId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(6, senderId_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg)) {
        return super.equals(obj);
      }
      com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg other = (com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg) obj;

      if (getMessageId()
          != other.getMessageId()) return false;
      if (getMessageType()
          != other.getMessageType()) return false;
      if (getGroupId()
          != other.getGroupId()) return false;
      if (!getMessageBody()
          .equals(other.getMessageBody())) return false;
      if (getReceiverId()
          != other.getReceiverId()) return false;
      if (getSenderId()
          != other.getSenderId()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + MESSAGEID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getMessageId());
      hash = (37 * hash) + MESSAGETYPE_FIELD_NUMBER;
      hash = (53 * hash) + getMessageType();
      hash = (37 * hash) + GROUPID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getGroupId());
      hash = (37 * hash) + MESSAGEBODY_FIELD_NUMBER;
      hash = (53 * hash) + getMessageBody().hashCode();
      hash = (37 * hash) + RECEIVERID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getReceiverId());
      hash = (37 * hash) + SENDERID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getSenderId());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     **
     *private Long messageId;
     *private Integer messageType;
     *private Long groupId;
     *private String messageBody;
     *private Long receiverId;
     *private Long senderId;
     * </pre>
     *
     * Protobuf type {@code com.creolophus.im.protobuf.PushMessageMsg}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.creolophus.im.protobuf.PushMessageMsg)
        com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsgOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.creolophus.im.protobuf.ProtoPushMessageMsg.internal_static_com_creolophus_im_protobuf_PushMessageMsg_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.creolophus.im.protobuf.ProtoPushMessageMsg.internal_static_com_creolophus_im_protobuf_PushMessageMsg_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg.class, com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg.Builder.class);
      }

      // Construct using com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        messageId_ = 0L;

        messageType_ = 0;

        groupId_ = 0L;

        messageBody_ = "";

        receiverId_ = 0L;

        senderId_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.creolophus.im.protobuf.ProtoPushMessageMsg.internal_static_com_creolophus_im_protobuf_PushMessageMsg_descriptor;
      }

      @java.lang.Override
      public com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg getDefaultInstanceForType() {
        return com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg.getDefaultInstance();
      }

      @java.lang.Override
      public com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg build() {
        com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg buildPartial() {
        com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg result = new com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg(this);
        result.messageId_ = messageId_;
        result.messageType_ = messageType_;
        result.groupId_ = groupId_;
        result.messageBody_ = messageBody_;
        result.receiverId_ = receiverId_;
        result.senderId_ = senderId_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg) {
          return mergeFrom((com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg other) {
        if (other == com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg.getDefaultInstance()) return this;
        if (other.getMessageId() != 0L) {
          setMessageId(other.getMessageId());
        }
        if (other.getMessageType() != 0) {
          setMessageType(other.getMessageType());
        }
        if (other.getGroupId() != 0L) {
          setGroupId(other.getGroupId());
        }
        if (!other.getMessageBody().isEmpty()) {
          messageBody_ = other.messageBody_;
          onChanged();
        }
        if (other.getReceiverId() != 0L) {
          setReceiverId(other.getReceiverId());
        }
        if (other.getSenderId() != 0L) {
          setSenderId(other.getSenderId());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long messageId_ ;
      /**
       * <code>int64 messageId = 1;</code>
       */
      public long getMessageId() {
        return messageId_;
      }
      /**
       * <code>int64 messageId = 1;</code>
       */
      public Builder setMessageId(long value) {
        
        messageId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 messageId = 1;</code>
       */
      public Builder clearMessageId() {
        
        messageId_ = 0L;
        onChanged();
        return this;
      }

      private int messageType_ ;
      /**
       * <code>int32 messageType = 2;</code>
       */
      public int getMessageType() {
        return messageType_;
      }
      /**
       * <code>int32 messageType = 2;</code>
       */
      public Builder setMessageType(int value) {
        
        messageType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 messageType = 2;</code>
       */
      public Builder clearMessageType() {
        
        messageType_ = 0;
        onChanged();
        return this;
      }

      private long groupId_ ;
      /**
       * <code>int64 groupId = 3;</code>
       */
      public long getGroupId() {
        return groupId_;
      }
      /**
       * <code>int64 groupId = 3;</code>
       */
      public Builder setGroupId(long value) {
        
        groupId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 groupId = 3;</code>
       */
      public Builder clearGroupId() {
        
        groupId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object messageBody_ = "";
      /**
       * <code>string messageBody = 4;</code>
       */
      public java.lang.String getMessageBody() {
        java.lang.Object ref = messageBody_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          messageBody_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string messageBody = 4;</code>
       */
      public com.google.protobuf.ByteString
          getMessageBodyBytes() {
        java.lang.Object ref = messageBody_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          messageBody_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string messageBody = 4;</code>
       */
      public Builder setMessageBody(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        messageBody_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string messageBody = 4;</code>
       */
      public Builder clearMessageBody() {
        
        messageBody_ = getDefaultInstance().getMessageBody();
        onChanged();
        return this;
      }
      /**
       * <code>string messageBody = 4;</code>
       */
      public Builder setMessageBodyBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        messageBody_ = value;
        onChanged();
        return this;
      }

      private long receiverId_ ;
      /**
       * <code>int64 receiverId = 5;</code>
       */
      public long getReceiverId() {
        return receiverId_;
      }
      /**
       * <code>int64 receiverId = 5;</code>
       */
      public Builder setReceiverId(long value) {
        
        receiverId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 receiverId = 5;</code>
       */
      public Builder clearReceiverId() {
        
        receiverId_ = 0L;
        onChanged();
        return this;
      }

      private long senderId_ ;
      /**
       * <code>int64 senderId = 6;</code>
       */
      public long getSenderId() {
        return senderId_;
      }
      /**
       * <code>int64 senderId = 6;</code>
       */
      public Builder setSenderId(long value) {
        
        senderId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 senderId = 6;</code>
       */
      public Builder clearSenderId() {
        
        senderId_ = 0L;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:com.creolophus.im.protobuf.PushMessageMsg)
    }

    // @@protoc_insertion_point(class_scope:com.creolophus.im.protobuf.PushMessageMsg)
    private static final com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg();
    }

    public static com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PushMessageMsg>
        PARSER = new com.google.protobuf.AbstractParser<PushMessageMsg>() {
      @java.lang.Override
      public PushMessageMsg parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PushMessageMsg(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<PushMessageMsg> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<PushMessageMsg> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.creolophus.im.protobuf.ProtoPushMessageMsg.PushMessageMsg getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_creolophus_im_protobuf_PushMessageMsg_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_creolophus_im_protobuf_PushMessageMsg_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024PushMessageMsg.proto\022\032com.creolophus.i" +
      "m.protobuf\"\204\001\n\016PushMessageMsg\022\021\n\tmessage" +
      "Id\030\001 \001(\003\022\023\n\013messageType\030\002 \001(\005\022\017\n\007groupId" +
      "\030\003 \001(\003\022\023\n\013messageBody\030\004 \001(\t\022\022\n\nreceiverI" +
      "d\030\005 \001(\003\022\020\n\010senderId\030\006 \001(\003B1\n\032com.creolop" +
      "hus.im.protobufB\023ProtoPushMessageMsgb\006pr" +
      "oto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_creolophus_im_protobuf_PushMessageMsg_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_creolophus_im_protobuf_PushMessageMsg_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_creolophus_im_protobuf_PushMessageMsg_descriptor,
        new java.lang.String[] { "MessageId", "MessageType", "GroupId", "MessageBody", "ReceiverId", "SenderId", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
