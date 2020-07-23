package com.steam.service.thrift;

import com.steam.thrift.DataHanding.DataHandingService;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class datahandingservice {
    @Value("${thrift.DataHanding.ip}")
    private String serverIp;
    @Value("${thrift.DataHanding.port}")
    private int ServerPort;
    public <T>T startService(){
        TSocket socket = new TSocket(serverIp, ServerPort, 3000);
        TTransport transport = new TFramedTransport(socket);
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        TProtocol protocol = new TBinaryProtocol(transport);
        TServiceClient DataHandingservice=new DataHandingService.Client(protocol);
        return (T) DataHandingservice;
    }
}
