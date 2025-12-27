package com.example.middleservice.dto;

public class ServerDTO {
    private Long id;
    private String name;
    private String ipAddress;
    private String status;

    public ServerDTO() {
    }

    public ServerDTO(Long id, String name, String ipAddress, String status) {
        this.id = id;
        this.name = name;
        this.ipAddress = ipAddress;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
