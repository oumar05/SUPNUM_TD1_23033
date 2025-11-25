package com.example.TDSOA.service;

import com.example.TDSOA.model.Server;
import com.example.TDSOA.model.ServerStatus;
import com.example.TDSOA.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {
    
    @Autowired
    private ServerRepository serverRepository;
    
    private Server findServerById(Long id) {
        return serverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serveur non trouvé avec l'ID : " + id));
    }
    
    /**
     * Créer un nouveau serveur
     */
    public Server createServer(Server server) {
        if (serverRepository.existsByName(server.getName())) {
            throw new RuntimeException("Un serveur avec ce nom existe déjà : " + server.getName());
        }
        return serverRepository.save(server);
    }
    
    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }
    
    public Server renameServer(Long id, String newName) {
        Server server = findServerById(id);
        if (serverRepository.existsByName(newName)) {
            throw new RuntimeException("Un serveur avec ce nom existe déjà : " + newName);
        }
        server.setName(newName);
        return serverRepository.save(server);
    }
    
    public ServerStatus getServerStatus(Long id) {
        return findServerById(id).getStatus();
    }
    
    public Server startServer(Long id) {
        Server server = findServerById(id);
        server.setStatus(ServerStatus.TRUE);
        return serverRepository.save(server);
    }
    
    public Server stopServer(Long id) {
        Server server = findServerById(id);
        server.setStatus(ServerStatus.FALSE);
        return serverRepository.save(server);
    }
    
    public void deleteServer(Long id) {
        Server server = findServerById(id);
        if (server.getStatus() == ServerStatus.TRUE) {
            throw new RuntimeException("Impossible de supprimer un serveur en cours d'exécution.");
        }
        serverRepository.delete(server);
    }
}
