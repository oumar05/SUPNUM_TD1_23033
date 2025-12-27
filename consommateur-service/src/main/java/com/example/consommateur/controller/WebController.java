package com.example.consumer.controller;

import com.example.consumer.client.MiddleServiceClient;
import com.example.consumer.model.Server;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WebController {

    @Autowired
    private MiddleServiceClient middleServiceClient;

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("consomateur-service", "active");
        response.put("middle-service", middleServiceClient.checkHealth());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/servers")
    public ResponseEntity<List<Server>> getAllServers() {
        List<Server> servers = middleServiceClient.getAllServers();
        return ResponseEntity.ok(servers);
    }

    @PostMapping("/servers")
    public ResponseEntity<Server> createServer(
            @Parameter(description = "Nom du serveur") @RequestParam String name,
            @Parameter(description = "Adresse IP") @RequestParam String ipAddress,
            @Parameter(description = "Statut initial (TRUE/FALSE)") @RequestParam String status) {
        
        Server server = middleServiceClient.createServer(name, ipAddress, status);
        return ResponseEntity.status(HttpStatus.CREATED).body(server);
    }

    @PutMapping("/servers/{id}/start")
    public ResponseEntity<Server> startServer(
            @Parameter(description = "ID du serveur") @PathVariable Long id) {
        
        Server server = middleServiceClient.startServer(id);
        return ResponseEntity.ok(server);
    }

    @PutMapping("/servers/{id}/stop")
    public ResponseEntity<Server> stopServer(
            @Parameter(description = "ID du serveur") @PathVariable Long id) {
        
        Server server = middleServiceClient.stopServer(id);
        return ResponseEntity.ok(server);
    }

    @DeleteMapping("/servers/{id}")
    public ResponseEntity<Map<String, String>> deleteServer(
            @Parameter(description = "ID du serveur") @PathVariable Long id) {
        
        middleServiceClient.deleteServer(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Serveur supprimé avec succès");
        response.put("id", id.toString());
        
        return ResponseEntity.ok(response);
    }
}