# IM
## 1.断点文件上传
1. 发起客户端 发送 FileTransferUploadRequestPacket 到服务端
2. 服务端 FileTransferUploadRequestHandler 处理上传请求
3. 服务端 给 发起客户端 发送 FileTransferUploadResponsePacket 响应
4. 发起客户端 FileTransferUploadResponseHandler 处理响应，从本地文件指定位置读取本地文件
5. 发起客户端 发送 FileTransferUploadDataRequestPacket 到服务端
6. 服务端 FileTransferUploadDataRequestHandler 处理上传文件数据请求，保存到 服务端
7. 服务端 保存完文件，查找 要发送到客户端
8. 要发送到客户端 不在线，暂时结束，以后再增加需求
9. 要发送到客户端 在线，服务端 发送 FileTransferResponsePacket(进入断点文件下载流程)

     

## 2.断点文件下载