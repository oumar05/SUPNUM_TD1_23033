package com.example.middleservice.service;

import com.example.middleservice.client.SoapClient;
import com.example.middleservice.dto.ServerDTO;
import com.example.middleservice.soap.client.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServerService {

    @Autowired
    private SoapClient soapClient;

    
    private ServerDTO convertToDTO(Server server) {
        ServerDTO dto = new ServerDTO();
        dto.setId(server.getId());
        dto.setName(server.getName());
        dto.setIpAddress(server.getIpAddress());
        dto.setStatus(server.getStatus().value());
        return dto;
    }

    
    public ServerDTO createServer(String name, String ipAddress, String status) {
        Server server = soapClient.createServer(name, ipAddress, status);
        return convertToDTO(server);
    }

    
    public List<ServerDTO> getAllServers() {
        return soapClient.getAllServers().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    
    public ServerDTO startServer(Long id) {
        Server server = soapClient.startServer(id);
        return convertToDTO(server);
    }

    public ServerDTO stopServer(Long id) {
        Server server = soapClient.stopServer(id);
        return convertToDTO(server);
    }

    
    public void deleteServer(Long id) {
        soapClient.deleteServer(id);
    }
}
