package com.example.TDSOA.repository;

import com.example.TDSOA.model.Server;
import com.example.TDSOA.model.ServerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
    
    Optional<Server> findByName(String name);
    
    List<Server> findByStatus(ServerStatus status);
    
    boolean existsByName(String name);
    
    Optional<Server> findByIpAddress(String ipAddress);
}
