package cn.aaron911.netty.im.protocol.command;

/**
 * 指令
 */
public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte LOGOUT_REQUEST = 3;

    Byte LOGOUT_RESPONSE = 4;

    Byte MESSAGE_REQUEST = 5;

    Byte MESSAGE_RESPONSE = 6;

    Byte CREATE_GROUP_REQUEST = 7;

    Byte CREATE_GROUP_RESPONSE = 8;

    Byte LIST_GROUP_MEMBERS_REQUEST = 9;

    Byte LIST_GROUP_MEMBERS_RESPONSE = 10;

    Byte JOIN_GROUP_REQUEST = 11;

    Byte JOIN_GROUP_RESPONSE = 12;

    Byte QUIT_GROUP_REQUEST = 13;

    Byte QUIT_GROUP_RESPONSE = 14;

    Byte GROUP_MESSAGE_REQUEST = 15;

    Byte GROUP_MESSAGE_RESPONSE = 16;

    Byte HEARTBEAT_REQUEST = 17;

    Byte HEARTBEAT_RESPONSE = 18;

    Byte FILE_TRANSFER_DOWNLOAD_REQUEST = 81;

    Byte FILE_TRANSFER_DOWNLOAD_RESPONSE = 82;

    Byte FILE_TRANSFER_DOWNLOAD_INSTRUCT_REQUEST = 83;

    Byte FILE_TRANSFER_DOWNLOAD_INSTRUCT_RESPONSE = 84;

    Byte FILE_TRANSFER_DOWNLOAD_DATA_REQUEST = 85;

    Byte FILE_TRANSFER_DOWNLOAD_DATA_RESPONSE = 86;

    Byte FILE_TRANSFER_DOWNLOAD_NOTICE_RESPONSE = 89;

    Byte FILE_TRANSFER_UPLOAD_REQUEST = 91;

    Byte FILE_TRANSFER_UPLOAD_RESPONSE = 92;

    Byte FILE_TRANSFER_UPLOAD_INSTRUCT_REQUEST = 93;

    Byte FILE_TRANSFER_UPLOAD_INSTRUCT_RESPONSE = 94;

    Byte FILE_TRANSFER_UPLOAD_DATA_REQUEST = 95;

    Byte FILE_TRANSFER_UPLOAD_DATA_RESPONSE = 96;
}
