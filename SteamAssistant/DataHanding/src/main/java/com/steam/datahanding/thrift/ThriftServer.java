package com.steam.datahanding.thrift;

import com.steam.thrift.DataHanding.DataHandingService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

// Java 实现 ThriftServer，并对外提供 UserServiceImpl 的功能，并伴生(Springboot项目)启动
@Configuration  // 可以从 application.properties 配置文件中读取数据(读取 key、得到 value)
public class ThriftServer {

    // 从 application.properties 读入服务端口号
//    @Value("${service.port}")
    @Value("9083")
    private int servicePort;

    // 在当前的 Thrift Server 中要借助 UserServieImpl 对外提供功能
    @Resource   // 注入 UserService.Iface 类型的当前项目中的 UserServiceImpl
    private DataHandingService.Iface datahandingservice;

    // 进行单元测试时，暂时禁用
    @PostConstruct      // 当前类实例化后，立即自动执行，实现所谓的伴生启动
    public void startThriftServer(){
        // 1. 创建 ServerSocket
        TNonblockingServerSocket serverSocket = null;
        try {
            serverSocket = new TNonblockingServerSocket(servicePort);
        } catch (TTransportException e) {
            e.printStackTrace();
            return ;
        }

        // 2. 创建传输方式
        TFramedTransport.Factory transportFactory = new TFramedTransport.Factory();

        // 3. 创建协议
        TBinaryProtocol.Factory protocolFactory = new TBinaryProtocol.Factory();

        // 4. 创建处理器
        TProcessor processor = new DataHandingService.Processor<>(datahandingservice);

        // 5. 创建服务器
        // 5-1 创建 ThriftServer 的参数对象，放入上述所需参数
        TNonblockingServer.Args args = new TNonblockingServer.Args(serverSocket);
        args.transportFactory(transportFactory);
        args.protocolFactory(protocolFactory);
        args.processor(processor);

        TServer server = new TNonblockingServer(args);

        // 6. 启动服务器
        System.out.println("DataHandingService Thrift Server starting...");
        server.serve();
        System.out.println("DataHandingService Thrift Server stoped.");
    }

}
