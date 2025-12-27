package com.example.middleservice.client;

import com.example.middleservice.soap.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Component
public class SoapClient {

    @Value("${soap.service.url}")
    private String soapUrl;

    private WebServiceTemplate webServiceTemplate;

    @PostConstruct
    public void init() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.example.middleservice.soap.client");
        
        webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);
        webServiceTemplate.setDefaultUri(soapUrl);
    }

    public Server createServer(String name, String ipAddress, String status) {
        CreateServerRequest request = new CreateServerRequest();
        Server server = new Server();
        server.setName(name);
        server.setIpAddress(ipAddress);
        server.setStatus(ServerStatus.fromValue(status));
        request.setServer(server);

        CreateServerResponse response = (CreateServerResponse) webServiceTemplate
                .marshalSendAndReceive(request);
        
        return response.getServer();
    }

    public List<Server> getAllServers() {
        GetAllServersRequest request = new GetAllServersRequest();
        
        GetAllServersResponse response = (GetAllServersResponse) webServiceTemplate
                .marshalSendAndReceive(request);
        
        return response.getServers();
    }

    public Server startServer(Long id) {
        StartServerRequest request = new StartServerRequest();
        request.setId(id);
        
        StartServerResponse response = (StartServerResponse) webServiceTemplate
                .marshalSendAndReceive(request);
        
        return response.getServer();
    }

    public Server stopServer(Long id) {
        StopServerRequest request = new StopServerRequest();
        request.setId(id);
        
        StopServerResponse response = (StopServerResponse) webServiceTemplate
                .marshalSendAndReceive(request);
        
        return response.getServer();
    }

    public void deleteServer(Long id) {
        DeleteServerRequest request = new DeleteServerRequest();
        request.setId(id);
        
        webServiceTemplate.marshalSendAndReceive(request);
    }
}
