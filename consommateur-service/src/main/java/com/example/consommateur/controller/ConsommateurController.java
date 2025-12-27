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
public class ConsommateurController {

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
        return ResponseEntity.ok(middleServiceClient.getAllServers());
    }

    @PostMapping("/servers")
    public ResponseEntity<Server> createServer(
            @RequestParam String name,
            @RequestParam String ipAddress,
            @RequestParam String status) {
        Server server = middleServiceClient.createServer(name, ipAddress, status);
        return ResponseEntity.status(HttpStatus.CREATED).body(server);
    }

    @PutMapping("/servers/{id}/start")
    public ResponseEntity<Server> startServer(@PathVariable Long id) {
        return ResponseEntity.ok(middleServiceClient.startServer(id));
    }

    @PutMapping("/servers/{id}/stop")
    public ResponseEntity<Server> stopServer(@PathVariable Long id) {
        return ResponseEntity.ok(middleServiceClient.stopServer(id));
    }

    @DeleteMapping("/servers/{id}")
    public ResponseEntity<Void> deleteServer(@PathVariable Long id) {
        middleServiceClient.deleteServer(id);
        return ResponseEntity.noContent().build();
    }
}
