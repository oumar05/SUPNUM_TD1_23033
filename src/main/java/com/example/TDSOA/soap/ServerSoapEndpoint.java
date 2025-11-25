package com.example.TDSOA.soap;

import com.example.TDSOA.model.ServerStatus;
import com.example.TDSOA.service.ServerService;
import com.example.TDSOA.soap.generated.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class ServerSoapEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/servers";

    @Autowired
    private ServerService serverService;

    /**
     * Créer un serveur
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createServerRequest")
    @ResponsePayload
    public CreateServerResponse createServer(@RequestPayload CreateServerRequest request) {
        // Conversion SOAP -> JPA
        Server soapServer = request.getServer();
        com.example.TDSOA.model.Server jpaServer = new com.example.TDSOA.model.Server();
        jpaServer.setName(soapServer.getName());
        jpaServer.setIpAddress(soapServer.getIpAddress());
        jpaServer.setStatus(convertSoapToJpaStatus(soapServer.getStatus()));

        // Appel du service
        com.example.TDSOA.model.Server createdServer = serverService.createServer(jpaServer);

        // Conversion JPA -> SOAP
        CreateServerResponse response = new CreateServerResponse();
        Server responseServer = new Server();
        responseServer.setId(createdServer.getId());
        responseServer.setName(createdServer.getName());
        responseServer.setIpAddress(createdServer.getIpAddress());
        responseServer.setStatus(convertJpaToSoapStatus(createdServer.getStatus()));
        response.setServer(responseServer);

        return response;
    }

    /**
     * Lister tous les serveurs
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllServersRequest")
    @ResponsePayload
    public GetAllServersResponse getAllServers(@RequestPayload GetAllServersRequest request) {
        List<com.example.TDSOA.model.Server> jpaServers = serverService.getAllServers();

        GetAllServersResponse response = new GetAllServersResponse();
        
        for (com.example.TDSOA.model.Server jpaServer : jpaServers) {
            Server soapServer = new Server();
            soapServer.setId(jpaServer.getId());
            soapServer.setName(jpaServer.getName());
            soapServer.setIpAddress(jpaServer.getIpAddress());
            soapServer.setStatus(convertJpaToSoapStatus(jpaServer.getStatus()));
            response.getServers().add(soapServer);
        }

        return response;
    }

    /**
     * Démarrer un serveur
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "startServerRequest")
    @ResponsePayload
    public StartServerResponse startServer(@RequestPayload StartServerRequest request) {
        com.example.TDSOA.model.Server updatedServer = serverService.startServer(request.getId());

        StartServerResponse response = new StartServerResponse();
        Server soapServer = new Server();
        soapServer.setId(updatedServer.getId());
        soapServer.setName(updatedServer.getName());
        soapServer.setIpAddress(updatedServer.getIpAddress());
        soapServer.setStatus(convertJpaToSoapStatus(updatedServer.getStatus()));
        response.setServer(soapServer);

        return response;
    }

    /**
     * Arrêter un serveur
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "stopServerRequest")
    @ResponsePayload
    public StopServerResponse stopServer(@RequestPayload StopServerRequest request) {
        com.example.TDSOA.model.Server updatedServer = serverService.stopServer(request.getId());

        StopServerResponse response = new StopServerResponse();
        Server soapServer = new Server();
        soapServer.setId(updatedServer.getId());
        soapServer.setName(updatedServer.getName());
        soapServer.setIpAddress(updatedServer.getIpAddress());
        soapServer.setStatus(convertJpaToSoapStatus(updatedServer.getStatus()));
        response.setServer(soapServer);

        return response;
    }

    /**
     * Supprimer un serveur
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteServerRequest")
    @ResponsePayload
    public DeleteServerResponse deleteServer(@RequestPayload DeleteServerRequest request) {
        serverService.deleteServer(request.getId());

        DeleteServerResponse response = new DeleteServerResponse();
        response.setMessage("Serveur supprimé avec succès");
        return response;
    }

    // Méthodes utilitaires pour convertir les statuts
    private ServerStatus convertSoapToJpaStatus(com.example.TDSOA.soap.generated.ServerStatus soapStatus) {
        return soapStatus == com.example.TDSOA.soap.generated.ServerStatus.TRUE ? 
               ServerStatus.TRUE : ServerStatus.FALSE;
    }

    private com.example.TDSOA.soap.generated.ServerStatus convertJpaToSoapStatus(ServerStatus jpaStatus) {
        return jpaStatus == ServerStatus.TRUE ? 
               com.example.TDSOA.soap.generated.ServerStatus.TRUE : 
               com.example.TDSOA.soap.generated.ServerStatus.FALSE;
    }
}
