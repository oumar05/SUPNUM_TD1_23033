package com.example.middleservice.controller;

import com.example.middleservice.dto.ServerDTO;
import com.example.middleservice.service.ServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
@Tag(name = "Server Management")
@CrossOrigin(origins = "*")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @PostMapping
    public ResponseEntity<ServerDTO> createServer(
            @RequestParam String name,
            @RequestParam String ipAddress,
            @RequestParam(defaultValue = "FALSE") String status) {
        try {
            ServerDTO server = serverService.createServer(name, ipAddress, status);
            return ResponseEntity.status(HttpStatus.CREATED).body(server);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ServerDTO>> getAllServers() {
        try {
            List<ServerDTO> servers = serverService.getAllServers();
            return ResponseEntity.ok(servers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<ServerDTO> startServer(@PathVariable Long id) {
        try {
            ServerDTO server = serverService.startServer(id);
            return ResponseEntity.ok(server);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/stop")
    public ResponseEntity<ServerDTO> stopServer(@PathVariable Long id) {
        try {
            ServerDTO server = serverService.stopServer(id);
            return ResponseEntity.ok(server);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServer(@PathVariable Long id) {
        try {
            serverService.deleteServer(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Middle-Service REST est en cours d'execution");
    }
}
