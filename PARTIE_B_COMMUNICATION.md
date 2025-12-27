# 📋 Contrat de Communication - Partie B

## 🏗️ Architecture des Services

```
┌─────────────────────────────────────────────────────────────────┐
│                    SERVICE CONSOMMATEUR                          │
│                      (Port 8082)                                 │
│  - Interface Web (Thymeleaf)                                     │
│  - API REST public                                               │
│  - Client REST du middle-service                                 │
└────────────────────────────┬────────────────────────────────────┘
                             │ HTTP REST (JSON)
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                    MIDDLE-SERVICE REST                           │
│                      (Port 8081)                                 │
│  - Expose API REST                                               │
│  - Consomme SOAP                                                 │
│  - Client SOAP                                                   │
└────────────────────────────┬────────────────────────────────────┘
                             │ SOAP (XML)
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                    SERVICE SOAP                                  │
│                      (Port 8080)                                 │
│  - Web Service SOAP                                              │
│  - Endpoint SOAP                                                 │
│  - Business Logic                                                │
└────────────────────────────┬────────────────────────────────────┘
                             │ JPA/Hibernate
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                    PostgreSQL Database                           │
│                      (Port 5432)                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 📡 Service Consommateur (Port 8082)

### Description
Service final qui consomme le middle-service REST. Fournit une interface web et une API REST.

### Endpoints REST

#### 1. Interface Web
- **URL:** `http://localhost:8082/`
- **Méthode:** GET
- **Description:** Page d'accueil avec liste des serveurs
- **Format de réponse:** HTML

#### 2. Créer un serveur (Web Form)
- **URL:** `http://localhost:8082/create`
- **Méthode:** POST
- **Paramètres:**
  - `name` (string)
  - `ipAddress` (string)
  - `status` (string: "TRUE" ou "FALSE")

#### 3. API REST public
- **URL:** `http://localhost:8082/api/servers`
- **Méthode:** GET
- **Format de réponse:** JSON
- **Exemple de réponse:**
```json
[
  {
    "id": 1,
    "name": "Serveur Web Apache",
    "ipAddress": "192.168.1.100",
    "status": "TRUE"
  }
]
```

#### 4. Health Check
- **URL:** `http://localhost:8082/health`
- **Méthode:** GET
- **Format de réponse:** TEXT
- **Exemple:** `Consumer Service is running! 🎉`

---

## 🔄 Middle-Service REST (Port 8081)

### Description
Service intermédiaire qui expose une API REST et consomme le service SOAP.

### Endpoints REST Exposés

#### Base URL: `http://localhost:8081/api/servers`

#### 1. Créer un serveur
- **Endpoint:** `POST /api/servers`
- **Méthode HTTP:** POST
- **Content-Type:** application/json
- **Body:**
```json
{
  "name": "Serveur Web Apache",
  "ipAddress": "192.168.1.100",
  "status": "FALSE"
}
```
- **Réponse (201 Created):**
```json
{
  "id": 1,
  "name": "Serveur Web Apache",
  "ipAddress": "192.168.1.100",
  "status": "FALSE"
}
```
- **Exemple curl:**
```bash
curl -X POST http://localhost:8081/api/servers \
  -H "Content-Type: application/json" \
  -d '{"name":"Web Server","ipAddress":"192.168.1.100","status":"FALSE"}'
```

#### 2. Lister tous les serveurs
- **Endpoint:** `GET /api/servers`
- **Méthode HTTP:** GET
- **Réponse (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Serveur Web Apache",
    "ipAddress": "192.168.1.100",
    "status": "TRUE"
  }
]
```
- **Exemple curl:**
```bash
curl http://localhost:8081/api/servers
```

#### 3. Démarrer un serveur
- **Endpoint:** `PUT /api/servers/{id}/start`
- **Méthode HTTP:** PUT
- **Paramètres:** `id` (path parameter)
- **Réponse (200 OK):**
```json
{
  "id": 1,
  "name": "Serveur Web Apache",
  "ipAddress": "192.168.1.100",
  "status": "TRUE"
}
```
- **Exemple curl:**
```bash
curl -X PUT http://localhost:8081/api/servers/1/start
```

#### 4. Arrêter un serveur
- **Endpoint:** `PUT /api/servers/{id}/stop`
- **Méthode HTTP:** PUT
- **Paramètres:** `id` (path parameter)
- **Réponse (200 OK):**
```json
{
  "id": 1,
  "name": "Serveur Web Apache",
  "ipAddress": "192.168.1.100",
  "status": "FALSE"
}
```
- **Exemple curl:**
```bash
curl -X PUT http://localhost:8081/api/servers/1/stop
```

#### 5. Supprimer un serveur
- **Endpoint:** `DELETE /api/servers/{id}`
- **Méthode HTTP:** DELETE
- **Paramètres:** `id` (path parameter)
- **Réponse:** 204 No Content
- **Exemple curl:**
```bash
curl -X DELETE http://localhost:8081/api/servers/1
```

#### 6. Health Check
- **Endpoint:** `GET /api/servers/health`
- **Méthode HTTP:** GET
- **Réponse:** `Middle-Service REST is running! 🚀`

### Documentation Swagger
- **URL:** `http://localhost:8081/swagger-ui.html`

---

## 🧼 Service SOAP (Port 8080)

### Description
Service backend SOAP qui gère la logique métier et la base de données.

### Endpoints SOAP

#### WSDL
- **URL:** `http://localhost:8080/ws/servers.wsdl`
- **Namespace:** `http://example.com/servers`

#### Format des requêtes
- **Content-Type:** `text/xml; charset=utf-8`
- **Protocol:** SOAP 1.1
- **Encoding:** UTF-8

#### Opérations disponibles
1. `createServerRequest`
2. `getAllServersRequest`
3. `startServerRequest`
4. `stopServerRequest`
5. `deleteServerRequest`

---

## 🔗 Contrats de Communication

### 1. Consumer → Middle-Service

**Protocol:** HTTP REST  
**Format:** JSON  
**Port:** 8081

| Opération | HTTP Method | Endpoint | Request Body | Response |
|-----------|-------------|----------|--------------|----------|
| Créer | POST | `/api/servers` | JSON | 201 + JSON |
| Lister | GET | `/api/servers` | - | 200 + JSON Array |
| Start | PUT | `/api/servers/{id}/start` | - | 200 + JSON |
| Stop | PUT | `/api/servers/{id}/stop` | - | 200 + JSON |
| Supprimer | DELETE | `/api/servers/{id}` | - | 204 |

### 2. Middle-Service → SOAP Service

**Protocol:** SOAP  
**Format:** XML  
**Port:** 8080

| Opération | SOAP Action | Request Element | Response Element |
|-----------|-------------|-----------------|------------------|
| Créer | - | `createServerRequest` | `createServerResponse` |
| Lister | - | `getAllServersRequest` | `getAllServersResponse` |
| Start | - | `startServerRequest` | `startServerResponse` |
| Stop | - | `stopServerRequest` | `stopServerResponse` |
| Supprimer | - | `deleteServerRequest` | `deleteServerResponse` |

---

## 📊 Formats de données

### JSON (REST)
```json
{
  "id": 1,
  "name": "string",
  "ipAddress": "xxx.xxx.xxx.xxx",
  "status": "TRUE|FALSE"
}
```

### XML (SOAP)
```xml
<ns2:server>
  <ns2:id>1</ns2:id>
  <ns2:name>string</ns2:name>
  <ns2:ipAddress>xxx.xxx.xxx.xxx</ns2:ipAddress>
  <ns2:status>TRUE|FALSE</ns2:status>
</ns2:server>
```

---

## 🚀 Démarrage de l'architecture complète

### Ordre de démarrage

```bash
# Terminal 1 - Service SOAP
cd /home/oumar/Bureau/TDSOA
mvn spring-boot:run

# Terminal 2 - Middle-Service REST
cd /home/oumar/Bureau/TDSOA/middle-service
mvn spring-boot:run

# Terminal 3 - Consumer Service
cd /home/oumar/Bureau/TDSOA/consumer-service
mvn spring-boot:run
```

### Vérification

1. **SOAP Service:** http://localhost:8080/ws/servers.wsdl
2. **Middle-Service:** http://localhost:8081/swagger-ui.html
3. **Consumer Service:** http://localhost:8082/
4. **Consumer Health:** http://localhost:8082/health

---

## 🧪 Tests de bout en bout

```bash
# Test 1: Créer via le consumer
curl -X POST http://localhost:8082/api/servers \
  -H "Content-Type: application/json" \
  -d '{"name":"TestServer","ipAddress":"192.168.1.50","status":"FALSE"}'

# Test 2: Lister via le middle-service
curl http://localhost:8081/api/servers

# Test 3: Vérifier dans le SOAP
# (Utiliser SoapUI ou les fichiers XML de test)

# Test 4: Interface web
# Ouvrir http://localhost:8082/ dans le navigateur
```

---

## 📝 Points importants

✅ **Le consumer ne connaît PAS le service SOAP**  
✅ **Le middle-service fait l'abstraction entre REST et SOAP**  
✅ **Trois services indépendants sur trois ports différents**  
✅ **Communication claire avec contrats définis**  
✅ **Format JSON pour REST, XML pour SOAP**

---

## 🎓 Résumé de la Partie B

| Élément | Implémenté | Description |
|---------|------------|-------------|
| Branche SOA_TO_REST | ✅ | Créée |
| Middle-Service REST | ✅ | Port 8081, consomme SOAP |
| Service Consommateur | ✅ | Port 8082, interface web + API |
| Contrats clairs | ✅ | Documentation complète |
| Tests | ✅ | Scripts curl + interface web |
| Repository Git | ✅ | Tout dans la même arborescence |
