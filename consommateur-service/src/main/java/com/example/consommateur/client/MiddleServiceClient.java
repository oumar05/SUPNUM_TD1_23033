package com.example.consumer.client;

import com.example.consumer.model.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MiddleServiceClient {

    @Value("${middle.service.url}")
    private String middleServiceUrl;

    private final RestTemplate restTemplate;

    public MiddleServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    
    public Server createServer(String name, String ipAddress, String status) {
        String url = middleServiceUrl + "?name=" + name + 
                     "&ipAddress=" + ipAddress + "&status=" + status;
        
        ResponseEntity<Server> response = restTemplate.postForEntity(url, null, Server.class);
        return response.getBody();
    }

   
    public List<Server> getAllServers() {
        String url = middleServiceUrl;
        
        ResponseEntity<List<Server>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Server>>() {}
        );
        
        return response.getBody();
    }

   
    public Server startServer(Long id) {
        String url = middleServiceUrl + "/" + id + "/start";
        
        ResponseEntity<Server> response = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            null,
            Server.class
        );
        
        return response.getBody();
    }

    
    public Server stopServer(Long id) {
        String url = middleServiceUrl + "/" + id + "/stop";
        
        ResponseEntity<Server> response = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            null,
            Server.class
        );
        
        return response.getBody();
    }

    
    public void deleteServer(Long id) {
        String url = middleServiceUrl + "/" + id;
        
        restTemplate.delete(url);
    }

    
    public String checkHealth() {
        try {
            String url = middleServiceUrl.replace("/api/servers", "/api/servers/health");
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            return "Middle-service est eteint";
        }
    }
}
