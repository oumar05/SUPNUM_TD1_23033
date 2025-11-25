//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v3.0.0 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2025.11.25 à 12:30:13 PM UTC 
//


package com.example.TDSOA.soap.generated;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.TDSOA.soap.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.TDSOA.soap.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateServerRequest }
     * 
     */
    public CreateServerRequest createCreateServerRequest() {
        return new CreateServerRequest();
    }

    /**
     * Create an instance of {@link Server }
     * 
     */
    public Server createServer() {
        return new Server();
    }

    /**
     * Create an instance of {@link CreateServerResponse }
     * 
     */
    public CreateServerResponse createCreateServerResponse() {
        return new CreateServerResponse();
    }

    /**
     * Create an instance of {@link GetAllServersRequest }
     * 
     */
    public GetAllServersRequest createGetAllServersRequest() {
        return new GetAllServersRequest();
    }

    /**
     * Create an instance of {@link GetAllServersResponse }
     * 
     */
    public GetAllServersResponse createGetAllServersResponse() {
        return new GetAllServersResponse();
    }

    /**
     * Create an instance of {@link StartServerRequest }
     * 
     */
    public StartServerRequest createStartServerRequest() {
        return new StartServerRequest();
    }

    /**
     * Create an instance of {@link StartServerResponse }
     * 
     */
    public StartServerResponse createStartServerResponse() {
        return new StartServerResponse();
    }

    /**
     * Create an instance of {@link StopServerRequest }
     * 
     */
    public StopServerRequest createStopServerRequest() {
        return new StopServerRequest();
    }

    /**
     * Create an instance of {@link StopServerResponse }
     * 
     */
    public StopServerResponse createStopServerResponse() {
        return new StopServerResponse();
    }

    /**
     * Create an instance of {@link DeleteServerRequest }
     * 
     */
    public DeleteServerRequest createDeleteServerRequest() {
        return new DeleteServerRequest();
    }

    /**
     * Create an instance of {@link DeleteServerResponse }
     * 
     */
    public DeleteServerResponse createDeleteServerResponse() {
        return new DeleteServerResponse();
    }

}
