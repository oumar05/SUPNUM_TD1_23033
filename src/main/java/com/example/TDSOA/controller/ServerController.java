package com.example.TDSOA.controller;

import com.example.TDSOA.model.Server;
import com.example.TDSOA.model.ServerStatus;
import com.example.TDSOA.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class ServerController {
    
    @Autowired
    private ServerService serverService;
    
    @PostMapping
    public ResponseEntity<?> createServer(@RequestBody Server server) {
        try {
            Server createdServer = serverService.createServer(server);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdServer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Server>> getAllServers() {
        List<Server> servers = serverService.getAllServers();
        return ResponseEntity.ok(servers);
    }
    
    @PutMapping("/{id}/rename")
    public ResponseEntity<?> renameServer(@PathVariable Long id, @RequestParam String newName) {
        try {
            Server server = serverService.renameServer(id, newName);
            return ResponseEntity.ok(server);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}/status")
    public ResponseEntity<?> getServerStatus(@PathVariable Long id) {
        try {
            ServerStatus status = serverService.getServerStatus(id);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/start")
    public ResponseEntity<?> startServer(@PathVariable Long id) {
        try {
            Server server = serverService.startServer(id);
            return ResponseEntity.ok(server);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/stop")
    public ResponseEntity<?> stopServer(@PathVariable Long id) {
        try {
            Server server = serverService.stopServer(id);
            return ResponseEntity.ok(server);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteServer(@PathVariable Long id) {
        try {
            serverService.deleteServer(id);
            return ResponseEntity.ok("Serveur supprimé avec succès");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }
}
